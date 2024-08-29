package com.baysoftware.bayfit.history.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.db.ExerciseSessionEntity

class ExerciseSessionAdapter(
    private val onClick: (ExerciseSessionEntity) -> Unit
) : RecyclerView.Adapter<ExerciseSessionAdapter.ExerciseViewHolder>() {

    private lateinit var sessions: List<ExerciseSessionEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise_session, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(sessions[position])
        holder.itemView.setOnClickListener { onClick(sessions[position]) }
    }

    override fun getItemCount(): Int = sessions.size

    fun updateSessions(sessions: List<ExerciseSessionEntity>) {
        this.sessions = sessions
        notifyDataSetChanged()
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.date_item)
        private val durationTextView: TextView = itemView.findViewById(R.id.duration)

        fun bind(session: ExerciseSessionEntity) {
            dateTextView.text = session.date.toString()
            durationTextView.text = session.duration.toString()
        }
    }
}
