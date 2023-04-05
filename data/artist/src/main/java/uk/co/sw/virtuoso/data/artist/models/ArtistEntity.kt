package uk.co.sw.virtuoso.data.artist.models

import com.squareup.moshi.Json

data class ArtistEntity(
    val id: String,
    val name: String,
    val type: String?,
    @field:Json(name = "life-span")
    val lifeSpan: LifeSpanEntity,
    @field:Json(name = "release-groups")
    val releaseGroups: List<ReleaseGroupEntity>,
) {

    data class LifeSpanEntity(
        val ended: Boolean,
        val begin: String?,
        val end: String?
    )

    data class ReleaseGroupEntity(
        val id: String,
        val title: String,
        @field:Json(name = "primary-type")
        val primaryType: String,
        @field:Json(name = "secondary-types")
        val secondaryTypes: List<String>,
        @field:Json(name = "first-release-date")
        val firstReleaseDate: String,
        val disambiguation: String,
    )

}