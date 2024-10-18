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

class HistoryExerciseReportViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

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
                    return HistoryExerciseReportViewModel(exerciseRepository) as T
                }
            }
    }

    private val mutableExerciseSession = MutableLiveData<ExerciseSessionModel>()
    val exerciseSession: LiveData<ExerciseSessionModel>
        get() = mutableExerciseSession

    fun getSessionById(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val session = exerciseRepository.getSessionById(id)
            CoroutineScope(Dispatchers.Main).launch {
                mutableExerciseSession.value = session
            }
        }
    }

}