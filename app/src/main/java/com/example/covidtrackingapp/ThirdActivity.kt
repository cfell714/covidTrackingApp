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

   // private final val baseUrl = "https://api.covidactnow.org/v2/state/"
   private lateinit var client: AsyncHttpClient //? = AsyncHttpClient()

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
            if (TextUtils.isEmpty(editTextState.text) || TextUtils.isEmpty(editTextNumberPeople.text) || TextUtils.isEmpty(
                    editTextDuration.text
                ) || spinner.selectedItem == "" || TextUtils.isEmpty(editTextVaccinated.text)
            ) {
                Toast.makeText(this, "Section Missing", Toast.LENGTH_LONG).show()
            } else {
                try {
                    var check_temp = false
                    if (checkBoxMasks.isChecked) {
                        check_temp = true
                    }

                    val apiUrl =
                        "https://api.covidactnow.org/v2/state/CA.json?apiKey=4eb311892e484892a0fd50aa90df2b47"
                    println("CHELSEA line 64")

              //      client.addHeader("Accept", "application/json")
                    var client2 = AsyncHttpClient()
                    client2.addHeader("Accept", "application/json")
                    println("CHELSEA 67")
                    client2.get(apiUrl, object : AsyncHttpResponseHandler() {
                        override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                            try {
                                println("CHELSEA line 70")
                               // val charset = Charsets.UTF_8
                              // val stringResponse = responseBody?.let { it1 -> String(it1, charset) }
                            //    val stringResponse2 = responseBody.toString()
                        //        println("this is me printing ${stringResponse2}")
                            //    val jsonObj: JSONObject = JSONObject(stringResponse2)
                                //val body = responseBody.toString()
                                val result = String(responseBody)
                                val json = JSONObject(result)
                                println("CHELSEA line 72")
                                val stateNumber = json.getString("fips")
                                println(json.toString())
                                println("CHELSEA line 74")
                                println("${stateNumber} THIS IS WHAT IM PRINTGI")

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                        }

                        override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                            println("not working idk why")
                        }

                    })

                    val risk_temp = Risk(
                        0,
                        spinner.selectedItem.toString(),
                        editTextState.text.toString(),
                        editTextNumberPeople.text.toString(),
                        editTextDuration.text.toString(),
                        check_temp.toString(),
                        editTextVaccinated.text.toString()
                    )
                    riskViewModel.insert(risk_temp)
                    finish()

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