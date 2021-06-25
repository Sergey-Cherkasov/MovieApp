package pt.svcdev.movieapp.repository.datasource

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pt.svcdev.movieapp.model.Movie
import pt.svcdev.movieapp.model.MoviesList
import pt.svcdev.movieapp.repository.api.MoviesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl : DataSource<MoviesList> {
    override suspend fun getData(): MoviesList =
        getService().getMoviesListAsync(1, "274f828ad283bd634ef4fc1ee4af255f").await()

    private fun buildRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createOkHttpClient())
            .build()

    private fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        return httpClient.build()
    }

    private fun getService(): MoviesApi = buildRetrofit().create(MoviesApi::class.java)
}