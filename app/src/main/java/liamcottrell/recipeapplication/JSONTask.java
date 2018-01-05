package liamcottrell.recipeapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import liamcottrell.recipeapplication.datamodel.Recipe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONTask extends AsyncTask<String, String, Recipe> {

    Gson gson = new GsonBuilder().create();
    String jsonResult;

    @Override
    protected Recipe doInBackground(String... params) {

        Response response = null;


        try {
            URL url = new URL(params[0]);
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("X-Yummly-App-ID", "b907b54e")
                    .addHeader("X-Yummly-App-Key", "a681d0265a9363b071ff7a9811fb8c49")
                    .build();

            response = client.newCall(request).execute();

            jsonResult = response.body().string();

/*            //If server is down
            try(Reader reader = new InputStreamReader(JSONTask.class.getResourceAsStream("C:\\Users\\LiamC\\AndroidStudioProjects\\RecipeApplication\\app\\latest.json"), "UTF-8")){
                jsonResult = reader.toString();
            }
            //done*/

/*            Log.i("Http Response message", jsonResult);*/

            Recipe Latest = gson.fromJson(jsonResult, Recipe.class);
/*
            Log.i("Yummly Matches", String.valueOf(Latest.getMatches()));*/

            return Latest;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Recipe s) {
        super.onPostExecute(s);
    }
}
