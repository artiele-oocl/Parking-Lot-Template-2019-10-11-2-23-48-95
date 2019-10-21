package com.thoughtworks.parking_lot.entity;

import javax.persistence.*;

@Entity
public class ParkingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderNumber;
    @Column(unique = true, nullable = false)
    private String parkingName;
    @Column(unique = true, nullable = false)
    private String plateNumber;
    private String creationTime;
    private String closeTime;
    private String orderStatus;

    public ParkingOrder() {
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
