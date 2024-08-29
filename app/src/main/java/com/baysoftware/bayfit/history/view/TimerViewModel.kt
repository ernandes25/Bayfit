package com.baysoftware.bayfit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baysoftware.bayfit.db.ExerciseSessionEntity
import com.baysoftware.bayfit.db.ExerciseSessionDAO
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class TimerViewModel(private val exerciseSessionDAO: ExerciseSessionDAO) : ViewModel() {

    private val _timeElapsed = MutableLiveData<Long>()
    val timeElapsed: LiveData<Long> get() = _timeElapsed

    private val _currentSession = MutableLiveData<ExerciseSessionEntity>()
    val currentSession: LiveData<ExerciseSessionEntity> get() = _currentSession

    init {
        _timeElapsed.value = 0L
        startSession()
    }

    // Inicia uma nova sessão de exercício
    fun startSession() {
        val session = ExerciseSessionEntity(
            id = 0,  // ID autogerado pelo banco de dados
            date = LocalDate.now(),
            startTime = LocalTime.now(),
            endTime = LocalTime.now(),  // Inicialmente o mesmo que startTime
            totalTime = 0,  // O tempo total será calculado mais tarde
            restTime = 0   // Tempo de descanso, por enquanto 0
        )
        _currentSession.value = session
    }

    // Atualiza o tempo decorrido
    fun updateTimeElapsed(time: Long) {
        _timeElapsed.value = time
    }

    // Para o cronômetro e salva a sessão no banco de dados
    fun stopSession(restTime: Int) {
        _currentSession.value?.let { session ->
            session.endTime = LocalTime.now()
            session.totalTime = _timeElapsed.value?.toInt() ?: 0
            session.restTime = restTime
            saveSession(session)
        }
    }

    // Salva a sessão de exercício no banco de dados
    private fun saveSession(session: ExerciseSessionEntity) {
        viewModelScope.launch {
            exerciseSessionDAO.insertSession(session)
        }
    }

    // Inserir sessão manualmente (caso necessário)
    fun insertSession(session: ExerciseSessionEntity) {
        viewModelScope.launch {
            exerciseSessionDAO.insertSession(session)
        }
    }
}
