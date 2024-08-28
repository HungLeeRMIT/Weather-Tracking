package app.dataQuery;

public class memberClass {
    private String sID;
    private String name;
    private String image;
    private String facebook;
    private String instagram;
    private String twitter;
    private String role;

    public memberClass() {
    }

    public memberClass(String sID, String name, String image, String facebook, String instagram, String twitter, String role) {
        this.sID = sID;
        this.name = name;
        this.image = image;
        this.facebook = facebook;
        this.instagram = instagram;
        this.twitter = twitter;
        this.role = role;
    }

    public String getsID() {
        return sID;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getÍnstagram() {
        return instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getRole() {
        return role;
    }
}

