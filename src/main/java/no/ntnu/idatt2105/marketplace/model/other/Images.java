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
  private String filepathToImage;

  @ManyToOne
  @JoinColumn(name = "listing_id")
  private Listing listing;


  // constructor
  public Images() {}
  public Images(int id, String filepathToImage) {
    this.id = id;
    this.filepathToImage = filepathToImage;
  }

  // getters and setters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Listing getListing() {
    return listing;
  }

  public String getFilepath_to_image() {
    return filepathToImage;
  }

  public void setFilepath_to_image(String filepath_to_image) {
    this.filepathToImage = filepath_to_image;
  }

  public void setListing(Listing listing) {
    this.listing = listing;
  }
}
