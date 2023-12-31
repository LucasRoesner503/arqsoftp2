package com.arqsoft.project2.acmeProducts.repositories.neo4j;


import com.arqsoft.project2.acmeProducts.model.ProdImage;
import com.arqsoft.project2.acmeProducts.repositories.ImageRepository;

public class Neo4JImageRepositoryImp extends Neo4JBaseRepository<ProdImage,Long> implements ImageRepository {

    public Neo4JImageRepositoryImp(){
        super(ProdImage.class);
    }


}
