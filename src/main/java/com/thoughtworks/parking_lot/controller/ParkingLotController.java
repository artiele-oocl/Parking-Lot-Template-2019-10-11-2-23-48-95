package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import javassist.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import java.util.List;
import java.util.Optional;

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
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/{name}")
    public ParkingLot getParkingLotByName(@PathVariable String name) throws NotFoundException {
        return parkingLotService.getParkingLotByName(name);
    }
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public List<ParkingLot> getParkingLotByNameLike(@RequestParam(required = false) String name) throws NotFoundException {
        return parkingLotService.getParkingLotByNameLike(name);
    }
    @ResponseStatus(code = HttpStatus.OK)
    @DeleteMapping(path = "/{name}")
    public ParkingLot deleteParkingLot(@PathVariable String name) throws NotFoundException {
        return parkingLotService.deleteParkingLot(name);
    }
    @ResponseStatus(code = HttpStatus.OK)
    @PatchMapping(path = "/{name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParkingLot updateParkingLot(@RequestBody ParkingLot parkingLot, @PathVariable String name)
            throws NotFoundException, NotSupportedException {
        return parkingLotService.updateParkingLot(parkingLot, name);
    }
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ParkingLot saveParkingLot(@RequestBody ParkingLot parkingLot) throws NotSupportedException {
        return parkingLotService.saveParkingLot(parkingLot);
    }

}
