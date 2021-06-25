package pt.svcdev.movieapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.svcdev.movieapp.model.Movie

class MoviesRVAdapter: RecyclerView.Adapter<MoviesRVAdapter.Holder>() {

    private var moviesList: List<Movie> = listOf()

    inner class Holder(view: View): RecyclerView.ViewHolder(view) {
        private val imgMoviePoster = view.findViewById<ImageView>(R.id.img_movie_poster)
        private val txtMovieTitle = view.findViewById<TextView>(R.id.txt_movie_title)

        fun bind(moviePoster: Uri, movieTitle: String) {
            imgMoviePoster.setImageURI(moviePoster)
            txtMovieTitle.text = movieTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.movie_rv_holder, parent, false) as View
        )

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(moviesList[position].poster_path as Uri, moviesList[position].title)

    override fun getItemCount(): Int = moviesList.size

    fun setData(moviesList: List<Movie>) {
        this.moviesList = moviesList
        notifyDataSetChanged()
    }
}
