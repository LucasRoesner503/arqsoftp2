package com.isep.acme.repositories.mongo;

import com.isep.acme.model.ProdImage;
import com.isep.acme.repositories.ImageRepository;

public class MongoImageRepositoryImp extends MongoBaseRepository<ProdImage,Long> implements ImageRepository {

    public MongoImageRepositoryImp(){
        super(ProdImage.class);
    }

}
