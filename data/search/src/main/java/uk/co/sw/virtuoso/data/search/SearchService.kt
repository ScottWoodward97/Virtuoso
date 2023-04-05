package uk.co.sw.virtuoso.data.search

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uk.co.sw.virtuoso.data.search.models.SearchResultsEntity

interface SearchService {

    @GET("/ws/2/artist")
    suspend fun searchArtists(@Query("query") query: String): Response<SearchResultsEntity>

}