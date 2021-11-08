package com.example.covidtrackingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SecondActivity : AppCompatActivity(){

    private lateinit var recyclerview: RecyclerView
    private lateinit var buttonLogEntry: Button
    private lateinit var buttonCalcRisk: Button

    private val riskViewModel:RiskViewModel by viewModels{
        RiskViewModelFactory((application as RiskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        recyclerview = findViewById(R.id.recyclerview)
        buttonLogEntry = findViewById(R.id.button_logEntry)
        buttonCalcRisk = findViewById(R.id.button_risk)

        val adapter = RiskListAdapter()
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        riskViewModel.allRisks.observe(this, androidx.lifecycle.Observer {
                risks -> risks?.let {
            adapter.submitList(it)
        }
        })

        buttonLogEntry.setOnClickListener{
            launchThirdActivity()
        }

        buttonCalcRisk.setOnClickListener{
            launchFifthActivity()
        }

    }

    private fun launchThirdActivity(){
        val intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
    }

    private fun launchFifthActivity(){
        val intent = Intent(this, FifthActivity::class.java)
        startActivity(intent)
    }

}