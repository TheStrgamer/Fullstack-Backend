package no.ntnu.idatt2105.marketplace.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import no.ntnu.idatt2105.marketplace.model.other.Images;

import java.util.Optional;

public interface ImageRepo extends JpaRepository<Images, Integer> {

  // get image by id
  Optional<Images> findById(int id);

  // save function to save images is included by default
  // usage: ImageRepo.save(imageObj)


}
