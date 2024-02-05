package com.example.springaoptask.config;

import com.example.springaoptask.mapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public PersonMapper personMapper (){
        return new PersonMapperImpl();
    }

    @Bean
    public HouseMapper houseMapper (){
        return new HouseMapperImpl();
    }

    @Bean
    public HouseHistoryMapper houseHistoryMapper (){
        return new HouseHistoryMapperImpl();
    }
}
