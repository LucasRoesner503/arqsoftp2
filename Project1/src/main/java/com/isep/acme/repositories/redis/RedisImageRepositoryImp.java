package com.isep.acme.repositories.redis;

import com.isep.acme.model.ProdImage;
import com.isep.acme.repositories.ImageRepository;

import java.awt.*;

public class RedisImageRepositoryImp extends RedisBaseRepository<ProdImage, Long> implements ImageRepository {


    public RedisImageRepositoryImp(){
        super(ProdImage.class);
    }



}
