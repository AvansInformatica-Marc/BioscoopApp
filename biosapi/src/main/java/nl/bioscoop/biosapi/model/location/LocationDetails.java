package nl.bioscoop.biosapi.model.location;

import java.io.Serializable;

public class LocationDetails extends Location implements Serializable {

    public LocationDetails(String locationName, String locationAddress, String locationContactInfo) {
        super(locationName, locationAddress, locationContactInfo);
    }
}
