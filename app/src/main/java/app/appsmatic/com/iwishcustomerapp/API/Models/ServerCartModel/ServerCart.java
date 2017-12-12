package app.appsmatic.com.iwishcustomerapp.API.Models.ServerCartModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.appsmatic.com.iwishcustomerapp.CartStructure.CartMeal;

/**
 * Created by Mido PC on 2/6/2017.
 */
public class ServerCart {

    @SerializedName("order")
    @Expose
    private List<CartMeal> order = null;
    @SerializedName("cartid")
    @Expose
    private String cartid;

    public List<CartMeal> getOrder() {
        return order;
    }

    public void setOrder(List<CartMeal> order) {
        this.order = order;
    }

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }
}
