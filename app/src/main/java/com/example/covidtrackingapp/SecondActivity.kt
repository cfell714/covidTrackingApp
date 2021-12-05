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

                        // adding section here where if entry is not the first / the more entries there are
                        // the higher the risk, allocated 50 points to increasing number of events
                        var newEvent = 50.0f
                        if(i >= 2){ newEvent = 10.0f }
                        if(i >=4){ newEvent = 20.0f }
                        if(i >=6){ newEvent = 30.0f }
                        if(i >= 8){ newEvent = 50.0f }

                        // getting information from location / determining risk depending on specific location information
                        var location = it.location
                        var location_count = 75.0f
                        if(location == "outside - social distancing"){location_count = 20.0f}
                        if(location == "outside - no social distancing"){location_count = 35.0f}
                        if(location == "inside - social distancing"){location_count = 50.0f}
                        if(location == "inside - no social distancing"){location_count = 75.0f}
                        println("location: " + location + "chelsea")
                        println("location count: " + location_count)

                        // getting information from state location
                        var location_state = it.location_state
                        var location_state_count = 75.0f
                        var location_vacComp = it.vacCompleted.toFloat() // vaccinations completed
                        var location_posRatio = it.locationRatio.toFloat() // positivity ratio
                     //   var location_infectionRate = it.cases.toFloat() // infection rate

                        println("CHELSEA DOING TESTING HERE ")
                        println(location_vacComp * 75.0f)
                        println("pos rati" + location_posRatio)
                    //    println("inf rat" + location_infectionRate)

                        var location_tot = (location_vacComp * 75.0f + location_posRatio
                                * 75.0f) / 2.0f// + location_infectionRate * 75.0f) / 3.0f
                        println("location_state: " + location_state)
                        println("location_tot: " + location_tot)

                        // getting information on number of people
                        var numPeople = it.number_people.toFloat()
                        var numPeople_final = 175.0f
                        if(numPeople <= 50.0f){numPeople_final = 125.0f}
                        if(numPeople <= 10.0f){numPeople_final = 75.0f}
                       // if(numPeople <= 50.0f){numPeople_final = 125.0f}
                        println("numPeople: " + numPeople)
                        println("numPeople_final: " + numPeople_final)

                        // getting information on duration of time
                        var duration = it.duration.toFloat()
                        var duration_final = 125.0f
                        if(duration <= 60.0f){duration_final = 80.0f}
                        if(duration <= 15.0f){duration_final = 50.0f}
                        println("duration: " + duration)
                        println("duration_final: " + duration_final)

                        // getting information from masks checkboxes
                        // higher number = higher risk / contributes to higher probability of risk
                        var masks = it.masks
                        var masks_count = 275.0f
                        if(masks == "zero"){masks_count = 50.0f}
                        if(masks == "one"){masks_count = 150.0f}
                        if(masks == "two"){masks_count = 225.0f}
                        if(masks == "three"){masks_count = 275.0f}
                        println("masks: " + masks)
                        println("masks count: " + masks_count)

                        // getting information from vaccination status
                        var vaccinated = it.vaccinated
                        var vaccinated_count = 225.0f
                        if(vaccinated == "yes"){vaccinated_count = 100.0f}
                        println("vaccinated: " + vaccinated)
                        println("vaccinated count: " + vaccinated_count)

                        // calculating final probability
                        var finalProbability = location_count + location_tot
                        finalProbability += numPeople_final + duration_final
                        finalProbability += masks_count + vaccinated_count
                        finalProbability += newEvent
                        println("Final Probability: " + finalProbability)

                        intentArray.add(finalProbability.toString())
                        println("Intent Array: " + intentArray)

                        val intent = Intent(this, FifthActivity::class.java)
                        // sending intent as a string form since floats are being funky
                        intent.putStringArrayListExtra("intent", intentArray)
                        startActivity(intent)


                    }
                })
            } catch(e: Exception){
                println("CHELSEA: not sure what happened here")
            }
        }
        /*
        val intent = Intent(this, FifthActivity::class.java)
        // sending intent as a string form since floats are being funky
        intent.putStringArrayListExtra("intent", intentArray)
        startActivity(intent)

         */

    }

}