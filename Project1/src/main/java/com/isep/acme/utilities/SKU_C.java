package com.isep.acme.utilities;

import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class SKU_C implements SKUAlgorithms{

    private final SKU_A skuGeneratorA;
    private final SKU_B skuGeneratorB;


    @Override
    public String generateSKU(String designation) {
        return skuGeneratorA.generateSKU(designation).substring(0,6) + skuGeneratorB.generateSKU(designation).substring(0,5);
    }
}
