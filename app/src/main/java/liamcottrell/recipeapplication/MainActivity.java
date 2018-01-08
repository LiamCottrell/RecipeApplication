package liamcottrell.recipeapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import liamcottrell.recipeapplication.SQLite.DatabaseHelper;
import liamcottrell.recipeapplication.datamodel.Preferences.*;
import liamcottrell.recipeapplication.datamodel.Preferences.Preferences;
import nl.qbusict.cupboard.QueryResultIterable;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;

    static SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent feedIntent = new Intent(MainActivity.this, Feed.class);
                startActivity(feedIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        DatabaseHelper dbHelper = new DatabaseHelper(newBase);
        db = dbHelper.getWritableDatabase();

        liamcottrell.recipeapplication.datamodel.Preferences.Preferences preferences;
        preferences = cupboard().withDatabase(db).get(liamcottrell.recipeapplication.datamodel.Preferences.Preferences.class, 1L);

        if (preferences == null) {
            Log.i("Preferences", "No Rows in database, creating new row");
            Preferences settings = new Preferences(Boolean.FALSE);
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
}
