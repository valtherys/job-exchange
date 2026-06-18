package ru.practicum.android.diploma.presentation.filtration.region.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.area.AreaInteractor
import ru.practicum.android.diploma.domain.models.RegionsResult
import ru.practicum.android.diploma.presentation.filtration.region.mapper.toRegionUiList
import ru.practicum.android.diploma.ui.filtration.region.fragment.ChooseRegionFragmentArgs
import ru.practicum.android.diploma.ui.filtration.region.model.RegionUi
import ru.practicum.android.diploma.ui.filtration.region.state.ChooseRegionUiState

class ChooseRegionViewModel(
    private val areaInteractor: AreaInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val countryId: Int = ChooseRegionFragmentArgs.fromSavedStateHandle(savedStateHandle).countryId

    private var allRegions: List<RegionUi> = emptyList()
    private var filterJob: Job? = null

    private val _state = MutableStateFlow(ChooseRegionUiState(isLoading = true))
    val state: StateFlow<ChooseRegionUiState> = _state.asStateFlow()

    init {
        loadRegions()
    }

    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
        scheduleFilter(query)
    }

    fun onClearSearchClicked() {
        onSearchQueryChanged("")
    }

    private fun loadRegions() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false,
                    isEmptySearchResult = false,
                )
            }

            val loadedRegions = withContext(Dispatchers.IO) {
                when (val result = areaInteractor.getRegions(countryId)) {
                    is RegionsResult.Success -> result.regions.toRegionUiList()
                    else -> null
                }
            }

            if (loadedRegions != null) {
                allRegions = loadedRegions
                scheduleFilter(_state.value.searchQuery)
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        isEmptySearchResult = false,
                    )
                }
            }
        }
    }

    private fun scheduleFilter(searchQuery: String) {
        filterJob?.cancel()
        filterJob = viewModelScope.launch {
            val trimmedQuery = searchQuery.trim()
            val filteredRegions = withContext(Dispatchers.Default) {
                filterRegions(trimmedQuery)
            }

            if (_state.value.searchQuery != searchQuery) {
                return@launch
            }

            _state.update {
                it.copy(
                    regions = filteredRegions,
                    isLoading = false,
                    isError = false,
                    isEmptySearchResult = trimmedQuery.isNotEmpty() && filteredRegions.isEmpty(),
                )
            }
        }
    }

    private fun filterRegions(trimmedQuery: String): List<RegionUi> {
        return if (trimmedQuery.isEmpty()) {
            allRegions
        } else {
            allRegions.filter { region ->
                region.name.contains(trimmedQuery, ignoreCase = true)
            }
        }
    }
}
