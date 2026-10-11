package com.example.week3.extraSoal1

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.ui.platform.LocalContext


class extraSoal1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Coffee(
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Coffee (modifier: Modifier = Modifier){
    val context = LocalContext.current

    var isLarge by rememberSaveable { mutableStateOf(false) }
    var qty by rememberSaveable { mutableStateOf(3) }

    val price = 25000
    val extra = if (isLarge) 6000 else 0
    val pricePerCup = price + extra

    val subtotal = pricePerCup * qty
    val tax = (subtotal * 0.1).toInt()
    val total = subtotal + tax

    val rupiah: (Int) -> String = { amount ->
        "Rp %, d". format(amount).replace(',','.')
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.TopCenter
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Kopi Kenangan Senja",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text("☕",
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier =  Modifier
                        .fillMaxWidth()
                        .border(
                            width = 0.4.dp,
                            color = Color.Red,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .background(Color.Red, shape = RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Coffee,
                                contentDescription = "Coffee",
                                tint = Color.White,
                                modifier = Modifier.size(64.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "Caramel Latte",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            "Espresso shot, steamed milk & caramel syrup",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text(
                            "${rupiah(pricePerCup)} / cup",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "PILIHAN UKURAN:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(45.dp)
                                .background(
                                    color = if (!isLarge) Color.White else Color.Gray,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .border(
                                    width = 1.5.dp,
                                    color = if (!isLarge) Color.Red else Color.DarkGray,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { isLarge = false },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Regular (+0)",
                                fontWeight = FontWeight.Bold,
                                color = if (!isLarge) Color.Black else Color.White
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(45.dp)
                                .background(
                                    color = if (isLarge) Color.White else Color.Gray,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .border(
                                    width = 1.5.dp,
                                    color = if (isLarge) Color.Red else Color.DarkGray,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { isLarge = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Large (+6k)",
                                fontWeight = FontWeight.Bold,
                                color = if (isLarge) Color.Black else Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                            .border(
                                width = 0.4.dp,
                                color = Color.Red,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Jumlah Pesanan:",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { if (qty > 1) qty-- },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.LightGray),
                                ) {
                                    Icon(
                                        Icons.Default.Remove,
                                        contentDescription = "Decrease",
                                        tint = Color.Black
                                    )
                                }
                                Text(
                                    "$qty",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                                IconButton(
                                    onClick = { qty++ },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Gray)
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = "Increase",
                                        tint = Color.DarkGray
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                            .border(
                                width = 0.4.dp,
                                color = Color.Red,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Subtotal:", color = Color.Gray)
                                Text(rupiah(subtotal), fontWeight = FontWeight.Medium)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Pajak Resto (10%):", color = Color.Gray)
                                Text(text = rupiah(tax), fontWeight = FontWeight.Medium)
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Total Tagihan:", fontWeight = FontWeight.Bold, color = Color.Black)
                                Text(text = rupiah(total), fontWeight = FontWeight.Bold, color = Color.Red)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        Toast.makeText(context, "Berhasil menambahkan $qty Caramel Latte ke keranjang!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                    Text("TAMBAH KE KERANJANG",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun coffeePreview(){
    Coffee()
}
