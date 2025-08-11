package com.example.audiobooks.di

import com.example.audiobooks.data.repo.BookRepository
import com.example.audiobooks.data.repo.MockBookRepository

object ServiceLocator {
    val repository: BookRepository by lazy { MockBookRepository() }
}