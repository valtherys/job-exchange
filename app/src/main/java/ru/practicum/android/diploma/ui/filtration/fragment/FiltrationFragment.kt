package ru.practicum.android.diploma.ui.filtration.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filtration.state.PlaceOfWorkFilters
import ru.practicum.android.diploma.presentation.filtration.viewmodel.FiltrationViewModel
import ru.practicum.android.diploma.ui.filtration.screen.FiltrationScreen
import ru.practicum.android.diploma.ui.theme.AppTheme

class FiltrationFragment : Fragment() {
    private val viewModel: FiltrationViewModel by viewModel()
    private val navController: NavController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadFilters()
    }

    override fun onResume() {
        super.onResume()
        observeAreaResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    FiltrationScreen(
                        industryName = state.industry?.name,
                        salary = state.salary?.toString().orEmpty(),
                        dontShowWithoutSalaryChecked = state.onlyWithSalary,
                        placeOfWork = state.area,
                        showButtons = state.showButtons,
                        onCheckedChange = { viewModel.onOnlyWithSalaryChanged(it) },
                        onSearchTextChange = { viewModel.onSalaryChanged(it) },
                        onClear = { viewModel.onSalaryCleared() },
                        onApplyClick = {
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.saveFilters()
                                findNavController().popBackStack()
                            }
                        },
                        onCancelClick = { viewModel.resetFilters() },
                        onNavClick = { findNavController().popBackStack() },
                        onIndustryClick = {
                            findNavController().navigate(
                                R.id.action_filtrationFragment_to_industryFragment,
                            )
                        },
                        onAreaClick = {
                            findNavController().navigate(
                                R.id.action_filtrationFragment_to_placeOfWorkFragment,
                            )
                        },
                        onIndustryClear = { viewModel.clearIndustry() },
                        onAreaClear = { viewModel.onClearArea() },
                    )
                }
            }
        }
    }

    private fun observeAreaResult() {
        val savedStateHandle =
            navController.currentBackStackEntry?.savedStateHandle ?: return
        val placeOfWorkFilters = savedStateHandle.get<PlaceOfWorkFilters>(FILTERS_KEY)

        if (placeOfWorkFilters != null) {
            viewModel.onAreaChanged(placeOfWorkFilters)
        }
    }

    companion object {
        const val FILTERS_KEY = "placeOfWorkFilters"
    }
}
