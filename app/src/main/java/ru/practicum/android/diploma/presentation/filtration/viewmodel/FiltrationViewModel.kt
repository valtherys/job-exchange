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
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<FiltrationUIState> = _state.asStateFlow()

    init {
        loadFilters()
    }

    fun loadFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            val filters = filtrationInteractor.getFilter()
            val country = if (filters.countryId != null && filters.countryName != null) {
                AreaUi(filters.countryId, filters.countryName)
            } else {
                null
            }
            val region = if (filters.regionId != null && filters.regionName != null) {
                AreaUi(filters.regionId, filters.regionName)
            } else {
                null
            }

            _state.update {
                FiltrationUIState(
                    salary = filters.salary,
                    onlyWithSalary = filters.hideWithoutSalary,
                    industry = filters.industryId?.let { id ->
                        FilterIndustry(id = id, name = filters.industryName.orEmpty())
                    },
                    area = PlaceOfWorkFilters(country = country, region = region)
                )
            }
        }
    }

    fun onSalaryChanged(text: String) {
        val digits = text.filter { it.isDigit() }
        when {
            digits.isEmpty() -> onSalaryCleared()
            else -> digits.toIntOrNull()?.let { newSalary -> _state.update { it.copy(salary = newSalary) } }
        }
    }

//    fun onSalaryChanged(salary: Int) {
//        _state.update { it.copy(salary = salary) }
//    }

    fun onSalaryCleared() {
        _state.update { it.copy(salary = null) }
    }

    fun onOnlyWithSalaryChanged(checked: Boolean) {
        _state.update { it.copy(onlyWithSalary = checked) }
    }

    fun clearIndustry() {
        _state.update { it.copy(industry = null) }
    }

    fun onIndustryChanged(industry: FilterIndustry) {
        _state.update { it.copy(industry = industry) }
    }

    fun onAreaChanged(area: PlaceOfWorkFilters) {
        _state.update { it.copy(area = PlaceOfWorkFilters(country = area.country, region = area.region)) }
    }

    fun onClearArea() {
        _state.update { it.copy(area = null) }
    }

    fun saveFilters() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                filtrationInteractor.saveFilter(
                    filterParameters = FilterParameters(
                        salary = _state.value.salary,
                        hideWithoutSalary = _state.value.onlyWithSalary,
                        industryId = _state.value.industry?.id,
                        industryName = _state.value.industry?.name,
                        countryId = _state.value.area?.country?.id,
                        countryName = _state.value.area?.country?.name,
                        regionId = _state.value.area?.region?.id,
                        regionName = _state.value.area?.region?.name
                    ),
                )
            }
        }
    }

    fun resetFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            filtrationInteractor.clearFilter()
            _state.value = initialState
        }
    }

    private suspend fun persistFilters(filters: FiltrationUIState) {
        withContext(Dispatchers.IO) {
            filtrationInteractor.saveFilter(
                filterParameters = FilterParameters(
                    salary = filters.salary,
                    hideWithoutSalary = filters.onlyWithSalary,
                    industryId = filters.industry?.id,
                    industryName = filters.industry?.name,
                    countryId = filters.area?.country?.id,
                    countryName = filters.area?.country?.name,
                    regionId = filters.area?.region?.id,
                    regionName = filters.area?.region?.name
                ),
            )
        }
    }
}
