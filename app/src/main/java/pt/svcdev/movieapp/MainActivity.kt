package pt.svcdev.movieapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.svcdev.movieapp.repository.RepositoryImpl
import pt.svcdev.movieapp.repository.datasource.RetrofitImpl

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy { MainViewModel(RepositoryImpl(RetrofitImpl())) }
    private val adapter: MoviesRVAdapter by lazy { MoviesRVAdapter() }
    private lateinit var listMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        viewModel.getData()
        initView()
    }

    private fun initView() {
        listMovies = findViewById(R.id.list_movies)
        listMovies.layoutManager = LinearLayoutManager(applicationContext)
        listMovies.adapter = adapter
    }

    private fun initViewModel() {
        viewModel.subscribeLiveData().observe(this) {
            onStateChanged(it)
        }
    }

    private fun onStateChanged(screenState: ScreenState) {
        when(screenState) {
            is ScreenState.Success -> {
                adapter.setData(screenState.result)
            }
            is ScreenState.Error -> {
                Toast.makeText(this, screenState.error.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
}