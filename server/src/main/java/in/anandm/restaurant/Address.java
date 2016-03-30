package in.anandm.restaurant;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Property;

public class Address {

    private String building;

    private String street;

    @Property(value = "zipcode")
    private String zipCode;

    private List<Double> coord = new ArrayList<Double>();

    /**
     * 
     */
    public Address() {
        super();

    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<Double> getCoord() {
        return coord;
    }

    public void setCoord(List<Double> coord) {
        this.coord = coord;
    }

}
