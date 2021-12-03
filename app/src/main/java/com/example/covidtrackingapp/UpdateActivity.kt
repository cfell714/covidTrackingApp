package com.example.covidtrackingapp

import android.content.Intent
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
    private lateinit var checkBoxMasks1: CheckBox
    private lateinit var checkBoxMasks2: CheckBox
    private lateinit var checkBoxMasks3: CheckBox
    private lateinit var editTextVaccinated: EditText
    private lateinit var textViewSelectLocation: TextView
    private lateinit var textViewNumPeople: TextView
    private lateinit var textViewDuration: TextView
    private lateinit var textViewMasks: TextView
    private lateinit var textViewVacStatus: TextView
    var spinner_temp = arrayOf("outside - social distancing", "outside - no social distancing", "inside - social distancing", "inside - no social distancing")

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
        checkBoxMasks1 = findViewById(R.id.checkBox_masks1)
        checkBoxMasks2= findViewById(R.id.checkBox_masks2)
        checkBoxMasks3 = findViewById(R.id.checkBox_masks3)
        editTextVaccinated = findViewById(R.id.editText_vaccinated)
        textViewSelectLocation = findViewById(R.id.textView_selectLocation)
        textViewNumPeople = findViewById(R.id.textView_numPeople)
        textViewDuration = findViewById(R.id.textView_duration)
        textViewMasks = findViewById(R.id.textView_masks)
        textViewVacStatus = findViewById(R.id.textView_vacStatus)

        val tempId = intent.getStringExtra("id")
        val id = Integer.parseInt(tempId)

        // getting the information out of the intent sent
        val tempLocation = intent.getStringExtra("location")
        val tempState = intent.getStringExtra("state")
        val tempNumberPeople = intent.getStringExtra("numberPeople")
        val tempDuration = intent.getStringExtra("duration")
        val tempMasks = intent.getStringExtra("masks")
        val tempVaccinated = intent.getStringExtra("vaccinated")
        val locationRatio = intent.getStringExtra("locationRatio")
        val cases = intent.getStringExtra("cases")
        val vacCompleted = intent.getStringExtra("vacCompleted")

        editTextState.setText(tempState)
        editTextNumberPeople.setText(tempNumberPeople)
        editTextDuration.setText(tempDuration)
        editTextVaccinated.setText(tempVaccinated)
        spinner.setSelection(1)

        // taking intnent and setting spinner to the user selection
        if(tempLocation == "outside - social distancing"){spinner.setSelection(1)}
        if(tempLocation == "outside - no social distancing"){spinner.setSelection(2)}
        if(tempLocation == "inside - social distancing"){spinner.setSelection(3)}
        if(tempLocation == "inside - no social distancing"){spinner.setSelection(4)}

        // checking the same box the user checked before
        if(tempMasks == "zero"){ checkBoxMasks.setChecked(true) }
        if(tempMasks == "one"){ checkBoxMasks1.setChecked(true) }
        if(tempMasks == "two"){ checkBoxMasks2.setChecked(true) }
        if(tempMasks == "three"){ checkBoxMasks3.setChecked(true) }

        SearchSpinner()

        // button save function to save the new updated information to the database in the same id as before
        buttonSave.setOnClickListener {
            if(TextUtils.isEmpty(editTextState.text) || TextUtils.isEmpty(editTextNumberPeople.text) || TextUtils.isEmpty(editTextDuration.text) || spinner.selectedItem == "" || TextUtils.isEmpty(editTextVaccinated.text)){
                Toast.makeText(this, "Section Missing", Toast.LENGTH_LONG).show()
            }else{
                try {
                    var check_temp0 = false
                    if (checkBoxMasks.isChecked) { check_temp0 = true }

                    var check_temp1 = false
                    if (checkBoxMasks1.isChecked) { check_temp1 = true }

                    var check_temp2 = false
                    if (checkBoxMasks2.isChecked) { check_temp2 = true }

                    var check_temp3 = false
                    if (checkBoxMasks3.isChecked) { check_temp3 = true }

                    var check = "string"
                    if(check_temp0 == true){
                        check = "zero"
                    } else if (check_temp1 == true){
                        check = "one"
                    } else if (check_temp2 == true){
                        check = "two"
                    } else if (check_temp3 == true){
                        check = "three"
                    }

                    // this is where the database is being updated and new intent is being send the user back to the second activity
                    riskViewModel.update(id, spinner.selectedItem.toString(), editTextState.text.toString(), editTextNumberPeople.text.toString(),
                            editTextDuration.text.toString(), check, editTextVaccinated.text.toString(), locationRatio.toString(), cases.toString(), vacCompleted.toString())
                    val intent = Intent(this, SecondActivity::class.java)
                    startActivity(intent)

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