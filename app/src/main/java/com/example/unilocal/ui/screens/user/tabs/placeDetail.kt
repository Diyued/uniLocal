package com.example.unilocal.ui.screens.user.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.unilocal.model.Schedule
import com.example.unilocal.ui.nav.LocalMainViewModel
import com.example.unilocal.R
import com.example.unilocal.model.Review
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetail(
    userId: String?,
    placeId: String
){
    val placesViewModel = LocalMainViewModel.current.placesViewModel
    val reviewsViewModel = LocalMainViewModel.current.reviewsViewModel

    val place = placesViewModel.findByID(placeId)
    val images = place?.images ?: emptyList()
    val reviews = remember{ mutableStateListOf<Review>() }
    reviews.addAll(reviewsViewModel.getReviewsByPlace(placeId))

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showComments by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showComments = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(count = 2),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(all = 10.dp)
            ) {
                item(span = { GridItemSpan(currentLineSpan = 2) }) {
                    AsyncImage(
                        model = images.first(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(250.dp)
                            .clip(RoundedCornerShape(size = 15.dp))
                    )
                }
                items(images.drop(1)) { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(150.dp)
                            .clip(RoundedCornerShape(size = 15.dp))
                    )
                }
            }
            PlaceDetail(
                nombre = place?.title ?: "",
                categoria = place?.type?.displayName ?: "",
                ciudad = place?.city?.displayName ?: "",
                direccion = place?.address ?: "",
                telefono = place?.phoneNumber ?: "",
                horarios = place?.schedules ?: emptyList(),
                descripcion = place?.description ?: "",
                abierto = place?.isOpen() ?: false
            )
        }
        if(showComments){
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    showComments = false
                }
            ){

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    Text(
                        text = "Comentarios",
                    )
                    CommentList(
                        reviews = reviews

                    )
                    CreateCommentForm(
                        placeId = placeId,
                        userId = userId,
                        onCreateReview = {
                            reviews.add(it)
                            reviewsViewModel.create(it)

                        }
                    )
                }

            }
        }

    }

}

@Composable
fun CommentList(
    reviews: List<Review>
){
    LazyColumn {
        items(reviews){
            ListItem(
                headlineContent = {
                    Text(
                        text = it.username
                    )
                },
                supportingContent = {
                    Text(
                        text = it.comment
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null
                    )
                }
            )
        }

    }

}

@Composable
fun CreateCommentForm(
    placeId: String,
    userId: String?,
    onCreateReview: (Review) -> Unit
){
    var comment by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            value = comment,
            onValueChange = {comment = it},
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    text = "Escribe un comentario..."
                )
            }
        )
        IconButton(
            onClick = {
                val review = Review(
                    id = UUID.randomUUID().toString(),
                    userID = userId?: "",
                    username = "Carlos",
                    placeID = placeId,
                    rating = 5,
                    comment = comment,
                    date = java.time.LocalDateTime.now()

                )
                if (comment.isEmpty()){
                    return@IconButton
                }
                onCreateReview(review)
                comment = ""
            }
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null
            )
        }
    }
}

@Composable
fun PlaceDetail(
    nombre: String,
    categoria: String,
    ciudad: String,
    direccion: String,
    telefono: String,
    horarios: List<Schedule>,
    descripcion: String,
    abierto: Boolean
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (abierto) stringResource(R.string.txt_open) else stringResource(R.string.txt_close),
                        color = if (abierto) colorResource(R.color.open) else colorResource(R.color.close),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = categoria,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }
}