package com.example.week3.soal2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.week3.ui.theme.Week3Theme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.week3.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class soal2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Clicker(
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}





@Composable
fun Clicker(modifier: Modifier = Modifier) {
    var coin by rememberSaveable { mutableStateOf(0.0) }
    var coinPerTap by rememberSaveable { mutableStateOf(1.0) }
    var upgrade by rememberSaveable { mutableStateOf(10.0) }

    var meow by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var mew by remember { mutableStateOf<Job?>(null) }

    val nextCoin = coinPerTap * 1.5
    val coinDifference = (upgrade-coin).coerceAtLeast(0.0)

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center,){
        Image(painter = painterResource(R.drawable.bg2),
            contentDescription = "img",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize())

        Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp)
        ) {


            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Your Coins", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = "%.0f".format(coin),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Green)
                    Text(text = "%.1f coins per tap".format(coinPerTap),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White)
                }
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "Tap the Cat!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .size(220.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ){
                            coin+= coinPerTap
                            meow = true
                            mew?.cancel()
                            mew = coroutineScope.launch {
                                delay(300)
                                meow = false
                            }
                        }
                    ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        val imageRes = if (meow) {
                            R.drawable.meow
                        } else {
                            R.drawable.mew
                        }
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Cat",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                Text(
                    text = if (meow) "Meow!" else "Purr~",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }



            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Give Me Your Coin",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Next upgrade: +%.1f coins per tap".format(nextCoin - coinPerTap),
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    val canAfford = coin >= upgrade
                    Button(
                        onClick = {
                            if (canAfford) {
                                coin -= upgrade          // Mengurangi coin dengan biaya upgrade
                                upgrade *= 2.0           // Biaya upgrade dikali 2
                                coinPerTap *= 1.5        // Value dikali 1.5
                            }
                        },
                        enabled = canAfford,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            disabledContainerColor = Color(0xFFBDBDBD)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = if (canAfford) {
                                "Pay for %.0f coins".format(upgrade)
                            } else {
                                "Find %.0f more coins".format(coinDifference)
                            },
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClickerPreview() {
    Week3Theme {
        Clicker()
    }
}