package com.arqsoft.project2.acmeProducts.repositories.mongo;


import com.arqsoft.project2.acmeProducts.model.ProdImage;
import com.arqsoft.project2.acmeProducts.repositories.ImageRepository;

public class MongoImageRepositoryImp extends MongoBaseRepository<ProdImage,Long> implements ImageRepository {

    public MongoImageRepositoryImp(){
        super(ProdImage.class);
    }

}
