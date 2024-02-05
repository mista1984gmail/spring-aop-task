package com.example.springaoptask.service.impl;

import com.example.EntityNotFoundException;
import com.example.springaoptask.entity.dto.HouseDto;
import com.example.springaoptask.entity.dto.HouseHistoryDto;
import com.example.springaoptask.entity.dto.PersonDto;
import com.example.springaoptask.entity.model.House;
import com.example.springaoptask.entity.model.HouseHistory;
import com.example.springaoptask.entity.model.Person;
import com.example.springaoptask.mapper.HouseHistoryMapper;
import com.example.springaoptask.mapper.HouseMapper;
import com.example.springaoptask.mapper.PersonMapper;
import com.example.springaoptask.repository.HouseHistoryRepository;
import com.example.springaoptask.repository.HouseRepository;
import com.example.springaoptask.repository.PersonRepository;
import com.example.springaoptask.service.HouseHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HouseHistoryServiceImpl implements HouseHistoryService {

	private final HouseHistoryRepository houseHistoryRepository;
	private final PersonRepository personRepository;
	private final HouseRepository houseRepository;
	private final HouseHistoryMapper houseHistoryMapper;
	private final PersonMapper personMapper;
	private final HouseMapper houseMapper;

	/**
	 * Сохраняет HouseHistory из houseHistoryDto
	 * задает время создания
	 *
	 * @param houseHistoryDto HouseHistoryDto с информацией о создании
	 * @return HouseHistory
	 */
	@Override
	@Transactional
	public HouseHistory save(HouseHistoryDto houseHistoryDto) {
		HouseHistory houseHistory = houseHistoryMapper.dtoToEntity(houseHistoryDto);
		houseHistory.setCreateDate(LocalDateTime.now());
		return houseHistoryRepository.save(houseHistory);
	}

	/**
	 * Находит всех жильцов (Person) когда либо проживающих в доме (House)
	 *
	 * @throws EntityNotFoundException если House не найден
	 * @param uuidHouse House uuid
	 * @return List<PersonDto> список жильцов (Person)
	 */
	@Override
	public List<PersonDto> findAllTenantsEverLivedInHouse(UUID uuidHouse) {
		House houseFromDb = houseRepository.findByUuidHouse(uuidHouse).orElseThrow(() -> new EntityNotFoundException(House.class, uuidHouse));
		return personMapper.entityListToDtoList(personRepository.findAllTenantsEverLivedInHouse(houseFromDb.getId()));
	}

	/**
	 * Находит всех владельцев (Person) когда либо владевших домом (House)
	 *
	 * @throws EntityNotFoundException если House не найден
	 * @param uuidHouse House uuid
	 * @return List<PersonDto> список владельцев (Person)
	 */
	@Override
	public List<PersonDto> findAllOwnersEverOwnedHouse(UUID uuidHouse) {
		House houseFromDb = houseRepository.findByUuidHouse(uuidHouse).orElseThrow(() -> new EntityNotFoundException(House.class, uuidHouse));
		return personMapper.entityListToDtoList(personRepository.findAllOwnersEverOwnedHouse(houseFromDb.getId()));
	}

	/**
	 * Находит все дома (House), в которых когда-либо проживал жилец (Person)
	 *
	 * @throws EntityNotFoundException если Person не найден
	 * @param uuidPerson Person uuid
	 * @return List<HouseDto> список домов (House)
	 */
	@Override
	public List<HouseDto> findAllHousesEverLivedPerson(UUID uuidPerson) {
		Person personFromDb = personRepository.findByUuidPerson(uuidPerson).orElseThrow(() -> new EntityNotFoundException(Person.class, uuidPerson));
		return houseMapper.entityListToDtoList(houseRepository.findAllHousesEverLivedPerson(personFromDb.getId()));
	}

	/**
	 * Находит все дома (House), которыми когда-либо владел (Person)
	 *
	 * @throws EntityNotFoundException если Person не найден
	 * @param uuidPerson Person uuid
	 * @return List<HouseDto> список домов (House)
	 */
	@Override
	public List<HouseDto> findAllHousesEverOwnedPerson(UUID uuidPerson) {
		Person personFromDb = personRepository.findByUuidPerson(uuidPerson).orElseThrow(() -> new EntityNotFoundException(Person.class, uuidPerson));
		return houseMapper.entityListToDtoList(houseRepository.findAllHousesEverOwnedPerson(personFromDb.getId()));
	}

}
