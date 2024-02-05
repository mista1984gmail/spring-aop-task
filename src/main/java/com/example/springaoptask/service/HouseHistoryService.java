package com.example.springaoptask.service;


import com.example.springaoptask.entity.dto.HouseDto;
import com.example.springaoptask.entity.dto.HouseHistoryDto;
import com.example.springaoptask.entity.dto.PersonDto;
import com.example.springaoptask.entity.model.HouseHistory;

import java.util.List;
import java.util.UUID;

public interface HouseHistoryService {

	HouseHistory save(HouseHistoryDto houseHistory);

	List<PersonDto> findAllTenantsEverLivedInHouse(UUID uuidHouse);

	List<PersonDto> findAllOwnersEverOwnedHouse(UUID uuidHouse);

	List<HouseDto> findAllHousesEverLivedPerson(UUID uuidPerson);

	List<HouseDto> findAllHousesEverOwnedPerson(UUID uuidPerson);

}
