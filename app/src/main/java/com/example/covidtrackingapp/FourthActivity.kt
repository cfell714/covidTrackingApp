package com.example.covidtrackingapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class FourthActivity : AppCompatActivity(){

    private lateinit var editTextFourthState: EditText
    private lateinit var editTextFourthNumberPeople: EditText
    private lateinit var buttonDelete: Button
    private lateinit var buttonUpdate: Button

    private val riskViewModel: RiskViewModel by viewModels{
        RiskViewModelFactory((application as RiskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        editTextFourthState = findViewById(R.id.editText_fourthState)
        editTextFourthNumberPeople = findViewById(R.id.editText_fourthNumberPeople)
        buttonDelete = findViewById(R.id.button_delete)
        buttonUpdate = findViewById(R.id.button_update)

        val get_id = intent.getStringExtra("id")
        val id = Integer.parseInt(get_id)

        buttonDelete.setOnClickListener{
            riskViewModel.delete(id)
            finish()
        }



    }

}