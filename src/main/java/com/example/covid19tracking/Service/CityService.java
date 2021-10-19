package com.example.covid19tracking.Service;

import com.example.covid19tracking.Model.City;
import com.example.covid19tracking.Repo.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    CityRepo cityRepo;

    public List<City> findAllCity(){
        return (List<City>) cityRepo.findAll();
    }

    public City findCityById(int id){
        return cityRepo.findById((long) id).get();
    }
}
