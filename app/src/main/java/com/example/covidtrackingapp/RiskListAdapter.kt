package com.example.covidtrackingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class RiskListAdapter : ListAdapter<Risk, RiskListAdapter.RiskViewHolder>(RiskComparator()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiskViewHolder {
        return RiskViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RiskViewHolder, position: Int) {
        val currentRisk = getItem(position)
        holder.bindText(currentRisk.location, holder.riskTextView)
    }

    class RiskViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val riskTextView: TextView = itemView.findViewById(R.id.textView_riskId)

        // write a helper function that takes a string and a text view
        // assign the text to the text view

        fun bindText (text:String?, textview: TextView){
            textview.text = text
        }

        companion object{
            fun create (parent: ViewGroup) : RiskViewHolder{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_risk, parent, false)
                return RiskViewHolder(view)
            }
        }
    }

    class RiskComparator : DiffUtil.ItemCallback<Risk>(){
        override fun areContentsTheSame(oldItem: Risk, newItem: Risk): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Risk, newItem: Risk): Boolean {
            return oldItem === newItem
        }
    }


}
