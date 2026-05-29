package ru.practicum.android.diploma.ui.favorites.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.favorites.state.FavoriteVacanciesState
import ru.practicum.android.diploma.ui.common.PlaceholderLayout
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.common.search.VacancyItem
import ru.practicum.android.diploma.ui.mocks.MocData
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun FavoritesScreen(
    state: FavoriteVacanciesState,
    onVacancyClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                text = stringResource(R.string.favorite_vacancies_title),
                navIconVisible = false,
                endFirstIconVisible = false,
                endSecondIconVisible = false
            )
        }, contentWindowInsets = WindowInsets(bottom = 0)
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                when (state) {
                    is FavoriteVacanciesState.Content -> {
                        FavoriteVacanciesList(
                            vacancies = state.vacancies,
                            onClick = onVacancyClick
                        )
                    }

                    is FavoriteVacanciesState.Error -> {
                        PlaceholderLayout(
                            R.drawable.img_nothing_found,
                            R.string.no_vacancies_error
                        )
                    }

                    is FavoriteVacanciesState.Empty -> {
                        PlaceholderLayout(
                            R.drawable.img_no_favorite_vacancies,
                            R.string.no_favorite_vacancies
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteVacanciesList(
    vacancies: List<Vacancy>,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = Dimens.ScreenHorizontalPadding,
                top = 8.dp,
                end = Dimens.ScreenHorizontalPadding,
            )
    ) {
        items(
            items = vacancies,
            key = { it.id }
        ) { vacancy ->
            VacancyItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                vacancy,
                onClick = onClick
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FavoriteVacanciesContentPreview() {
    AppTheme {
        FavoritesScreen(
            state = FavoriteVacanciesState.Content(MocData.vacancies),
            {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FavoriteVacanciesEmptyPreview() {
    AppTheme {
        FavoritesScreen(
            state = FavoriteVacanciesState.Empty,
            {}
        )
    }
}
