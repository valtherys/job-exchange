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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.filtration.state.PlaceOfWorkFilters
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
                        onRegionClick = { navigateRegion(state) }
                    )
                }
            }
        }
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

    private companion object {
        const val FILTERS_KEY = "place_of_work_filters"
    }
}
