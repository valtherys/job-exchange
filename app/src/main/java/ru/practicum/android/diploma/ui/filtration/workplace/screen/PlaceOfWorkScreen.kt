package ru.practicum.android.diploma.ui.filtration.workplace.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filtration.workplace.state.PlaceOfWorkUIState
import ru.practicum.android.diploma.ui.common.FilterItem
import ru.practicum.android.diploma.ui.common.PrimaryButton
import ru.practicum.android.diploma.ui.common.TopBar

@Composable
fun PlaceOfWorkScreen(
    modifier: Modifier = Modifier,
    state: PlaceOfWorkUIState,
    onCountryClick: () -> Unit = {},
    onRegionClick: () -> Unit = {},
    onCountryCrossClick: () -> Unit = {},
    onRegionCrossClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onApplyClick: () -> Unit = {},
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopBar(
            text = stringResource(R.string.place_of_work),
            navIconVisible = true,
            onNavClick = onBackClick,
            endFirstIconVisible = false,
            endSecondIconVisible = false
        )
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            FilterItem(
                title = stringResource(R.string.place_of_work_country),
                value = state.country?.name,
                onItemClick = onCountryClick,
                onCrossClick = onCountryCrossClick,
            )
            FilterItem(
                title = stringResource(R.string.place_of_work_region),
                value = state.region?.name,
                onItemClick = onRegionClick,
                onCrossClick = onRegionCrossClick,
            )
            Spacer(modifier = Modifier.weight(1F))
            if (state.buttonIsShowed) {
                PrimaryButton(
                    modifier = Modifier.padding(bottom = 24.dp),
                    text = stringResource(R.string.place_of_work_apply),
                    onClick = onApplyClick,
                )
            }
        }
    }
}
