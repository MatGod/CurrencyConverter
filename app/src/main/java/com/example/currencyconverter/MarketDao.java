package com.example.currencyconverter;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import io.reactivex.Single;

@Dao
public interface MarketDao {

    @Query("SELECT * FROM market")
    Single<List<Market>> getAll();

    @Query("SELECT * FROM market WHERE id = :id")
    Single<Market> getById(long id);

    @Insert
    void insert(Market market);

    @Update
    void update(Market market);

    @Delete
    void delete(Market market);

}
