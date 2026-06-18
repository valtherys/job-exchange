package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.area.AreaRepositoryImpl
import ru.practicum.android.diploma.data.db.VacancyDbRepositoryImpl
import ru.practicum.android.diploma.data.industry.IndustryRepositoryImpl
import ru.practicum.android.diploma.data.search.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.storage.FiltrationRepositoryImpl
import ru.practicum.android.diploma.data.vacancy.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.area.AreaRepository
import ru.practicum.android.diploma.domain.api.db.VacancyDbRepository
import ru.practicum.android.diploma.domain.api.industry.IndustryRepository
import ru.practicum.android.diploma.domain.api.search.VacanciesRepository
import ru.practicum.android.diploma.domain.api.storage.FiltrationRepository
import ru.practicum.android.diploma.domain.api.vacancy.VacancyDetailsRepository

val repositoryModule = module {

    single<VacancyDbRepository> {
        VacancyDbRepositoryImpl(get())
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get())
    }

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }

    single<FiltrationRepository> {
        FiltrationRepositoryImpl(get())
    }

    single<IndustryRepository> {
        IndustryRepositoryImpl(get())
    }

    single<AreaRepository> {
        AreaRepositoryImpl(get(), get())
    }
}
