package uk.co.sw.virtuoso.domain.artist

import javax.inject.Inject

class GetArtistFlowUseCase @Inject constructor(
    private val artistFlow: ArtistFlow,
) {
    operator fun invoke() = artistFlow.getArtistFlow()
}