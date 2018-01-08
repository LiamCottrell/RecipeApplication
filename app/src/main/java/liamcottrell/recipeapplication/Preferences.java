package liamcottrell.recipeapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

import liamcottrell.recipeapplication.SQLite.DatabaseHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static liamcottrell.recipeapplication.MainActivity.db;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class Preferences extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

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
        setContentView(R.layout.activity_preferenes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        liamcottrell.recipeapplication.datamodel.Preferences.Preferences preferences;
        preferences = cupboard().withDatabase(db).get(liamcottrell.recipeapplication.datamodel.Preferences.Preferences.class, 1L);

        CheckBox openDyslexia = findViewById(R.id.openDyslexia);


        openDyslexia.setChecked(preferences.openDyslexic);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        /*AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();*/
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
            in = new Intent(this, Preferences.class);
            startActivity(in);

        } else if (id == R.id.nav_git) {
            in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/LiamCottrell/RecipeApplication/"));
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        liamcottrell.recipeapplication.datamodel.Preferences.Preferences preferences;
        preferences = cupboard().withDatabase(db).get(liamcottrell.recipeapplication.datamodel.Preferences.Preferences.class, 1L);
        if (checked) {
            Log.i("checked","true");
            preferences.openDyslexic = true;
            cupboard().withDatabase(db).put(preferences);
        } else {
            preferences.openDyslexic = false;
            cupboard().withDatabase(db).put(preferences);
        }

        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}