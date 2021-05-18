package com.repositories;

import com.entities.Weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherSqlRepository extends JpaRepository<Weather, Long> {
    
}
