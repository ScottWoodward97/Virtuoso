package uk.co.sw.virtuoso.data.artist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import uk.co.sw.virtuoso.data.artist.models.ArtistEntity
import uk.co.sw.virtuoso.data.artist.models.ArtistModelMapper
import uk.co.sw.virtuoso.domain.artist.ArtistFlow
import uk.co.sw.virtuoso.domain.artist.models.ArtistModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistFlowImpl @Inject constructor(
    private val artistModelMapper: ArtistModelMapper,
) : ArtistFlow, EmitArtist {

    private val _artistFlow = MutableSharedFlow<ArtistEntity?>()
    override fun getArtistFlow(): Flow<ArtistModel?> =
        _artistFlow.map { entity -> entity?.let { artistModelMapper.map(it) } }

    override suspend fun emitArtist(entity: ArtistEntity?) {
        _artistFlow.emit(entity)
    }

}