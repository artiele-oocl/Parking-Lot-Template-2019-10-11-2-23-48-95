package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    private static final String PARKING_NOT_FOUND = "PARKING LOT NOT FOUND";
    private static final String INVALID_CAPACITY = "INVALID CAPACITY";
    private static final String PARKING_NAME_ALREADY_EXISTS = "PARKING NAME ALREADY EXISTS";

    public Iterable<ParkingLot> getAllParkingLots(Integer page, Integer pageSize) {
        return parkingLotRepository.findAll();
    }
    public ParkingLot getParkingLotByName(String name) throws NotFoundException {
        Optional<ParkingLot> parkingLot = Optional.ofNullable(parkingLotRepository.findOneByName(name));
        if(!parkingLot.isPresent()) throw new NotFoundException(PARKING_NOT_FOUND);
        return parkingLot.get();
    }
    public List<ParkingLot> getParkingLotByNameLike(String name) throws NotFoundException {
        List<ParkingLot> parkingLot = parkingLotRepository.findByNameContaining(name);
        if(parkingLot.size() == 0) throw new NotFoundException(PARKING_NOT_FOUND);
        return parkingLotRepository.findByNameContaining(name);
    }
    public ParkingLot deleteParkingLot(String name) throws NotFoundException {
        Optional<ParkingLot> parkingLot = Optional.ofNullable(parkingLotRepository.findOneByName(name));
        if(!parkingLot.isPresent()) throw new NotFoundException(PARKING_NOT_FOUND);
        parkingLotRepository.delete(parkingLot.get());
        return parkingLot.get();
    }
    public ParkingLot updateParkingLot(ParkingLot parkingLot, String name) throws NotFoundException, NotSupportedException {
        Optional<ParkingLot> parkingLotDB = Optional.ofNullable(parkingLotRepository.findOneByName(name));
        if(parkingLot == null || !parkingLotDB.isPresent()) throw new NotFoundException(PARKING_NOT_FOUND);
        if(parkingLot.getCapacity() < 1) throw new NotSupportedException(INVALID_CAPACITY);

        parkingLotDB.get().setCapacity(parkingLot.getCapacity()); // you can only update capacity
        return parkingLotRepository.save(parkingLotDB.get());
    }
}
