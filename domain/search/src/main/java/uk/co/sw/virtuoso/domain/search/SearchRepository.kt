package uk.co.sw.virtuoso.domain.search

interface SearchRepository {

    suspend fun searchArtist(artistName: String)

}