package ru.practicum.android.diploma.presentation.filtration.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.presentation.filtration.state.FiltrationUIState
import ru.practicum.android.diploma.presentation.filtration.state.PlaceOfWorkFilters
import ru.practicum.android.diploma.presentation.filtration.workplace.state.AreaUi

class FiltrationViewModel(private val filtrationInteractor: FiltrationInteractor) : ViewModel() {
    private val initialState = FiltrationUIState(
        salary = null,
        onlyWithSalary = false,
        industry = null,
        area = null
    )
    private var savedFilterParameters: FilterParameters = FilterParameters()
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<FiltrationUIState> = _state.asStateFlow()

    init {
        loadFilters()
    }

    fun loadFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            val filters = filtrationInteractor.getFilter()
            savedFilterParameters = filters
            setState(filters.toUiState())
        }
    }

    fun onSalaryChanged(text: String) {
        val digits = text.filter { it.isDigit() }
        when {
            digits.isEmpty() -> onSalaryCleared()
            else -> digits.toIntOrNull()?.let { newSalary ->
                publishState { it.copy(salary = newSalary) }
            }
        }
    }

//    fun onSalaryChanged(salary: Int) {
//        _state.update { it.copy(salary = salary) }
//    }

    fun onSalaryCleared() {
        publishState { it.copy(salary = null) }
    }

    fun onOnlyWithSalaryChanged(checked: Boolean) {
        publishState { it.copy(onlyWithSalary = checked) }
    }

    fun clearIndustry() {
        publishState { it.copy(industry = null) }
    }

    fun onIndustryChanged(industry: FilterIndustry) {
        publishState { it.copy(industry = industry) }
    }

    fun onAreaChanged(area: PlaceOfWorkFilters) {
        _state.update { it.copy(area = PlaceOfWorkFilters(country = area.country, region = area.region)) }
    }

    fun onClearArea() {
        _state.update { it.copy(area = null) }
    }

    fun saveFilters() {
        viewModelScope.launch {
            val filterParameters = _state.value.toFilterParameters()
            withContext(Dispatchers.IO) {
                filtrationInteractor.saveFilter(filterParameters)
            }
            savedFilterParameters = filterParameters
            publishState { it }
        }
    }

    fun resetFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            filtrationInteractor.clearFilter()
            savedFilterParameters = FilterParameters()
            setState(initialState)
        }
    }

    private fun publishState(transform: (FiltrationUIState) -> FiltrationUIState) {
        _state.update { currentState ->
            val newState = transform(currentState)
            newState.copy(showButtons = shouldShowButtons(newState))
        }
    }

    private fun setState(state: FiltrationUIState) {
        _state.value = state.copy(showButtons = shouldShowButtons(state))
    }

    private fun shouldShowButtons(state: FiltrationUIState): Boolean {
        val hasActiveFilters = state.salary != null || state.onlyWithSalary || state.industry != null
        return hasActiveFilters || state.toFilterParameters() != savedFilterParameters
    }

    private fun FiltrationUIState.toFilterParameters(): FilterParameters = FilterParameters(
        salary = salary,
        hideWithoutSalary = onlyWithSalary,
        industryId = industry?.id,
        industryName = industry?.name,
        countryId = area?.country?.id,
        countryName = area?.country?.name,
        regionId = area?.region?.id,
        regionName = area?.region?.name
    )

    private fun FilterParameters.toUiState(): FiltrationUIState {
        val country = if (countryId != null && countryName != null) {
            AreaUi(countryId, countryName)
        } else {
            null
        }
        val region = if (regionId != null && regionName != null) {
            AreaUi(regionId, regionName)
        } else {
            null
        }
        return FiltrationUIState(
            salary = salary,
            onlyWithSalary = hideWithoutSalary,
            industry = industryId?.let { id ->
                FilterIndustry(id = id, name = industryName.orEmpty())
            },
            area = PlaceOfWorkFilters(country, region)
        )
    }
}
