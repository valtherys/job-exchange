package ru.practicum.android.diploma.presentation.filtration.workplace.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.area.AreaInteractor
import ru.practicum.android.diploma.presentation.filtration.workplace.state.AreaUi
import ru.practicum.android.diploma.presentation.filtration.workplace.state.PlaceOfWorkUIState

class PlaceOfWorkViewModel(private val areaInteractor: AreaInteractor) : ViewModel() {
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
        viewModelScope.launch {
            if (countryFlow.value == null) {
                val response = withContext(Dispatchers.IO) {
                    areaInteractor.getParentByRegionId(region.id)
                }
                countryFlow.value = response?.let { AreaUi(it.id, it.name) }
            }

            regionFlow.value = region
        }
    }

    fun onDeleteCounty() {
        countryFlow.value = null
        regionFlow.value = null
    }

    fun onDeleteRegion() {
        regionFlow.value = null
    }
}
