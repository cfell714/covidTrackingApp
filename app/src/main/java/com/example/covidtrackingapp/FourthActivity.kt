package com.example.covidtrackingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class FourthActivity : AppCompatActivity(){

    private lateinit var textViewFourState: TextView
    private lateinit var textViewFourLocation: TextView
    private lateinit var textViewFourNumberPeople: TextView
    private lateinit var textViewFourDuration: TextView
    private lateinit var textViewFourMasks: TextView
    private lateinit var textViewFourVaccinated: TextView

    private lateinit var textViewFourthState: TextView
    private lateinit var textViewFourthNumberPeople: TextView
    private lateinit var textViewFourthLocation: TextView
    private lateinit var textViewFourthDuration: TextView
    private lateinit var textViewFourthMasks: TextView
    private lateinit var textViewFourthVaccinated: TextView

    private lateinit var buttonDelete: Button
    private lateinit var buttonUpdate: Button

    private lateinit var tempId: String
    private lateinit var tempLocation: String
    private lateinit var tempState: String
    private lateinit var tempNumberPeople: String
    private lateinit var tempDuration: String
    private lateinit var tempMasks: String
    private lateinit var tempVaccinated: String
    private lateinit var tempLocationRatio: String
    private lateinit var tempCases: String
    private lateinit var tempVacCompleted: String

    private val riskViewModel: RiskViewModel by viewModels{
        RiskViewModelFactory((application as RiskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        textViewFourState = findViewById(R.id.textView_fourState)
        textViewFourLocation = findViewById(R.id.textView_fourLocation)
        textViewFourNumberPeople = findViewById(R.id.textView_fourNumberPeople)
        textViewFourDuration = findViewById(R.id.textView_fourDuration)
        textViewFourMasks = findViewById(R.id.textView_fourMasks)
        textViewFourVaccinated = findViewById(R.id.textView_fourVaccinated)

        textViewFourthLocation = findViewById(R.id.textView_fourthLocation)
        textViewFourthState = findViewById(R.id.textView_fourthState)
        textViewFourthNumberPeople = findViewById(R.id.textView_fourthNumberPeople)
        textViewFourthDuration = findViewById(R.id.textView_fourthDuration)
        textViewFourthMasks = findViewById(R.id.textView_fourthMasks)
        textViewFourthVaccinated = findViewById(R.id.textView_fourthVaccinated)

        buttonDelete = findViewById(R.id.button_delete)
        buttonUpdate = findViewById(R.id.button_update)

        val getId = intent.getStringExtra("id")
        val id = Integer.parseInt(getId)

        buttonDelete.setOnClickListener{
            riskViewModel.delete(id)
            finish()
        }

        riskViewModel.select(id).observe(this, Observer{
            if(it!=null){
                textViewFourLocation.text = it.location
                textViewFourState.text = it.location_state
                textViewFourNumberPeople.text = it.number_people
                textViewFourDuration.text = it.duration
                textViewFourMasks.text = it.masks
                textViewFourVaccinated.text = it.vaccinated

                tempId = it.id.toString()
                tempLocation = it.location
                tempState = it.location_state
                tempNumberPeople = it.number_people
                tempDuration = it.duration
                tempMasks = it.masks
                tempVaccinated = it.vaccinated
                tempLocationRatio = it.locationRatio
                tempCases = it.cases
                tempVacCompleted = it.vacCompleted

            }
        })

        buttonUpdate.setOnClickListener{
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("id", tempId)
            intent.putExtra("location", tempLocation)
            intent.putExtra("state", tempState)
            intent.putExtra("numberPeople", tempNumberPeople)
            intent.putExtra("duration", tempDuration)
            intent.putExtra("masks", tempMasks)
            intent.putExtra("vaccinated", tempVaccinated)
            intent.putExtra("locationRatio", tempLocationRatio)
            intent.putExtra("cases", tempCases)
            intent.putExtra("vacCompleted", tempVacCompleted)
            startActivity(intent)
        }
    }
}