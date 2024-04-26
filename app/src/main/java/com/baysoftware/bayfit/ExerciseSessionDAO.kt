package com.baysoftware.bayfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow



@Dao
interface ExerciseSessionDAO {

    @Query("SELECT * FROM history_table ORDER BY data, duration, totalTimePause ASC")

    fun getAlphabetizedExerciseSession(): Flow<List<ExerciseSession>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: ExerciseSession, duration: ExerciseSession, totaolTimePause: ExerciseSession )
    // Verificar se a sintaxe da linha acima está correta. Antes, no colelab era:  suspend fun insert(word: Word)
    // Agora, como são três itens na tabela...

    @Query("DELETE FROM history_table")
    suspend fun deleteAll()
}