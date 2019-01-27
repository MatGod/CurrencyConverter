package com.example.currencyconverter;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface MarketDao {

    @Query("SELECT * FROM market")
    Observable<List<Market>> getAll();

    @Query("SELECT * FROM market WHERE id = :id")
    Observable<Market> getById(long id);

    @Insert
    Completable insert(Market market);

    @Update
    Completable update(Market market);

    @Delete
    Completable delete(Market market);

}
