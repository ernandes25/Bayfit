import androidx.lifecycle.LiveData
import com.baysoftware.bayfit.db.ExerciseSessionDAO
import com.baysoftware.bayfit.db.ExerciseSessionEntity

class ExerciseRepository(private val exerciseSessionDAO: ExerciseSessionDAO) {

    suspend fun insertSession(session: ExerciseSessionEntity) {
        exerciseSessionDAO.insertSession(session)
    }
    fun getAllSessions(): LiveData<List<ExerciseSessionEntity>> {
        return exerciseSessionDAO.getAllSessions()
    }
    fun getSessionById(sessionId: Long): LiveData<ExerciseSessionEntity> {
        return exerciseSessionDAO.getSessionById(sessionId)
    }
}
