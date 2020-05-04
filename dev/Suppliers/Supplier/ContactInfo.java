package Suppliers.Supplier;

/**
 * Data class
 */
public class ContactInfo {
    private String phoneNumber;
    private String email;
    private String name;

    public ContactInfo(String name, String phoneNumber, String email){
        this.email=email;
        this.name=name;
        this.phoneNumber=phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
