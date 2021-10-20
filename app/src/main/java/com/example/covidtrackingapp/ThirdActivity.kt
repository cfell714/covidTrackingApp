package com.example.covidtrackingapp

import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.sql.Date

class ThirdActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var buttonSave: Button
    private lateinit var editTextState: EditText
    private lateinit var editTextNumberPeople: EditText
    private lateinit var editTextDuration: EditText
    private lateinit var checkBoxMasks: CheckBox
    private lateinit var editTextVaccinated: EditText
    var spinner_temp = arrayOf("bar", "club", "library", "dinner")

    private val riskViewModel: RiskViewModel by viewModels{
        RiskViewModelFactory((application as RiskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        spinner = findViewById(R.id.spinner)
        buttonSave = findViewById(R.id.button_save)
        editTextState = findViewById(R.id.editText_state)
        editTextNumberPeople = findViewById(R.id.editText_numberPeople)
        editTextDuration = findViewById(R.id.editText_duration)
        checkBoxMasks = findViewById(R.id.checkBox_masks)
        editTextVaccinated = findViewById(R.id.editText_vaccinated)

        SearchSpinner()

        buttonSave.setOnClickListener {
            if(TextUtils.isEmpty(editTextState.text) || TextUtils.isEmpty(editTextNumberPeople.text) || TextUtils.isEmpty(editTextDuration.text) || spinner.selectedItem == "" || TextUtils.isEmpty(editTextVaccinated.text)){
                Toast.makeText(this, "Section Missing", Toast.LENGTH_LONG).show()
            }else{
                try {
                    var check_temp = false
                    if(checkBoxMasks.isChecked){
                        check_temp = true
                    }

                    val risk_temp = Risk(0, spinner.selectedItem.toString(), editTextState.text.toString(), editTextNumberPeople.text.toString().toInt(), editTextDuration.text.toString().toInt(), check_temp, editTextVaccinated.text.toString())
                    riskViewModel.insert(risk_temp)
                    finish()

                }catch(e: Exception){
                    Toast.makeText(this, "Something is wrong", Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    private fun SearchSpinner(){
        val searchMethod = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinner_temp)
        searchMethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = searchMethod
    }

}