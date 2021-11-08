package com.example.covidtrackingapp

import android.app.Application

class RiskApplication : Application() {
    // this creates an instance of a database

    val database by lazy { RiskRoomDatabase.getDatabase(this) }
    val repository by lazy { RiskRepository(database.riskDAO()) }

}