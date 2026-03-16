package com.example.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


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


// Data class for recipe information including ID for navigation
data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val details: String
)

// List of sample recipes including id's for navigation arguments on phone layout
val sampleRecipes = listOf(
    Recipe(
        id = 1,
        title = "Mapo Tofu",
        description = "Spicy tofu in a rich chili sauce with ground pork.",
        details = """
            Ingredients:
            • 1 block firm tofu, cubed
            • 150g ground pork
            • 2 tbsp doubanjiang (chili bean paste)
            • 1 cup chicken broth
            • 1 tsp Sichuan peppercorn
            • 2 garlic cloves, minced
            • 1 tsp cornstarch + 2 tbsp water
            • Green onion to garnish

            Instructions:
            1. Brown ground pork in a hot wok, then set aside.
            2. Fry doubanjiang and garlic in the same wok for 1 min.
            3. Add broth and bring to a simmer.
            4. Gently add tofu and pork, simmer 5 minutes.
            5. Stir in cornstarch mixture to thicken.
            6. Top with Sichuan peppercorn and green onion.
        """.trimIndent()
    ),
    Recipe(
        id = 2,
        title = "Overnight Protein Oats",
        description = "Oats with protein powder ready overnight.",
        details = """
            Ingredients:
            • 1/2 cup rolled oats
            • 1 scoop vanilla protein powder
            • 3/4 cup milk of choice
            • 1/4 cup Greek yogurt
            • 1 tbsp chia seeds
            • Toppings: berries, nut butter, honey

            Instructions:
            1. Mix oats, protein powder, chia seeds in a jar.
            2. Stir in milk and Greek yogurt until combined.
            3. Seal and refrigerate overnight (at least 6 hours).
            4. In the morning, stir and add your toppings.
            5. Eat cold straight from the jar.
        """.trimIndent()
    ),
    Recipe(
        id = 3,
        title = "Peanut Butter and Jelly Sandwich",
        description = "One of the best ever created, simple and easy.",
        details = """
            Ingredients:
            • 2 slices of bread
            • 2 tbsp peanut butter
            • 1 tbsp jelly (grape is best)

            Instructions:
            1. Lay both slices of bread flat.
            2. Spread peanut butter on one slice.
            3. Spread jelly on the other slice.
            4. Press the two slices together.
            5. Cut diagonally for the classic look.
        """.trimIndent()
    )
)

// Holds current UI state, selected recipe is null until selected/clicked
data class RecipeUiState(
    val selectedRecipe: Recipe? = null
)

// ViewModel keeps changes between rotating screens, StateFlow holds UI state and notifies of changes
class RecipeViewModel : ViewModel() {
    // Recipe list
    val recipes: List<Recipe> = sampleRecipes
    // Private mutable state that only ViewModel may change
    private val _uiState = MutableStateFlow(RecipeUiState())
    // Public state exposed to UI
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    // Updates the state with selected recipe
    fun selectRecipe(recipe: Recipe) {
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
fun getRecipeById(id: Int): Recipe? {
    return recipes.find { it.id == id }
}
}

enum class RecipeScreen {
    List,
    Detail
}

// Determines the layout to show after receiving the window size class
@Composable
fun RecipeExplorerApp(windowSizeClass: WindowSizeClass) {
    val viewModel: RecipeViewModel = viewModel()

    // Compact refers to phones, medium and expanded for tablets and large screens
    val isTablet = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact

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

// Phone layout goes between list and detail as seperate screens
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
            // No back buton for tablet
            showBackButton = false,
            modifier = Modifier.weight(1f)
        )
    }
}

// LazyColumn for recipe cards
@Composable
fun RecipeListScreen(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // items() goes over the recipe list and creates a composable for each
        items(recipes) { recipe ->
            RecipeCard(
                recipe = recipe,
                onClick = { onRecipeClick(recipe) }
            )
        }
    }
}

// Recipe card showing information about each recipe, using Material3 card
@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

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










