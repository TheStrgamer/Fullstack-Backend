package no.ntnu.idatt2105.marketplace.model.user;

import jakarta.persistence.*;
import no.ntnu.idatt2105.marketplace.model.other.Images;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String firstname;

  @Column(nullable = false)
  private String surname;

  @Column(unique = true, nullable = false)
  private String phonenumber;

  @OneToOne
  @JoinColumn(name = "images_id", unique = true)
  private Images profile_picture;


  // Constructor

  public User() {}

  public User(String email, String password, String firstname, String surname, String phonenumber, Images profile_picture) {
    this.email = email;
    this.password = password;
    this.firstname = firstname;
    this.surname = surname;
    this.phonenumber = phonenumber;
    this.profile_picture = profile_picture;
  }

  // Getters and Setters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getPhonenumber() {
    return phonenumber;
  }

  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }

  public Images getProfile_picture() {
    return profile_picture;
  }

  public void setProfile_picture(Images profile_picture) {
    this.profile_picture = profile_picture;
  }
}
