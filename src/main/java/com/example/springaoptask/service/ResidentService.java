package com.example.springaoptask.service;

import com.example.springaoptask.entity.dto.HouseDto;
import com.example.springaoptask.entity.dto.PersonDto;
import com.example.springaoptask.web.request.ResidenceRequest;

import java.util.List;
import java.util.UUID;

public interface ResidentService {

    List<PersonDto> getResidents(UUID uuidHouse);
    HouseDto getHouseLivePerson(UUID uuidPerson);
    void changeResidence(ResidenceRequest changeResidenceRequest);

}
