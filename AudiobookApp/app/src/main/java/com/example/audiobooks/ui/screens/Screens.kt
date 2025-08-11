package com.example.audiobooks.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.audiobooks.data.model.Book
import com.example.audiobooks.di.ServiceLocator

@Composable
fun HomeScreen() {
    val repo = ServiceLocator.repository
    var books by remember { mutableStateOf<List<Book>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        books = repo.getLatestBooks()
        isLoading = false
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(books) { book -> BookRow(book) }
            }
        }
    }
}

@Composable
private fun BookRow(book: Book) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AsyncImage(
                model = book.coverUrl,
                contentDescription = book.title,
                modifier = Modifier.size(72.dp)
            )
            Column(Modifier.weight(1f)) {
                Text(book.title, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(book.author.name, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(book.genres.joinToString { it.name }, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
fun SearchScreen() {
    val repo = ServiceLocator.repository
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf<List<Book>>(emptyList()) }

    LaunchedEffect(Unit) {
        results = repo.search(query = null)
    }

    Column(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                // naive search trigger
                LaunchedEffect(query) {}
            },
            label = { Text("Поиск по названию или автору") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { 
            // trigger search
            @Suppress("DeferredResultUnused")
            kotlinx.coroutines.GlobalScope.launch {
                results = repo.search(query = query)
            }
        }) { Text("Искать") }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
            items(results) { book -> BookRow(book) }
        }
    }
}

@Composable fun HistoryScreen() { PlaceholderScreen("История") }
@Composable fun FavoritesScreen() { PlaceholderScreen("Избранное") }

@Composable
fun LinksScreen() {
    val context = LocalContext.current
    val links = listOf(
        "Сайт" to "https://example.com",
        "Группа VK" to "https://vk.com/example",
        "Telegram" to "https://t.me/example"
    )
    LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(links) { (label, url) ->
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            )
            Text(url, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Divider()
        }
    }
}

@Composable fun SettingsScreen() { PlaceholderScreen("Настройки (тема, шрифт, эквалайзер)") }

@Composable
private fun PlaceholderScreen(title: String) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text("Скоро здесь будет функционал")
        }
    }
}