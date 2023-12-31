package com.arqsoft.project2.acmeProducts.repositories;

import com.arqsoft.project2.acmeProducts.model.ProdImage;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<ProdImage, Long> {
}
