package com.example.addressline.service;

import com.example.addressline.dao.DetailedAddress;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.Stack;

@Service
public class AddressLineService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String invalidAddress = "invalid address";

    public String parseAddress(String address) {
        address = address.replaceAll("\\p{Punct}", "");
        String addressJson = invalidAddress;
        DetailedAddress detailedAddress = null;
        //TODO check if string 'no' or 'nr' if so we split on it create the json.
        if (address.toLowerCase(Locale.ROOT).contains("no") || address.toLowerCase(Locale.ROOT).contains("nr")) {
            String[] splicedString = address.toLowerCase(Locale.ROOT).split("nr|no");
            detailedAddress = new DetailedAddress(splicedString[0], splicedString[1]);
            try {
                addressJson = jsonifyObject(detailedAddress);
                logger.info("Successfully parsed the address");
            } catch (Exception e) {
                logger.error("Failed to create json string");
            }
        } else {
            if (startsWithNumber(address)) {
                try {
                    detailedAddress = new DetailedAddress(address.substring(address.indexOf(' ') + 1), address.substring(0, address.indexOf(' ')));
                    addressJson = jsonifyObject(detailedAddress);
                    logger.info("Successfully parsed the address");
                } catch (Exception e) {
                    logger.error("Failed to create json string");
                }
            } else {
                try {
                    String[] splitAddress = address.split("\\s+");
                    Stack<String> stringsStack = new Stack<>();
                    for (String s : splitAddress) {
                        if (isNumber(s)) {
                            stringsStack.pop();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringsStack.forEach(stringBuilder::append);
                            detailedAddress = new DetailedAddress(stringBuilder.toString(), s);
                        } else {
                            stringsStack.push(s);
                        }
                    }
                    addressJson = jsonifyObject(Objects.requireNonNull(detailedAddress));
                } catch (Exception e) {
                    logger.error("Failed to create json string");
                }
            }
        }


        return addressJson;
    }

    private boolean startsWithNumber(String address) {
        return address.split("\\s+")[0].chars().allMatch(Character::isDigit);
    }

    private String jsonifyObject(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private boolean isNumber(String s) {
        return s.chars().allMatch(Character::isDigit);
    }
}

