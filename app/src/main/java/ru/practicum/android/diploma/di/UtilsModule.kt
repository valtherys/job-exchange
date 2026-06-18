package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.sharing.ExternalNavigatorImpl
import ru.practicum.android.diploma.domain.api.sharing.ExternalNavigator
import ru.practicum.android.diploma.util.NetworkConnectionChecker
import ru.practicum.android.diploma.util.NetworkConnectionCheckerImpl

val utilsModule = module {
    single<NetworkConnectionChecker> {
        NetworkConnectionCheckerImpl(get())
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(get())
    }
}
