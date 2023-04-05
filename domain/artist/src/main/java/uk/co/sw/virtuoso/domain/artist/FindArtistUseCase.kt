package uk.co.sw.virtuoso.domain.artist

import javax.inject.Inject

class FindArtistUseCase @Inject constructor(
    private val artistRepository: ArtistRepository,
) {
    suspend operator fun invoke(id: String) {
        artistRepository.findArtist(id)
    }
}