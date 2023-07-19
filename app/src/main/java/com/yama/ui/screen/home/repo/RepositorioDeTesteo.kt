package com.yama.ui.screen.home.repo

import com.example.yama.R
import com.yama.domain.Media

object RepositorioDeTesteo {

    val mediaTest = listOf(
        Media(
            1,
            "K-on!",
            "Anime music",
            listOf("Music", "Girls Love"),
            R.drawable.test_image.toString()
        ),
        Media(
            2,
            "Bocchi the Rock!",
            "Anime music",
            listOf("Music", "Girls Love", "Anxiety"),
            R.drawable.test_image2.toString()
        ),
        Media(
            1,
            "K-on!",
            "Anime music",
            listOf("Music", "Girls Love"),
            R.drawable.test_image.toString()
        ), Media(
            1,
            "K-on!",
            "Anime music",
            listOf("Music", "Girls Love"),
            R.drawable.test_image.toString()
        ),
        Media(
            2,
            "Bocchi the Rock!",
            "Anime music",
            listOf("Music", "Girls Love", "Anxiety"),
            R.drawable.test_image2.toString()
        ),
        Media(
            1,
            "K-on!",
            "Anime music",
            listOf("Music", "Girls Love"),
            R.drawable.test_image.toString()
        ), Media(
            1,
            "K-on!",
            "Anime music",
            listOf("Music", "Girls Love"),
            R.drawable.test_image.toString()
        ),
        Media(
            2,
            "Bocchi the Rock!",
            "Anime music",
            listOf("Music", "Girls Love", "Anxiety"),
            R.drawable.test_image2.toString()
        ),
        Media(
            1,
            "K-on!",
            "Anime music",
            listOf("Music", "Girls Love"),
            R.drawable.test_image.toString()
        )
    )

}



data class TitlesTest(val name: String, val thumbnail: Int, val description: String)