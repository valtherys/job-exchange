package ru.practicum.android.diploma.presentation.filtration.workplace.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ru.practicum.android.diploma.presentation.filtration.workplace.state.AreaUi
import ru.practicum.android.diploma.presentation.filtration.workplace.state.PlaceOfWorkUIState

class PlaceOfWorkViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val countryIdFlow = savedStateHandle.getStateFlow(COUNTRY_ID_KEY, -1)
    private val countryNameFlow = savedStateHandle.getStateFlow(COUNTRY_NAME_KEY, "")
    private val regionIdFlow = savedStateHandle.getStateFlow(REGION_ID_KEY, -1)
    private val regionNameFlow = savedStateHandle.getStateFlow(REGION_NAME_KEY, "")
    val state = combine(
        countryIdFlow,
        countryNameFlow,
        regionIdFlow,
        regionNameFlow
    ) { countryId, countryName, regionId, regionName ->
        val country = if (countryId != ID_IS_ABSENT) {
            AreaUi(countryId, countryName)
        } else {
            null
        }
        val region = if (regionId != ID_IS_ABSENT) {
            AreaUi(regionId, regionName)
        } else {
            null
        }
        PlaceOfWorkUIState(
            country = country,
            region = region
        )
    }.stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = PlaceOfWorkUIState())

    private companion object {
        const val ID_IS_ABSENT = -1
        const val COUNTRY_ID_KEY = "countryId"
        const val COUNTRY_NAME_KEY = "countryName"
        const val REGION_ID_KEY = "regionId"
        const val REGION_NAME_KEY = "regionName"
    }
}
