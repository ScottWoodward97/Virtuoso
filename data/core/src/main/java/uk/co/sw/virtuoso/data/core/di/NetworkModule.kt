package uk.co.sw.virtuoso.data.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.co.sw.virtuoso.data.core.interceptor.ApiHeaderInterceptor

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://www.musicbrainz.org"

    @BaseClient
    @Provides
    fun provideOkHttpClient(
        apiHeaderInterceptor: ApiHeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiHeaderInterceptor)
            .build()
    }

    @BaseRetrofit
    @Provides
    fun provideRetrofit(
        @BaseClient okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

}