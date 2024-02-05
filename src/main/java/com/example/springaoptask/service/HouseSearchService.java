package com.example.springaoptask.service;

import com.example.springaoptask.entity.dto.HouseDto;

import java.util.List;

public interface HouseSearchService {

    List<HouseDto> findByCountryOrCityOrStreet(String name);

}
