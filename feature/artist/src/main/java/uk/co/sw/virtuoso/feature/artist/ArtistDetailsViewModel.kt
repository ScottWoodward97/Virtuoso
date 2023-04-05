package uk.co.sw.virtuoso.feature.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.co.sw.virtuoso.domain.artist.FindArtistUseCase
import uk.co.sw.virtuoso.domain.artist.GetArtistFlowUseCase
import uk.co.sw.virtuoso.feature.artist.viewstate.ArtistDetailsViewState
import uk.co.sw.virtuoso.feature.artist.viewstate.ArtistDetailsViewStateMapper
import uk.co.sw.virtuoso.feature.core.CoroutineDispatchers
import javax.inject.Inject

@HiltViewModel
class ArtistDetailsViewModel @Inject constructor(
    private val findArtistUseCase: FindArtistUseCase,
    private val getArtistFlowUseCase: GetArtistFlowUseCase,
    private val artistDetailsViewStateMapper: ArtistDetailsViewStateMapper,
    private val coroutineDispatchers: CoroutineDispatchers,
) : ViewModel() {

    private val _artistDetailsViewState: MutableStateFlow<ArtistDetailsViewState> =
        MutableStateFlow(ArtistDetailsViewState.Loading)
    val artistDetailsViewState: StateFlow<ArtistDetailsViewState> = _artistDetailsViewState

    init {
        viewModelScope.launch(coroutineDispatchers.io()) {
            getArtistFlowUseCase().collect {
                _artistDetailsViewState.emit(
                    artistDetailsViewStateMapper.map(it)
                )
            }
        }
    }

    fun findArtist(id: String) {
        viewModelScope.launch(coroutineDispatchers.io()) {
            _artistDetailsViewState.emit(ArtistDetailsViewState.Loading)
            findArtistUseCase(id)
        }
    }

}