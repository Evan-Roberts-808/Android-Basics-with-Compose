package com.example.lemonade

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.copy
import com.example.lemonade.ui.theme.LemonadeTheme
import kotlin.random.Random
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Lemonade(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Header(modifier: Modifier = Modifier) {
    Text(
        text = "Lemonade",
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .background(Color(red = 24f, green = 0.984f, blue = 0f, alpha = 0.7f))
            .padding(25.dp)
    )
}

@Composable
fun Informational(modifier: Modifier = Modifier) {

    var step by remember { mutableStateOf(1) }
    var currentImage by remember { mutableStateOf(R.drawable.lemon_tree) }
    var currentDescription by remember { mutableStateOf(R.string.lemon_tree_description) }
    var clicksRemainingForStep2 by remember { mutableStateOf(0) }
    println(step)
    LaunchedEffect(step) {
        when (step) {
            1 -> {
                currentImage = R.drawable.lemon_tree
                currentDescription = R.string.lemon_tree_description
            }

            2 -> {
                if (clicksRemainingForStep2 == 0) {
                    clicksRemainingForStep2 = Random.nextInt(2, 5)
                }
                currentImage = R.drawable.lemon_squeeze
                currentDescription = R.string.lemon_description
            }

            3 -> {
                currentImage = R.drawable.lemon_drink
                currentDescription = R.string.glass_of_lemonade_description
            }

            4 -> {
                currentImage = R.drawable.lemon_restart
                currentDescription = R.string.empty_glass_description
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(Color(red = 0f, green = 1f, blue = 0f, alpha = 0.2f))
        ) {
            Image(
                painter = painterResource(currentImage),
                contentDescription = stringResource(currentDescription),
                modifier = Modifier

                    .clickable {
                        when (step) {
                            2 -> {
                                clicksRemainingForStep2--
                                if (clicksRemainingForStep2 == 0) {
                                    step = 3
                                } else {
                                    currentDescription = R.string.lemon_description
                                }
                            }

                            else -> {
                                step = if (step < 4) step + 1 else 1
                                if (step == 1) clicksRemainingForStep2 = 0
                            }
                        }
                    }
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(currentDescription)
        )
    }
}

@Composable
fun Lemonade(modifier: Modifier = Modifier) {
    Box(modifier) {
        Column(modifier = modifier.fillMaxSize()) {
            Header()
            Spacer(Modifier.height(16.dp))
            Informational(modifier = modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadePreview() {
    LemonadeTheme {
        Lemonade(Modifier)
    }
}