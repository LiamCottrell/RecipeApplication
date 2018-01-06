package liamcottrell.recipeapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.concurrent.ExecutionException;

import liamcottrell.recipeapplication.datamodel.Matches.Matches;
import liamcottrell.recipeapplication.datamodel.singleRecipe.SingleRecipe;
import okhttp3.HttpUrl;

public class Recipe extends AppCompatActivity {

    private SingleRecipe singleRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        Intent myIntent = getIntent(); // gets the previously created intent
        String recipeID = myIntent.getStringExtra("recipeID"); // will return "FirstKeyValue"



        Log.i("id", recipeID);

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.yummly.com/v1/api/recipe/").newBuilder();
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
