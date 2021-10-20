package com.example.covidtrackingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textViewMain: TextView
    private lateinit var buttonMain: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewMain = findViewById(R.id.textView_main)
        buttonMain = findViewById(R.id.button_main)

        buttonMain.setOnClickListener{
            launchSecondActivity()
        }
    }

    private fun launchSecondActivity(){
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

}