package ru.practicum.android.diploma.ui.filtration.workplace.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.presentation.filtration.workplace.state.PlaceOfWorkUIState

@Composable
fun PlaceOfWorkScreen(
    modifier: Modifier = Modifier,
    state: PlaceOfWorkUIState,
    onApplyClick: () -> Unit,
    onBackClick: () -> Unit,
    onCountyClick: () -> Unit,
    onRegionClick: () -> Unit
) {
    Box(modifier = modifier)
}
