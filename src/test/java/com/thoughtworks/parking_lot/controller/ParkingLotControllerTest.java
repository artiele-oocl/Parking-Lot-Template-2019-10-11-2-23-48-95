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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private List<ParkingLot> parkingLotList = new ArrayList<>();
    private ParkingLot createParkingLot(String name) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setCapacity(100);
        parkingLot.setLocation("BGC");

        return parkingLot;
    }

    @Test
    void should_save_new_parkinglot_when_capacity_is_greater_than_1_and_parkinglot_is_new() throws Exception {
        ParkingLot parkingLot = createParkingLot("myParkingLot");

        when(parkingLotService.saveParkingLot(parkingLot)).thenReturn(parkingLot);

        ResultActions resultOfExecution = mvc.perform(post("/parkingLots")
                .content(objectMapper.writeValueAsString(parkingLot))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultOfExecution.andExpect(status().isCreated());
    }

    @Test
    void should_delete_parking_lot_when_parking_lot_name_is_found_in_DB() throws Exception {
        ParkingLot parkingLot = createParkingLot("myParkingLot");

        when(parkingLotService.deleteParkingLot(anyString())).thenReturn(parkingLot);

        ResultActions resultOfExecution = mvc.perform(delete("/parkingLots/{name}", "myParkingLot"));

        resultOfExecution.andExpect(status().isOk());
    }

    @Test
    void should_show_parking_lot_when_no_name_is_specified() throws Exception {
        ParkingLot parkingLot = createParkingLot("myParkingLot");

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "0");
        requestParams.add("pageSize", "5");

        parkingLotList.add(parkingLot);

        when(parkingLotService.getAllParkingLots(0, 5)).thenReturn(parkingLotList);

        ResultActions resultOfExecution = mvc.perform(get("/parkingLots").params(requestParams));

        resultOfExecution.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("myParkingLot")));
    }


}
