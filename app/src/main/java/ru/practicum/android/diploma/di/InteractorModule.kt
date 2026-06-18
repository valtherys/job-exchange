package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.actions.VacancyActionInteractor
import ru.practicum.android.diploma.domain.api.area.AreaInteractor
import ru.practicum.android.diploma.domain.api.db.VacancyDbInteractor
import ru.practicum.android.diploma.domain.api.industry.IndustryInteractor
import ru.practicum.android.diploma.domain.api.search.SearchInteractor
import ru.practicum.android.diploma.domain.api.storage.FiltrationInteractor
import ru.practicum.android.diploma.domain.api.vacancy.VacancyDetailInteractor
import ru.practicum.android.diploma.domain.impl.action.VacancyActionInteractorImpl
import ru.practicum.android.diploma.domain.impl.area.AreaInteractorImpl
import ru.practicum.android.diploma.domain.impl.db.VacancyDbInteractorImpl
import ru.practicum.android.diploma.domain.impl.industry.IndustryInteractorImpl
import ru.practicum.android.diploma.domain.impl.search.SearchInteractorImpl
import ru.practicum.android.diploma.domain.impl.storage.FiltrationInteractorImpl
import ru.practicum.android.diploma.domain.impl.vacancy.VacancyDetailInteractorImpl

val interactorModule = module {
    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    single<VacancyDetailInteractor> {
        VacancyDetailInteractorImpl(get())
    }

    single<VacancyActionInteractor> {
        VacancyActionInteractorImpl(get())
    }
    single<VacancyDbInteractor> {
        VacancyDbInteractorImpl(get())
    }
    single<FiltrationInteractor> {
        FiltrationInteractorImpl(get())
    }

    single<IndustryInteractor> {
        IndustryInteractorImpl(get())
    }

    single<AreaInteractor> {
        AreaInteractorImpl(get())
    }
}
