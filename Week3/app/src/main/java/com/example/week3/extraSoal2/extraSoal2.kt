package com.example.week3.extraSoal2

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week3.ui.theme.Week3Theme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import com.example.week3.extraSoal1.Coffee


class extraSoal1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Journal(
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun Journal (modifier: Modifier = Modifier){
    val context = LocalContext.current

    var spot by rememberSaveable { mutableStateOf("Fjellheisen Cable Car") }
    var experience by rememberSaveable { mutableStateOf("Pemandangan langit malam hijau aurora spektakuler dari puncak gunung...") }
    var notes by rememberSaveable { mutableStateOf("") }

    var rating by rememberSaveable { mutableStateOf("Luar Biasa") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Black,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Spot wisata: $spot berhasil disimpan!",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                containerColor = Color.Cyan,
                contentColor = Color.Black,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Journal")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                                colors = listOf(Color.Blue, Color.Cyan)
                            ), shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column() {
                        Text(
                            "LOG PERJALANAN",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Tromsø, Norway ❄️",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Text(
                            "Ekspedisi Aurora Borealis",
                            fontSize = 13.sp,
                            color = Color.White
                        )
                    }
                }


                Column {
                    Text(
                        "SPOT WISATA FAVORIT",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    OutlinedTextField(
                        value = spot,
                        onValueChange = { spot = it },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.DarkGray,
                            unfocusedContainerColor = Color.DarkGray,
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }


                Column {
                    Text(
                        "APA YANG PALING KAMU NIKMATI?",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    OutlinedTextField(
                        value = experience,
                        onValueChange = { experience = it },
                        minLines = 3,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.DarkGray,
                            unfocusedContainerColor = Color.DarkGray,
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }


                Column {
                    Text(
                        "CATATAN TAMBAHAN / PERLENGKAPAN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        placeholder = {
                            Text(
                                "(Ketik perlengkapan ekstra di sini)",
                                color = Color.Gray
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.DarkGray,
                            unfocusedContainerColor = Color.DarkGray,
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }


                Column {
                    Text(
                        text = "TINGKAT KEPUASAN:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val options = listOf("Biasa", "Seru", "Luar Biasa")
                        options.forEach { option ->
                            val isSelected = rating == option
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) Color.Cyan else Color.DarkGray,
                                modifier = Modifier.clickable { rating = option }
                            ) {
                                Text(
                                    if (option == "Luar Biasa") "$option ★" else option,
                                    color = if (isSelected) Color.Black else Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
            Row {
                Text("Status: ",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(" Draft Tersimpan",
                    fontSize = 13.sp,
                    color = Color.Cyan,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun JournalPreview(){
    Journal()
}