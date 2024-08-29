package com.baysoftware.bayfit.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset

@Entity(tableName = "exercise_sessions")
@Parcelize
data class ExerciseSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var date: LocalDate,
    var totalTime: Int,
    var startTime: LocalTime,
    var endTime: LocalTime,
    var restTime: Int
) : Parcelable
