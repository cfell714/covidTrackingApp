package com.example.covidtrackingapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class ThirdActivity : AppCompatActivity() {

    // defining variables in activity
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

   private lateinit var client: AsyncHttpClient

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

        SearchSpinner()

        buttonSave.setOnClickListener {
            if (TextUtils.isEmpty(editTextState.text) || TextUtils.isEmpty(editTextNumberPeople.text) || TextUtils.isEmpty(
                    editTextDuration.text
                ) || spinner.selectedItem == "" || TextUtils.isEmpty(editTextVaccinated.text)
            ) {
                Toast.makeText(this, "Section Missing", Toast.LENGTH_LONG).show()
            } else {
                try {
                    var check_temp0 = false
                    if (checkBoxMasks.isChecked) {
                        check_temp0 = true
                    }
                    var check_temp1 = false
                    if (checkBoxMasks1.isChecked) {
                        check_temp1 = true
                    }
                    var check_temp2 = false
                    if (checkBoxMasks2.isChecked) {
                        check_temp2 = true
                    }
                    var check_temp3 = false
                    if (checkBoxMasks3.isChecked) {
                        check_temp3 = true
                    }

                    val apiUrl = "https://api.covidactnow.org/v2/state/CA.json?apiKey=4eb311892e484892a0fd50aa90df2b47"

                    var client2 = AsyncHttpClient()
                    client2.addHeader("Accept", "application/json")
                    client2.get(apiUrl, object : AsyncHttpResponseHandler() {
                        override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                            try {
                                val result = String(responseBody)
                                val json = JSONObject(result)

                                val metrics = json.getJSONObject("metrics")
                                val testPosRatio = metrics.getString("testPositivityRatio")
                                val infectionRate = metrics.getString("caseDensity")
                                val vaccinationsCompletedRatio = metrics.getString("vaccinationsCompletedRatio")

                                println("CHELSEA line 74")
                                println("${testPosRatio} THIS IS WHAT IM PRINTGI")
                                println("${infectionRate} THIS IS WHAT IM PRINTGI")
                                println("${vaccinationsCompletedRatio} THIS IS WHAT IM PRINTGI")
                                var check = "string"
                                var x = 0
                                if(check_temp0 == true){
                                    check = "zero"
                                    x++
                                }
                                if (check_temp1 == true){
                                    check = "one"
                                    x++
                                }
                                if (check_temp2 == true){
                                    check = "two"
                                    x++
                                }
                                if (check_temp3 == true){
                                    check = "three"
                                    x++
                                }

                                val risk_temp = Risk(
                                        0,
                                        spinner.selectedItem.toString(),
                                        editTextState.text.toString(),
                                        editTextNumberPeople.text.toString(),
                                        editTextDuration.text.toString(),
                                        check,
                                        editTextVaccinated.text.toString(),
                                        testPosRatio,
                                        infectionRate,
                                        vaccinationsCompletedRatio
                                )

                                // this section is about user proofing the app so their entries match what I need in other activities
                                if(x >=2){
                                    Toast.makeText(this@ThirdActivity, "Please only check one box", Toast.LENGTH_LONG).show()
                                    println("Please only check one box")
                                } else {
                                    if (editTextVaccinated.text.toString() == "yes" || editTextVaccinated.text.toString() == "no") {
                                        println("vaccination status entered is good")
                                        if(editTextDuration.text.contains(",") || editTextDuration.text.contains(".")){
                                            Toast.makeText(this@ThirdActivity, "Enter duration without commas/periods", Toast.LENGTH_LONG).show()
                                        }else {
                                            riskViewModel.insert(risk_temp)
                                            finish()
                                        }
                                    } else{
                                        Toast.makeText(this@ThirdActivity, "Enter yes or no for vaccination status", Toast.LENGTH_LONG).show()
                                        println("Enter yes or no for vaccination status")
                                    }
                                   // riskViewModel.insert(risk_temp)
                                    //finish()
                                }

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }

                        override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                            println("not working idk why")
                        }
                    })

                } catch (e: Exception) {
                    Toast.makeText(this, "Something is wrong", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun SearchSpinner(){
        val searchMethod = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            spinner_temp
        )
        searchMethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = searchMethod
    }

}