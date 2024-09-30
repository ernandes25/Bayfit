package com.baysoftware.bayfit.history.model

import com.baysoftware.bayfit.db.ExerciseSessionEntity

data class ExerciseSessionModel(
    val id: Long = 0,
    val date: String,
    val duration: Long,
    val startTime: Long,
    val endTime: Long,
    val restTime: Long
)

fun ExerciseSessionModel.toEntity() =
    ExerciseSessionEntity(
        id = this.id,
        date = this.date,
        duration = this.duration,
        startTime = this.startTime,
        endTime = this.endTime,
        restTime = this.restTime
    )