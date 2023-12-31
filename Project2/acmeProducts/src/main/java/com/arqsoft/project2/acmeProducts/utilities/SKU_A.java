package com.arqsoft.project2.acmeProducts.utilities;

import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class SKU_A implements SKUAlgorithms{

    @Override
    public String generateSKU(String designation) {
        //n + l + n + l + n + l + l + n + l + n + special char
        StringBuilder sku = new StringBuilder();
        Random random = new Random();
        // 6ª
        // Generate numbers, letters, and special character parts
        for (int i = 0; i < 10; i++) {

                if ((i % 2 == 0 && i<6) || (i>6 && i % 2 != 0)) {
                    // add a number
                    sku.append(random.nextInt(10));
                } else {
                    // add a letter
                    char letter = (char) (random.nextInt(26) + 'A');
                    sku.append(letter);
                }

        }

        // Add a special character
        char[] specialCharacters = "!@#$&*()-_=+:',.".toCharArray();
        char specialChar = specialCharacters[random.nextInt(specialCharacters.length)];
        sku.append(specialChar);



        return sku.toString();



    }
}
