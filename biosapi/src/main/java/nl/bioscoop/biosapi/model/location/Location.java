package nl.bioscoop.biosapi.model.location;

public class Location {
    private String locationName;
    private String locationAddress;
    private String locationContactInfo;

    public Location(String locationName, String locationAddress, String locationContactInfo) {
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.locationContactInfo = locationContactInfo;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationContactInfo() {
        return locationContactInfo;
    }

    public void setLocationContactInfo(String locationContactInfo) {
        this.locationContactInfo = locationContactInfo;
    }
}
