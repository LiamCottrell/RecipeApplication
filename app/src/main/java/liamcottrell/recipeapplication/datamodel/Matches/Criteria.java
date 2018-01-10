package liamcottrell.recipeapplication.datamodel.Matches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Criteria {

@SerializedName("requirePictures")
@Expose
private Boolean requirePictures;
@SerializedName("q")
@Expose
private Object q;
@SerializedName("allowedIngredient")
@Expose
private Object allowedIngredient;
@SerializedName("excludedIngredient")
@Expose
private Object excludedIngredient;

public Boolean getRequirePictures() {
return requirePictures;
}

public void setRequirePictures(Boolean requirePictures) {
this.requirePictures = requirePictures;
}

public Object getQ() {
return q;
}

public void setQ(Object q) {
this.q = q;
}

public Object getAllowedIngredient() {
return allowedIngredient;
}

public void setAllowedIngredient(Object allowedIngredient) {
this.allowedIngredient = allowedIngredient;
}

public Object getExcludedIngredient() {
return excludedIngredient;
}

public void setExcludedIngredient(Object excludedIngredient) {
this.excludedIngredient = excludedIngredient;
}

}