package com.example.businesscardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.businesscardapp.ui.theme.BusinessCardAppTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BusinessCard(
                        stringResource(R.string.name),
                        jobTitle = stringResource(R.string.job_title),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BusinessCard(name: String, jobTitle: String, modifier: Modifier = Modifier) {
    Box(modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PersonalDetails(name = name, jobTitle = jobTitle, modifier = Modifier)
            Spacer(Modifier.height(32.dp))
            ContactDetails(modifier = Modifier)
        }
    }
}

@Composable
fun PersonalDetails(name: String, jobTitle: String, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.default_avatar_icon_of_social_media_user_vector)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Avatar Image
        Image(
            painter = image,
            contentDescription = stringResource(R.string.image_of_evan_roberts),
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(16.dp))

        // Name Text
        Text(
            text = name,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Job Title Text
        Text(
            text = jobTitle,
            fontSize = 18.sp, // Appropriate font size for job title
            color = MaterialTheme.colorScheme.onSurfaceVariant // Use a slightly muted color
        )
    }
}

@Composable
fun ContactDetails(modifier: Modifier = Modifier) {
    val instagramImage = painterResource(R.drawable.insta)
    val phoneImage = painterResource(R.drawable.phone)
    val emailImage = painterResource(R.drawable.email)

    Column(
        modifier = modifier
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactDetail(phoneImage, contentDescription = null, detail = "123-456-7891")
        Spacer(Modifier.height(8.dp))
        ContactDetail(
            emailImage,
            contentDescription = null,
            detail = "robertsevandevelopment@gmail.com"
        )
        Spacer(Modifier.height(8.dp))
        ContactDetail(instagramImage, contentDescription = null, detail = "@Evan-Roberts-808")
    }
}

@Composable
fun ContactDetail(
    image: Painter,
    contentDescription: String?,
    detail: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = image,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
        )
        Text(
            text = detail,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    BusinessCardAppTheme {
        BusinessCard(stringResource(R.string.name), jobTitle = stringResource(R.string.job_title))
    }
}