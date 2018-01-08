package liamcottrell.recipeapplication;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by LiamC on 08/01/2018.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("opendyslexic.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
