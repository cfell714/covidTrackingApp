package com.example.covidtrackingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // in every activity, need to define variables outside of methods
    // defining variables in activity_main
    private lateinit var textViewMain: TextView
    private lateinit var buttonMain: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // looking up button and textView by ids
        textViewMain = findViewById(R.id.textView_main)
        buttonMain = findViewById(R.id.button_main)

        // listens to see if the button was clicked
        buttonMain.setOnClickListener{
            launchSecondActivity()
        }
    }

    // function to start next activity from the button being clicked
    private fun launchSecondActivity(){
        // intent is what you're passing from one activity to another (could be something, in this case just going to the next activity with nothing)
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

}