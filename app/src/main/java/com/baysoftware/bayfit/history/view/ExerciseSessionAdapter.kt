package com.baysoftware.bayfit.history.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baysoftware.bayfit.databinding.ItemExerciseSessionBinding
import com.baysoftware.bayfit.db.ExerciseSessionEntity
import com.baysoftware.bayfit.util.getTimeStringFromDouble

class ExerciseSessionAdapter(
    private var sessions: List<ExerciseSessionEntity>,
    private val onClick: (ExerciseSessionEntity) -> Unit
) : RecyclerView.Adapter<ExerciseSessionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemExerciseSessionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(session: ExerciseSessionEntity) {
            binding.dateItem.text = session.date.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy"))
            binding.duration.text = session.totalTime.toDouble().getTimeStringFromDouble()
            binding.root.setOnClickListener {
                onClick(session)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExerciseSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sessions[position])
    }

    override fun getItemCount(): Int = sessions.size

    fun updateSessions(newSessions: List<ExerciseSessionEntity>) {
        sessions = newSessions
        notifyDataSetChanged()
    }
}
