package liamcottrell.recipeapplication.datamodel.Preferences;

import static java.lang.Boolean.FALSE;

/**
 * Created by LiamC on 07/01/2018.
 */

public class Preferences {
    public Long _id;
    public Boolean openDyslexic;

    public Preferences(){
        this.openDyslexic = FALSE;
    }

    public Preferences(Boolean value){
        this.openDyslexic = value;
    }
}
