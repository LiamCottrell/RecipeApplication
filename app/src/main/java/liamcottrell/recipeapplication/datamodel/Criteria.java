package liamcottrell.recipeapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Criteria {

    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("requirePictures")
    @Expose
    private Boolean requirePictures;
    @SerializedName("allowedIngredient")
    @Expose
    private Object allowedIngredient;
    @SerializedName("excludedIngredient")
    @Expose
    private Object excludedIngredient;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Boolean getRequirePictures() {
        return requirePictures;
    }

    public void setRequirePictures(Boolean requirePictures) {
        this.requirePictures = requirePictures;
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