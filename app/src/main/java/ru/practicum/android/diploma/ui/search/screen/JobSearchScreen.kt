package ru.practicum.android.diploma.ui.search.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.search.viewmodel.JobSearchState
import ru.practicum.android.diploma.ui.search.components.VacancyListItem

private val ScreenPadding = 16.dp
private val SearchFieldCornerRadius = 8.dp

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
        SearchTopBar(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onClearQuery = onClearQuery,
            onFiltersClick = onFiltersClick,
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
private fun SearchTopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onFiltersClick: () -> Unit,
) {
    RowWithFilter(
        onFiltersClick = onFiltersClick,
        searchField = {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(R.string.search_vacancies_hint))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = onClearQuery) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(R.string.search_vacancies_hint),
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(SearchFieldCornerRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
            )
        },
    )
}

@Composable
private fun RowWithFilter(
    onFiltersClick: () -> Unit,
    searchField: @Composable () -> Unit,
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(1f)) {
            searchField()
        }
        IconButton(onClick = onFiltersClick) {
            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = stringResource(R.string.search_vacancies_filters),
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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.search_vacancies_placeholder_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.search_vacancies_placeholder_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun SearchLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
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
        Text(
            text = pluralStringResource(R.plurals.search_vacancies_found, found, found),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                items = vacancies,
                key = { it.id },
            ) { vacancy ->
                VacancyListItem(
                    vacancy = vacancy,
                    onClick = { onVacancyClick(vacancy.id) },
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }
    }
}
