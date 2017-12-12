package app.appsmatic.com.iwishcustomerapp.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mido PC on 2/23/2017.
 */
public class ResNewLocation {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private MyLocation message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public MyLocation getMessage() {
        return message;
    }

    public void setMessage(MyLocation message) {
        this.message = message;
    }
}
