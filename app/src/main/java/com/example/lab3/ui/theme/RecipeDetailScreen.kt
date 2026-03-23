package com.example.lab3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab3.model.Recipe

// Recipe Detail showing full information, if nothing is selected prompts user to make selection
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipe: Recipe?,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    showBackButton: Boolean = false,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = recipe?.title ?: "Recipe Detail") },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (recipe == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Select a recipe to see details",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyLarge
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                Text(
                    text = recipe.details,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}