package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingOrder;
import com.thoughtworks.parking_lot.service.ParkingOrderService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkingOrder")
public class ParkingOrderController {
    @Autowired
    ParkingOrderService parkingOrderService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<ParkingOrder> getAllParkingOrder(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(required = false, defaultValue = "15") Integer pageSize) {
        return parkingOrderService.getAllParkingOrder(page, pageSize);
    }
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public ParkingOrder getParkingOrderByOrderNumber(@RequestParam(required = false) Long orderNumber) throws NotFoundException {
        return parkingOrderService.getParkingOrderByOrderNumber(orderNumber);
    }
    @ResponseStatus(code = HttpStatus.OK)
    @DeleteMapping(path = "/{orderNumber}")
    public ParkingOrder deleteParkingOrder(@PathVariable Long orderNumber) throws NotFoundException {
        return parkingOrderService.deleteParkingOrder(orderNumber);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PatchMapping(path = "/{orderNumber}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParkingOrder setParkingOrderStatusAsClosed(@PathVariable Long orderNumber) throws NotFoundException {
        return parkingOrderService.setParkingOrderStatusAsClosed(orderNumber);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = "/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ParkingOrder saveParkingOrder(@RequestBody ParkingOrder parkingOrder,
                                         @PathVariable String name) throws NotFoundException {
        return parkingOrderService.saveParkingOrder(parkingOrder, name);
    }
}
