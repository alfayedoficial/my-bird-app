/*
 * Created by Ali Al Fayed on 7/29/23, 10:00 PM.
 *
 * Copyright (c) 2023  Ali Al Fayed. All rights reserved.
 * Last modified: 7/31/23, 5:57 PM
 *
 * LinkedIn: https://www.linkedin.com/in/alfayedoficial/
 */

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.BirdImage
import org.jetbrains.compose.resources.ExperimentalResourceApi


@Composable
fun BirdAppTheme(content:@Composable ()-> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            AbsoluteCutCornerShape(0.dp),
            AbsoluteCutCornerShape(0.dp),
            AbsoluteCutCornerShape(0.dp)
        )
    ){
        content()
    }
}

@Composable
fun App() {
    BirdAppTheme {
        val mViewModel = getViewModel(Unit , viewModelFactory { BirdViewModel() })
        BirdsPage(mViewModel)
    }
}

@Composable
fun BirdsPage(mViewModel: BirdViewModel) {

    val uiState by mViewModel.uiState.collectAsState()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Row(
            Modifier.fillMaxWidth().padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            for (cat in uiState.categories){
                Button({
                    mViewModel.selectCategory(cat?:"")
                },
                    modifier = Modifier.aspectRatio(1.0f).fillMaxSize().weight(1.0f),
                    elevation = ButtonDefaults.elevation(0.dp , 0.dp)
                    ){
                    Text(cat?:"")
                }
            }
        }

        AnimatedVisibility(uiState.selectImages.isNotEmpty()){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
                content = {
                    items(uiState.selectImages){
                        BirdImageCell(it)
                    }
                }
            )


        }

    }



}

@Composable
fun BirdImageCell(birdImage: BirdImage) {

    KamelImage(
        asyncPainterResource(data= "https://sebastianaigner.github.io/demo-image-api/${birdImage.path}") ,
        contentDescription = "${birdImage.category} by ${birdImage.author}" ,
        contentScale = ContentScale.Crop ,
        modifier = Modifier.fillMaxWidth().aspectRatio(1.0f )
    )
}

expect fun getPlatformName(): String