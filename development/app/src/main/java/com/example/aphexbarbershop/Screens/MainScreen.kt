package com.example.aphexbarbershop.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.aphexbarbershop.MainViewModel
import com.example.aphexbarbershop.Models.Haircut
import com.example.aphexbarbershop.R

@Composable
fun MainScreen(navController: NavHostController, MainViewModel: MainViewModel) {
    // Observe the haircuts list
    val haircuts by MainViewModel.haircuts

    // Load haircuts when the screen is launched
    LaunchedEffect(Unit) {
        MainViewModel.fetchHaircuts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)), // Light background
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with logo and menu icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.aphexlogobarber),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(70.dp)
                    .width(160.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Search field
        Row(
            modifier = Modifier
                .width(340.dp)
                .background(Color(0xFF98E58C), RoundedCornerShape(12.dp))
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Поиск",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Discount information block
        Box(
            modifier = Modifier
                .width(340.dp)
                .background(Color(0xFF98E58C), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Скидка",
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Color(0xFFD32F2F), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "скидка за 5 и более стрижек",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.width(340.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Все прически", "Мужские", "Женские").forEach { category ->
                Box(
                    modifier = Modifier
                        .background(
                            if (category == "Все прически") Color(0xFF98E58C) else Color.LightGray,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = category,
                        color = if (category == "Все прически") Color.White else Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (haircuts.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(haircuts) { haircut ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val photoUrl = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(haircut.photo)
                                    .size(450, 450).build()
                            ).state

                            if (photoUrl is AsyncImagePainter.State.Success) {
                                Image(
                                    painter = photoUrl.painter!!,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(100.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            }
                            if (photoUrl is AsyncImagePainter.State.Loading) {
                                Box(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                            if (photoUrl is AsyncImagePainter.State.Error) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.aphexlogobarber),
                                        contentDescription = "Fallback Image",
                                        modifier = Modifier.size(50.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = haircut.name, fontWeight = FontWeight.Bold)
                            //Text(text = haircut.gender.toString(), fontSize = 12.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "${haircut.price} ₽", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Navigation panel
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = { /*todo*/ },
                    modifier = Modifier
                        .size(64.dp) // Увеличение размера кнопки
                        .background(
                            color = Color(0xFF99D77D).copy(alpha = 0.5f),
                            shape = RoundedCornerShape(10.dp) // Закругление углов
                        )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(64.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "Go to Main",
                            tint = Color(0xFFFFFFFF), // Цвет иконки
                            modifier = Modifier.size(39.dp) // Увеличение размера иконки (при желании)
                        )
                    }
                }
                IconButton(
                    onClick = { /*todo*/ },
                    modifier = Modifier
                        .size(64.dp) // Увеличение размера кнопки
                        .background(
                            color = Color(0xFF99D77D).copy(alpha = 0.5f),
                            shape = RoundedCornerShape(10.dp) // Закругление углов
                        )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(64.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Go to Main",
                            tint = Color(0xFFFFFFFF), // Цвет иконки
                            modifier = Modifier.size(39.dp) // Увеличение размера иконки (при желании)
                        )
                    }
                }
                IconButton(
                    onClick = { /*todo*/ },
                    modifier = Modifier
                        .size(64.dp) // Увеличение размера кнопки
                        .background(
                            color = Color(0xFF99D77D).copy(alpha = 0.5f),
                            shape = RoundedCornerShape(10.dp) // Закругление углов
                        )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(64.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.userprofile),
                            contentDescription = "Go to Main",
                            tint = Color(0xFFFFFFFF), // Цвет иконки
                            modifier = Modifier.size(39.dp) // Увеличение размера иконки (при желании)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(navController, MainViewModel())
}