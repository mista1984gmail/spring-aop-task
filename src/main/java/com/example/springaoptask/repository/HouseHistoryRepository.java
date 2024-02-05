package com.example.springaoptask.repository;

import com.example.springaoptask.entity.model.HouseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseHistoryRepository extends JpaRepository<HouseHistory, Long> {

}
