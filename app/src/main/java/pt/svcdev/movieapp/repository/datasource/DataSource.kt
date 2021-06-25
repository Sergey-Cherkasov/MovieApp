package pt.svcdev.movieapp.repository.datasource

interface DataSource<T> {
    suspend fun getData(): T
}