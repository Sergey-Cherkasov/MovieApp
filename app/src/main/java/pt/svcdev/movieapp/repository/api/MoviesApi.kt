package pt.svcdev.movieapp.repository.api

import kotlinx.coroutines.Deferred
import pt.svcdev.movieapp.model.MoviesList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("/3/list/{list_id}")
    fun getMoviesListAsync(
        @Path("list_id") list_id: Int,
        @Query("api_key") api_key: String
    ): Deferred<MoviesList>
}