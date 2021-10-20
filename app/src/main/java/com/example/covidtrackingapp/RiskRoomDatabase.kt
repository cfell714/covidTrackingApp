package com.example.covidtrackingapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Risk::class), version = 1, exportSchema = false)
public abstract class RiskRoomDatabase : RoomDatabase() {

    abstract fun riskDAO():RiskDAO

    companion object{
        @Volatile // singleton
        private var INSTANCE:RiskRoomDatabase? = null

        fun getDatabase(context: Context): RiskRoomDatabase{

            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RiskRoomDatabase::class.java,
                    "risk_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}