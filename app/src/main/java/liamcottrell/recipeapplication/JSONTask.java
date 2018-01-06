package liamcottrell.recipeapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;

import liamcottrell.recipeapplication.datamodel.Matches.Matches;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONTask extends AsyncTask<String, String, String> {

    String jsonResult;

    @Override
    protected String doInBackground(String... params) {

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

            return jsonResult;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
