package com.example.addressline.service;

import com.example.addressline.dao.DetailedAddress;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddressLineService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String invalidAddress = "invalid address";

    public String parseAddress(String address) {
        address = address.replaceAll("\\p{Punct}", "");
        String addressJson = invalidAddress;
        if (startsWithNumber(address)) {
            try {
                DetailedAddress detailedAddress = new DetailedAddress(address.substring(address.indexOf(' ') + 1), address.substring(0, address.indexOf(' ')));
                ObjectMapper objectMapper = new ObjectMapper();
                addressJson = objectMapper.writeValueAsString(detailedAddress);
                logger.info("Successfully parsed the address");
            } catch (Exception e) {
                logger.error("Failed to create json string");
            }
        } else {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                DetailedAddress detailedAddress = new DetailedAddress("test", "test");
                addressJson = objectMapper.writeValueAsString(detailedAddress);
            } catch (Exception e) {
                logger.error("Failed to create json string");
            }
        }
        return addressJson;
    }

    private boolean startsWithNumber(String address) {
        return address.split("\\s+")[0].chars().allMatch(Character::isDigit);
    }
}
