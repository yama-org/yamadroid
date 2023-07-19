package com.yama.domain

data class Media(
    val id: Int,
    val title: String,
    val description: String,
    val genres: List<String>,
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


