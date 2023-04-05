package uk.co.sw.virtuoso.domain.search

import javax.inject.Inject

class SearchArtistUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend operator fun invoke(artistName: String) {
        searchRepository.searchArtist(artistName)
    }

}