package com.example.covid19tracking.Controller;

import com.example.covid19tracking.Model.*;
import com.example.covid19tracking.Service.CityService;
import com.example.covid19tracking.Service.DetailsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/details/")
public class DetailsController {

    @Autowired
    DetailsService detailsService;
    @Autowired
    CityService cityService;

    @PostMapping("/save")
    public String save(@RequestBody Details details){
        String state;
        state = "success";
        try {
            detailsService.saveDetails(details);
        } catch (Exception e){
            e.printStackTrace();
            state = "error";
        }
        return state;
    }

    @Data
    public class Pagination {
        private int city;
        private int offset = 10;
        private int page = 1;
        private int totalPage;
        private int totalItems;
        List<Details> detailsList;
        List<AllCity> allCityList;
    }

    //find all and pagination
    @PutMapping("/pagination/{page}/{offset}")
    public Pagination PaginationList (@PathVariable int page,
                                      @PathVariable int offset){
        Pagination pagination = new Pagination();
        List<City> cities = cityService.findAllCity();
        List<AllCity> allCityList1 = new ArrayList<>();
        List<AllCity> allCityList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        long mili = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(mili);
        calendar.setTime(date);
        int all_infected = 0;
        int all_recovered = 0;
        int all_deaths = 0;
        int all_new_infected = 0;
        int all_new_recovered = 0;
        int all_new_deaths = 0;
        for(Object ob : cities){
            City city = (City) ob;
            List<Details> cityList = new ArrayList<>();
            AllCity allCity = new AllCity();
            Long cityId = city.getCity_id();
            int cityIdd = Math.toIntExact(cityId);
            cityList = detailsService.allCityById(cityIdd);
            int total_infected = 0;
            int new_infected = 0;
            int total_recovered = 0;
            int new_recovered = 0;
            int total_deaths = 0;
            int new_deaths = 0;
            for (Object o : cityList){
                Details details  = (Details) o;
                total_infected += details.getInfected();
                total_recovered += details.getRecovered();
                total_deaths += details.getDeaths();
                if(date.toString().equals(details.getDate().toString())){
                    new_infected = details.getInfected();
                    new_recovered = details.getRecovered();
                    new_deaths = details.getDeaths();
                }
            }
            allCity.setCity(cityIdd);
            allCity.setTotal_infected(total_infected);
            allCity.setTotal_recovered(total_recovered);
            allCity.setTotal_deaths(total_deaths);
            allCity.setNew_infected(new_infected);
            allCity.setNew_recovered(new_recovered);
            allCity.setNew_deaths(new_deaths);
            allCityList.add(allCity);
            //set parameter for country
            all_infected += total_infected;
            all_recovered += total_recovered;
            all_deaths += total_deaths;
            all_new_infected += new_infected;
            all_new_recovered += new_recovered;
            all_new_deaths += new_deaths;
        }
        AllCity country = new AllCity();
        country.setCity(2);
        country.setTotal_infected(all_infected);
        country.setNew_recovered(all_new_recovered);
        country.setTotal_deaths(all_deaths);
        country.setNew_infected(all_new_infected);
        country.setTotal_recovered(all_recovered);
        country.setNew_deaths(all_new_deaths);
        allCityList.add(country);
        //sort arraylist by total_infected
        Collections.sort(allCityList,new Comparator<AllCity>(){
            @Override
            public int compare(AllCity allCity1, AllCity allCity2){
                if(allCity1.getTotal_infected()<allCity2.getTotal_infected()){
                    return 1;
                }else {
                    return -1;
                }
            }
        });
        int total;
        if(page*offset<64){
            total = page*offset;
        } else {
            total = 64;
        }
        for(int i = (page-1)*offset; i<(total); i++){
            allCityList1.add(allCityList.get(i));
        }
        pagination.setAllCityList(allCityList1);
        pagination.setPage(page);
        pagination.setOffset(offset);
        int totalItems = allCityList.size();
        pagination.setTotalItems(totalItems);
        if(totalItems%offset==0){
            pagination.setTotalPage(totalItems/offset);
        }else {
            pagination.setTotalPage(totalItems/offset+1);
        }
        return pagination;
    }

