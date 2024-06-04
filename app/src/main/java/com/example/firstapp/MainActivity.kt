package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlin.random.Random
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var soundPlayer: SoundPlayer // SoundPlayer instance to handle sound effects
    private var matchCount = 0 // Counter to keep track of matched pairs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SoundPlayer
        soundPlayer = SoundPlayer(this)

        // Find view elements by their IDs
        val button1 = findViewById<Button>(R.id.btn1)
        val button2 = findViewById<Button>(R.id.btn2)
        val button3 = findViewById<Button>(R.id.btn3)
        val button4 = findViewById<Button>(R.id.btn4)
        val button5 = findViewById<Button>(R.id.btn5)

        val buttonPlayAgain = findViewById<Button>(R.id.playAgainBtn)

        val image1 = findViewById<ImageView>(R.id.img1)
        val image2 = findViewById<ImageView>(R.id.img2)
        val image3 = findViewById<ImageView>(R.id.img3)
        val image4 = findViewById<ImageView>(R.id.img4)
        val image5 = findViewById<ImageView>(R.id.img5)

        val txtState = findViewById<TextView>(R.id.textState)

        var pickLeft: String? = null // Variable to store the name of the selected button
        var pickRight: String? = null // Variable to store the name of the selected image

        // List of button and image views
        val buttons = listOf(button1, button2, button3, button4, button5)
        val images = listOf(image1, image2, image3, image4, image5)

        // List of all possible animals
        val allAnimals = listOf(
            Animal("Bear", R.drawable.bear),
            Animal("Bird", R.drawable.bird),
            Animal("Cat", R.drawable.cat),
            Animal("Cow", R.drawable.cow),
            Animal("Dog", R.drawable.dog),
            Animal("Fox", R.drawable.fox),
            Animal("Horse", R.drawable.horse),
            Animal("Pig", R.drawable.pig),
            Animal("Sheep", R.drawable.sheep),
            Animal("Tiger", R.drawable.tiger)
        )

        // Shuffle the animals list and select 5 random animals
        val shuffledAnimals = allAnimals.shuffled().take(5)
        val selectedAnimalsLeft = shuffledAnimals.take(5)
        val selectedAnimalsRight = selectedAnimalsLeft.shuffled().take(5)

        buttonPlayAgain.visibility = View.GONE // Hide the play again button initially

        // Assign the selected animals to the buttons
        buttons.forEachIndexed { index, button ->
            button.text = selectedAnimalsLeft[index].name
            button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_purple))
        }

        // Assign the selected animals to the images
        images.forEachIndexed { index, image ->
            image.setImageResource(selectedAnimalsRight[index].imageResId)
            image.contentDescription = selectedAnimalsRight[index].name
        }

        /**
         * Function to check the selected button and image and update the UI accordingly.
         */
        fun checkSelections() {
            if (pickLeft != null && pickRight != null) {
                if (pickLeft == pickRight) {
                    // Find and hide the matched button and image
                    buttons.find { it.text == pickLeft }?.visibility = View.GONE
                    images.find { it.contentDescription == pickRight }?.visibility = View.GONE

                    // Play the specific animal sound
                    soundPlayer.playAnimalSound(pickLeft!!)

                    //Game State
                    txtState.setText("GOOD JOB")
                    matchCount++

                    // Check if the game is won
                    if (matchCount == 5) {
                        txtState.setText("YOU WIN!!")
                        buttonPlayAgain.visibility = View.VISIBLE
                    }
                } else {
                    txtState.setText("TRY, AGAIN...")
                }

                // Reset the selections
                pickLeft = null
                pickRight = null

                //Reset Button Color
                for (button in buttons) {
                    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_purple))
                }

                // Clear color filters for all images
                for (image in images) {
                    image.clearColorFilter()
                }
            }
        }


        /**
         * Function to handle button clicks.
         * @param clickedButton the button that was clicked
         */
        fun handleButtonClick(clickedButton: Button) {
            for (button in buttons) {
                if (button == clickedButton) {
                    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
                    pickLeft = button.text as String?

                } else {
                    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
                }
            }
            checkSelections()
        }


        /**
         * Function to handle image clicks.
         * @param clickedImage the image that was clicked
         */
        fun handleImageClick(clickedImage: ImageView) {
            val grayMatrix = ColorMatrix().apply { setSaturation(0f) }
            val grayFilter = ColorMatrixColorFilter(grayMatrix)

            for (image in images) {
                if (image == clickedImage) {
                    image.clearColorFilter()
                    pickRight = image.contentDescription.toString()

                } else {
                    image.colorFilter = grayFilter
                }
            }
            checkSelections()
        }


        /**
         * Function to reset the game state.
         */
        fun resetGame() {
            // Reset visibility of buttons and images
            buttons.forEach { it.visibility = View.VISIBLE }
            images.forEach { it.visibility = View.VISIBLE }

            // Reset selections
            pickLeft = null
            pickRight = null

            // Reset match count
            matchCount = 0

            // Reset text view
            txtState.text = ""

            // Shuffle the array and select the first 5 animals
            val shuffledAnimals = allAnimals.shuffled().take(5)
            val selectedAnimalsLeft = shuffledAnimals.take(5)
            val selectedAnimalsRight = selectedAnimalsLeft.shuffled().take(5)

            // Assign the selected animals to the buttons
            buttons.forEachIndexed { index, button ->
                button.text = selectedAnimalsLeft[index].name
                button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_purple))
            }

            // Assign the selected animals to the images
            images.forEachIndexed { index, image ->
                image.setImageResource(selectedAnimalsRight[index].imageResId)
                image.contentDescription = selectedAnimalsRight[index].name
            }

            // Hide the Play Again button
            buttonPlayAgain.visibility = View.GONE
        }

        // Set click listeners for buttons and images
        button1.setOnClickListener { handleButtonClick(button1) }
        button2.setOnClickListener { handleButtonClick(button2) }
        button3.setOnClickListener { handleButtonClick(button3) }
        button4.setOnClickListener { handleButtonClick(button4) }
        button5.setOnClickListener { handleButtonClick(button5) }

        image1.setOnClickListener { handleImageClick(image1) }
        image2.setOnClickListener { handleImageClick(image2) }
        image3.setOnClickListener { handleImageClick(image3) }
        image4.setOnClickListener { handleImageClick(image4) }
        image5.setOnClickListener { handleImageClick(image5) }

        buttonPlayAgain.setOnClickListener { resetGame() }
    }
}