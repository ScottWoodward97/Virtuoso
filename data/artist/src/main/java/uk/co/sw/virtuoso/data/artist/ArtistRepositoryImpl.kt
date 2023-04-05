package uk.co.sw.virtuoso.data.artist

import uk.co.sw.virtuoso.domain.artist.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val artistService: ArtistService,
    private val emitArtist: EmitArtist,
) : ArtistRepository {

    override suspend fun findArtist(id: String) {
        try {
            artistService.getArtist(id).let {
                val entity = it.body()
                if (it.isSuccessful) {
                    emitArtist.emitArtist(entity)
                } else {
                    emitArtist.emitArtist(null)
                }
            }
        } catch (e: Exception) {
            emitArtist.emitArtist(null)
        }
    }

}