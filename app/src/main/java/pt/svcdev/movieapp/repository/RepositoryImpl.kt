package pt.svcdev.movieapp.repository

import pt.svcdev.movieapp.model.MoviesList
import pt.svcdev.movieapp.repository.datasource.DataSource

class RepositoryImpl(private val dataSource: DataSource<MoviesList>) : Repository<MoviesList> {
    override suspend fun getData(): MoviesList = dataSource.getData()
}