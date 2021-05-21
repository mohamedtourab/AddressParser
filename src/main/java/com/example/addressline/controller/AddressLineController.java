package com.example.addressline.controller;


import com.example.addressline.service.AddressLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/split_address")
public class AddressLineController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AddressLineService addressLineService;

    @GetMapping
    public ResponseEntity<String> getDetailedAddress(@NonNull @RequestParam("address") String address) {
        String outputAddress = addressLineService.parseAddress(address);
        logger.info("Detailed address is: " + outputAddress);
        return new ResponseEntity<>(outputAddress, HttpStatus.OK);
    }
}
