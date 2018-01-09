package liamcottrell.recipeapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

import liamcottrell.recipeapplication.SQLite.DatabaseHelper;
import liamcottrell.recipeapplication.datamodel.Matches.Flavors;
import liamcottrell.recipeapplication.datamodel.Matches.Match;
import liamcottrell.recipeapplication.datamodel.Matches.Matches;
import okhttp3.HttpUrl;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static liamcottrell.recipeapplication.MainActivity.db;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class Feed extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

        private Matches LatestRecipes;

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
            setContentView(R.layout.activity_feed);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);


            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.yummly.com/v1/api/recipes").newBuilder();
            urlBuilder.addQueryParameter("requirePictures","true");
            final String requestURL = urlBuilder.build().toString();

            Log.i("Request URL", requestURL.toString());

            JSONMatches getLatest = new JSONMatches();

            final Matches LatestRecipes = getLatest.JSONMatches(requestURL);



            Log.i("Eg", String.valueOf(LatestRecipes.getMatches().get(1).getRecipeName()));



/*            Log.i("URL", String.valueOf(LatestRecipes.getMatches().get(0).getSmallImageUrls().get(0)));*/







            LayoutInflater li = getLayoutInflater();

            LinearLayout linearLayout = findViewById(R.id.match_content);
            final ViewGroup attribute = (ViewGroup) li.inflate(R.layout.match_attribute,null);
            attribute.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent attributeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(LatestRecipes.getAttribution().getUrl()));
                    startActivity(attributeIntent);
                }
            });
            linearLayout.addView(attribute);

            TextView Attribute = (TextView) findViewById(R.id.attributeText);
            String attributeTextContext = LatestRecipes.getAttribution().getText();
            Attribute.setText(attributeTextContext);

            //Initialize ImageView
            ImageView attributeImage = (ImageView) findViewById(R.id.attributeImage);

            //Loading image from below url into imageView

            Picasso.with(this)
                    .load(LatestRecipes.getAttribution().getLogo())
                    .into(attributeImage);


            for( final Match match : LatestRecipes.getMatches() ) {
                ViewGroup myView = (ViewGroup) li.from(this).inflate(R.layout.match_widget,null);

                TextView matchTitle = myView.findViewById(R.id.matchTitle);
                TextView totalTime = myView.findViewById(R.id.TotalTimeText);
                TextView userRating = myView.findViewById(R.id.matchRating);
                TextView source = myView.findViewById(R.id.MatchSource);

                TextView spiceContainer = myView.findViewById(R.id.MatchPiquantDV);
                TextView bitterContainer = myView.findViewById(R.id.MatchBitterDV);
                TextView sweetContainer = myView.findViewById(R.id.MatchSweetDV);
                TextView savoryContainer = myView.findViewById(R.id.MatchMeatyDV);
                TextView saltyContainer = myView.findViewById(R.id.MatchSaltyDV);
                TextView sourContainer = myView.findViewById(R.id.MatchSourDV);

                ImageView imageView = (ImageView) myView.findViewById(R.id.matchImage);




                Picasso.with(this)
                        .load(match.getSmallImageUrls().get(0))
                        .into(imageView);


                matchTitle.setText(match.getRecipeName());
                totalTime.setText(String.format("%d hr(s) %02d min(s)", match.getTotalTimeInSeconds() / 3600, (match.getTotalTimeInSeconds() % 3600) / 60));
                userRating.setText(match.getRating().toString());
                source.setText(match.getSourceDisplayName());

                try{
                    Flavors flavors = match.getFlavors();

                    spiceContainer.setText( flavourToPercent(Double.valueOf(flavors.getPiquant())).toString() + "%" );
                    bitterContainer.setText( flavourToPercent(Double.valueOf(flavors.getBitter())).toString() + "%");
                    sweetContainer.setText( flavourToPercent(Double.valueOf(flavors.getSweet())).toString() + "%" );
                    savoryContainer.setText( flavourToPercent(Double.valueOf(flavors.getMeaty())).toString() + "%" );
                    saltyContainer.setText( flavourToPercent(Double.valueOf(flavors.getSalty())).toString() + "%" );
                    sourContainer.setText( flavourToPercent(Double.valueOf(flavors.getSour())).toString() + "%" );



                } catch (Exception e){
                    //Laugh
                }

                myView.setOnClickListener( new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent recipeIntent = new Intent(Feed.this, Recipe.class);
                        recipeIntent.putExtra("recipeID", match.getId());
                        startActivity(recipeIntent);
                    }
                });

                linearLayout.addView(myView);


            }

        }

        public Double flavourToPercent(Double flavour){
            Double result = Math.round(flavour * 100.0) / 100.0;
            result = result * 100;
            return result;
        }

        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }


        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            Intent in;

            if (id == R.id.nav_google_signin) {

            } else if (id == R.id.nav_preferences) {
                in = new Intent (this, Preferences.class);
                startActivity (in);

            } else if (id == R.id.nav_git) {
                in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/LiamCottrell/RecipeApplication/"));
                startActivity(in);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        public void generateDecoView(float flavor, DecoView container){
            container.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                    .setRange(0, 1, 1)
                    .setInitialVisibility(false)
                    .setLineWidth(6f)
                    .build());


            SeriesItem item = new SeriesItem.Builder(Color.argb(255, 48, 0, 96))
                    .setRange(0, 1, flavor)
                    .setLineWidth(6f)
                    .build();

            container.addSeries(item);

        }

    }



