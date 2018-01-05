package liamcottrell.recipeapplication.datamodel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attributes {

    @SerializedName("course")
    @Expose
    private List<String> course = null;
    @SerializedName("holiday")
    @Expose
    private List<String> holiday = null;
    @SerializedName("cuisine")
    @Expose
    private List<String> cuisine = null;

    public List<String> getCourse() {
        return course;
    }

    public void setCourse(List<String> course) {
        this.course = course;
    }

    public List<String> getHoliday() {
        return holiday;
    }

    public void setHoliday(List<String> holiday) {
        this.holiday = holiday;
    }

    public List<String> getCuisine() {
        return cuisine;
    }

    public void setCuisine(List<String> cuisine) {
        this.cuisine = cuisine;
    }

}