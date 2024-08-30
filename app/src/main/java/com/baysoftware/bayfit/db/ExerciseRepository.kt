package com.baysoftware.bayfit.db

import com.baysoftware.bayfit.history.model.ExerciseSessionModel
import com.baysoftware.bayfit.history.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseRepository(private val exerciseSessionDAO: ExerciseSessionDAO) {

    suspend fun getAllSessions(): List<ExerciseSessionModel> =
        withContext(Dispatchers.IO) {
            exerciseSessionDAO.getAllSessions().map { it.toModel() }
        }


    suspend fun getSessionById(id: Long): ExerciseSessionModel =
        withContext(Dispatchers.IO) {
            exerciseSessionDAO.getSessionById(id).toModel()
        }
    
    suspend fun insertSession(session: ExerciseSessionModel) {
        val sessionEntity = session.toEntity()
        exerciseSessionDAO.insertSession(sessionEntity)
    }

    // TODO: será usado no futuro quando implementarmos a exclusão de sessões
    suspend fun deleteSession(exerciseSessionId: Long) {
        exerciseSessionDAO.deleteSession(exerciseSessionId)
    }
}