package com.example.lab3.ui.theme

import com.example.lab3.model.Recipe

// Holds current UI state, selected recipe is null until selected/clicked
data class RecipeUiState(
    val selectedRecipe: Recipe? = null
)