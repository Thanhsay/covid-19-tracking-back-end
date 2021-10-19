package com.example.covid19tracking.Repo;

import com.example.covid19tracking.Model.City;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends PagingAndSortingRepository<City, Long> {
}
