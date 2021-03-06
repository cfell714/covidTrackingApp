package com.example.covidtrackingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FifthActivity : AppCompatActivity() {

    // defining variables
    private lateinit var textViewFifth: TextView
    private lateinit var textViewFifthCalcDis: TextView
    private lateinit var textViewExplanation: TextView
    private lateinit var buttonFifth: Button
    private lateinit var buttonTesting: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)

        // looking up variables by ids
        textViewFifth = findViewById(R.id.textView_fifth)
        textViewFifthCalcDis = findViewById(R.id.textView_fifrthCalcDis)
        textViewExplanation = findViewById(R.id.textView_explanation)
        buttonFifth = findViewById(R.id.button_fifth)
        buttonTesting = findViewById(R.id.button_testing)

        val intent = intent.getStringArrayListExtra("intent")
        println("intent" + intent)
        var length = intent?.size
        var i = 0
        var x = 0.0f
        if (length != null) {
            while (i < length) {
                if (intent != null) {
                    intent.get(i)
                    println("printing" + i)
                    println(intent.get(i))
                    x += intent.get(i).toFloat()
                    i ++
                }

            }
        }
        println(x)
        x = x/(length!!)
        println("THIS IS X " + x)
        textViewFifthCalcDis.setText(x.toString())
        if(x > 0){
            textViewExplanation.setText("The number " + x + " means you are not risky! You do not go out and do very little to possibly contract covid-19")
        }
        if(x>400){
            textViewExplanation.setText("The number " + x + " means you are at mild risk! You go out, but are very safe in your activities")
        }
        if (x > 500){
            textViewExplanation.setText("The number " + x + " means you are at moderate risk! When you go out, you are mostly safe in your activities")
        }
        if(x > 600){
            textViewExplanation.setText("The number " + x + " means you are at pretty high risky! You go out and take fewer precautions")
        }
        if (x > 700){
            textViewExplanation.setText("This number " + x +  " means you are incredibly risky! You go out often and don't do much to stay safe at all")
        }

            // launches to second activity function after click
                buttonFifth.setOnClickListener{
                    launchSecondActivity()
                }
        buttonTesting.setOnClickListener{
            sixth()
        }

    }
     // launches to second activity with an empty intent
    private fun launchSecondActivity(){
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
    private fun sixth(){
        val intent = Intent(this, SixthActivity::class.java)
        startActivity(intent)
    }


}