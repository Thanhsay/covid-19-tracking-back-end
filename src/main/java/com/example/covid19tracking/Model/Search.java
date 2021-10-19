package com.example.covid19tracking.Model;

import lombok.Data;

import java.sql.Date;
@Data
public class Search {
    private int city;
    private Date date;
    private int page;
    private int offset;
}
