package com.baysoftware.bayfit.history.view

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.baysoftware.bayfit.db.ExerciseDatabase
import com.baysoftware.bayfit.db.ExerciseRepository
import com.baysoftware.bayfit.history.model.ExerciseSessionModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryListViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

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
                    return HistoryListViewModel(exerciseRepository) as T
                }
            }
    }

    private val mutableExerciseSessions = MutableLiveData<List<ExerciseSessionModel>>()
    val exerciseSessions: LiveData<List<ExerciseSessionModel>>
        get() = mutableExerciseSessions

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val sessions = exerciseRepository.getAllSessions()
            CoroutineScope(Dispatchers.Main).launch {
                mutableExerciseSessions.value = sessions
            }
        }
    }

//    suspend fun getSessionById(id: Long): ExerciseSessionModel {
//        CoroutineScope(Dispatchers.IO).launch {
//            exerciseRepository.getSessionById(id)
//        }
//    }

}