package liamcottrell.recipeapplication.datamodel.singleRecipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flavors {

@SerializedName("Bitter")
@Expose
private Double bitter;
@SerializedName("Meaty")
@Expose
private Double meaty;
@SerializedName("Piquant")
@Expose
private Integer piquant;
@SerializedName("Salty")
@Expose
private Double salty;
@SerializedName("Sour")
@Expose
private Double sour;
@SerializedName("Sweet")
@Expose
private Integer sweet;

public Double getBitter() {
return bitter;
}

public void setBitter(Double bitter) {
this.bitter = bitter;
}

public Double getMeaty() {
return meaty;
}

public void setMeaty(Double meaty) {
this.meaty = meaty;
}

public Integer getPiquant() {
return piquant;
}

public void setPiquant(Integer piquant) {
this.piquant = piquant;
}

public Double getSalty() {
return salty;
}

public void setSalty(Double salty) {
this.salty = salty;
}

public Double getSour() {
return sour;
}

public void setSour(Double sour) {
this.sour = sour;
}

public Integer getSweet() {
return sweet;
}

public void setSweet(Integer sweet) {
this.sweet = sweet;
}

}