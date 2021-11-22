package com.segunfrancis.randm.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfrancis.randm.data.remote.IRemoteClient
import com.segunfrancis.randm.ui.home.HomeAction
import com.segunfrancis.randm.util.Result
import com.segunfrancis.randm.util.SingleLiveEvent
import com.segunfrancis.randm.util.mapToCharacterDetail
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel(id: String, private val client: IRemoteClient) : ViewModel() {

    private val _uiState = MutableLiveData<Result<CharacterDetail?>>()
    val uiState: LiveData<Result<CharacterDetail?>> = _uiState

    private val _interaction = SingleLiveEvent<HomeAction>()
    val interaction: LiveData<HomeAction> = _interaction

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.postValue(Result.Error(throwable.localizedMessage))
        Timber.e(throwable)
    }

    init {
        getCharacterDetail(id)
    }

    private fun getCharacterDetail(id: String) = viewModelScope.launch(exceptionHandler) {
        _uiState.postValue(Result.Loading)
        val response = client.getCharacterDetail(id)
        if (response.hasErrors().not()) {
            val data = response.data?.mapToCharacterDetail()
            _uiState.postValue(Result.Success(data))
        } else {
            _uiState.postValue(Result.Error(response.errors?.get(0)?.message))
        }
    }

    fun toEpisodeDetail(id: String?) {
        _interaction.postValue(
            HomeAction.Navigate(
                DetailFragmentDirections.toEpisodeDetailFragment(
                    id
                )
            )
        )
    }
}
