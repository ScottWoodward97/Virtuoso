package uk.co.sw.virtuoso.domain.artist

interface ArtistRepository {

    suspend fun findArtist(id: String)

}