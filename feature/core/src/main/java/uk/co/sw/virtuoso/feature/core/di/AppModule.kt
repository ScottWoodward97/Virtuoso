package uk.co.sw.virtuoso.feature.core.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideResources(@ApplicationContext context: Context): Resources = context.resources

}