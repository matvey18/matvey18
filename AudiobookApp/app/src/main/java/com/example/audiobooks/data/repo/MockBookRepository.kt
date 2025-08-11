package com.example.audiobooks.data.repo

import com.example.audiobooks.data.model.*
import kotlinx.coroutines.delay
import java.time.Instant

class MockBookRepository : BookRepository {
    private val fantasy = Genre("g1", "Фэнтези")
    private val sciFi = Genre("g2", "Фантастика")
    private val detective = Genre("g3", "Детектив")

    private val authors = listOf(
        Author("a1", "Сергей Лукьяненко"),
        Author("a2", "Дмитрий Глуховский"),
        Author("a3", "Агата Кристи"),
    )

    private val books = listOf(
        Book(
            id = "b1",
            title = "Ночной Дозор",
            author = authors[0],
            genres = listOf(fantasy),
            coverUrl = "https://picsum.photos/seed/d1/300/450",
            description = "Город, магия и баланс света и тьмы.",
            createdAt = Instant.now().minusSeconds(60 * 60 * 24 * 2)
        ),
        Book(
            id = "b2",
            title = "Метро 2033",
            author = authors[1],
            genres = listOf(sciFi),
            coverUrl = "https://picsum.photos/seed/d2/300/450",
            description = "Постапокалипсис в московском метро.",
            createdAt = Instant.now().minusSeconds(60 * 60 * 24)
        ),
        Book(
            id = "b3",
            title = "Убийство в Восточном экспрессе",
            author = authors[2],
            genres = listOf(detective),
            coverUrl = "https://picsum.photos/seed/d3/300/450",
            description = "Классический детектив с Пуаро.",
            createdAt = Instant.now()
        ),
    )

    override suspend fun getLatestBooks(limit: Int): List<Book> {
        delay(300)
        return books.sortedByDescending { it.createdAt }.take(limit)
    }

    override suspend fun getGenres(): List<Genre> {
        delay(100)
        return listOf(fantasy, sciFi, detective)
    }

    override suspend fun search(query: String?, genreIds: Set<String>, authorName: String?): List<Book> {
        delay(300)
        return books.filter { book ->
            (query.isNullOrBlank() || book.title.contains(query, ignoreCase = true) || book.author.name.contains(query, ignoreCase = true)) &&
            (genreIds.isEmpty() || book.genres.any { it.id in genreIds }) &&
            (authorName.isNullOrBlank() || book.author.name.contains(authorName, ignoreCase = true))
        }
    }

    override suspend fun getFavorites(): List<Book> {
        delay(150)
        return books.take(2)
    }

    override suspend fun getHistory(): List<Book> {
        delay(150)
        return books
    }
}