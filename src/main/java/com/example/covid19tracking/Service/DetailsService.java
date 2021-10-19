package com.example.covid19tracking.Service;

import com.example.covid19tracking.Model.AllCity;
import com.example.covid19tracking.Model.Details;
import com.example.covid19tracking.Repo.DetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.sql.Date;
import java.util.List;

@Service
public class DetailsService {
    @Autowired
    DetailsRepo detailsRepo;

    //add new detail
    public Details saveDetails(Details details){
        return detailsRepo.save(details);
    }

    //pagination
    public List<Details> findAll (int page, int offset){
        Pageable pageable = PageRequest.of(page -1, offset, Sort.by(Sort.Direction.DESC,"infected"));
        Page<Details> detailsPage = detailsRepo.findAll(pageable);
        List<Details> detailsList = detailsPage.getContent();
        return detailsList;
    }

    //find city by id and pagination sort by datetime
    public List<Details> findAllCity (int id, int page, int offset){
        Pageable pageable = PageRequest.of(page -1, offset, Sort.by(Sort.Direction.DESC,"date"));
        List<Details> detailsList = detailsRepo.findByCityId(id, pageable);
        return detailsList;
    }
    //Find all
    public List<Details> findAll(){
        return (List<Details>) detailsRepo.findAll();
    }
    //get all values
    public int allItems() {
        return (int) detailsRepo.count();
    }

    //find by id
    public Details findById(int id){
        return detailsRepo.findById((long) id).get();
    }
    //findAllByCityId
    public List<Details> allCityById(int id){
        return detailsRepo.findAllByCityId(id);
    }
    //Search detail in table 2
    public List<Details> searchingDetailCity(int city, Date date, int page, int offset){
        Pageable pageable = PageRequest.of(page-1, offset, Sort.by(Sort.Direction.DESC, "date"));
        List<Details> detailsList = detailsRepo.searchingDetails(city, date, pageable);
        return detailsList;
    }
    //search details by only city
    public List<Details> searchingDetailCity(int city, int page, int offset){
        Pageable pageable = PageRequest.of(page-1, offset, Sort.by(Sort.Direction.DESC, "date"));
        List<Details> detailsList = detailsRepo.searchingDetailsByCity(city, pageable);
        return detailsList;
    }
    //search all detail by only city
    public List<Details> searchingDetailCity(int city){
        List<Details> detailsList = detailsRepo.searchingDetailsByCity(city);
        return detailsList;
    }
    //search details by only date
    public List<Details> searchingDetailCity(Date date, int page, int offset){
        Pageable pageable = PageRequest.of(page-1, offset, Sort.by(Sort.Direction.DESC, "date"));
        List<Details> detailsList = detailsRepo.searchingDetailsByDate(date, pageable);
        return detailsList;
    }
    //search details by only date
    public List<Details> searchingDetailCity(Date date){
        List<Details> detailsList = detailsRepo.searchingDetailsByDate(date);
        return detailsList;
    }
}
