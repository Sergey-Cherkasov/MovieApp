package pt.svcdev.movieapp.model

data class Movie(
    val adult: Boolean,
    val poster_path: String,
    val overview: String,
    val release_date: String,
    val id: Int,
    val title: String
)