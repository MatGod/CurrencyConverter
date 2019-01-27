package com.example.currencyconverter;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MarketDao {

    @Query("SELECT * FROM markets")
    List<Market> getAll();

    @Query("SELECT * FROM markets WHERE id = :id")
    Market getById(long id);

    @Insert
    void insert(Market market);

    @Update
    void update(Market market);

    @Delete
    void delete(Market market);
}
