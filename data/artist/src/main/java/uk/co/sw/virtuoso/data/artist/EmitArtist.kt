package uk.co.sw.virtuoso.data.artist

import uk.co.sw.virtuoso.data.artist.models.ArtistEntity

interface EmitArtist {

    suspend fun emitArtist(entity: ArtistEntity?)

}