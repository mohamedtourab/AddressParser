package com.example.addressline.service;

import org.junit.jupiter.api.Test;

class AddressLineServiceTest {

    @Test
    void parseAddress() {
        AddressLineService addressLineService = new AddressLineService();
        String output = addressLineService.parseAddress("Winterallee 3");
        System.out.println(output);
        output = addressLineService.parseAddress("Musterstrasse 45");
        System.out.println(output);
        output = addressLineService.parseAddress("Blaufeldweg 123B");
        System.out.println(output);
        output = addressLineService.parseAddress("Am Bächle 23");
        System.out.println(output);
        output = addressLineService.parseAddress("Auf der Vogelwiese 23 b");
        System.out.println(output);
        output = addressLineService.parseAddress("4, rue de la revolution");
        System.out.println(output);

    }
}
