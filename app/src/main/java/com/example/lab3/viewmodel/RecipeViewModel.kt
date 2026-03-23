package com.example.lab3.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lab3.RecipeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


// ViewModel keeps changes between rotating screens, StateFlow holds UI state and notifies of changes
class RecipeViewModel : ViewModel() {
    // Recipe list
    val recipes: List<com.example.lab3.Recipe> = com.example.lab3.sampleRecipes
    // Private mutable state that only ViewModel may change
    private val _uiState = MutableStateFlow(RecipeUiState())
    // Public state exposed to UI
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    // Updates the state with selected recipe
    fun selectRecipe(recipe: com.example.lab3.Recipe) {
        _uiState.update { currentState ->
            currentState.copy(selectedRecipe = recipe)
        }
    }

    // When navigating back, resets selected recipe
    fun clearSelection() {
        _uiState.update { currentState ->
            currentState.copy(selectedRecipe = null)
        }
    }
    // Gets recipe by ID for phone navigation, look up object using ID
    fun getRecipeById(id: Int): com.example.lab3.Recipe? {
        return recipes.find { it.id == id }
    }
}
