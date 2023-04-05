package uk.co.sw.virtuoso.feature.search

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import uk.co.sw.virtuoso.feature.core.CoroutineDispatchers

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatchers(
    private val testDispatcher: TestDispatcher
) : CoroutineDispatchers {
    override fun main() = testDispatcher
    override fun io() = testDispatcher
}