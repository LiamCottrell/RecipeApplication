package liamcottrell.recipeapplication;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutionException;

import liamcottrell.recipeapplication.datamodel.Matches.Match;
import liamcottrell.recipeapplication.datamodel.Matches.Matches;

/**
 * Created by LiamC on 06/01/2018.
 */

public class JSONMatches {


    protected Matches JSONMatches(String requestURL) {
        Matches Latest = new Matches();
        Gson gson = new GsonBuilder().create();
        String returnedJson = null;

        JSONTask Json = new JSONTask();
        Json.execute(requestURL);

        try {
            returnedJson = Json.get();
            Latest = gson.fromJson(returnedJson, Matches.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return Latest;
    }
}
