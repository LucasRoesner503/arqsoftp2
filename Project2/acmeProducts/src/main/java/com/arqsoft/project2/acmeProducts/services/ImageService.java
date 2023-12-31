package com.arqsoft.project2.acmeProducts.services;


import com.arqsoft.project2.acmeProducts.model.ImageDTO;
import com.arqsoft.project2.acmeProducts.model.ProdImage;
import com.arqsoft.project2.acmeProducts.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

public class ImageService {

    @Autowired
    private Resource image;
    @Autowired
     private ProdImage id;
     @Autowired
     private ImageRepository repository;
    private String filename;


    public Iterable<ImageDTO> getImageProduct(){
          Iterable<ProdImage> p = repository.findAll();
          List<ImageDTO> iDto= new ArrayList();
          for (ProdImage pd:p) {
               iDto.add(pd.toDto());
          }

          return iDto;
     };









}
