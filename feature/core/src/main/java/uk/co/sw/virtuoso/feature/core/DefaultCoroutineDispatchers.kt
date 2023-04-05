package uk.co.sw.virtuoso.feature.core

import kotlinx.coroutines.Dispatchers

class DefaultCoroutineDispatchers: CoroutineDispatchers {
    override fun main() = Dispatchers.Main
    override fun io() = Dispatchers.IO

}