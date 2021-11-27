package com.example.covidtrackingapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer

class SecondActivity : AppCompatActivity(){

    private lateinit var recyclerview: RecyclerView
    private lateinit var buttonLogEntry: Button
    private lateinit var buttonCalcRisk: Button

    private val riskViewModel:RiskViewModel by viewModels{
        RiskViewModelFactory((application as RiskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        recyclerview = findViewById(R.id.recyclerview)
        buttonLogEntry = findViewById(R.id.button_logEntry)
        buttonCalcRisk = findViewById(R.id.button_risk)

        val adapter = RiskListAdapter()
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        riskViewModel.allRisks.observe(this, androidx.lifecycle.Observer {
                risks -> risks?.let {
            adapter.submitList(it)
        }
        })

        buttonLogEntry.setOnClickListener{
            launchThirdActivity()
        }

        buttonCalcRisk.setOnClickListener{
            launchFifthActivity()
        }

    }

    private fun launchThirdActivity(){
        val intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
    }

    private fun launchFifthActivity(){
        val intentArray: ArrayList<String> = ArrayList()
        for(i in 1..20) {
            try {
                riskViewModel.select(i).observe(this, Observer {
                    if (it != null) {
                        // getting information from location / determining risk depending on specific location information
                        var location = it.location
                        var location_count = 75
                        if(location == "bar"){location_count = 65}
                        println("location: " + location + "chelsea")
                        println("location count: " + location_count)

                        // getting information from state location
                        var location_state = it.location_state
                        var location_state_count = 75
                        var location_vacComp = it.vacCompleted.toFloat() // vaccinations completed
                        var location_posRatio = it.locationRatio.toFloat() // positivity ratio
                        var location_infectionRate = it.cases.toFloat() // infection rate
                        var location_tot = (location_vacComp * 75 + location_posRatio
                                * 75 + location_infectionRate * 75) / 3
                        println("location_state: " + location_state)
                        println("location_tot: " + location_tot)

                        // getting information on number of people
                        var numPeople = it.number_people.toFloat()
                        var numPeople_final = 175
                        if(numPeople <= 10){numPeople_final = 75}
                        if(numPeople <= 50){numPeople_final = 125}
                        println("numPeople: " + numPeople)
                        println("numPeople_final: " + numPeople_final)

                        // getting information on duration of time
                        var duration = it.duration.toFloat()
                        var duration_final = 175
                        if(duration <= 15){duration_final = 50}
                        if(duration <= 60){duration_final = 100}
                        println("duration: " + duration)
                        println("duration_final: " + duration_final)

                        // getting information from masks checkboxes
                        // higher number = higher risk / contributes to higher probability of risk
                        var masks = it.masks
                        var masks_count = 275
                        if(masks == "zero"){masks_count = 75}
                        if(masks == "one"){masks_count = 175}
                        if(masks == "two"){masks_count = 225}
                        if(masks == "three"){masks_count = 275}
                        println("masks: " + masks)
                        println("masks count: " + masks_count)

                        // getting information from vaccination status
                        var vaccinated = it.vaccinated
                        var vaccinated_count = 225
                        if(vaccinated == "yes"){vaccinated_count = 125}
                        println("vaccinated: " + vaccinated)
                        println("vaccinated count: " + vaccinated_count)

                        var finalProbability = location_count + location_tot + numPeople_final
                        + duration_final + masks_count + vaccinated_count
                        println("Final Probability: " + finalProbability)

                        intentArray.add(finalProbability.toString())
                        println("Intent Array: " + intentArray)


                    }
                })
            } catch(e: Exception){
                println("CHELSEA: not sure what happened here")
            }
        }
        val intent = Intent(this, FifthActivity::class.java)
        // sending intent as a string form since floats are being funky
        intent.putStringArrayListExtra("intent", intentArray)
        startActivity(intent)

    }

}