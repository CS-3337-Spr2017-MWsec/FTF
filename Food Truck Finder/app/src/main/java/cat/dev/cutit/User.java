package cat.dev.cutit;

class User {

    private String firstName, lastName, email;
    private boolean vendor;

    User() {}

    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    boolean isVendor() {
        return vendor;
    }

//    void setVendor(boolean vendor) {
//        this.vendor = vendor;
//    }
}
