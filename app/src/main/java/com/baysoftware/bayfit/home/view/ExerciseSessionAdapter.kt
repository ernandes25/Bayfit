package com.baysoftware.bayfit.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.db.ExerciseSessionEntity

class ExerciseSessionAdapter(
    private val objects: List<ExerciseSessionEntity>,
    private val onClickListener: (ExerciseSessionEntity) -> Unit
) : RecyclerView.Adapter<ExerciseSessionAdapter.ExerciseViewHolder>() {

    // getItemCount(), onCreateViewHolder() e onBindViewHolder() existem em RecyclerView.Adapter como
    // metodosabstratos e portanto precisa ser implementado aqui na classe concreta
    // ExerciseSessionAdapter (por isso o uso de override)
    override fun getItemCount(): Int = objects.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(objects[position])

        holder.itemView.setOnClickListener { onClickListener(objects[position]) }
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dataTextView: TextView = itemView.findViewById(R.id.data)
        private val durationTextView: TextView = itemView.findViewById(R.id.duration)

        fun bind(exerciseSessionEntity: ExerciseSessionEntity) {
            dataTextView.text = exerciseSessionEntity.data.toString()
            durationTextView.text = exerciseSessionEntity.duration.toString()
        }

        companion object {
            fun create(parent: ViewGroup): ExerciseViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_exercise_session, parent, false)
                return ExerciseViewHolder(view)
            }
        }
    }
}