package com.baysoftware.bayfit.running.view

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.savedstate.SavedStateRegistryOwner
import com.baysoftware.bayfit.db.ExerciseDatabase
import com.baysoftware.bayfit.db.ExerciseRepository
import com.baysoftware.bayfit.history.model.ExerciseSessionModel
import kotlinx.coroutines.launch
import java.time.LocalDate

class TimerViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    companion object {
        /**
         * https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories
         */
        fun provideFactory(
            application: Application,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    val database = ExerciseDatabase.getDatabase(application)
                    val exerciseRepository = ExerciseRepository(database.exerciseSessionDAO())
                    return TimerViewModel(exerciseRepository) as T
                }
            }
    }

    private var startTime: Long = 0L

    fun startSession() {
        startTime = System.currentTimeMillis()
    }

    /**
     * Chama o repositório, que irá chamar o DAO para inserir uma sessão de exercícios no banco de dados.
     */
    fun saveSession(endTime: Long, totalRestTime: Long) {
        viewModelScope.launch {
            val exerciseSession = ExerciseSessionModel(
                date = LocalDate.now().toString(),
                duration = endTime - startTime,
                startTime = startTime,
                endTime = endTime,
                restTime = totalRestTime
            )
            exerciseRepository.insertSession(exerciseSession)
        }
    }
}
