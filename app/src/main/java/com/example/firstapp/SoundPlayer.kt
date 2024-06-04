package com.example.firstapp

import android.content.Context
import android.media.MediaPlayer

/**
 * A helper class to handle playing sound effects.
 * @param context the context from which this class is instantiated
 */
class SoundPlayer(private val context: Context) {

    // Map of animal names to their corresponding sound resource IDs
    private val soundMap = mapOf(
        "Bear" to R.raw.bear,
        "Bird" to R.raw.bird,
        "Cat" to R.raw.cat,
        "Cow" to R.raw.cow,
        "Dog" to R.raw.dog,
        "Fox" to R.raw.fox,
        "Horse" to R.raw.horse,
        "Pig" to R.raw.pig,
        "Sheep" to R.raw.sheep,
        "Tiger" to R.raw.tiger
    )

    // Media players for specific sounds
    //private val wrongMatchSound: MediaPlayer = MediaPlayer.create(context, R.raw.ohno)
    //private val youWinSound: MediaPlayer = MediaPlayer.create(context, R.raw.youwon)

    /**
     * Play the sound corresponding to the given animal name.
     * @param animal the name of the animal
     */
    fun playAnimalSound(animal: String) {
        val soundResId = soundMap[animal] ?: return
        MediaPlayer.create(context, soundResId).start()
    }

    /*
    fun playWrongMatchSound() {
        wrongMatchSound.start()
    }

    fun playYouWinSound() {
        youWinSound.start()
    }*/
}