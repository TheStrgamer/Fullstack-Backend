package no.ntnu.idatt2105.marketplace.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import no.ntnu.idatt2105.marketplace.dto.negotiation.OfferDTO;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.negotiation.Conversation;
import no.ntnu.idatt2105.marketplace.model.negotiation.Offer;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.ConversationRepo;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.OfferRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OfferService {

  @Autowired
  private JWT_token jwt;

  @Autowired
  private OfferRepo offerRepo;

  @Autowired
  private ConversationRepo conversationRepo;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private ListingRepo listingRepo;

  /**
   * Verifies that the user is authorized to access the offer.
   *
   * @param user   the user making the offer
   * @param offerId the ID of the offer
   * @throws IllegalArgumentException if the user is not authorized to access the offer
   */
  private void validateUserInOffer(User user, int offerId) {
    Offer offer = offerRepo.findById(offerId);
    if (offer == null || user == null) {
      throw new IllegalArgumentException("User or Offer not found");
    }
    if (offer.getBuyer() != user ||
        offer.getListing().getCreator() != user) {
      throw new IllegalArgumentException("User is not authorized to access this offer");
    }
  }

  private User getUserAndValidate(String token, int offerId) {
    User user = jwt.getUserByToken(token);
    try {
      validateUserInOffer(user, offerId);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("User is not authorized to access this offer");
    }
    return user;
  }

  /**
   * Verifies that the user is authorized to accept or reject the offer.
   *
   * @param user   the user making the offer
   * @param offerId the ID of the offer
   * @return true if the user is authorized to accept the offer
   */
  private boolean userCanAcceptOrRejectOffer(User user, int offerId) {
    Offer offer = offerRepo.findById(offerId);
    if (offer == null || user == null) {
      return false;
    }
    return user != offer.getCreator();
  }

  /**
   * Verifies that the user can create an offer.
   *
   * @param user     the user making the offer
   * @param listingId the ID of the listing
   * @return true if the user is authorized to create the offer
   */
  private boolean userCanCreateOffer(User user, int listingId, int conversationId) {
    System.out.println("User: " + user.getId() + " Listing: " + listingId + " Conversation: " + conversationId);
    Optional<Listing> listing = listingRepo.findById(listingId);
    Optional<Conversation> conversation = conversationRepo.findById(conversationId);
    if (listing.isEmpty() || user == null) {
      System.out.println("Listing or user not found");
      return false;
    }
    if (conversation.isEmpty()) {
      System.out.println("Conversation not found");
      return false;
    }
    if (conversation.get().getListing() != listing.get()) {
      System.out.println("Conversation does not match listing");
      return false;
    }
    Conversation conv = conversation.get();
    System.out.println("User: " + user.getId() + " Buyer: " + conv.getBuyer().getId() + " Seller: " + conv.getSeller().getId() + " Listing: " + listing.get().getId());
    return user == conv.getBuyer() || user == conv.getSeller();
  }

  /**
   * Creates a new offer for a listing.
   *
   * @param listingId the ID of the listing
   * @param token     the JWT token of the user making the offer
   */
  public OfferDTO createOffer( String token, int listingId, int price, int conversationId) {
    User user = jwt.getUserByToken(token);
    if (user == null) {
      System.out.println("User not found");
      throw new IllegalArgumentException("User not found");
    }
    Optional<Listing> listing = listingRepo.findById(listingId);
    if (listing.isEmpty()) {
      System.out.println("Listing not found");
      throw new IllegalArgumentException("Listing not found");
    }
    if (!userCanCreateOffer(user, listingId, conversationId)) {
      System.out.println("User is not authorized to create this offer");
      throw new IllegalArgumentException("User is not authorized to create this offer");
    }
    if (price <= 0) {
      System.out.println("Price cannot be negative");
      throw new IllegalArgumentException("Price cannot be negative");
    }
    Conversation conversation = conversationRepo.findById(conversationId).orElse(null);
    User offerUser;
    if (conversation == null) {
      System.out.println("Conversation not found");
      throw new IllegalArgumentException("Conversation not found");
    }
    if (user == conversation.getBuyer()) {
      offerUser = conversation.getSeller();
    } else {
      offerUser = conversation.getBuyer();
    }
    Offer offer = new Offer(offerUser, listing.get(), price, 0, new java.util.Date(), new java.util.Date(), user);
    offerRepo.save(offer);
    return new OfferDTO(offer.getId(), offer.getBuyer().getId(), offer.getListing().getId(), offer.getCurrent_offer(), offer.getStatus(), offer.getUpdated_at().toString(), user.getId());
  }


  /**
   * Accepts an offer for a listing.
   * Can be done by the user who received the offer.
   *
   * @param token   the JWT token of the user accepting the offer
   * @param offerId the ID of the offer to accept
   */
  public void acceptOffer(String token, int offerId) {
    User user = getUserAndValidate(token, offerId);
    Offer offer = offerRepo.findById(offerId);
    if (offer == null) {
      throw new IllegalArgumentException("Offer not found");
    }
    if (!userCanAcceptOrRejectOffer(user, offerId)) {
      throw new IllegalArgumentException("User is not authorized to accept this offer");
    }
    offer.setStatus(1);
    offerRepo.save(offer);
  }

  /**
   * Rejects an offer for a listing.
   * Can be done by the user who received the offer.
   *
   * @param token   the JWT token of the user rejecting the offer
   * @param offerId the ID of the offer to reject
   */
  public void rejectOffer(String token, int offerId) {
    User user = getUserAndValidate(token, offerId);
    Offer offer = offerRepo.findById(offerId);
    if (offer == null) {
      throw new IllegalArgumentException("Offer not found");
    }
    if (!userCanAcceptOrRejectOffer(user, offerId)) {
      throw new IllegalArgumentException("User is not authorized to reject this offer");
    }
    offer.setStatus(2);
    offerRepo.save(offer);
  }

  /**
   * Removes an offer for a listing.
   * Can be done by the user who created the offer.
   * @param token   the JWT token of the user removing the offer
   * @param offerId the ID of the offer to remove
   */
  public void removeOffer(String token, int offerId) {
    User user = getUserAndValidate(token, offerId);
    Offer offer = offerRepo.findById(offerId);
    if (offer == null) {
      throw new IllegalArgumentException("Offer not found");
    }
    if (user != offer.getBuyer()) {
      throw new IllegalArgumentException("User is not authorized to remove this offer");
    }
    offerRepo.delete(offer);
  }

  /**
   * Gets all offers from a conversation
   * @param token the JWT token of the user getting the offers
   * @param conversationId the ID of the conversation
   */
  public List<OfferDTO> getOffersFromConversation(String token, int conversationId) {
    User user = jwt.getUserByToken(token);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }
    System.out.println("User: " + user.getId() + " Conversation: " + conversationId);
    Conversation conversation = conversationRepo.findById(conversationId).orElse(null);
    if (conversation == null) {
      throw new IllegalArgumentException("Conversation not found");
    }
    System.out.println("Conversation: " + conversation.getListing().getId() + " Buyer: " + conversation.getBuyer().getId() + " Seller: " + conversation.getSeller().getId() + " User: " + user.getId());

    if (user != conversation.getBuyer() && user != conversation.getSeller()) {
      throw new IllegalArgumentException("User is not authorized to access this conversation");
    }
    System.out.println(conversation.getListing());
    List<Offer> sellerOffers = offerRepo.findByCreatorAndListingId(user, conversation.getListing().getId());
    List<Offer> buyerOffers = offerRepo.findByBuyerAndListingId(user, conversation.getListing().getId());
    ArrayList<Offer> offers = new ArrayList<>();
    offers.addAll(sellerOffers);
    offers.addAll(buyerOffers);
    offers.removeIf(offer -> offer.getCreator() != conversation.getBuyer()
        && offer.getCreator() != conversation.getSeller());
    ArrayList<OfferDTO> offerDTOs = new ArrayList<>();
    for (Offer offer : offers) {
      offerDTOs.add(new OfferDTO(offer.getId(), offer.getBuyer().getId(), offer.getListing().getId(), offer.getCurrent_offer(), offer.getStatus(), offer.getUpdated_at().toString(), user.getId(), offer.getCreator() == user, offer.getCreator().toString()));
    }
    return offerDTOs;

  }
}
