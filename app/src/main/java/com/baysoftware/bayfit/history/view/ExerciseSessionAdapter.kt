package com.baysoftware.bayfit.history.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.db.ExerciseSession

class ExerciseSessionAdapter(
    private val context: Context,
    private val sessions: List<ExerciseSession>,
    private val onClickListener: (ExerciseSession) -> Unit
) : RecyclerView.Adapter<ExerciseSessionAdapter.ExerciseViewHolder>() {

    override fun getItemCount(): Int = sessions.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise_session, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(sessions[position])

        holder.itemView.setOnClickListener { onClickListener(sessions[position]) }
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dataTextView: TextView = itemView.findViewById(R.id.date)
        private val durationTextView: TextView = itemView.findViewById(R.id.duration)

        fun bind(exerciseSessionEntity: ExerciseSession) {
            dataTextView.text = exerciseSessionEntity.date.toString()
            durationTextView.text = exerciseSessionEntity.duration.toString()
        }


        // getItemCount(), onCreateViewHolder() e onBindViewHolder() existem em RecyclerView.Adapter como
        // metodosabstratos e portanto precisa ser implementado aqui na classe concreta
        // ExerciseSessionAdapter (por isso o uso de override)

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val dataTextView: TextView = itemView.findViewById(R.id.date)
            val durationTextView: TextView = itemView.findViewById(R.id.duration)
        }
    }
}