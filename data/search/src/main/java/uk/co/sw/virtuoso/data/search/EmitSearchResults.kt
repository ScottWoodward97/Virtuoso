package uk.co.sw.virtuoso.data.search

import uk.co.sw.virtuoso.data.search.models.SearchResultsEntity

interface EmitSearchResults {

    suspend fun emitSearchResults(entity: SearchResultsEntity?)

}