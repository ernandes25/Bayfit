package com.baysoftware.bayfit.db

import com.baysoftware.bayfit.history.model.ExerciseSessionModel
import com.baysoftware.bayfit.history.model.toEntity

class ExerciseRepository(private val exerciseSessionDAO: ExerciseSessionDAO) {

    suspend fun getAllSessions(): List<ExerciseSessionModel> {
        return exerciseSessionDAO.getAllSessions().map { it.toModel() }
    }
    
    suspend fun insertSession(session: ExerciseSessionModel) {
        val sessionEntity = session.toEntity()
        exerciseSessionDAO.insertSession(sessionEntity)
    }

    suspend fun deleteSession(exerciseSessionId: Long) {
        exerciseSessionDAO.deleteSession(exerciseSessionId)
    }
}
