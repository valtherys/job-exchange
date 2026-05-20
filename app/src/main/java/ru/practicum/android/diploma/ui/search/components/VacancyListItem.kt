package ru.practicum.android.diploma.ui.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.formatSalary

private val LogoSize = 48.dp
private val LogoCornerRadius = 12.dp
private val ItemSpacing = 8.dp

@Composable
fun VacancyListItem(
    vacancy: Vacancy,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.Top,
    ) {
        VacancyLogo(
            logoUrl = vacancy.logoUrl,
            modifier = Modifier.size(LogoSize),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
        ) {
            Text(
                text = buildVacancyTitle(vacancy),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            val salaryText = formatSalary(vacancy.salary)
            if (salaryText.isNotEmpty()) {
                Spacer(modifier = Modifier.height(ItemSpacing))
                Text(
                    text = salaryText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            } else {
                Spacer(modifier = Modifier.height(ItemSpacing))
                Text(
                    text = stringResource(R.string.search_vacancies_salary_not_specified),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            vacancy.company?.let { company ->
                Spacer(modifier = Modifier.height(ItemSpacing))
                Text(
                    text = company,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            vacancy.city?.let { city ->
                Spacer(modifier = Modifier.height(ItemSpacing))
                Text(
                    text = city,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun VacancyLogo(
    logoUrl: String?,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(LogoCornerRadius)
    if (logoUrl.isNullOrBlank()) {
        Spacer(
            modifier = modifier
                .clip(shape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
        )
    } else {
        AsyncImage(
            model = logoUrl,
            contentDescription = null,
            modifier = modifier.clip(shape),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_logo_placeholder_48),
            error = painterResource(R.drawable.ic_logo_placeholder_48),
        )
    }
}

private fun buildVacancyTitle(vacancy: Vacancy): String {
    val company = vacancy.company
    return if (company.isNullOrBlank()) {
        vacancy.name
    } else {
        "${vacancy.name}, $company"
    }
}
