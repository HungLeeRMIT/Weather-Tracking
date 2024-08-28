package app.dataQuery;

public class personaClass {
    private String name;
    private String image;
    private String quote;
    private String age;
    private String role;
    private String otherattribute1;
    private String otherattribute2;
    private String need;
    private String goal;
    private String skill;
    private String experience;

    public personaClass() {
    }

    public personaClass(String name, String image, String quote, String age, String role, String otherattribute1, String otherattribute2, String need, String goal, String skill, String experience) {
        this.name = name;
        this.image = image;
        this.quote = quote;
        this.age = age;
        this.role = role;
        this.otherattribute1 = otherattribute1;
        this.otherattribute2 = otherattribute2;
        this.need = need;
        this.goal = goal;
        this.skill = skill;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getQuote() {
        return quote;
    }

    public String getAge() {
        return age;
    }

    public String getRole() {
        return role;
    }

    public String getOtherattribute1() {
        return otherattribute1;
    }

    public String getOtherattribute2() {
        return otherattribute2;
    }

    public String getNeed() {
        return need;
    }

    public String getGoal() {
        return goal;
    }

    public String getSkill() {
        return skill;
    }

    public String getExperience() {
        return experience;
    }
    
}
