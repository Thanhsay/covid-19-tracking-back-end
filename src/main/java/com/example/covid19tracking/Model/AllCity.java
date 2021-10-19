package com.example.covid19tracking.Model;

import lombok.Data;

@Data
public class AllCity {
    private int city;
    private int total_infected;
    private int new_infected = 0;
    private int total_recovered;
    private int new_recovered = 0;
    private int total_deaths;
    private int new_deaths = 0;
}
