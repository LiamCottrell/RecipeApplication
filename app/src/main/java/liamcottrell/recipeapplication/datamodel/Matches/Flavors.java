package liamcottrell.recipeapplication.datamodel.Matches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flavors {

@SerializedName("bitter")
@Expose
private Double bitter;
@SerializedName("meaty")
@Expose
private Double meaty;
@SerializedName("piquant")
@Expose
private Integer piquant;
@SerializedName("salty")
@Expose
private Double salty;
@SerializedName("sour")
@Expose
private Double sour;
@SerializedName("sweet")
@Expose
private Double sweet;

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

public Double getSweet() {
return sweet;
}

public void setSweet(Double sweet) {
this.sweet = sweet;
}

}