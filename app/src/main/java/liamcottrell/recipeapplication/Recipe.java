package liamcottrell.recipeapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.concurrent.ExecutionException;

import liamcottrell.recipeapplication.SQLite.DatabaseHelper;
import liamcottrell.recipeapplication.datamodel.Matches.Matches;
import liamcottrell.recipeapplication.datamodel.singleRecipe.SingleRecipe;
import okhttp3.HttpUrl;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static liamcottrell.recipeapplication.MainActivity.db;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class Recipe extends AppCompatActivity {

    private SingleRecipe singleRecipe;

    @Override
    protected void attachBaseContext(Context newBase) {
        DatabaseHelper dbHelper = new DatabaseHelper(newBase);
        db = dbHelper.getWritableDatabase();

        liamcottrell.recipeapplication.datamodel.Preferences.Preferences preferences;
        preferences = cupboard().withDatabase(db).get(liamcottrell.recipeapplication.datamodel.Preferences.Preferences.class, 1L);

        if (preferences == null) {
            Log.i("Preferences", "No Rows in database, creating new row");
            liamcottrell.recipeapplication.datamodel.Preferences.Preferences settings = new liamcottrell.recipeapplication.datamodel.Preferences.Preferences(Boolean.FALSE);
            long id = cupboard().withDatabase(db).put(settings);
            preferences = cupboard().withDatabase(db).get(liamcottrell.recipeapplication.datamodel.Preferences.Preferences.class, 1L);
        }

        if (preferences.openDyslexic) {
            Log.i("Preferences", "Preferences found, setting accessibility font to " + preferences.openDyslexic);
            super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        TextView numberofServings = findViewById(R.id.numberofServings);
        TextView totalTime = findViewById(R.id.totalTime);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView ingredientsText = findViewById(R.id.ingredientsText);


        Intent myIntent = getIntent(); // gets the previously created intent
        String recipeID = myIntent.getStringExtra("recipeID"); // will return "FirstKeyValue"


        Log.i("id", recipeID);

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.yummly.com/v1/api/recipe/").newBuilder();
        urlBuilder.addPathSegment(recipeID);
        final String requestURL = urlBuilder.build().toString();

        JSONSingleRecipe getLatest = new JSONSingleRecipe();

        final SingleRecipe recipe = getLatest.JSONSingleRecipe(requestURL);

        toolbar.setTitle(recipe.getName().toString());
        toolbarLayout.setTitle(recipe.getName().toString());


        final ImageView img = new ImageView(this);
        Picasso.with(img.getContext())
                .load(recipe.getImages().get(0).getHostedLargeUrl().toString())
                .into(img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        toolbarLayout.setBackgroundDrawable(img.getDrawable());
                    }

                    @Override
                    public void onError() {
                    }
                });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Link URL", recipe.getSource().getSourceRecipeUrl());
                Intent attributeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getSource().getSourceRecipeUrl()));
                startActivity(attributeIntent);
            }
        });


        TextView Attribute = (TextView) findViewById(R.id.attributeText);
        String attributeTextContext = recipe.getAttribution().getText();
        Attribute.setText(attributeTextContext);

        //Initialize ImageView
        ImageView attributeImage = (ImageView) findViewById(R.id.attributeImage);

        //Loading image from below url into imageView

        Picasso.with(this)
                .load(recipe.getAttribution().getLogo())
                .into(attributeImage);
        for (String ingredients : recipe.getIngredientLines()) {
            ingredientsText.setText(ingredientsText.getText() +"\u2022 " + ingredients + "\n");
        }

        Log.i("Rating", String.valueOf(recipe.getRating()));

        ratingBar.setRating(recipe.getRating());

        numberofServings.setText(recipe.getNumberOfServings().toString());
        totalTime.setText(recipe.getTotalTime());

    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
