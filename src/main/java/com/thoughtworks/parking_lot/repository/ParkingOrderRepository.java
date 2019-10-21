package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.entity.ParkingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ParkingOrderRepository extends JpaRepository <ParkingOrder, Long> {
    ParkingOrder findOneByPlateNumber(@Param("plateNumber") String plateNumber);
    ParkingOrder findOneByOrderNumber(@Param("orderNumber") Long orderNumber);
}
