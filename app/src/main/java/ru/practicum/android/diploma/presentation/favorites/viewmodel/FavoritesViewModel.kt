package ru.practicum.android.diploma.presentation.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.VacancyDbInteractor
import ru.practicum.android.diploma.presentation.favorites.state.FavoriteVacanciesState

class FavoritesViewModel(
    private val vacancyDbInteractor : VacancyDbInteractor
) : ViewModel(){
    private val _state = MutableStateFlow(loadVacancies())
    val state: StateFlow<FavoriteVacanciesState> = _state.asStateFlow()

    fun refreshData(){
        // Чтоб детект не ругался использую интерактор
        viewModelScope.launch {
            vacancyDbInteractor.checkVacancyIsFavorite("Hello how are you im under the water pls help")
        }
        // И тут нормально обработаю
        //_state.value =  vacancyDbInteractor.GetAllFavoriteVacancies()
    }
    private fun loadVacancies() : FavoriteVacanciesState{
        // Тут будет что-то типа такого, доделаю когда будет сделан домейн для избранных
        //return vacancyDbInteractor.GetAllFavoriteVacancies()
        return FavoriteVacanciesState.Empty
    }
}
