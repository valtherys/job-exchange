package ru.practicum.android.diploma.presentation.filtration.workplace.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ru.practicum.android.diploma.presentation.filtration.workplace.state.AreaUi
import ru.practicum.android.diploma.presentation.filtration.workplace.state.PlaceOfWorkUIState

class PlaceOfWorkViewModel : ViewModel() {
    private val countryFlow = MutableStateFlow<AreaUi?>(null)
    private val regionFlow = MutableStateFlow<AreaUi?>(null)

    val state = combine(countryFlow, regionFlow) { country, region ->
        PlaceOfWorkUIState(country = country, region = region)
    }.stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = PlaceOfWorkUIState())

    fun onCountrySelected(country: AreaUi) {
        countryFlow.value = country
        regionFlow.value = null
    }

    fun onRegionSelected(region: AreaUi) {
        regionFlow.value = region
    }

    fun onDeleteCounty() {
        countryFlow.value = null
        regionFlow.value = null
    }

    fun onDeleteRegion() {
        regionFlow.value = null
    }
}
