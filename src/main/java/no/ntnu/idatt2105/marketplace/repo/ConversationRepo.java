package no.ntnu.idatt2105.marketplace.repo;

import java.util.Optional;
import no.ntnu.idatt2105.marketplace.model.negotiation.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepo extends JpaRepository<Conversation, Integer> {
  Optional<Conversation> findById(int id);
}