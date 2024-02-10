package com.example.aklah.Model.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.aklah.Model.Meal;

@Database(entities = {Meal.class}, version = 1)
//@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;
    public abstract MealDAO getMealDAO();
    public static synchronized AppDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "Meal")
                    .build();
        }
        return instance;
    }
}
