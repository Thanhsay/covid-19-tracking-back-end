package com.example.covid19tracking.Model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "details")
public class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long details_id;

    @Column(name = "details_city_id")
    private int city;

    @Column(name = "infected")
    private int infected;

    @Column(name = "recovered")
    private int recovered;

    @Column(name = "deaths")
    private int deaths;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;
}
