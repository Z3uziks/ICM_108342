package com.example.homework1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework1.ui.theme.Homework1Theme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.TextField
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)

        setContent {
            Homework1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WatchListScreen(viewModel = viewModel(factory = viewModelFactory))
                }
            }
        }
    }
}

enum class FilterType {
    ALL, FAVORITES, WATCHED
}

class WatchListViewModel : ViewModel() {
    data class WatchListItem(
        val title: String,
        val isFavorite: MutableState<Boolean> = mutableStateOf(false),
        val isWatched: MutableState<Boolean> = mutableStateOf(false)
    )

    var isFilterActive = mutableStateOf(false)
    var newItemText = mutableStateOf("")
    var isWatchedFilterActive = mutableStateOf(false)
    var filterType = mutableStateOf(FilterType.ALL)

    var watchList = mutableStateListOf(
        WatchListItem("Filme 1"),
        WatchListItem("Filme 2", mutableStateOf(true)), // Segundo filme marcado como favorito
        WatchListItem("Filme 3")
    )

    fun addItemToWatchList(item: String) {
        val newItem = WatchListItem(item)
        if (isFilterActive.value) {
            newItem.isFavorite.value = true // Se o filtro de favoritos estiver ativado, o novo item é marcado como favorito
        }
        watchList.add(newItem)
    }

    fun toggleFavoriteStatus(item: WatchListItem) {
        item.isFavorite.value = !item.isFavorite.value
    }

    fun removeItemFromWatchList(item: WatchListItem) {
        watchList.remove(item)
    }

    fun toggleFilter() {
        filterType.value = when (filterType.value) {
            FilterType.ALL -> FilterType.FAVORITES
            FilterType.FAVORITES -> FilterType.WATCHED
            FilterType.WATCHED -> FilterType.ALL
        }
    }

    fun watchListFiltered(): List<WatchListItem> {
        return when (filterType.value) {
            FilterType.ALL -> watchList
            FilterType.FAVORITES -> watchList.filter { it.isFavorite.value }
            FilterType.WATCHED -> watchList.filter { it.isWatched.value }
        }
    }

    fun setNewItemText(text: String) {
        newItemText.value = text
    }

    fun toggleWatchedStatus(item: WatchListItem) {
        item.isWatched.value = !item.isWatched.value
    }

}

@Composable
fun WatchListScreen(viewModel: WatchListViewModel) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        item {
            Text(
                text = "Watch List",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            Button(
                onClick = { viewModel.toggleFilter() }
            ) {
                Text(when (viewModel.filterType.value) {
                    FilterType.ALL -> "Mostrar Todos"
                    FilterType.FAVORITES -> "Mostrar Favoritos"
                    FilterType.WATCHED -> "Mostrar Já Vistos"
                })
            }
        }

        item{
            TextField(
                value = viewModel.newItemText.value,
                onValueChange = { viewModel.setNewItemText(it) },
                label = { Text("Novo Item") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item{Spacer(modifier = Modifier.height(8.dp))}

        item {
            Button(
                onClick = {
                    viewModel.addItemToWatchList(viewModel.newItemText.value)
                    // Limpar o texto do campo após adicionar o item
                    viewModel.setNewItemText("")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar Item")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        item{Spacer(modifier = Modifier.height(8.dp))}

        items(viewModel.watchListFiltered()) { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { viewModel.toggleWatchedStatus(item) },
                ) {
                    Icon(
                        Icons.Filled.Done,
                        contentDescription = "Marcar como visto",
                        tint = if (item.isWatched.value) Color.Green else Color.Gray
                    )
                }
                // Botão para adicionar aos favoritos (com ícone de estrela)
                IconButton(
                    onClick = { viewModel.toggleFavoriteStatus(item) },
                ) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Adicionar aos favoritos",
                        // Cor do ícone baseada no status de favorito do item
                        tint = if (item.isFavorite.value) Color.Yellow else Color.Gray
                    )
                }
                // Botão para apagar o item da lista
                Button(
                    onClick = { viewModel.removeItemFromWatchList(item) }
                ) {
                    Text("Apagar")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Homework1Theme {
        WatchListScreen(viewModel = WatchListViewModel())
    }
}
