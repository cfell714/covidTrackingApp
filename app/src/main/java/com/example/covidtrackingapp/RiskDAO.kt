package com.example.covidtrackingapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RiskDAO {

    @Query("SELECT * FROM risk_table ORDER BY id ASC")
    fun getAlphabeticalRisks() : Flow<List<Risk>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(risk:Risk)

    @Query("DELETE FROM risk_table WHERE id=:id")
    suspend fun delete(id:Int)

    @Query ("SELECT * FROM risk_table WHERE id=:id")
    fun select(id:Int) : Flow<Risk>

    @Query("UPDATE risk_table SET location=:location, location_state=:location_state, number_people=:number_people, duration=:duration, masks=:masks, vaccinated=:vaccinated WHERE id=:id")
    suspend fun update(id:Int, location:String, location_state:String, number_people:Int, duration:Int, masks:Boolean, vaccinated:String)


}