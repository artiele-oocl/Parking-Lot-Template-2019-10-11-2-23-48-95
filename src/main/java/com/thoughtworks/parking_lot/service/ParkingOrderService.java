package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.entity.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.repository.ParkingOrderRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ParkingOrderService {
    @Autowired
    ParkingOrderRepository parkingOrderRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    private static final String STATUS_OPEN = "Open";
    private static final String STATUS_CLOSED = "Close";
    private static final String PARKING_LOT_DOES_NOT_EXIST = "PARKING LOT DOES NOT EXIST";
    private static final String PARKING_LOT_IS_FULL = "PARKING LOT IS FULL";
    private static final String ORDER_NUMBER_NOT_FOUND = "ORDER NUMBER NOT FOUND";
    private static final String PLATE_NUMBER_ALREADY_EXISTS = "PLATE NUMBER ALREADY EXISTS";
    private static final String ORDER_NUMBER_ALREADY_CLOSED = "ORDER NUMBER ALREADY CLOSED";
    private static final String ORDER_STATUS_IS_OPEN = "ORDER STATUS IS OPEN";

    public String getCurrentDateTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }
    public Iterable<ParkingOrder> getAllParkingOrder(Integer page, Integer pageSize) {
        return parkingOrderRepository.findAll(PageRequest.of(page,pageSize));
    }
    public ParkingOrder getParkingOrderByOrderNumber(Long orderNumber) throws NotFoundException {
        Optional<ParkingOrder> parkingOrder = Optional.ofNullable(parkingOrderRepository.findOneByOrderNumber(orderNumber));
        if(!parkingOrder.isPresent()) throw new NotFoundException(ORDER_NUMBER_NOT_FOUND);
        return parkingOrder.get();
    }
    public ParkingOrder deleteParkingOrder(Long orderNumber) throws NotFoundException {
        Optional<ParkingOrder> parkingOrder = Optional.ofNullable(parkingOrderRepository.findOneByOrderNumber(orderNumber));
        if(!parkingOrder.isPresent()) throw new NotFoundException(ORDER_NUMBER_NOT_FOUND);
        if(parkingOrder.get().getCloseTime().isEmpty()) throw new NotFoundException(ORDER_STATUS_IS_OPEN);

        parkingOrderRepository.delete(parkingOrder.get());
        return parkingOrder.get();
    }
    public ParkingOrder setParkingOrderStatusAsClosed(Long orderNumber) throws NotFoundException {
        Optional<ParkingOrder> parkingOrder = Optional.ofNullable(parkingOrderRepository.findOneByOrderNumber(orderNumber));
        if(!parkingOrder.isPresent()) throw new NotFoundException(ORDER_NUMBER_NOT_FOUND);
        if(!parkingOrder.get().getCloseTime().isEmpty()) throw new NotFoundException(ORDER_NUMBER_ALREADY_CLOSED);

        Optional<ParkingLot> parkingLot = Optional.ofNullable(parkingLotRepository.findOneByName(parkingOrder.get().getParkingName()));
        Integer parkingLotCapacity = parkingLot.get().getCapacity() + 1;
        parkingOrder.get().setOrderStatus(STATUS_CLOSED);

        parkingOrder.get().setCloseTime(getCurrentDateTime());
        parkingLot.get().setCapacity(parkingLotCapacity);
        parkingLotRepository.save(parkingLot.get());
        return parkingOrderRepository.save(parkingOrder.get());
    }
    public ParkingOrder saveParkingOrder(ParkingOrder parkingOrder, String name) throws NotFoundException {
        Optional<ParkingLot> parkingLot = Optional.ofNullable(parkingLotRepository.findOneByName(name));
        Optional<ParkingOrder> findOneByPlateNumber = Optional.ofNullable(parkingOrderRepository
                .findOneByPlateNumber(parkingOrder.getPlateNumber()));
        if(!parkingLot.isPresent()) throw new NotFoundException(PARKING_LOT_DOES_NOT_EXIST);
        if(parkingLot.get().getCapacity() < 1) throw new NotFoundException(PARKING_LOT_IS_FULL);
        if(findOneByPlateNumber.isPresent()) throw new NotFoundException(PLATE_NUMBER_ALREADY_EXISTS);

        Integer parkingLotCapacity = parkingLot.get().getCapacity() - 1;
        parkingLot.get().setCapacity(parkingLotCapacity);
        parkingOrder.setParkingName(name);
        parkingOrder.setCreationTime(getCurrentDateTime());
        parkingOrder.setOrderStatus(STATUS_OPEN);
        parkingLotRepository.save(parkingLot.get());
        return parkingOrderRepository.save(parkingOrder);
    }
}
