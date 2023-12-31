package com.arqsoft.project2.acmeProducts.utilities;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SKU_B implements SKUAlgorithms{

    @Override
    public String generateSKU(String designation) {
        float hashcode = designation.hashCode();
        String hexadecimal = Float.toHexString(hashcode);
        int length = hexadecimal.length();

        if(length > 10){
            // Calculate the starting index for the middle substring
            int startIndex = (length - 10) / 2;

            // Extract the middle 10 characters
            return hexadecimal.substring(startIndex, startIndex + 10);

        }
        return hexadecimal;
    }









}
