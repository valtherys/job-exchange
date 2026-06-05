package ru.practicum.android.diploma.ui.filtration.country.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.presentation.filtration.country.state.ChooseCountryUIState
import ru.practicum.android.diploma.presentation.filtration.workplace.viewmodel.PlaceOfWorkViewModel.Companion.COUNTRY_ID_KEY
import ru.practicum.android.diploma.presentation.filtration.workplace.viewmodel.PlaceOfWorkViewModel.Companion.COUNTRY_NAME_KEY
import ru.practicum.android.diploma.ui.filtration.country.model.CountryUI
import ru.practicum.android.diploma.ui.filtration.country.screen.ChooseCountryScreen
import ru.practicum.android.diploma.ui.theme.AppTheme

class ChooseCountryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            val state: ChooseCountryUIState = ChooseCountryUIState.Loading
            setContent {
                AppTheme {
                    ChooseCountryScreen(
                        state = state,
                        onItemClick = { country -> onCountryClick(country) },
                        onBackClick = { findNavController().navigateUp() }
                    )
                }
            }
        }
    }

    private fun onCountryClick(country: CountryUI) {
        setCountryResult(country)
        findNavController().navigateUp()
    }

    private fun setCountryResult(country: CountryUI) {
        findNavController()
            .previousBackStackEntry
            ?.savedStateHandle
            ?.set(COUNTRY_ID_KEY, country.id)

        findNavController()
            .previousBackStackEntry
            ?.savedStateHandle
            ?.set(COUNTRY_NAME_KEY, country.name)
    }
}