    //search city in table 1
    @PutMapping("/searching/{id}")
    public Pagination Searching(@PathVariable int id){
        Pagination pagination = new Pagination();
        List<AllCity> allCityList = new ArrayList<>();
        AllCity allCity = new AllCity();
        List<Details> detailsList = new ArrayList<>();
        long mili = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(mili);
        detailsList = detailsService.allCityById(id);
        int total_infected = 0;
        int new_infected = 0;
        int total_recovered = 0;
        int new_recovered = 0;
        int total_deaths = 0;
        int new_deaths = 0;
        for (Object o : detailsList){
            Details details  = (Details) o;
            total_infected += details.getInfected();
            total_recovered += details.getRecovered();
            total_deaths += details.getDeaths();
            if(date.toString().equals(details.getDate().toString())){
                new_infected = details.getInfected();
                new_recovered = details.getRecovered();
                new_deaths = details.getDeaths();
            }
        }
        allCity.setCity(id);
        allCity.setTotal_infected(total_infected);
        allCity.setTotal_recovered(total_recovered);
        allCity.setTotal_deaths(total_deaths);
        allCity.setNew_infected(new_infected);
        allCity.setNew_recovered(new_recovered);
        allCity.setNew_deaths(new_deaths);
        allCityList.add(allCity);
        pagination.setAllCityList(allCityList);
        return pagination;
    }
    //Data for home
    @PutMapping("/home")
    public HomeData Home(){
        HomeData homeData = new HomeData();
        List<City> cities = cityService.findAllCity();
        long mili = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(mili);
        int all_infected = 0;
        int all_recovered = 0;
        int all_deaths = 0;
        int all_new_infected = 0;
        int all_new_recovered = 0;
        int all_new_deaths = 0;
        for(Object ob : cities){
            City city = (City) ob;
            List<Details> cityList = new ArrayList<>();
            Long cityId = city.getCity_id();
            int cityIdd = Math.toIntExact(cityId);
            cityList = detailsService.allCityById(cityIdd);
            int total_infected = 0;
            int new_infected = 0;
            int total_recovered = 0;
            int new_recovered = 0;
            int total_deaths = 0;
            int new_deaths = 0;
            for (Object o : cityList){
                Details details  = (Details) o;
                total_infected += details.getInfected();
                total_recovered += details.getRecovered();
                total_deaths += details.getDeaths();
                if(date.toString().equals(details.getDate().toString())){
                    new_infected = details.getInfected();
                    new_recovered = details.getRecovered();
                    new_deaths = details.getDeaths();
                }
            }
            //set parameter for country
            all_infected += total_infected;
            all_recovered += total_recovered;
            all_deaths += total_deaths;
            all_new_infected += new_infected;
            all_new_recovered += new_recovered;
            all_new_deaths += new_deaths;
        }
        AllCity country = new AllCity();
        country.setCity(2);
        country.setTotal_infected(all_infected);
        country.setNew_recovered(all_new_recovered);
        country.setTotal_deaths(all_deaths);
        country.setNew_infected(all_new_infected);
        country.setTotal_recovered(all_recovered);
        country.setNew_deaths(all_new_deaths);
        homeData.setTotal(country);
        //country
        List<Details> detailsList = detailsService.findAll();
        List<Details> detailsListByDate = new ArrayList<>();
        for(int i = 0; i< detailsList.size(); i++){
            int infectedByDate = detailsList.get(i).getInfected();
            int recoveredByDate = detailsList.get(i).getRecovered();
            int deathsByDate = detailsList.get(i).getDeaths();
            Details details = new Details();
            for(int j=i+1; j< detailsList.size(); j++){
                if(detailsList.get(i).getDate().toString().equals(detailsList.get(j).getDate().toString())){
                    infectedByDate += detailsList.get(j).getInfected();
                    recoveredByDate += detailsList.get(j).getRecovered();
                    deathsByDate += detailsList.get(j).getDeaths();
                    detailsList.remove(detailsList.get(j));
                    j -= 1;
                }
            }
            details.setCity(2);
            details.setInfected(infectedByDate);
            details.setRecovered(recoveredByDate);
            details.setDeaths(deathsByDate);
            details.setDate(detailsList.get(i).getDate());
            detailsListByDate.add(details);
        }
        Collections.sort(detailsListByDate,new Comparator<Details>(){
            @Override
            public int compare(Details details1, Details details2){
                if(details1.getDate().compareTo(details2.getDate())>0){
                    return 1;
                }else {
                    return -1;
                }
            }
        });
        homeData.setDetailCountry(detailsListByDate);
        List<Details> totalDataInCountry = detailsListByDate;
        for(int i=0; i<totalDataInCountry.size()-1; i++){
            totalDataInCountry.get(i+1).setInfected(totalDataInCountry.
                    get(i).getInfected()+totalDataInCountry.get(i+1).getInfected());
            totalDataInCountry.get(i+1).setRecovered(totalDataInCountry.
                    get(i).getRecovered()+totalDataInCountry.get(i+1).getRecovered());
            totalDataInCountry.get(i+1).setDeaths(totalDataInCountry.
                    get(i).getDeaths()+totalDataInCountry.get(i+1).getDeaths());
        }
        homeData.setAllCountry(totalDataInCountry);
        //HCM
        List<Details> detailHCM = detailsService.searchingDetailCity(32);
        Collections.sort(detailHCM,new Comparator<Details>(){
            @Override
            public int compare(Details details1, Details details2){
                if(details1.getDate().compareTo(details2.getDate())>0){
                    return 1;
                }else {
                    return -1;
                }
            }
        });
        homeData.setDetailHCM(detailHCM);
        //HN
        List<Details> detailHN = detailsService.searchingDetailCity(26);
        Collections.sort(detailHN,new Comparator<Details>(){
            @Override
            public int compare(Details details1, Details details2){
                if(details1.getDate().compareTo(details2.getDate())>0){
                    return 1;
                }else {
                    return -1;
                }
            }
        });
        homeData.setDetailHN(detailHN);
        return homeData;
    }

