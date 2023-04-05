package uk.co.sw.virtuoso.data.search.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import uk.co.sw.virtuoso.data.search.EmitSearchResults
import uk.co.sw.virtuoso.domain.search.SearchRepository
import uk.co.sw.virtuoso.data.search.SearchRepositoryImpl
import uk.co.sw.virtuoso.data.search.SearchResultsFlowImpl
import uk.co.sw.virtuoso.data.search.SearchService
import uk.co.sw.virtuoso.domain.search.SearchResultsFlow

@Module(includes = [SearchModule.BindModule::class])
@InstallIn(ViewModelComponent::class)
object SearchModule {

    @Provides
    fun provideSearchService(@uk.co.sw.virtuoso.data.core.di.BaseRetrofit retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class BindModule {
        @Binds
        abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

        @Binds
        abstract fun bindSearchResultsFlow(impl: SearchResultsFlowImpl): SearchResultsFlow

        @Binds
        abstract fun bindEmitSearchResults(impl: SearchResultsFlowImpl): EmitSearchResults
    }

}