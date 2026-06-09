package ru.practicum.android.diploma.ui.vacancy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyAction
import ru.practicum.android.diploma.presentation.vacancy.viewmodel.VacancyViewModel
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.vacancy.screen.VacancyScreen

class VacancyFragment : Fragment() {
    private val viewModel: VacancyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent { VacancyContent() }
        }
    }

    @Composable
    private fun VacancyContent() {
        AppTheme {
            val state by viewModel.state.collectAsStateWithLifecycle()
            VacancyScreen(
                state = state,
                onLoadVacancy = viewModel::loadVacancy,
                onBackClick = { findNavController().navigateUp() },
                onShareClick = { url -> onShareClick(url) },
                onFavoriteClick = { viewModel.toggleFavoriteClick() },
                onEmailClick = { email -> onEmailClick(email) },
                onPhoneClick = { phone -> onPhoneClick(phone) },
            )
        }
    }

    private fun onShareClick(url: String) {
        viewModel.onAction(
            VacancyAction.ShareVacancy(
                url,
                getString(R.string.share_link),
            ),
        )
    }

    private fun onEmailClick(email: String) {
        viewModel.onAction(
            VacancyAction.EmailClick(
                email,
                getString(R.string.send_email),
            ),
        )
    }

    private fun onPhoneClick(phone: String) {
        viewModel.onAction(
            VacancyAction.PhoneNumberClick(
                phone,
                getString(R.string.make_call),
            ),
        )
    }
}
