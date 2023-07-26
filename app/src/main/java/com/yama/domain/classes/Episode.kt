package com.yama.domain.classes

data class Episode(
    val title: String,
    val duration: Int,
    val description: String,
    val thumbnail: String,
    var isSelected: Boolean = false,
    var watched: Boolean = false
) {

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            title,
            " $title",
            "$title ",
            "${title.first()}",
            " ${title.first()}",
            "${title.first()} "
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }

}
