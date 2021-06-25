package pt.svcdev.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import pt.svcdev.movieapp.model.Movie
import pt.svcdev.movieapp.model.MoviesList
import pt.svcdev.movieapp.repository.Repository

class MainViewModel(private val repository: Repository<MoviesList>) : ViewModel() {

    private val liveDataMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    private val liveDataMoviesForObserver: LiveData<List<Movie>> = liveDataMovies

    private val coroutineScope = CoroutineScope(
        Dispatchers.Main +
                SupervisorJob()
    )

    fun subscribeLiveData() = liveDataMoviesForObserver

    fun getData() {
        cancelJob()
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                val result = repository.getData().moviesList
                liveDataMovies.postValue(result)
            }
        }
    }

    private fun cancelJob() {
        coroutineScope.coroutineContext.cancelChildren()
    }

}