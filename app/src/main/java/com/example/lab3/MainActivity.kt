package com.example.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.lab3.ui.theme.RecipeListScreen
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab3.model.sampleRecipes
import com.example.lab3.ui.theme.RecipeExplorerApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Use MaterialTheme to apply Material3 colors and typography
            MaterialTheme {
                // Reads current window size for the app to determine layout
                val windowSizeClass = calculateWindowSizeClass(this)
                RecipeExplorerApp(windowSizeClass = windowSizeClass)
            }
        }
    }
}

// Preview of the application without running rip my laptop
@Preview(showBackground = true)
@Composable
fun Lab3Preview() {
    MaterialTheme {
        RecipeListScreen(
            recipes = sampleRecipes,
            onRecipeClick = {}
        )
    }
}

