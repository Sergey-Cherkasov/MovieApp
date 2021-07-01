package pt.svcdev.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import pt.svcdev.movieapp.model.Movie
import pt.svcdev.movieapp.model.MoviesList
import pt.svcdev.movieapp.repository.Repository

class MainViewModel(private val repository: Repository<MoviesList>) : ViewModel() {

    private val liveDataMovies = MutableLiveData<ScreenState>()
    private val liveDataMoviesForObserver: LiveData<ScreenState> = liveDataMovies

    private val coroutineScope = CoroutineScope(
        Dispatchers.Main +
                SupervisorJob() +
                CoroutineExceptionHandler { _, throwable -> handleError(throwable) }
    )

    fun subscribeLiveData() = liveDataMoviesForObserver

    fun getData() {
        cancelJob()
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                val searchResponse = repository.getData()
                val result = searchResponse.moviesList
                val id = searchResponse.id
                if (result != null && id != null) {
                    liveDataMovies.postValue(ScreenState.Success(result))
                } else {
                    liveDataMovies.postValue(ScreenState.Error(
                        Throwable("Search results or total count are null")
                    ))
                }
            }
        }
    }

    private fun handleError(error: Throwable) {
        liveDataMovies.postValue(ScreenState.Error(
            Throwable(error.message ?: "Response is null or unsuccessful")
        ))
    }

    private fun cancelJob() {
        coroutineScope.coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

}

sealed class ScreenState {
    object Loading : ScreenState()
    data class Success(val result: List<Movie>) : ScreenState()
    data class Error(val error: Throwable) : ScreenState()
}