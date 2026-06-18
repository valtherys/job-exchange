package ru.practicum.android.diploma.presentation.filtration.country.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.area.AreaInteractor
import ru.practicum.android.diploma.domain.models.CountriesResult
import ru.practicum.android.diploma.presentation.filtration.country.mapper.toCountryUIList
import ru.practicum.android.diploma.presentation.filtration.country.state.ChooseCountryUIState

class ChooseCountryViewModel(
    private val areaInteractor: AreaInteractor
) : ViewModel() {
    private val _state = MutableStateFlow<ChooseCountryUIState>(ChooseCountryUIState.Loading)
    val state: StateFlow<ChooseCountryUIState> = _state.asStateFlow()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            when (val result = areaInteractor.getCountries()) {
                is CountriesResult.Success -> {
                    _state.value = ChooseCountryUIState.Content(result.countries.toCountryUIList())
                }

                CountriesResult.Empty,
                CountriesResult.Error,
                CountriesResult.NoInternet,
                CountriesResult.ServerError -> {
                    _state.value = ChooseCountryUIState.Error
                }
            }
        }
    }
}
