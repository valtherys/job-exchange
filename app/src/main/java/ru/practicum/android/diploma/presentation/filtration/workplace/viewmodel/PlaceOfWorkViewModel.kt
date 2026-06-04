package ru.practicum.android.diploma.presentation.filtration.workplace.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.AreasInteractor
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.presentation.filtration.workplace.state.PlaceOfWorkUIState
import ru.practicum.android.diploma.ui.filtration.workplace.fragment.PlaceOfWorkFragmentArgs

class PlaceOfWorkViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val areasInteractor: AreasInteractor
) : ViewModel() {
    private val countryId: Int = PlaceOfWorkFragmentArgs.fromSavedStateHandle(savedStateHandle).countryId
    private val regionId: Int = PlaceOfWorkFragmentArgs.fromSavedStateHandle(savedStateHandle).regionId
    private val _state = MutableStateFlow(PlaceOfWorkUIState())
    val state: StateFlow<PlaceOfWorkUIState> = _state.asStateFlow()

    fun initScreen() {
        viewModelScope.launch {
            var area: FilterArea? = null
            if (countryId != -1) {
                val country = areasInteractor.getAreaById(countryId)
                area = FilterArea(
                    id = countryId,
                    name = country.name,
                    parentId = null,
                    areas = listOf()
                )
            }
            if (regionId != -1) {
                val region = areasInteractor.getAreaById(regionId)
                area = area?.copy(areas = listOf(region))
            }

            _state.value = PlaceOfWorkUIState(area)
        }
    }

    fun onApplySettions
}
