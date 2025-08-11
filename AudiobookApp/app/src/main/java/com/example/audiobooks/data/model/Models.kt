package com.example.audiobooks.data.model

import java.time.Instant

data class Author(
    val id: String,
    val name: String,
)

data class Genre(
    val id: String,
    val name: String,
)

data class Chapter(
    val id: String,
    val title: String,
    val audioUrl: String,
    val durationMs: Long,
    val indexInVolume: Int,
)

data class Volume(
    val id: String,
    val title: String,
    val indexInBook: Int,
    val chapters: List<Chapter>,
)

data class Book(
    val id: String,
    val title: String,
    val author: Author,
    val genres: List<Genre>,
    val coverUrl: String?,
    val description: String,
    val volumes: List<Volume> = emptyList(),
    val createdAt: Instant = Instant.now()
)