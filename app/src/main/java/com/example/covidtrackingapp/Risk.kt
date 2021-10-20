package com.example.covidtrackingapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "risk_table")
class Risk(@PrimaryKey(autoGenerate = true) val id:Int,
           @ColumnInfo(name="location") val location:String,
           @ColumnInfo(name="location_state") val location_state:String,
           @ColumnInfo(name="number_people") val number_people:Int,
           @ColumnInfo(name="duration") val duration:Int,
           @ColumnInfo(name="masks") val masks:Boolean,
           @ColumnInfo(name="vaccinated") val vaccinated:String)