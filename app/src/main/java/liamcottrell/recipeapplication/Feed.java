package liamcottrell.recipeapplication;

import android.content.Intent;
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

import liamcottrell.recipeapplication.datamodel.Matches.Flavors;
import liamcottrell.recipeapplication.datamodel.Matches.Match;
import liamcottrell.recipeapplication.datamodel.Matches.Matches;
import okhttp3.HttpUrl;

public class Feed extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

        private Matches LatestRecipes;


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

                DecoView spiceContainer = myView.findViewById(R.id.MatchPiquantDV);
                DecoView bitterContainer = myView.findViewById(R.id.MatchBitterDV);
                DecoView sweetContainer = myView.findViewById(R.id.MatchSweetDV);
                DecoView savoryContainer = myView.findViewById(R.id.MatchMeatyDV);
                DecoView saltyContainer = myView.findViewById(R.id.MatchSaltyDV);
                DecoView sourContainer = myView.findViewById(R.id.MatchSourDV);

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

                    generateDecoView(flavors.getPiquant().floatValue(), spiceContainer);
                    generateDecoView(flavors.getBitter().floatValue(), bitterContainer);
                    generateDecoView(flavors.getSweet().floatValue(), sweetContainer);
                    generateDecoView(flavors.getMeaty().floatValue(), savoryContainer);
                    generateDecoView(flavors.getSalty().floatValue(), saltyContainer);
                    generateDecoView(flavors.getSour().floatValue(), sourContainer);


                } catch (Exception e){

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



