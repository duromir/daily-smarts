package net.firstcolor.android.dailysmarts.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyQuotesDao {

    @Query("SELECT * FROM quotes ORDER BY id DESC")
    List<Quote> getAll();

    @Insert
    void insertAll(List<Quote> quotes);

    @Delete
    void delete(List<Quote> quotes);

}
