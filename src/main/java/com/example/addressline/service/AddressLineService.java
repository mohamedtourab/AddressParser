package com.example.addressline.service;

import com.example.addressline.dao.DetailedAddress;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AddressLineService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String invalidAddress = "invalid address";

    public String parseAddress(String address) {
        address = address.replaceAll("\\p{Punct}", "");
        String addressJson = invalidAddress;
        DetailedAddress detailedAddress;
        if (doesContainNoOrNr(address)) {
            String[] splicedString = address.toLowerCase(Locale.ROOT).split("nr|no");
            detailedAddress = new DetailedAddress(splicedString[0], splicedString[1]);
            addressJson = tryJsonifyObject(detailedAddress);
        } else {
            if (startsWithNumber(address)) {
                detailedAddress = new DetailedAddress(address.substring(address.indexOf(' ') + 1), address.substring(0, address.indexOf(' ')));
                addressJson = tryJsonifyObject(detailedAddress);
            } else {
                try {
                    StringBuilder streetName = new StringBuilder();
                    StringBuilder addressNumber = new StringBuilder();
                    handleAddressThatDoesntStartWithNumber(streetName, addressNumber, address);
                    detailedAddress = new DetailedAddress(streetName.toString(), addressNumber.toString());
                    addressJson = jsonifyObject(Objects.requireNonNull(detailedAddress));
                } catch (Exception e) {
                    logger.error("Failed to create json string");
                }
            }
        }
        return addressJson;
    }

    private void handleAddressThatDoesntStartWithNumber(StringBuilder streetName, StringBuilder addressNumber, String address) {
        Pattern pattern = Pattern.compile("((.*?)(\\d+)([A-Za-z]+))");
        Matcher matcher = pattern.matcher(address);
        if (matcher.find()) {
            address = String.format("%s %s %s", matcher.group(2), matcher.group(3), matcher.group(4));
        }
        String[] splitAddress = address.split("\\s+");
        boolean streetComplete = false;
        for (String s : splitAddress) {
            if (isNumber(s) || streetComplete) {
                streetComplete = true;
                addressNumber.append(s);
                addressNumber.append(' ');
            } else {
                streetName.append(s);
                streetName.append(' ');
            }
        }
    }

    private boolean startsWithNumber(String address) {
        return address.split("\\s+")[0].chars().allMatch(Character::isDigit);
    }

    private String jsonifyObject(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private String tryJsonifyObject(DetailedAddress detailedAddress) {
        String addressJson = invalidAddress;
        try {
            addressJson = jsonifyObject(detailedAddress);
            logger.info("Successfully parsed the address");
        } catch (Exception e) {
            logger.error("Failed to create json string");
        }
        return addressJson;
    }

    private boolean isNumber(String s) {
        return s.chars().allMatch(Character::isDigit);
    }

    private boolean doesContainNoOrNr(String address) {
        return address.toLowerCase(Locale.ROOT).contains("no") || address.toLowerCase(Locale.ROOT).contains("nr");
    }
}

