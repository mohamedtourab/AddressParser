package com.example.addressline.service;

import com.example.addressline.dao.DetailedAddress;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddressLineServiceTest {

    @Test
    void parseAddress() throws JsonProcessingException {
        DetailedAddress address;
        AddressLineService addressLineService = new AddressLineService();
        String output = addressLineService.parseAddress("Winterallee 3");
        address = new ObjectMapper().readValue(output, DetailedAddress.class);
        Assertions.assertEquals("Winterallee", address.getStreet());
        Assertions.assertEquals("3", address.getHouseNumber());

        output = addressLineService.parseAddress("Musterstrasse 45");
        address = new ObjectMapper().readValue(output, DetailedAddress.class);
        Assertions.assertEquals("Musterstrasse", address.getStreet());
        Assertions.assertEquals("45", address.getHouseNumber());

        output = addressLineService.parseAddress("Blaufeldweg 123B");
        address = new ObjectMapper().readValue(output, DetailedAddress.class);
        Assertions.assertEquals("Blaufeldweg", address.getStreet());
        Assertions.assertEquals("123 B", address.getHouseNumber());

        output = addressLineService.parseAddress("Am Bächle 23");
        address = new ObjectMapper().readValue(output, DetailedAddress.class);
        Assertions.assertEquals("Am Bächle", address.getStreet());
        Assertions.assertEquals("23", address.getHouseNumber());

        output = addressLineService.parseAddress("Auf der Vogelwiese 23 b");
        address = new ObjectMapper().readValue(output, DetailedAddress.class);
        Assertions.assertEquals("Auf der Vogelwiese", address.getStreet());
        Assertions.assertEquals("23 b", address.getHouseNumber());

        output = addressLineService.parseAddress("4, rue de la revolution");
        address = new ObjectMapper().readValue(output, DetailedAddress.class);
        Assertions.assertEquals("rue de la revolution", address.getStreet());
        Assertions.assertEquals("4", address.getHouseNumber());

        output = addressLineService.parseAddress("200 Broadway Av");
        address = new ObjectMapper().readValue(output, DetailedAddress.class);
        Assertions.assertEquals("Broadway Av", address.getStreet());
        Assertions.assertEquals("200", address.getHouseNumber());

        output = addressLineService.parseAddress("Calle Aduana, 29");
        address = new ObjectMapper().readValue(output, DetailedAddress.class);
        Assertions.assertEquals("Calle Aduana", address.getStreet());
        Assertions.assertEquals("29", address.getHouseNumber());

        output = addressLineService.parseAddress("Calle 39 No 1540");
        address = new ObjectMapper().readValue(output, DetailedAddress.class);
        Assertions.assertEquals("calle 39", address.getStreet());
        Assertions.assertEquals("1540", address.getHouseNumber());


    }
}
