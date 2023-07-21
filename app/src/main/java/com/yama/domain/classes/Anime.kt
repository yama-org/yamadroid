package com.yama.domain.classes

data class Anime(
    val id: Int,
    val title: String,
    val description: String,
    val genres: List<String>,
    val episodes: List<Episode>,
    val thumbnail: String
) {

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$title",
            "${title.first()}"
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}


