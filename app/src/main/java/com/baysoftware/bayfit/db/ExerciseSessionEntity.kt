package com.baysoftware.bayfit.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baysoftware.bayfit.history.model.ExerciseSessionModel
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "exercise_sessions")
data class ExerciseSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDate,
    val duration: LocalTime,
    var totalTime: Int,
    var startTime: LocalTime,
    var endTime: LocalTime,
    var restTime: Int
)

fun ExerciseSessionEntity.toModel() =
    ExerciseSessionModel(
        id = this.id,
        date = this.date.toString(),
        duration = this.duration.toString()
    )