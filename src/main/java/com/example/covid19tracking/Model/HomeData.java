package com.example.covid19tracking.Model;

import lombok.Data;

import java.util.List;
import java.util.PrimitiveIterator;

@Data
public class HomeData {
    private AllCity total;
    private List<Details> detailCountry;
    private List<Details> allCountry;
    private List<Details> detailHCM;
    private List<Details> detailHN;
}
