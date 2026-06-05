package ru.practicum.android.diploma.ui.filtration.workplace.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.filtration.state.PlaceOfWorkFilters
import ru.practicum.android.diploma.presentation.filtration.workplace.state.AreaUi
import ru.practicum.android.diploma.presentation.filtration.workplace.state.PlaceOfWorkUIState
import ru.practicum.android.diploma.presentation.filtration.workplace.viewmodel.PlaceOfWorkViewModel
import ru.practicum.android.diploma.ui.filtration.workplace.screen.PlaceOfWorkScreen
import ru.practicum.android.diploma.ui.theme.AppTheme

class PlaceOfWorkFragment : Fragment() {
    private val viewModel: PlaceOfWorkViewModel by viewModel()
    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                AppTheme {
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    PlaceOfWorkScreen(
                        modifier = Modifier.fillMaxSize(),
                        state = state,
                        onApplyClick = { applyParams(state) },
                        onBackClick = { navigateUp() },
                        onCountryClick = { navigateCountry() },
                        onRegionClick = { navigateRegion(state) },
                        onCountryCrossClick = { resetCountry() },
                        onRegionCrossClick = { resetRegion() }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeCountryResult()
        observeRegionResult()
    }

    private fun observeCountryResult() {
        val savedStateHandle =
            navController.currentBackStackEntry?.savedStateHandle ?: return

        val countryId = savedStateHandle.get<Int>(COUNTRY_ID_KEY)
        val countryName = savedStateHandle.get<String>(COUNTRY_NAME_KEY).orEmpty()

        if (countryId != ID_IS_ABSENT && countryId != null) {
            viewModel.onCountrySelected(AreaUi(id = countryId, name = countryName))
            clearCountryResult(savedStateHandle)
        }
    }

    private fun observeRegionResult() {
        val savedStateHandle =
            navController.currentBackStackEntry?.savedStateHandle ?: return
        val regionId = savedStateHandle.get<Int>(REGION_ID_KEY)
        val regionName = savedStateHandle.get<String>(REGION_NAME_KEY).orEmpty()

        if (regionId != ID_IS_ABSENT && regionId != null) {
            viewModel.onRegionSelected(AreaUi(id = regionId, name = regionName))
            clearRegionResult(savedStateHandle)
        }
    }

    private fun clearCountryResult(handle: SavedStateHandle) {
        handle.remove<Int>(COUNTRY_ID_KEY)
        handle.remove<String>(COUNTRY_NAME_KEY)
        handle.remove<Int>(REGION_ID_KEY)
        handle.remove<String>(REGION_NAME_KEY)
    }

    private fun clearRegionResult(handle: SavedStateHandle) {
        handle.remove<Int>(REGION_ID_KEY)
        handle.remove<String>(REGION_NAME_KEY)
    }

    private fun resetCountry() {
        viewModel.onDeleteCounty()
    }

    private fun resetRegion() {
        viewModel.onDeleteRegion()
    }

    private fun navigateUp() {
        navController.navigateUp()
    }

    private fun applyParams(state: PlaceOfWorkUIState) {
        setFiltrationFragmentParams(state)
        navigateUp()
    }

    private fun navigateCountry() {
        val action = PlaceOfWorkFragmentDirections.actionPlaceOfWorkFragmentToChooseCountryFragment()
        navController.navigate(action)
    }

    private fun navigateRegion(state: PlaceOfWorkUIState) {
        val countryId = state.country?.id
        val action = PlaceOfWorkFragmentDirections.actionPlaceOfWorkFragmentToChooseRegionFragment(countryId ?: -1)
        navController.navigate(action)
    }

    private fun setFiltrationFragmentParams(state: PlaceOfWorkUIState) {
        val filters = PlaceOfWorkFilters(
            country = state.country,
            region = state.region
        )
        navController
            .previousBackStackEntry
            ?.savedStateHandle
            ?.set(FILTERS_KEY, filters) ?: return
    }

    companion object {
        const val FILTERS_KEY = "placeOfWorkFilters"
        const val ID_IS_ABSENT = -1
        const val COUNTRY_ID_KEY = "countryId"
        const val COUNTRY_NAME_KEY = "countryName"
        const val REGION_ID_KEY = "regionId"
        const val REGION_NAME_KEY = "regionName"
    }
}
