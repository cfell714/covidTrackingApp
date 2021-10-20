package com.example.covidtrackingapp

import android.app.Application

class RiskApplication : Application() {
 //   val database by
    val database by lazy { RiskRoomDatabase.getDatabase(this) }
    val repository by lazy { RiskRepository(database.riskDAO()) }

}