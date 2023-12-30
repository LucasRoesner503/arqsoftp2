package com.isep.acme.repositories.neo4j;

import com.isep.acme.model.ProdImage;
import com.isep.acme.repositories.ImageRepository;

public class Neo4JImageRepositoryImp extends Neo4JBaseRepository<ProdImage,Long> implements ImageRepository {

    public Neo4JImageRepositoryImp(){
        super(ProdImage.class);
    }


}
