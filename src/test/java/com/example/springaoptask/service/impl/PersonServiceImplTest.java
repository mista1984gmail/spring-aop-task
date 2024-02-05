package com.example.springaoptask.service.impl;

import com.example.EntityNotFoundException;
import com.example.springaoptask.entity.dto.PersonDto;
import com.example.springaoptask.entity.model.House;
import com.example.springaoptask.entity.model.Person;
import com.example.springaoptask.mapper.HouseMapper;
import com.example.springaoptask.mapper.PersonMapper;
import com.example.springaoptask.repository.HouseRepository;
import com.example.springaoptask.repository.PersonRepository;
import com.example.springaoptask.util.ConstantsForTest;
import com.example.springaoptask.util.HouseTestData;
import com.example.springaoptask.util.PersonTestData;
import com.example.springaoptask.web.request.PersonRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {


    @Mock
    private HouseRepository houseRepository;

    @Mock
    private HouseMapper houseMapper;
    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void shouldDeletePerson() {
        // given
        UUID uuid = ConstantsForTest.UUID_PERSON;
        Optional<Person> person = Optional.of(PersonTestData.builder()
                .build()
                .buildPerson());

        when(personRepository.findByUuidPerson(uuid))
                .thenReturn(person);
        doNothing()
                .when(personRepository)
                .delete(person.get());

        //when
        personService.delete(uuid);

        //then
        verify(personRepository).delete(any());
    }

    @Test
    void shouldNotDeletePersonAndThrowsPersonNotFoundException() {
        // given
        UUID uuid = ConstantsForTest.UUID_PERSON;
        String errorMessage = "Person with uuid: " + uuid + " not found";
        Person person = PersonTestData.builder()
                .withId(null)
                .withUuidPerson(null)
                .build()
                .buildPerson();

        when(personRepository.findByUuidPerson(uuid))
                .thenThrow(new EntityNotFoundException(Person.class, uuid));

        //when
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            personService.delete(uuid);
        });

        //then
        Assertions.assertEquals(errorMessage, thrown.getMessage());
        verify(personRepository, never()).delete(person);
    }

     @Test
    void shouldNotGetPersonByUUIDAndThrowsHouseNotFoundException() {
        // given
        UUID uuid = ConstantsForTest.UUID_PERSON;
        String errorMessage = "Person with uuid: " + uuid + " not found";

         when(personRepository.findByUuidPerson(uuid))
                 .thenThrow(new EntityNotFoundException(Person.class, uuid));

         //when
         EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
             personService.findByUUID(uuid);
         });

        //then
        Assertions.assertEquals(errorMessage, thrown.getMessage());
    }

    @Test
    void shouldGetPersonByUUID() {
        // given
        UUID uuid = ConstantsForTest.UUID_PERSON;
        Optional<Person> person = Optional.of(PersonTestData.builder()
                .build()
                .buildPerson());
        PersonDto expected = PersonTestData.builder()
                .build()
                .buildPersonDto();

        when(personRepository.findByUuidPerson(uuid))
                .thenReturn(person);
        when(personMapper.entityToDto(person.get()))
                .thenReturn(expected);

        //when
        PersonDto actual = personService.findByUUID(uuid);

        //then
        assertEquals(expected, actual);
        verify(personRepository).findByUuidPerson(uuid);
        verify(personMapper).entityToDto(person.get());
    }

     @Test
    void shouldCreatePerson() {
        // given
        PersonRequest personRequestForSave = PersonTestData.builder()
                .build()
                .buildPersonRequest();
        Person personForSave = PersonTestData.builder()
                .build()
                .buildPerson();
         Person expected = PersonTestData.builder()
                 .build()
                 .buildPerson();
        PersonDto expectedDto = PersonTestData.builder()
                .build()
                .buildPersonDto();
         Optional<House> optionalHouseFromDB = Optional.of(HouseTestData.builder()
                 .build()
                 .buildHouse());
         UUID uuid = ConstantsForTest.UUID_HOUSE;

         when(houseRepository.findByUuidHouse(uuid))
                 .thenReturn(optionalHouseFromDB);
        when(personMapper.requestToModel(personRequestForSave))
                .thenReturn(personForSave);
        when(personRepository.save(personForSave))
                .thenReturn(expected);
         when(personMapper.entityToDto(expected))
                 .thenReturn(expectedDto);

        //when
        PersonDto actual = personService.save(personRequestForSave, uuid);

        //then
        verify(personRepository).save(personForSave);

        assertEquals(expectedDto, actual);
    }

    @Test
    void shouldUpdatePerson() {
        // given
        PersonDto personDtoForUpdate = PersonTestData.builder()
                .withSurname("Sergeev")
                .build()
                .buildPersonDto();
        Person personSaved = PersonTestData.builder()
                .withSurname("Sergeev")
                .build()
                .buildPerson();
        PersonDto personDtoSaved = PersonTestData.builder()
                .withSurname("Sergeev")
                .build()
                .buildPersonDto();
        PersonRequest personRequestForUpdate = PersonTestData.builder()
                .withSurname("Sergeev")
                .build()
                .buildPersonRequest();
        Optional<Person> personFromDB = Optional.of(PersonTestData.builder()
                .build()
                .buildPerson());
        UUID uuid = ConstantsForTest.UUID_PERSON;

        when(personRepository.findByUuidPerson(uuid)).thenReturn(personFromDB);
        when(personRepository.save(any())).thenReturn(personSaved);
        when(personMapper.entityToDto(personSaved)).thenReturn(personDtoSaved);

        //when
        PersonDto expected = personService.update(uuid, personRequestForUpdate);

        //then
        verify(personRepository).save(any());

        assertThat(personDtoForUpdate)
                .hasFieldOrPropertyWithValue(Person.Fields.uuidPerson, expected.getUuidPerson())
                .hasFieldOrPropertyWithValue(Person.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Person.Fields.surname, expected.getSurname())
                .hasFieldOrPropertyWithValue(Person.Fields.sex, expected.getSex())
                .hasFieldOrPropertyWithValue(Person.Fields.passportSeries, expected.getPassportSeries())
                .hasFieldOrPropertyWithValue(Person.Fields.passportNumber, expected.getPassportNumber())
                .hasFieldOrPropertyWithValue(Person.Fields.createDate, expected.getCreateDate())
                .hasFieldOrPropertyWithValue(Person.Fields.updateDate, expected.getUpdateDate());
    }

   @Test
    void shouldNotUpdatePersonAndThrowsPersonNotFoundException() throws Exception {
        // given
        UUID uuid = ConstantsForTest.UUID_PERSON;
        PersonRequest personRequestForUpdate = PersonTestData.builder()
                .withSurname("Serveev")
                .build()
                .buildPersonRequest();
        String errorMessage = "Person with uuid: " + uuid + " not found";

       when(personRepository.findByUuidPerson(uuid))
               .thenThrow(new EntityNotFoundException(Person.class, uuid));

       //when
       EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
           personService.update(uuid, personRequestForUpdate);
       });

        //then
        Assertions.assertEquals(errorMessage, thrown.getMessage());
    }

}