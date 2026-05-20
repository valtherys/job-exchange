package ru.practicum.android.diploma.ui.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.get
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.impl.SearchInteractor
import ru.practicum.android.diploma.presentation.search.viewmodel.JobSearchViewModel
import ru.practicum.android.diploma.ui.search.screen.JobSearchScreen
import ru.practicum.android.diploma.ui.theme.AppTheme

class JobSearchFragment : Fragment() {

    private val viewModel: JobSearchViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return JobSearchViewModel(get<SearchInteractor>()) as T
            }
        }
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
                    val searchQuery by viewModel.searchQuery.collectAsState()
                    val state by viewModel.state.collectAsState()

                    JobSearchScreen(
                        searchQuery = searchQuery,
                        state = state,
                        onSearchQueryChange = viewModel::onSearchQueryChanged,
                        onClearQuery = viewModel::clearSearch,
                        onFiltersClick = ::openFilters,
                        onVacancyClick = ::openVacancy,
                    )
                }
            }
        }
    }

    private fun openFilters() {
        findNavController().navigate(R.id.action_jobSearchFragment_to_filtrationFragment)
    }

    private fun openVacancy(vacancyId: String) {
        findNavController().navigate(
            R.id.action_jobSearchFragment_to_vacancyFragment,
            bundleOf(ARG_VACANCY_ID to vacancyId),
        )
    }

    private companion object {
        const val ARG_VACANCY_ID = "vacancyId"
    }
}
