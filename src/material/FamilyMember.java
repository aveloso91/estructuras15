package material;

/**
 * Created by Alejandro on 15/6/16.
 */
public class FamilyMember {
    private String id;
    private String name;
    private String surname;
    private boolean gender; //MALE = TRUE; FEMALE = FALSE
    private int age;

    public FamilyMember(String id, String name, String surname, boolean gender, int age) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public FamilyMember() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}