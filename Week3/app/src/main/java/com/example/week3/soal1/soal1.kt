package com.example.week3.soal1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import kotlin.random.Random
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


enum class state() {
    START,
    WAIT,
    GO,
    TRIAL,
    FINAL,
    FAILED
}

enum class reaction(val title: String, val icon: ImageVector, val color: Color){
    FAST("DANG YOU'RE SO FAST", Icons.Default.SelfImprovement, color = Color.Green),
    GOOD("YOUR REFLEX IS SO GOOD", Icons.Default.ThumbUp, color = Color.Blue),
    NORMAL("MEH LIKE OTHER PERSON", Icons.Default.PanTool, color = Color.Yellow),
    SLOW("YOU'RE LIKE A SNAIL", Icons.Default.ThumbDown, color = Color.Red)
}

fun catergory(avgTime: Long): reaction{
    return when{
        avgTime < 100 -> reaction.FAST
        avgTime < 200 -> reaction.GOOD
        avgTime < 450 -> reaction.NORMAL
        else -> reaction.SLOW
    }
}


@Composable
fun resultCard (
    result: List<Long?>,
    showAvg: Boolean = false,
    avg: Long = 0L
){
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth(0.85f).padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Trial Results",
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (i in 1..3) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "$i",
                            fontWeight = FontWeight.Bold,
                            color = Color.Green
                        )
                        val res = result.getOrNull(i - 1)
                        Text(
                            text = if (res != null) "${res}ms" else "-",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
            }
            if (showAvg) {
                Text("Average Score", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
                Text("${avg}ms", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            }
        }
    }
}


@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var tampilan by rememberSaveable { mutableStateOf(state.START) }

    var current by rememberSaveable { mutableStateOf(1) }
    var start by rememberSaveable { mutableStateOf(0L) }
    var last by rememberSaveable { mutableStateOf(0L) }
    var result = remember { mutableListOf<Long?>(null, null, null) }
    var failed by rememberSaveable { mutableStateOf(false) }

    var valid = result.filterNotNull()
    var avg = if (valid.isNotEmpty()) {
                    valid.average().toLong()
                } else {
                    null
                }

    LaunchedEffect(tampilan) {
        if (tampilan == state.WAIT){
            val randomDelay = Random.nextLong(500L, 4500L)
            delay(randomDelay)
            start = System.currentTimeMillis()
            tampilan = state.GO
        }
    }




    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (tampilan) {
            state.START -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Cyan)
                        .clickable {
                            failed = false
                            tampilan = state.WAIT
                        }
                ) {
                    Text(
                        text = "Reaction",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 35.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.FlashOn,
                        contentDescription = "Petir",
                        tint = Color.White,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 30.dp)
                    )
                    Text(
                        text = "Test",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "Click To Start",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    if (valid.isNotEmpty() || failed) {
                        Spacer(Modifier.height(24.dp))
                        resultCard(result = result)
                    }
                }
            }


            state.WAIT -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                        .clickable {
                            result[current - 1] = null
                            failed = true
                            tampilan = state.FAILED
                        }
                ) {
                    Text(
                        text = "Get Ready",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 35.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Danger",
                        tint = Color.White,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 30.dp)
                    )
                    Text(
                        text = "Wait for green light...",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "DON'T CLICK YET!",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }


            state.FAILED -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                        .clickable{
                            failed = true
                            if (current < 3) {
                                current++
                                tampilan = state.START
                            } else {
                                tampilan = state.FINAL
                            }
                        }
                ) {
                    Text(
                        text = "FAIL!",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 35.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.ThumbDown,
                        contentDescription = "Run",
                        tint = Color.White,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 30.dp)
                    )
                    Text(
                        text = "You clicked too early, TRY TO READ THE RULE BRO",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                    )
                    Text(
                        text = if (current < 3)
                            "Try Again"
                        else
                            "Tap to see final results",
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(24.dp))
                    resultCard(result = result)
                }
            }


            state.GO -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Green)
                        .clickable {
                            val end = System.currentTimeMillis()
                            last = end-start
                            result[current-1] = last

                            if (current <3){
                                tampilan = state.TRIAL
                            } else {
                                tampilan = state.FINAL
                            }
                        }
                ) {
                    Text(
                        text = "GO!",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 35.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.DirectionsRun,
                        contentDescription = "Run",
                        tint = Color.White,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 30.dp)
                    )
                    Text(
                        text = "CLICK NOW!",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "TAP AS FAST AS YOU CAN!",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }


            state.TRIAL -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Green)
                        .clickable{
                            current++
                            tampilan = state.WAIT
                        }
                ) {
                    Text(
                        text = "Trial $current Complete!",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 35.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Check",
                        tint = Color.White,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 30.dp)
                    )
                    Text(
                        text = "Time: ${last}ms",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "Continue to Trial ${current + 1}",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Spacer(Modifier.height(24.dp))
                    resultCard(result = result)
                }
            }

            state.FINAL -> {
                val categories = if (avg != null) {
                    catergory(avg)
                } else {
                    reaction.SLOW
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(categories.color)
                        .clickable {
                            current = 1
                            result[0] = null
                            result[1] = null
                            result[2] = null
                            tampilan = state.START
                        }
                ) {
                    Text(
                        categories.title,
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 35.dp)
                    )
                    Icon(
                        categories.icon,
                        contentDescription = "Check",
                        tint = Color.White,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 30.dp)
                    )
                    Text(
                        text = if (avg != null) {
                            "Average: ${avg}ms"
                        } else {
                            "Average: 0ms"
                        },
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "Click to Start New Test",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Spacer(Modifier.height(24.dp))
                    resultCard(result = result, showAvg = true, avg = avg ?: 0L
                    )
                }
            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Week3Theme {
        Greeting()
    }
}


