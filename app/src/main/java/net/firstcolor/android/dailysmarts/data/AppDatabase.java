package net.firstcolor.android.dailysmarts.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Quote.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase{
    public abstract MyQuotesDao myQuotesDao();
}
