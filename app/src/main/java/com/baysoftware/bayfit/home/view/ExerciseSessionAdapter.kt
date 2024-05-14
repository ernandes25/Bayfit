package com.baysoftware.bayfit.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.db.ExerciseSessionEntity

class ExerciseSessionAdapter : ListAdapter<ExerciseSessionEntity, ExerciseSessionAdapter.ExerciseViewHolder>(EXERCISE_COMPARATOR) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseViewHolder {
        return ExerciseViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: ExerciseViewHolder,
        position: Int
    ) {
        val current = getItem(position)
        holder.bind(current.data)   //Codelab consta "word" em duration. Sugestão aparece data,duration e totalTimePause
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exerciseItemView: TextView = itemView.findViewById(R.id.textView)
        fun bind(text: String?) {
            exerciseItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): ExerciseViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_exercise_session, parent, false)
                return ExerciseViewHolder(view)
            }
        }
    }

    companion object {
        private val EXERCISE_COMPARATOR = object : DiffUtil.ItemCallback<ExerciseSessionEntity>() {
            override fun areItemsTheSame(oldItem: ExerciseSessionEntity, newItem: ExerciseSessionEntity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ExerciseSessionEntity, newItem: ExerciseSessionEntity): Boolean {
                return oldItem.data == newItem.data  //mesa duvida descrita na linha 29
            }
        }
    }
}