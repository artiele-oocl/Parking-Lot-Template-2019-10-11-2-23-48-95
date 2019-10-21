package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkingLots")
public class ParkingLotController {
    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<ParkingLot> getAllParkingLots(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "15") Integer pageSize) {
        return parkingLotService.getAllParkingLots(page, pageSize);
    }
}
