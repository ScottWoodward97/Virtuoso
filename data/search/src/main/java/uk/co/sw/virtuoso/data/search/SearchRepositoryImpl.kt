package uk.co.sw.virtuoso.data.search

import uk.co.sw.virtuoso.domain.search.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
    private val emitSearchResults: EmitSearchResults,
) : SearchRepository {

    private companion object {
        const val SERVICE_NAME_PREFIX = "name:"
    }

    override suspend fun searchArtist(artistName: String) {
        try {
            searchService.searchArtists(SERVICE_NAME_PREFIX + artistName).let {
                val entity = it.body()
                if (it.isSuccessful) {
                    emitSearchResults.emitSearchResults(entity)
                } else {
                    emitSearchResults.emitSearchResults(null)
                }
            }
        } catch (e: Exception) {
            emitSearchResults.emitSearchResults(null)
        }
    }

}