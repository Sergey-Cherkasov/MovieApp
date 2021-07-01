package pt.svcdev.movieapp.model

import com.google.gson.annotations.SerializedName

data class MoviesList(
    val id: Int?,
    @field:SerializedName("items") val moviesList: List<Movie>
)