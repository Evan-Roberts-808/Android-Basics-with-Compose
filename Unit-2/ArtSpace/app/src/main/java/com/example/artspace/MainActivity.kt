package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.artspace.ui.theme.ArtSpaceTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.shadow
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpace(
                        windowWidthSizeClass = windowSizeClass.widthSizeClass,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ImageFrame(currentImage: Int, currentTitle: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .shadow(4.dp, ambientColor = Color.LightGray)
            .fillMaxWidth(1f)
            .aspectRatio(1f)
    ) {
        Image(
            painter = painterResource(id = currentImage),
            contentDescription = stringResource(id = currentTitle),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun ImageDetails(currentTitle: Int, currentSubtitle: Int, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = currentTitle),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,

            )
        Text(text = stringResource(id = currentSubtitle), fontSize = 14.sp)
    }
}

@Composable
fun UIContainer(
    step: Int,
    onNextClick: (Int) -> Unit,
    onPrevClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (isExpandedScreen) Arrangement.SpaceAround else Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { prevImage(step, onPrevClick) },
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.prevbutton))
        }
        if (isExpandedScreen) {
            Spacer(Modifier.width(32.dp))
        } else {
            Spacer(Modifier.width(16.dp))
        }
        Button(
            onClick = { nextImage(step, onNextClick) },
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.nextbutton))
        }
    }
}

private fun nextImage(step: Int, updateStep: (Int) -> Unit) {
    if (step == 5) {
        updateStep(1)
    } else {
        updateStep(step + 1)
    }
}

private fun prevImage(step: Int, updateStep: (Int) -> Unit) {
    if (step == 1) {
        updateStep(5)
    } else {
        updateStep(step - 1)
    }
}

@Composable
fun ArtSpace(
    windowWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableStateOf(1) }
    var currentImage by remember { mutableStateOf(R.drawable.wakanarei) }
    var currentTitle by remember { mutableStateOf(R.string.image1) }
    var currentSubtitle by remember { mutableStateOf(R.string.image1desc) }

    LaunchedEffect(step) {
        when (step) {
            1 -> {
                currentImage = R.drawable.wakanarei
                currentTitle = R.string.image1
                currentSubtitle = R.string.image1desc
            }
            2 -> {
                currentImage = R.drawable.asahirokka
                currentTitle = R.string.image2
                currentSubtitle = R.string.image2desc
            }
            3 -> {
                currentImage = R.drawable.satoumasuki
                currentTitle = R.string.image3
                currentSubtitle = R.string.image3desc
            }
            4 -> {
                currentImage = R.drawable.nyubarareona
                currentTitle = R.string.image4
                currentSubtitle = R.string.image4desc
            }
            5 -> {
                currentImage = R.drawable.tamadechiyu
                currentTitle = R.string.image5
                currentSubtitle = R.string.image5desc
            }
        }
    }

    val isCompact = windowWidthSizeClass == WindowWidthSizeClass.Compact
    val isExpanded = windowWidthSizeClass == WindowWidthSizeClass.Expanded

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp), // Overall horizontal padding for the screen
        horizontalAlignment = Alignment.CenterHorizontally, // Center all content horizontally
        verticalArrangement = Arrangement.SpaceBetween // Push content to top, buttons to bottom
    ) {
        // --- Top/Middle Content (Image and Details) ---
        Column(
            modifier = Modifier
                .weight(1f, fill = false) // Takes available vertical space, but children don't stretch
                .padding(top = if (isCompact) 16.dp else 48.dp), // More top padding on larger screens
            horizontalAlignment = Alignment.CenterHorizontally // Center image frame and details within this column
        ) {
            ImageFrame(currentImage = currentImage, currentTitle = currentTitle)
            Spacer(Modifier.height(if (isCompact) 24.dp else 48.dp)) // Adjust space based on screen size
            ImageDetails(currentTitle = currentTitle, currentSubtitle = currentSubtitle)
        }


        UIContainer(
            step = step,
            onNextClick = { newStep -> step = newStep },
            onPrevClick = { newStep -> step = newStep },
            isExpandedScreen = isExpanded,
            modifier = Modifier
                .padding(vertical = if (isCompact) 32.dp else 64.dp) // Padding for buttons from content and bottom edge
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640, name = "Phone Portrait (Compact)")
@Composable
fun ArtSpaceCompactPreview() {
    ArtSpaceTheme {
        ArtSpace(WindowWidthSizeClass.Compact)
    }
}

@Preview(showBackground = true, widthDp = 1024, heightDp = 768, name = "Tablet Landscape (Expanded)")
@Composable
fun ArtSpaceExpandedPreview() {
    ArtSpaceTheme {
        ArtSpace(WindowWidthSizeClass.Expanded)
    }
}

@Preview(showBackground = true, widthDp = 600, heightDp = 900, name = "Tablet Portrait (Medium)")
@Composable
fun ArtSpaceMediumPreview() {
    ArtSpaceTheme {
        ArtSpace(WindowWidthSizeClass.Medium)
    }
}