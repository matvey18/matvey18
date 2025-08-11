package com.example.audiobooks.data.repo

import com.example.audiobooks.data.model.Book
import com.example.audiobooks.data.model.Genre

interface BookRepository {
    suspend fun getLatestBooks(limit: Int = 20): List<Book>
    suspend fun getGenres(): List<Genre>
    suspend fun search(query: String?, genreIds: Set<String> = emptySet(), authorName: String? = null): List<Book>
    suspend fun getFavorites(): List<Book>
    suspend fun getHistory(): List<Book>
}