package no.ntnu.idatt2105.marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class user_roles {

  @OneToOne
  @JoinColumn(name = "role_id", nullable = false)
  private role role;

  @OneToOne
  @JoinColumn(name = "users_id", nullable = false)
  private user user;


  @Id
  private int id;

  // constructor
  public user_roles() {}

  public user_roles(int id, role role, user user) {
    this.id = id;
    this.role = role;
    this.user = user;
  }

  // setters  and getters


  public role getRole() {
    return role;
  }

  public void setRole(role role) {
    this.role = role;
  }

  public user getUser() {
    return user;
  }

  public void setUser(user user) {
    this.user = user;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}