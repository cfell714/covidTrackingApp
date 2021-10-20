package com.example.covidtrackingapp

import kotlinx.coroutines.flow.Flow

class RiskRepository (private val riskDao: RiskDAO) {

    val allRisks:Flow<List<Risk>> = riskDao.getAlphabeticalRisks()

    suspend fun insert(risk:Risk){
        riskDao.insert(risk)
    }

    suspend fun delete(id:Int) {
        riskDao.delete(id)
    }

    fun select(id:Int) :Flow<Risk> {
        return riskDao.select(id)
    }

    suspend fun update(id:Int, location:String, location_state:String, number_people:Int, duration:Int, masks:Boolean, vaccinated:String){
        riskDao.update(id, location, location_state, number_people, duration, masks, vaccinated)
    }

}