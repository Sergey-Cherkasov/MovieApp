package pt.svcdev.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.svcdev.movieapp.model.Movie
import pt.svcdev.movieapp.repository.RepositoryImpl
import pt.svcdev.movieapp.repository.datasource.RetrofitImpl

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy { MainViewModel(RepositoryImpl(RetrofitImpl())) }
    private val adapter: MoviesRVAdapter by lazy { MoviesRVAdapter() }
    private val listMovies: RecyclerView by lazy { findViewById(R.id.list_movies) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        viewModel.getData()
        initView()
    }

    private fun initView() {
        listMovies.layoutManager = LinearLayoutManager(applicationContext)
        listMovies.adapter = adapter
    }

    private fun initViewModel() {
        viewModel.subscribeLiveData().observe(this@MainActivity, {
            setDataToAdapter(it)
        })
    }

    private fun setDataToAdapter(moviesList: List<Movie>) {
        adapter.setData(moviesList = moviesList)
    }
}