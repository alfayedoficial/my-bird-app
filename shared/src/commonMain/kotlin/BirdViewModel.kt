/*
 * Created by Ali Al Fayed on 7/29/23, 10:00 PM.
 *
 * Copyright (c) 2023  Ali Al Fayed. All rights reserved.
 * Last modified: 7/31/23, 5:57 PM
 *
 * LinkedIn: https://www.linkedin.com/in/alfayedoficial/
 */

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import model.BirdImages


data class BirdsUiState(
    val images : BirdImages = emptyList(),
    val selectCategory: String? = null
){
    val categories = images.map { it.category }.toSet().union(setOf("All"))
    val selectImages = when(selectCategory){
        "All" -> images
        else ->{
            images.filter { it.category == selectCategory }
        }
    }
}
class BirdViewModel : ViewModel() {

    private val _uiState : MutableStateFlow<BirdsUiState> = MutableStateFlow(BirdsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateImages()
    }


    fun selectCategory(category: String){
        _uiState.update {
            it.copy(selectCategory = category)
        }
    }

    fun updateImages(){
        viewModelScope.launch {
            val images = getImages()
            _uiState.update {
                it.copy(images = images)
            }
        }
    }


    private val httpClient = HttpClient {
        install(ContentNegotiation){
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }


    private suspend fun getImages():BirdImages {
        return httpClient
            .get("https://sebastianaigner.github.io/demo-image-api/pictures.json")
            .body<BirdImages>()
    }

    override fun onCleared() {
        httpClient.close()
    }
}