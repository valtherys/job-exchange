package ru.practicum.android.diploma.ui.filtration.region.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.common.Loader
import ru.practicum.android.diploma.ui.common.PlaceholderLayout
import ru.practicum.android.diploma.ui.filtration.region.action.ChooseRegionAction
import ru.practicum.android.diploma.ui.filtration.region.model.RegionUi
import ru.practicum.android.diploma.ui.filtration.region.screen.components.ChooseRegionTopBar
import ru.practicum.android.diploma.ui.filtration.region.screen.components.RegionList
import ru.practicum.android.diploma.ui.filtration.region.screen.components.RegionSearchField
import ru.practicum.android.diploma.ui.filtration.region.state.ChooseRegionUiState
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun ChooseRegionScreen(
    state: ChooseRegionUiState,
    onAction: (ChooseRegionAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { ChooseRegionTopBar(onAction = onAction) },
        contentWindowInsets = WindowInsets(bottom = 0),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            RegionSearchField(
                searchQuery = state.searchQuery,
                onAction = onAction,
            )
            Box(modifier = Modifier.weight(1f)) {
                ChooseRegionContent(
                    state = state,
                    onAction = onAction,
                )
            }
        }
    }
}

@Composable
private fun ChooseRegionContent(
    state: ChooseRegionUiState,
    onAction: (ChooseRegionAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        state.isLoading -> Loader(modifier = modifier.fillMaxSize())

        state.isError -> PlaceholderLayout(
            imageRes = R.drawable.img_region_list_error,
            textRes = R.string.choose_region_error,
        )

        state.isEmptySearchResult -> PlaceholderLayout(
            imageRes = R.drawable.img_nothing_found,
            textRes = R.string.choose_region_not_found,
        )

        else -> RegionList(
            regions = state.regions,
            onAction = onAction,
            modifier = modifier.fillMaxSize(),
        )
    }
}

private val previewRegions = listOf(
    RegionUi(id = 1, name = "Москва", parentId = 11, parentName = "Россия"),
    RegionUi(id = 2, name = "Апрелевка", parentId = 11, parentName = "Россия"),
    RegionUi(id = 3, name = "Балашиха", parentId = 11, parentName = "Россия"),
    RegionUi(id = 4, name = "Бронницы", parentId = 11, parentName = "Россия"),
    RegionUi(id = 5, name = "Верея", parentId = 11, parentName = "Россия"),
    RegionUi(id = 6, name = "Видное", parentId = 11, parentName = "Россия"),
    RegionUi(id = 7, name = "Волоколамск", parentId = 11, parentName = "Россия"),
    RegionUi(id = 8, name = "Воскресенск", parentId = 11, parentName = "Россия"),
    RegionUi(id = 9, name = "Высоковск", parentId = 11, parentName = "Россия"),
    RegionUi(id = 10, name = "Голицыно", parentId = 11, parentName = "Россия"),
)

// Figma HH--Android- node 67-4474: список регионов
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun ChooseRegionScreenPreview() {
    AppTheme {
        ChooseRegionScreen(
            state = ChooseRegionUiState(regions = previewRegions),
            onAction = {},
        )
    }
}

// Figma HH--Android- node 67-4482: «Такого региона нет»
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun ChooseRegionEmptySearchPreview() {
    AppTheme {
        ChooseRegionScreen(
            state = ChooseRegionUiState(
                searchQuery = "молодостьирадость",
                isEmptySearchResult = true,
            ),
            onAction = {},
        )
    }
}

// Figma HH--Android- node 67-4490: «Не удалось получить список»
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun ChooseRegionErrorPreview() {
    AppTheme {
        ChooseRegionScreen(
            state = ChooseRegionUiState(
                searchQuery = "Омск",
                isError = true,
            ),
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChooseRegionLoadingPreview() {
    AppTheme {
        ChooseRegionScreen(
            state = ChooseRegionUiState(isLoading = true),
            onAction = {},
        )
    }
}
