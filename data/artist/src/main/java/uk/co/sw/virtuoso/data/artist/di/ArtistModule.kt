package uk.co.sw.virtuoso.data.artist.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import uk.co.sw.virtuoso.data.artist.ArtistFlowImpl
import uk.co.sw.virtuoso.data.artist.ArtistRepositoryImpl
import uk.co.sw.virtuoso.data.artist.ArtistService
import uk.co.sw.virtuoso.data.artist.EmitArtist
import uk.co.sw.virtuoso.data.core.di.BaseRetrofit
import uk.co.sw.virtuoso.domain.artist.ArtistFlow
import uk.co.sw.virtuoso.domain.artist.ArtistRepository

@Module(includes = [ArtistModule.BindsModule::class])
@InstallIn(ViewModelComponent::class)
object ArtistModule {

    @Provides
    fun provideArtistService(@BaseRetrofit retrofit: Retrofit): ArtistService {
        return retrofit.create(ArtistService::class.java)
    }

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class BindsModule {

        @Binds
        abstract fun bindArtistRepository(impl: ArtistRepositoryImpl): ArtistRepository

        @Binds
        abstract fun bindArtistFlow(impl: ArtistFlowImpl): ArtistFlow

        @Binds
        abstract fun bindEmitArtist(impl: ArtistFlowImpl): EmitArtist

    }

}