package model;

public class Person {
    private String firstname;
    private String lastname;
    private String email;
    private int id_person;
    public Person(){
    }
    public Person(String firstname, String lastname, String email, int id_person) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.id_person = id_person;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }
}
