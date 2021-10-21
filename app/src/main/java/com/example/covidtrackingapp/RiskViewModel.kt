package com.example.covidtrackingapp

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class RiskViewModel (private val repository: RiskRepository) : ViewModel() {

    val allRisks: LiveData<List<Risk>> = repository.allRisks.asLiveData()

    fun insert(risk:Risk) = viewModelScope.launch {
        repository.insert(risk)
    }

    fun delete(id:Int) = viewModelScope.launch{
        repository.delete(id)
    }

    fun select(id:Int) : LiveData<Risk>{
        return repository.select(id).asLiveData()
    }

    fun update(id:Int, location:String, location_state:String, number_people:String, duration:String, masks:String, vaccinated:String) = viewModelScope.launch{
        repository.update(id, location, location_state, number_people, duration, masks, vaccinated)
    }


}
class RiskViewModelFactory(private val repository: RiskRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RiskViewModel::class.java)){
            return RiskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}