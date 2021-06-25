package pt.svcdev.movieapp.repository

interface Repository<T> {
    suspend fun getData(): T
}