package com.example.covidtrackingapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RiskDAO {

    // gets risks, ordered by their id
    @Query("SELECT * FROM risk_table ORDER BY id ASC")
    fun getAlphabeticalRisks() : Flow<List<Risk>>

    // insert a risk
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(risk:Risk)

    // delete risk by id
    @Query("DELETE FROM risk_table WHERE id=:id")
    suspend fun delete(id:Int)

    // select risk by id
    @Query ("SELECT * FROM risk_table WHERE id=:id")
    fun select(id:Int) : Flow<Risk>

    // update task, must include all items in database
    @Query("UPDATE risk_table SET location=:location, location_state=:location_state, number_people=:number_people, duration=:duration, masks=:masks, vaccinated=:vaccinated, locationRatio=:locationRatio, cases=:cases, vacCompleted=:vacCompleted WHERE id=:id")
    suspend fun update(id:Int, location:String, location_state:String, number_people:String, duration:String, masks:String, vaccinated:String, locationRatio:String, cases:String, vacCompleted:String)


}