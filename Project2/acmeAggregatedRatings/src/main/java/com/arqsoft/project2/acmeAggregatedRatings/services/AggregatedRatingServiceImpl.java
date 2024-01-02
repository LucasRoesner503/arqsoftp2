package com.arqsoft.project2.acmeAggregatedRatings.services;


import com.arqsoft.project2.acmeAggregatedRatings.model.AggregatedRating;
import com.arqsoft.project2.acmeAggregatedRatings.model.Product;
import com.arqsoft.project2.acmeAggregatedRatings.repositories.AggregatedRatingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Service
public class AggregatedRatingServiceImpl implements AggregatedRatingService{

    @Autowired
    AggregatedRatingRepository arRepository;

    @Value("${gateway.url}")
    private String url;

    @Override
    public AggregatedRating save(String sku ) {

        Optional<Product> product = this.getProduct( sku );

        if (product.isEmpty()){
            return null;
        }

        double average = getRating(product.get().getProductID());


        Optional<AggregatedRating> r = arRepository.findByProductId(product.get());
        AggregatedRating aggregateF;

        if(r.isPresent()) {
            r.get().setAverage( average );
            aggregateF = arRepository.save(r.get());
        }
        else {
            AggregatedRating f = new AggregatedRating(average, product.get());
            aggregateF = arRepository.save(f);
        }

        return aggregateF;
    }

    private Optional<Product> getProduct(String sku) {
        try{

            URL endpointUrl = new URL(url + "/products/" + sku);
            HttpURLConnection connection = (HttpURLConnection) endpointUrl.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();

            Product product = objectMapper.readValue(response.toString(), Product.class);

            return Optional.of(product);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();

    }

    private double getRating(Long productId) {
        try{

            URL endpointUrl = new URL(url + "/reviews/average/" + productId);
            HttpURLConnection connection = (HttpURLConnection) endpointUrl.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            return Double.parseDouble(response.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;

    }


}
