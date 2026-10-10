package com.example.week3.soal4

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import com.example.week3.soal3.ColorMatch


class soal4 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RockPaperScissors(
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

enum class state {
    START,
    PICK,
    REVEAL,
    FINAL
}

enum class RPSmove (val emoji: String, val label: String){
    ROCK("✊", "Rock"),
    PAPER("🖐️", "Paper"),
    SCISSORS("✌️", "Scissors")
}

enum class roundResult{
    WIN,
    LOSE,
    DRAW,
}


@Composable
fun RockPaperScissors(modifier: Modifier = Modifier){
    val bestOfN = 5
    val target = (bestOfN/2) +1

    var tampilan by rememberSaveable { mutableStateOf(state.START) }

    var userScore by rememberSaveable { mutableStateOf(0) }
    var cpuScore by rememberSaveable { mutableStateOf(0) }
    var bestScore by rememberSaveable { mutableStateOf(0) }

    var userChoice by remember { mutableStateOf<RPSmove?>(null) }
    var cpuChoice by remember { mutableStateOf<RPSmove?>(null) }
    var resultText by remember { mutableStateOf("") }

    var shuffle by remember { mutableStateOf(RPSmove.entries.shuffled()) }


    val reset = {
        userChoice = null
        cpuChoice = null
        shuffle = RPSmove.entries.shuffled()
    }

    val userPick = { move: RPSmove ->
        userChoice = move
        cpuChoice = RPSmove.entries.random()

        val result = when {
            userChoice == cpuChoice -> roundResult.DRAW
            (userChoice == RPSmove.ROCK && cpuChoice == RPSmove.SCISSORS) ||
            (userChoice == RPSmove.PAPER && cpuChoice == RPSmove.ROCK) ||
            (userChoice == RPSmove.SCISSORS && cpuChoice == RPSmove.PAPER) -> {
                userScore++
                roundResult.WIN
            }
            else -> {
                cpuScore++
                roundResult.LOSE
            }
        }

        resultText = when (result){
            roundResult.WIN -> "You Win!"
            roundResult.LOSE -> "You Lose"
            roundResult.DRAW -> "Draw"
        }

        tampilan = state.REVEAL
    }

    LaunchedEffect(tampilan) {
        if (tampilan == state.REVEAL){
            delay(1500L)
            if (userScore >= target || cpuScore >= target){
                val selisih = userScore-cpuScore
                if (selisih > bestScore){
                    bestScore = selisih
                }
                tampilan = state.FINAL
            } else {
                reset()
                tampilan = state.PICK
            }
        }
    }


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            when (tampilan){
                state.START -> {
                    Text("Rock . Paper . Scissors",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(32.dp))
                    Button(
                        onClick = {
                            userScore = 0
                            cpuScore = 0
                            reset()
                            tampilan = state.PICK
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(50.dp)
                    ) {
                        Text("Start",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold)
                    }
                }


                state.PICK, state.REVEAL -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 48.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("👩 $userScore - $cpuScore 🤖",
                            fontSize = 16.sp,
                            color = Color.Black)
                        Text("Best of $bestOfN",
                            fontSize = 16.sp,
                            color = Color.Black)
                    }

                    if (tampilan == state.PICK) {
                        Text(
                            "Pick your move!",
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        Row(
                            modifier = Modifier.padding(bottom = 64.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "❓", fontSize = 48.sp)
                            Text(" VS ",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Text(text = "❓", fontSize = 48.sp)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            shuffle.forEach { move ->
                                Button(
                                    onClick = { userPick(move) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(55.dp)

                                        .padding(horizontal = 4.dp)
                                ) {
                                    Text(
                                        text = "${move.emoji} ${move.label}",
                                        color = Color.Black,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                    } else {
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.padding(bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(userChoice?.emoji ?: "",
                                fontSize = 56.sp,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text( "VS",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            Text(cpuChoice?.emoji ?: "",
                                fontSize = 56.sp,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                        Text(text = resultText,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(bottom = 48.dp)
                        )
                    }
                }


                state.FINAL -> {
                    val isWin = userScore > cpuScore
                    Text(if (isWin) "You Win the Match!" else "You Lose the Match",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    Text("Final Score",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text("$userScore - $cpuScore",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text("Best Score",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text("$bestScore",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                    Button(onClick = {
                        userScore = 0
                        cpuScore = 0
                        reset()
                        tampilan = state.PICK
                    },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(50.dp)
                            .padding(bottom = 12.dp)
                        ) {
                        Text("Restart",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(onClick = {tampilan = state.START},
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(50.dp)
                            .padding(bottom = 12.dp)
                    ) {
                        Text("Exit",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }


}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RockPaperScissorsPreview(){
    RockPaperScissors()
}