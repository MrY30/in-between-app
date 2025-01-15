package com.example.slotmachine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlotMachineApp()
        }
    }
}

@Composable
fun SlotMachineApp() {
    val scope = rememberCoroutineScope()
    var slot1 by remember { mutableIntStateOf(0) }
    var slot2 by remember { mutableIntStateOf(0) }
    var isSpinning by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display the two numbers
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(text = "$slot1", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(8.dp))
            Text(text = "$slot2", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(8.dp))
        }

        // Button to start the slot machine
        Button(
            onClick = {
                if (!isSpinning) {
                    isSpinning = true
                    scope.launch {
                        val startTime = System.currentTimeMillis()
                        while (System.currentTimeMillis() - startTime < 3000) { // Run for 5 seconds
                            var newSlot1: Int
                            var newSlot2: Int
                            do {
                                newSlot1 = Random.nextInt(1, 10)
                                newSlot2 = Random.nextInt(1, 10)
                            } while (newSlot1 > newSlot2)
                            slot1 = newSlot1
                            slot2 = newSlot2
                            delay(100) // Change numbers every 100ms
                        }
                        isSpinning = false
                    }
                }
            },
            enabled = !isSpinning
        ) {
            Text(text = if (isSpinning) "Spinning..." else "Start")
        }
    }
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun SlotMachinePreview(){
    SlotMachineApp()
}
