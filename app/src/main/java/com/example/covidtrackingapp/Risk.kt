package com.example.covidtrackingapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "risk_table")
class Risk(@PrimaryKey(autoGenerate = true) val id:Int,
           @ColumnInfo(name="location") val location:String,
           @ColumnInfo(name="location_state") val location_state:String,
           @ColumnInfo(name="number_people") val number_people:String,
           @ColumnInfo(name="duration") val duration:String,
           @ColumnInfo(name="masks") val masks:String,
           @ColumnInfo(name="vaccinated") val vaccinated:String)
        // @ColumnInfo(name="location_locationPosRatio") val locationRatio:String,
        // @ColumnInfo(name="location_stateCases") val cases:String,
        // @ColumnInfo(name="vaccinations_completedRatio") val vacCompleted:String)