package com.baysoftware.bayfit.history.model

import com.baysoftware.bayfit.db.ExerciseSessionEntity

data class ExerciseSessionModel(
    val id: Int,
    val date: String,
    val duration: String
)

fun ExerciseSessionModel.toEntity() =
    ExerciseSessionEntity(
        id = this.id,
        date = LocalDate.parse(this.date),
        duration = LocalTime.parse(this.duration)
    )