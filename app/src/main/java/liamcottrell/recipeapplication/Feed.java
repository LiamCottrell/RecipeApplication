package liamcottrell.recipeapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.net.URL;
import java.util.concurrent.ExecutionException;

import liamcottrell.recipeapplication.datamodel.Recipe;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Feed extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

        private Recipe LatestRecipes;


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

            TextView Attribute = (TextView) findViewById(R.id.attribute);


            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.yummly.com/v1/api/recipes").newBuilder();
            urlBuilder.addQueryParameter("requirePictures","true");
            final String requestURL = urlBuilder.build().toString();

            JSONTask Json = new JSONTask();
            Json.execute(requestURL);
            try {
                LatestRecipes = Json.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            Log.i("Eg", String.valueOf(LatestRecipes.getMatches().get(1).getRecipeName()));
            String attributeText = LatestRecipes.getAttribution().getText();
            Log.i("Attribute", attributeText);

            Attribute.setText(attributeText);


            Log.i("URL", String.valueOf(LatestRecipes.getMatches().get(0).getSmallImageUrls().get(0)));


            //Initialize ImageView
            ImageView imageView = (ImageView) findViewById(R.id.test);

            //Loading image from below url into imageView

            Picasso.with(this)
                    .load(LatestRecipes.getMatches().get(0).getSmallImageUrls().get(0))
                    .placeholder(R.drawable.ic_feedme_icon)
                    .resize(360, 240)
                    .into(imageView);

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

    }



