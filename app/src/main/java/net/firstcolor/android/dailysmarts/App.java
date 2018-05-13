package net.firstcolor.android.dailysmarts;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import net.firstcolor.android.dailysmarts.data.AppDatabase;

public class App {

    public static Context appContext;
    private static AppDatabase database;

    public static AppDatabase getAppDB(){
        if(database == null){
            database = Room.databaseBuilder(appContext,
                    AppDatabase.class, "quotes")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    /**
     * This is probably an ugly hack
     * @param view
     * @return MainActivity
     */
    public static MainActivity getMainActivityByView(View view){
        try {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (MainActivity) context;
                }
                context = ((ContextWrapper)context).getBaseContext();
            }
        }
        catch (Exception e){
            try {
                //Android 7+
                return (MainActivity) view.findViewById(android.R.id.content).getContext();
            }
            catch (Exception ex){}
        }
        return null;
    }
}
