package com.example.springaoptask.service;

import com.example.springaoptask.entity.dto.HouseDto;
import com.example.springaoptask.entity.dto.PersonDto;
import com.example.springaoptask.web.request.OwnerRequest;

import java.util.List;
import java.util.UUID;

public interface OwnerService {

    List<PersonDto> findAllOwners(UUID uuidHouse);
    List<HouseDto> findAllHousesPerson(UUID uuidPerson);

    void addOwner(OwnerRequest ownerRequest);

    void removeOwner(OwnerRequest ownerRequest);

}
