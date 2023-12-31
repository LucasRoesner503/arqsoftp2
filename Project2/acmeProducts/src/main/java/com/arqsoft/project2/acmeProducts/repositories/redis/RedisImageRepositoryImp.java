package com.arqsoft.project2.acmeProducts.repositories.redis;


import com.arqsoft.project2.acmeProducts.model.ProdImage;
import com.arqsoft.project2.acmeProducts.repositories.ImageRepository;

public class RedisImageRepositoryImp extends RedisBaseRepository<ProdImage, Long> implements ImageRepository {


    public RedisImageRepositoryImp(){
        super(ProdImage.class);
    }



}
