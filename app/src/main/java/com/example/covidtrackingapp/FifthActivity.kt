package com.example.covidtrackingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FifthActivity : AppCompatActivity() {

    private lateinit var textViewFifth: TextView
    private lateinit var buttonFifth: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)

        textViewFifth = findViewById(R.id.textView_fifth)
        buttonFifth = findViewById(R.id.button_fifth)

        buttonFifth.setOnClickListener{
            launchSecondActivity()
        }

    }

    private fun launchSecondActivity(){
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }


}