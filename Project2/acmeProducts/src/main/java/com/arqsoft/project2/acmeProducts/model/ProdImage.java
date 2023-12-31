package com.arqsoft.project2.acmeProducts.model;


import com.arqsoft.project2.acmeProducts.repositories.Idget;

import javax.annotation.Resource;
import javax.persistence.*;

@Entity
public class ProdImage implements Idget<Long> {

    @Id
    @GeneratedValue
    private Long id;




    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Lob
    private Resource image;




public ProdImage( Product product, Resource image){
    setProduct(product);
    //addImage(image);;

}




    public ProdImage() {

    }

    private void setProduct(Product product) {
    }




    public ImageDTO toDto() {
        return new ImageDTO(this.id, product.getProductID());
    }

    @Override
    public Long getId() {
        return this.id;
    }

/*
    public ImageService addImage( Resource image){

        return new AddImage (this.image);
    }
*/
}

