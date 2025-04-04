package no.ntnu.idatt2105.marketplace.model.user;

import jakarta.persistence.*;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.other.Images;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt2105.marketplace.model.other.Offer;

@Entity
@Table(name = "users")
public class User {

  private static final long userHistoryMaxSize = 10;

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

  @ManyToMany
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  // private List<Role> roles;
  private List<Role> roles = new ArrayList<>(); //nødvendig for å unngå nullpointer i forbindelse med testdata

  @ManyToMany
  @JoinTable(
      name = "user_favorites",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "listing_id")
  )
  //private List<Listing> favorites;
  private List<Listing> favorites = new ArrayList<>(); //for å unngå nullpointer

  @ManyToMany
  @JoinTable(
      name = "user_history",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "listing_id")
  )
  //private List<Listing> history;
  private List<Listing> history = new ArrayList<>(); //for å unngå nullpointer

  @OneToMany(mappedBy = "buyer")
  //private List<Offer> offers;
  private List<Offer> offers = new ArrayList<>(); //for å unngå nullpointer

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

  public String getIdAsString() {
    return Integer.toString(id);
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

  public List<Role> getRoles() {
    return roles;
  }

  public void addRole(Role role) {
    roles.add(role);
  }

  public void removeRole(Role role) {
    roles.remove(role);
  }

  public List<Listing> getFavorites() {
    return favorites;
  }

  public void addFavorite(Listing favorite) {
    favorites.add(favorite);
  }

  public void removeFavorite(Listing favorite) {
    favorites.remove(favorite);
  }

  public void clearFavorites() {
    favorites.clear();
  }

  public List<Listing> getHistory() {
    return history;
  }

  public void addHistory(Listing listing) {
    if (history.size() >= userHistoryMaxSize) {
      history.remove(0);
    }
    history.add(listing);
  }

  public void clearHistory() {
    history.clear();
  }

  public List<Offer> getOffers() {
    return offers;
  }

  public void addOffer(Offer offer) {
    offers.add(offer);
  }

  public void removeOffer(Offer offer) {
    offers.remove(offer);
  }

}

