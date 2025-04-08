package no.ntnu.idatt2105.marketplace.repo;

import no.ntnu.idatt2105.marketplace.model.other.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImagesRepo extends JpaRepository<Images, Integer> {
    Optional<Images> findByFilepathToImage(String filepathToImage);

    Optional<Images> findById(int id);
}

