package uk.co.sw.virtuoso.data.artist

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import uk.co.sw.virtuoso.data.artist.models.ArtistEntity

interface ArtistService {

    @GET("/ws/2/artist/{id}?inc=release-groups")
    suspend fun getArtist(@Path("id") id: String): Response<ArtistEntity>

}