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
    private lateinit var buttonFifth: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)

        // looking up variables by ids
        textViewFifth = findViewById(R.id.textView_fifth)
        textViewFifthCalcDis = findViewById(R.id.textView_fifrthCalcDis)
        buttonFifth = findViewById(R.id.button_fifth)

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
                 //   x.plus(intent.get(i))
                    i ++
                }

            }
        }
        println(x)
        x = x/(length!!)
        println("THIS IS X " + x)
        textViewFifthCalcDis.setText(x.toString())

            // launches to second activity function after click
                buttonFifth.setOnClickListener{
                    launchSecondActivity()
                }

    }
     // launches to second activity with an empty intent
    private fun launchSecondActivity(){
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }


}