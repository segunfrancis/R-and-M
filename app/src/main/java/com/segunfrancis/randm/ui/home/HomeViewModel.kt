package com.segunfrancis.randm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.segunfrancis.randm.data.remote.RemoteClient
import com.segunfrancis.randm.util.Result
import com.segunfrancis.randm.util.SingleLiveEvent
import com.segunfrancis.randm.util.mapToCharacter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(private val client: RemoteClient) : ViewModel() {

    private val _uiState = MutableLiveData<Result<List<Character?>>>()
    val uiState: LiveData<Result<List<Character?>>> = _uiState

    private val _interaction = SingleLiveEvent<HomeAction>()
    val interaction: LiveData<HomeAction> = _interaction

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.postValue(Result.Error(throwable.localizedMessage))
        Timber.e(throwable)
    }

    init {
        getCharacterList()
    }

    private fun getCharacterList() = viewModelScope.launch(exceptionHandler) {
        _uiState.postValue(Result.Loading)
        val response = client.getCharacterList()
        if (response.hasErrors().not()) {
            val data = response.data?.characters?.results
            if (data != null) {
                _uiState.postValue(Result.Success(data.map {
                    it?.mapToCharacter()
                }))
            }
        } else {
            _uiState.postValue(Result.Error(response.errors?.get(0)?.message))
        }
    }

    fun toDetail(id: String?) {
        _interaction.postValue(
            HomeAction.Navigate(
                HomeFragmentDirections.toDetailFragment(id)
            )
        )
    }
}

sealed class HomeAction {
    data class Navigate(val destination: NavDirections) : HomeAction()
}
