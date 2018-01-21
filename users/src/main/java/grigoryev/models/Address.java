package grigoryev.models;

/**
 * Address model in the database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 01.12.2017
 */
public class Address extends Model {

    private static final long serialVersionUID = -6638238497007609085L;
    /**
     * Street's name.
     */
    private String streetName;
    /**
     * Number of house.
     */
    private String houseNo;
    /**
     * Number of apartment.
     */
    private String apartmentNo;

    /**
     * Constructor.
     */
    public Address() {
    }

    /**
     * Constructor.
     * @param id Id of address in the database.
     */
    public Address(Integer id) {
        super(id);
    }

    /**
     * Gets street name.
     * @return street name
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets street name.
     * @param streetName street name
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Gets house number.
     * @return house number
     */
    public String getHouseNo() {
        return houseNo;
    }

    /**
     * Sets house number.
     * @param houseNo house number
     */
    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    /**
     * Gets apartment number.
     * @return apartment number
     */
    public String getApartmentNo() {
        return apartmentNo;
    }

    /**
     * Sets apartment number.
     * @param apartmentNo apartment number
     */
    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }
}
