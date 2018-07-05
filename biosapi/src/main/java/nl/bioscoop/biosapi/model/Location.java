package nl.bioscoop.biosapi.model;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Location implements Serializable {
    private @NonNull String city;
    private @NonNull String street;
    private @NonNull String postalCode;
    private @NonNull String number;

    public Location(@NonNull JSONObject json) throws JSONException {
        this(json.getString("city"), json.getString("street"), json.getString("postalcode"), json.getString("number"));
    }

    public Location(@NonNull String city, @NonNull String street, @NonNull String postalCode, @NonNull String number) {
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.number = number;
    }

    @NonNull public String getCity() {
        return city;
    }

    @NonNull public String getStreet() {
        return street;
    }

    @NonNull public String getPostalCode() {
        return postalCode;
    }

    @NonNull public String getNumber() {
        return number;
    }

    @NonNull public String toShortString() {
        return street + ", " + city;
    }

    @NonNull public String toLongString(boolean multiline) {
        return street + " " + number + "," + (multiline ? "\n" : " ") + postalCode + " " + city;
    }
}
