package com.example.unilocal.ui.screens.user.tabs

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.unilocal.model.Schedule
import com.example.unilocal.ui.nav.LocalMainViewModel
import com.example.unilocal.R
import com.example.unilocal.model.Place
import com.example.unilocal.model.Review
import com.example.unilocal.ui.viewmodel.MainViewModel
import com.example.unilocal.ui.viewmodel.PlacesViewModel
import com.example.unilocal.ui.viewmodel.ReviewsViewModel
import com.example.unilocal.ui.viewmodel.UsersViewModel
import java.time.LocalDateTime
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetail(
    userId: String?,
    placeId: String,
    mainViewModel: MainViewModel
) {


    val place = mainViewModel.placesViewModel.findByID(placeId)
    val images = place?.images ?: emptyList()
    val reviews = remember { mutableStateListOf<Review>() }
    reviews.addAll(mainViewModel.reviewsViewModel.getReviewsByPlace(placeId))

    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Overview", "Reviews", "More")

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // 🖼️ Grid de imágenes del lugar
            LazyHorizontalGrid(
                rows = GridCells.Fixed(2),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(all = 10.dp)
            ) {
                item(span = { GridItemSpan(2) }) {
                    AsyncImage(
                        model = images.firstOrNull(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(250.dp)
                            .clip(RoundedCornerShape(15.dp))
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
                            .clip(RoundedCornerShape(15.dp))
                    )
                }
            }

            // 🧭 Tabs de navegación
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.background
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            val usersViewModel = mainViewModel.usersViewModel
            val context = LocalContext.current

            // 📄 Contenido según tab
            when (selectedTabIndex) {
                0 -> OverviewTab(place)
                1 -> ReviewsTab(mainViewModel.reviewsViewModel, placeId, userId)
                2 -> MoreTab(
                    onNavigate = { key ->
                        when (key) {
                            "save_place" -> {
                                println("🎯 Guardando lugar - userId: $userId, placeId: $placeId")

                                usersViewModel.addFavoritePlace(
                                    userId = userId ?: "",
                                    placeId = placeId
                                )

                                Toast.makeText(context, "Place saved to favorites", Toast.LENGTH_SHORT).show()
                            }

                            "leave_review" -> {
                                selectedTabIndex = 1
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun OverviewTab(place: Place?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        item {
            Text(
                text = place?.title ?: "",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = place?.description ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
@Composable
fun ReviewsTab(
    reviewsViewModel: ReviewsViewModel,
    placeId: String,
    userId: String?
) {
    val reviews = remember { mutableStateListOf<Review>() }
    reviews.clear()
    reviews.addAll(reviewsViewModel.getReviewsByPlace(placeId))

    Box(modifier = Modifier.fillMaxSize()) {

        // 🧾 Lista de comentarios
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp), // deja espacio para el campo de texto
        ) {
            item {
                Text(
                    text = "Reviews",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }

            items(reviews) { review ->
                ListItem(
                    headlineContent = { Text(review.username) },
                    supportingContent = { Text(review.comment) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    }
                )
            }
        }

        // ✏️ Campo para escribir comentario (pegado abajo)
        CreateCommentForm(
            placeId = placeId,
            userId = userId,
            onCreateReview = {
                reviews.add(it)
                reviewsViewModel.create(it)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}


@Composable
fun MoreTab(
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onNavigate("save_place") }
                .padding(8.dp)
        ) {
            Icon(Icons.Outlined.Add, contentDescription = null)
            Text(text = "Save place", modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- Opción 2: Dejar reseña ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onNavigate("leave_review") }
                .padding(8.dp)
        ) {
            Icon(Icons.Default.Create, contentDescription = null)
            Text(text = "Leave a review", modifier = Modifier.padding(start = 8.dp))
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
    onCreateReview: (Review) -> Unit,
    modifier: Modifier = Modifier
) {
    val mainViewModel = LocalMainViewModel.current
    val currentUser = mainViewModel.usersViewModel.getUserById(userId ?: "")

    var comment by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Escribe un comentario...") }
        )

        IconButton(
            onClick = {
                if (comment.isNotEmpty()) {
                    val review = Review(
                        id = UUID.randomUUID().toString(),
                        userID = userId ?: "",
                        username = currentUser?.username ?: "Unknown User",
                        placeID = placeId,
                        rating = 5,
                        comment = comment,
                        date = LocalDateTime.now()
                    )
                    onCreateReview(review)
                    comment = ""
                }
            }
        ) {
            Icon(Icons.Default.Send, contentDescription = null)
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