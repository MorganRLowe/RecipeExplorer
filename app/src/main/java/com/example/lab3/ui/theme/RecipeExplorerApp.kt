package com.example.lab3

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab3.ui.theme.RecipeDetailScreen
import com.example.lab3.ui.theme.RecipeListScreen
import com.example.lab3.viewmodel.RecipeScreen
import com.example.lab3.viewmodel.RecipeViewModel

// Determines the layout to show after receiving the window size class
@Composable
fun RecipeExplorerApp(windowSizeClass: WindowSizeClass) {
    val viewModel: RecipeViewModel = viewModel()

    // Compact refers to phones, medium and expanded for tablets and large screens
    val isTablet = windowSizeClass.widthSizeClass != WindowWidthSizeClass.COMPACT

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Shows split-screen on tablet and single navigation screen on phones
        if (isTablet) {
            TabletLayout(viewModel = viewModel)
        } else {
            PhoneLayout(viewModel = viewModel)
        }
    }
}

// Phone layout goes between list and detail as separate screens
@Composable
fun PhoneLayout(viewModel: RecipeViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RecipeScreen.List.name
    ) {
        // List screen route
        composable(route = RecipeScreen.List.name) {
            RecipeListScreen(
                recipes = viewModel.recipes,
                onRecipeClick = { recipe ->
                    viewModel.selectRecipe(recipe)
                    // Navigates to recipe detail using ID as a nav argument
                    navController.navigate("${RecipeScreen.Detail.name}/${recipe.id}")
                }
            )
        }
        // Detail screen route
        composable(
            route = "${RecipeScreen.Detail.name}/{recipeId}",
            arguments = listOf(
                navArgument("recipeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId")
            val recipe = recipeId?.let { viewModel.getRecipeById(it) }

            RecipeDetailScreen(
                recipe = recipe,
                showBackButton = true,
                onBack = {
                    viewModel.clearSelection()
                    navController.navigateUp()
                }
            )
        }
    }
}

// Tablet layout using collectAsState() for updating detail panel
@Composable
fun TabletLayout(viewModel: RecipeViewModel) {
    // collectAsState converts StateFlow to compose state
    val uiState by viewModel.uiState.collectAsState()

    // Places list and detail next to each other horizontally
    Row(modifier = Modifier.fillMaxSize()) {
        RecipeListScreen(
            recipes = viewModel.recipes,
            onRecipeClick = { recipe -> viewModel.selectRecipe(recipe) },
            modifier = Modifier.fillMaxWidth(fraction = 0.4f) // 40 percent of screen width
        )
        // Separator between two panes
        VerticalDivider()
        RecipeDetailScreen(
            // Pass selected recipe, null until clicked
            recipe = uiState.selectedRecipe,
            // No back button for tablet
            showBackButton = false,
            modifier = Modifier.weight(1f)
        )
    }
}