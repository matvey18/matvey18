package com.example.audiobooks

import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.audiobooks.playback.AudioService
import kotlinx.coroutines.launch
import kotlinx.coroutines.guava.await

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppScreen()
            }
        }
    }
}

@Composable
private fun AppScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()

    var controller by remember { mutableStateOf<MediaController?>(null) }
    var isPrepared by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val token = SessionToken(context, ComponentName(context, AudioService::class.java))
        controller = MediaController.Builder(context, token).buildAsync().await()
        controller?.let { ctrl ->
            isPlaying = ctrl.isPlaying
        }
    }

    val samples = remember {
        listOf(
            // Public sample streams for demo purposes
            "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3",
            "https://storage.googleapis.com/exoplayer-test-media-1/mp3/3.mp3"
        )
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Аудиокниги", style = MaterialTheme.typography.titleLarge)

            samples.forEachIndexed { index, url ->
                ElevatedCard(onClick = {
                    scope.launch {
                        controller?.let { ctrl ->
                            ctrl.setMediaItem(MediaItem.fromUri(url))
                            ctrl.prepare()
                            isPrepared = true
                            ctrl.play()
                            isPlaying = true
                        }
                    }
                }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Трек ${index + 1}")
                        Text("Воспроизвести")
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = {
                    scope.launch {
                        controller?.play()
                        isPlaying = true
                    }
                }, enabled = isPrepared) {
                    Text("Пуск")
                }
                Button(onClick = {
                    scope.launch {
                        controller?.pause()
                        isPlaying = false
                    }
                }, enabled = isPrepared && isPlaying) {
                    Text("Пауза")
                }
            }
        }
    }
}