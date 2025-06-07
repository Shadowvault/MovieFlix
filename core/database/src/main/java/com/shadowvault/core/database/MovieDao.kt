package com.shadowvault.core.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getPagedMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies")
    fun getAllMoviesFlow(): Flow<List<MovieEntity>>

    @Query("SELECT movieId FROM liked_movies WHERE userId = :userId")
    fun getLikedMovieIdsFlow(userId: Int): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun likeMovie(likedMovieEntity: LikedMovieEntity)

    @Query("DELETE FROM liked_movies WHERE userId = :userId AND movieId = :movieId")
    suspend fun unlikeMovie(userId: Int, movieId: Int)

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getCount(): Int
}