    // find by id
    @PutMapping("/find/{id}")
    public Details Find(@PathVariable int id){
        return detailsService.findById(id);
    }
    // find by cityId and pagination
    @PutMapping("/findCity/{city}/{page}/{offset}")
    public Pagination City(@PathVariable int city, @PathVariable int page,
                           @PathVariable int offset){
        Pagination pagination = new Pagination();
//        Details details = detailsService.findById(id);
        List<Details> detailsList = detailsService.findAllCity(city, page, offset);
        List<Details> detailsList1 = detailsService.allCityById(city);
        int totalItems = detailsList1.size();
        int total;
        if(totalItems%offset==0){
            total = totalItems/offset;
        }else {
            total = totalItems/offset +1;
        }
        pagination.setCity(city);
        pagination.setPage(page);
        pagination.setOffset(offset);
        pagination.setTotalItems(totalItems);
        pagination.setTotalPage(total);
        pagination.setDetailsList(detailsList);
        return pagination;
    }
    //search in table 2
    @PutMapping("/searchingCity")
    public Pagination SearchCity(@RequestBody Search search){
        List<Details> detailsList = new ArrayList<>();
        List<Details> detailsList1 = new ArrayList<>();
        if(search.getCity()!=0 && search.getDate()!=null){
             detailsList = detailsService.
                    searchingDetailCity(search.getCity(), search.getDate(), search.getPage(), search.getOffset());
             detailsList1 = detailsService.
                     searchingDetailCity(search.getCity(), search.getDate(), search.getPage(), search.getOffset());
        }else{
            if(search.getCity()==0){
                detailsList = detailsService.
                        searchingDetailCity(search.getDate(), search.getPage(), search.getOffset());
                detailsList1 = detailsService.searchingDetailCity(search.getDate());
            }else {
                if(search.getDate()==null){
                    detailsList = detailsService.
                            searchingDetailCity(search.getCity(), search.getPage(), search.getOffset());
                    detailsList1 = detailsService.searchingDetailCity(search.getCity());
                }
            }
        }
        int count = detailsList1.size();
        int total=0;
        if(count%search.getOffset()==0){
            total = count/search.getOffset();
        }else {
            total = count/search.getOffset() + 1;
        }
        Pagination pagination = new Pagination();
        pagination.setPage(search.getPage());
        pagination.setCity(search.getCity());
        pagination.setDetailsList(detailsList);
        pagination.setOffset(search.getOffset());
        pagination.setTotalPage(total);
        pagination.setTotalItems(count);
        return pagination;
    }
}
