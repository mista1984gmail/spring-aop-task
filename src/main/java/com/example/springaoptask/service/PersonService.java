package com.example.springaoptask.service;

import com.example.springaoptask.entity.dto.PersonDto;
import com.example.springaoptask.web.request.PersonRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PersonService {

    PersonDto save(PersonRequest personRequest, UUID houseUUID);

    void delete(UUID uuid);

    PersonDto update(UUID uuid, PersonRequest personRequest);

    PersonDto findByUUID(UUID uuid);

    Page<PersonDto> findAllWithPagination(Integer page, Integer size, String orderBy, String direction);

}
