package no.ntnu.idatt2105.marketplace.model.other;

import jakarta.persistence.*;
import java.util.List;
import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;

@Entity
@Table(name = "images")
public class Images {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(unique = true, nullable = false)
  private String filepath_to_image;

  @ManyToOne
  @JoinColumn(name = "listing_id")
  private Listing listing;


  // constructor
  public Images() {}
  public Images(int id, String filepath_to_image) {
    this.id = id;
    this.filepath_to_image = filepath_to_image;
  }

  // getters and setters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFilepath_to_image() {
    return filepath_to_image;
  }

  public void setFilepath_to_image(String filepath_to_image) {
    this.filepath_to_image = filepath_to_image;
  }
}
