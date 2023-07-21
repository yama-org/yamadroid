package com.yama.domain.classes

data class Episode(
    val title: String,
    val duration: Int,
    val description: String,
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
