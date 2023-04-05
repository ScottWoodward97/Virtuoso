package uk.co.sw.virtuoso.feature.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uk.co.sw.virtuoso.feature.core.CoroutineDispatchers
import uk.co.sw.virtuoso.feature.core.DefaultCoroutineDispatchers

@Module
@InstallIn(ViewModelComponent::class)
object ThreadModule {

    @Provides
    fun provideCoroutineDispatchers(): CoroutineDispatchers = DefaultCoroutineDispatchers()

}