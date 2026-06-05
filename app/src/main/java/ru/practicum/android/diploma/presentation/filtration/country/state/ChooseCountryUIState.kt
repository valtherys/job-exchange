package ru.practicum.android.diploma.presentation.filtration.country.state

import ru.practicum.android.diploma.ui.filtration.country.model.CountryUI

sealed interface ChooseCountryUIState {
    data object Loading : ChooseCountryUIState
    data object Error : ChooseCountryUIState
    data class Content(val countries: List<CountryUI>) : ChooseCountryUIState
}
