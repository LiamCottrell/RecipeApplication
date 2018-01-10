package liamcottrell.recipeapplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutionException;

import liamcottrell.recipeapplication.datamodel.singleRecipe.SingleRecipe;

/**
 * Created by LiamC on 06/01/2018.
 */

public class JSONSingleRecipe {

    protected SingleRecipe JSONSingleRecipe(String requestURL) {
        SingleRecipe Recipe = new SingleRecipe();
        Gson gson = new GsonBuilder().create();
        String returnedJson = null;

        JSONTask Json = new JSONTask();
        Json.execute(requestURL);

        try {
            returnedJson = Json.get();
            Recipe = gson.fromJson(returnedJson, SingleRecipe.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return Recipe;
    }
}
