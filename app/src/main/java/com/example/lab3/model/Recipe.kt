package com.example.lab3.model

// Data class for recipe information including ID for navigation
data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val details: String
)
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