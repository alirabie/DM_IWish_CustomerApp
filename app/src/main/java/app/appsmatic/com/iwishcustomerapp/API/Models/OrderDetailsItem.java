package app.appsmatic.com.iwishcustomerapp.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mido PC on 3/20/2017.
 */
public class OrderDetailsItem {

    @SerializedName("MealItemID")
    @Expose
    private Integer mealItemID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("additions")
    @Expose
    private List<OrderDetailsAddition> additions = null;
    @SerializedName("customization")
    @Expose
    private List<OrderDetailsCustomization> customization = null;
    @SerializedName("TotalPrice")
    @Expose
    private Double totalPrice;

    public Integer getMealItemID() {
        return mealItemID;
    }

    public void setMealItemID(Integer mealItemID) {
        this.mealItemID = mealItemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<OrderDetailsAddition> getAdditions() {
        return additions;
    }

    public void setAdditions(List<OrderDetailsAddition> additions) {
        this.additions = additions;
    }

    public List<OrderDetailsCustomization> getCustomization() {
        return customization;
    }

    public void setCustomization(List<OrderDetailsCustomization> customization) {
        this.customization = customization;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
