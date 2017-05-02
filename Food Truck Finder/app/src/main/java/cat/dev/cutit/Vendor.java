package cat.dev.cutit;

class Vendor extends User {
    private boolean active;
    private BusinessHours businessHours;
    private int rating;
    private String businessName;
    private String description;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BusinessHours getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(BusinessHours businessHours) {
        this.businessHours = businessHours;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    static class BusinessHours {

    }
}
