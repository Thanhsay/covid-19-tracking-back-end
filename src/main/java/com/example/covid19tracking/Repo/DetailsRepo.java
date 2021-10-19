package com.example.covid19tracking.Repo;

import com.example.covid19tracking.Model.Details;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface DetailsRepo extends CrudRepository<Details, Long>, PagingAndSortingRepository<Details, Long> {
    @Query("SELECT u from Details u WHERE u.city=?1")
    List<Details> findAllByCityId(int id);
    @Query("SELECT u from Details u WHERE u.city=?1")
    List<Details> findByCityId(int id, Pageable pageable);

    @Query("SELECT u from Details u WHERE (u.city=?1) AND (u.date=?2)")
    List<Details> searchingDetails(int city, Date date, Pageable pageable);
    @Query("SELECT u FROM Details u WHERE u.city=?1")
    List<Details> searchingDetailsByCity(int city, Pageable pageable);
    @Query("SELECT u FROM Details u WHERE u.date=?1")
    List<Details> searchingDetailsByDate(Date date, Pageable pageable);
    @Query("SELECT u FROM Details u WHERE u.city=?1")
    List<Details> searchingDetailsByCity(int city);
    @Query("SELECT u FROM Details u WHERE u.date=?1")
    List<Details> searchingDetailsByDate(Date date);
}
