package com.yama.ui.screen.home.repo

import com.example.yama.R

object RepositorioDeTesteo {

    val titlesTest = listOf(
        TitlesTest("K-on!", R.drawable.test_image, "Anime de música"),
        TitlesTest("Bocchi the Rock!", R.drawable.test_image2, "Anime de música"),
        TitlesTest("K-on!", R.drawable.test_image, "Anime de música"),
        TitlesTest("Bocchi the Rock!", R.drawable.test_image2, "Anime de música"),
        TitlesTest("K-on!", R.drawable.test_image, "Anime de música"),
        TitlesTest("Bocchi the Rock!", R.drawable.test_image2, "Anime de música"),
        TitlesTest("K-on!", R.drawable.test_image, "Anime de música"),
        TitlesTest("Bocchi the Rock!", R.drawable.test_image2, "Anime de música"),
        TitlesTest("K-on!", R.drawable.test_image, "Anime de música")
    )
}

data class TitlesTest(val name: String, val thumbnail: Int, val description: String)