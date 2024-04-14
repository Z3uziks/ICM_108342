package com.example.practice_composebasicssecond

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice_composebasicssecond.ui.theme.PracticeComposeBasicsSecondTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeComposeBasicsSecondTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskCompleted()
                }
            }
        }
    }
}

@Composable
fun ShowImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.ic_task_completed)
    Image(
        painter = image,
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
    )
}


@Composable
fun TaskCompleted(modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        ShowImage()
        Text(
            text = stringResource(R.string.first_des),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
        )
        Text(
            text = stringResource(R.string.sec_des),
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PracticeComposeBasicsSecondTheme {
        TaskCompleted()
    }
}