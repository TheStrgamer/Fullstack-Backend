package no.ntnu.idatt2105.marketplace.repo;

import java.util.Optional;
import no.ntnu.idatt2105.marketplace.model.negotiation.Conversation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationRepo extends JpaRepository<Conversation, Integer> {
  @EntityGraph(attributePaths = "messages")

  Optional<Conversation> findById(int id);

  @Query("SELECT c FROM Conversation c JOIN FETCH c.listing l JOIN FETCH l.creator WHERE c.id = :id")
  Optional<Conversation> findByIdWithListingAndCreator(@Param("id") int id);

}