package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
@ActiveProfiles(profiles = "test")
public class ParkingLotControllerTest {

    @MockBean
    ParkingLotService parkingLotService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    private ParkingLot createParkingLot(String name) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setCapacity(100);
        parkingLot.setLocation("BGC");

        return parkingLot;
    }

    @Test
    void should_save_new_parkinglot_when_capacity_is_greater_than_1_and_parkinglot_is_new() throws Exception {
        ParkingLot parkingLot = createParkingLot("ParkingLotTest");

        when(parkingLotService.saveParkingLot(parkingLot)).thenReturn(parkingLot);

        ResultActions resultOfExecution = mvc.perform(post("/parkingLots")
                .content(objectMapper.writeValueAsString(parkingLot))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultOfExecution.andExpect(status().isCreated());
    }
}
