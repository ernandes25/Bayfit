package com.baysoftware.bayfit.db

import java.time.LocalDate
import java.time.LocalTime

data class ExerciseSession (

    val date: LocalDate,
    val duration: LocalTime
)
