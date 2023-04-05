package uk.co.sw.virtuoso.domain.artist

import kotlinx.coroutines.flow.Flow
import uk.co.sw.virtuoso.domain.artist.models.ArtistModel

interface ArtistFlow {

    fun getArtistFlow(): Flow<ArtistModel?>

}