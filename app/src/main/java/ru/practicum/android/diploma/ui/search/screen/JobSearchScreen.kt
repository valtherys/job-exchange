package ru.practicum.android.diploma.ui.search.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.search.viewmodel.JobSearchState
import ru.practicum.android.diploma.ui.search.components.VacancyListItem
import ru.practicum.android.diploma.ui.theme.Black
import ru.practicum.android.diploma.ui.theme.Blue
import ru.practicum.android.diploma.ui.theme.Bold32
import ru.practicum.android.diploma.ui.theme.Grey
import ru.practicum.android.diploma.ui.theme.LightGrey
import ru.practicum.android.diploma.ui.theme.White

private val ScreenPadding = 16.dp
private val SearchFieldCornerRadius = 8.dp
private val FoundBadgeCornerRadius = 50.dp
private val SearchFieldVerticalPadding = 12.dp
private val SearchFieldHorizontalPadding = 16.dp
private val FoundBadgeHorizontalPadding = 12.dp
private val FoundBadgeVerticalPadding = 4.dp

@Composable
fun JobSearchScreen(
    searchQuery: String,
    state: JobSearchState,
    onSearchQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onFiltersClick: () -> Unit,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = ScreenPadding),
    ) {
        Spacer(modifier = Modifier.height(ScreenPadding))
        SearchScreenHeader(onFiltersClick = onFiltersClick)
        Spacer(modifier = Modifier.height(ScreenPadding))
        SearchInputField(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onClearQuery = onClearQuery,
        )
        Spacer(modifier = Modifier.height(ScreenPadding))
        when (state) {
            JobSearchState.Initial -> SearchPlaceholder(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, fill = true),
            )
            JobSearchState.Loading -> SearchLoading(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, fill = true),
            )
            is JobSearchState.Content -> SearchResults(
                found = state.found,
                vacancies = state.vacancies,
                onVacancyClick = onVacancyClick,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, fill = true),
            )
        }
    }
}

@Composable
private fun SearchScreenHeader(onFiltersClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.search_vacancies_title),
            style = Bold32,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = onFiltersClick) {
            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = stringResource(R.string.search_vacancies_filters),
                tint = Black,
            )
        }
    }
}

@Composable
private fun SearchInputField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(SearchFieldCornerRadius))
            .background(LightGrey)
            .padding(
                horizontal = SearchFieldHorizontalPadding,
                vertical = SearchFieldVerticalPadding,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.weight(1f),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Black),
            singleLine = true,
            cursorBrush = SolidColor(Blue),
            decorationBox = { innerTextField ->
                if (searchQuery.isEmpty()) {
                    Text(
                        text = stringResource(R.string.search_vacancies_hint),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Grey,
                    )
                }
                innerTextField()
            },
        )
        if (searchQuery.isNotEmpty()) {
            IconButton(
                onClick = onClearQuery,
                modifier = Modifier.size(24.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.search_vacancies_clear),
                    tint = Black,
                )
            }
        } else {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Black,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Composable
private fun SearchPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.image_search_empty),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit,
        )
    }
}

@Composable
private fun SearchLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = Blue)
    }
}

@Composable
private fun SearchResults(
    found: Int,
    vacancies: List<Vacancy>,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = ScreenPadding),
            contentAlignment = Alignment.Center,
        ) {
            FoundVacanciesBadge(found = found)
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                items = vacancies,
                key = { it.id },
            ) { vacancy ->
                VacancyListItem(
                    vacancy = vacancy,
                    onClick = { onVacancyClick(vacancy.id) },
                )
            }
        }
    }
}

@Composable
private fun FoundVacanciesBadge(found: Int) {
    Surface(
        color = Blue,
        shape = RoundedCornerShape(FoundBadgeCornerRadius),
    ) {
        Text(
            text = pluralStringResource(R.plurals.search_vacancies_found, found, found),
            style = MaterialTheme.typography.titleMedium,
            color = White,
            modifier = Modifier.padding(
                horizontal = FoundBadgeHorizontalPadding,
                vertical = FoundBadgeVerticalPadding,
            ),
        )
    }
}
