package uk.co.sw.virtuoso.data.core.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiHeaderInterceptor @Inject constructor(): Interceptor {

    private companion object {
        const val ACCEPT_HEADER_KEY = "Accept"
        const val ACCEPT_HEADER_VALUE = "application/json"

        const val USER_AGENT_HEADER_KEY = "User-Agent"
        const val USER_AGENT_HEADER_VALUE = "Virtuoso/1.0.0 ( firstname.lastname@gmail.com )"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(ACCEPT_HEADER_KEY, ACCEPT_HEADER_VALUE)
            .addHeader(USER_AGENT_HEADER_KEY, USER_AGENT_HEADER_VALUE)
            .build()
        return chain.proceed(request)
    }
}