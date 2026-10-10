package com.example.week3.soal3

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week3.ui.theme.Week3Theme
import kotlinx.coroutines.delay
import kotlin.random.Random
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width


class soal3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ColorMatch(
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}



enum class state{
    START,
    COUNTDOWN,
    PLAYING,
    FINAL
}

enum class mode{
    COLOR,
    TEXT,
}

enum class choice{
    INK,
    WORD,
}

enum class colorData(val label: String, val color: Color) {
    RED("RED", Color.Red),
    BLUE("BLUE", Color.Blue),
    GREEN("GREEN", Color.Green),
    YELLOW("YELLOW", Color.Yellow),
    PURPLE("PURPLE", Color.Magenta),
}

data class question(
    val word: colorData,
    val ink: colorData
)




@Composable
fun ColorMatch(modifier: Modifier = Modifier) {
    var tampilan by rememberSaveable { mutableStateOf(state.START) }
    var countdown by remember { mutableStateOf("3") }

    var score by rememberSaveable { mutableIntStateOf(0) }
    var strikes by rememberSaveable { mutableIntStateOf(0) }
    var bestScore by rememberSaveable { mutableIntStateOf(0) }

    var currentMode by remember { mutableStateOf(mode.COLOR) }
    var currentWord by remember { mutableStateOf(colorData.RED) }
    var currentInk by remember { mutableStateOf(colorData.BLUE) }

    var leftButtonChoice by remember { mutableStateOf(colorData.RED) }
    var rightButtonChoice by remember { mutableStateOf(colorData.BLUE) }

    var time by remember { mutableStateOf(5000L) }

    val generateNewQuestion = {
        currentMode = if (Random.nextBoolean()) mode.COLOR else mode.TEXT
        currentWord = colorData.entries.random()
        currentInk = colorData.entries.random()

        while (currentWord == currentInk) {
            currentInk = colorData.entries.random()
        }

        val correctAnswer = if (currentMode == mode.COLOR) currentInk else currentWord
        val wrongChoices = colorData.entries.filter { it != correctAnswer }
        val wrongAnswer = wrongChoices.random()

        val isLeftCorrect = Random.nextBoolean()
        if (isLeftCorrect) {
            leftButtonChoice = correctAnswer
            rightButtonChoice = wrongAnswer
        } else {
            leftButtonChoice = wrongAnswer
            rightButtonChoice = correctAnswer
        }
        time = 5000L
    }


    val handleAnswer = { chosenColor: colorData ->
        val correctAnswer = if (currentMode == mode.COLOR) currentInk else currentWord
        if (chosenColor == correctAnswer) {
            score++
        } else {
            strikes++
        }

        if (strikes >= 3) {
            if (score > bestScore) {
                bestScore = score
            }
            tampilan = state.FINAL
        } else {
            generateNewQuestion()
        }
    }

    LaunchedEffect(tampilan) {
        if (tampilan == state.COUNTDOWN) {
            countdown = "3"
            delay(700)
            countdown = "2"
            delay(700)
            countdown = "1"
            delay(700)
            countdown = "Start!"
            delay(700)

            // Inisialisasi skor & strike awal permainan
            score = 0
            strikes = 0
            generateNewQuestion()
            tampilan = state.PLAYING
        }
    }


    LaunchedEffect(tampilan, currentWord) {
        if (tampilan == state.PLAYING) {
            while (time > 0 && tampilan == state.PLAYING) {
                delay(100)
                time -= 100
            }

            if (time <= 0 && tampilan == state.PLAYING) {
                strikes++
                if (strikes >= 3) {
                    if (score > bestScore) bestScore = score
                    tampilan = state.FINAL
                } else {
                    generateNewQuestion()
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center ) {
        Column() {
            when (tampilan){
                state.START -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text("Welcome", fontSize = 24.sp, color = Color.Black)
                        Text("to", fontSize = 20.sp, color = Color.Black, modifier = Modifier.padding(bottom = 8.dp))
                        Text(
                            "Color Word Matching",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 32.dp)
                        )
                        Button(
                            onClick = { tampilan = state.COUNTDOWN },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Start Game", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }


                state.COUNTDOWN -> {
                    Text(
                        text = countdown,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                }


                state.PLAYING -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Mode: ${currentMode.name}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("✅ $score", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("❌ $strikes/3", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${(time / 1000) + 1} s",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 24.dp)
                            )
                            Text(
                                text = if (currentMode == mode.COLOR) "Pilih WARNA TINTA" else "Pilih NAMA KATA",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            Text(
                                text = currentWord.label,
                                fontSize = 52.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = currentInk.color,
                                modifier = Modifier.padding(vertical = 24.dp)
                            )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Button(
                                onClick = { handleAnswer(leftButtonChoice) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                                    .height(55.dp)
                            ) {
                                Text(
                                    leftButtonChoice.name,
                                    color = Color.DarkGray,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Button(
                                onClick = { handleAnswer(rightButtonChoice) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                                    .height(55.dp)
                            ) {
                                Text(
                                    rightButtonChoice.name,
                                    color = Color.DarkGray,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        }
                    }
                }


                state.FINAL -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            "Game Over!",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        Text("You're Score", fontSize = 16.sp, color = Color.Gray)
                        Text(
                            "$score",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Text("Best Score", fontSize = 14.sp, color = Color.Gray)
                        Text(
                            "$bestScore",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 32.dp)
                        )

                        Button(
                            onClick = {
                                score = 0
                                strikes = 0
                                tampilan = state.COUNTDOWN
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(0.7f).padding(bottom = 12.dp)
                        ) {
                            Text(
                                "Restart Game",
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = { tampilan = state.START },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(0.7f)
                        ) {
                            Text("Exit", color = Color.DarkGray, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ColorMatchPreview(){
    ColorMatch()
}