package com.example.currencyconverter;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = { Market.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

//    public abstract
}
