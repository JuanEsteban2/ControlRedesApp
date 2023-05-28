package com.example.controlredesapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.controlredesapp.core.BaseViewHolder
import com.example.controlredesapp.data.model.UsageGoalEntity
import com.example.controlredesapp.databinding.GoalItemBinding

class GoalsAdapter(private val goalsList: List<UsageGoalEntity>): RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = GoalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoalsViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is GoalsViewHolder -> holder.bind(goalsList[position])
        }
    }

    override fun getItemCount(): Int = goalsList.size

    private inner class GoalsViewHolder(val binding: GoalItemBinding, context: Context): BaseViewHolder<UsageGoalEntity>(binding.root) {
        override fun bind(item: UsageGoalEntity) {
            binding.redName.text = item.name
            binding.redHoras.text = "${item.hoursGoal} h"
            binding.redPromedio.text = "Uso promedio ${item.current_average}"
            binding.redDiferencia.text = "Diferencia ${item.difference}"
        }
    }

}