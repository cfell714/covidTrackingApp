package com.example.covidtrackingapp

import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.spinner)
        buttonSave = findViewById(R.id.button_save)
        editTextState = findViewById(R.id.editText_state)
        editTextNumberPeople = findViewById(R.id.editText_numberPeople)
        editTextDuration = findViewById(R.id.editText_duration)
        checkBoxMasks = findViewById(R.id.checkBox_masks)
        editTextVaccinated = findViewById(R.id.editText_vaccinated)

        val tempId = intent.getStringExtra("id")
        val id = Integer.parseInt(tempId)

        val tempLocation = intent.getStringExtra("location")
        val tempState = intent.getStringExtra("state")
        val tempNumberPeople = intent.getStringExtra("numberPeople")
        val tempDuration = intent.getStringExtra("duration")
        val tempMasks = intent.getStringExtra("masks")
        val tempVaccinated = intent.getStringExtra("vaccinated")

        editTextState.setText(tempState)
        editTextNumberPeople.setText(tempNumberPeople)
        editTextDuration.setText(tempDuration)
        editTextVaccinated.setText(tempVaccinated)
        spinner.setSelection(1)

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

                    val risk_temp = Risk(0, spinner.selectedItem.toString(), editTextState.text.toString(), editTextNumberPeople.text.toString(), editTextDuration.text.toString(), check_temp.toString(), editTextVaccinated.text.toString())
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