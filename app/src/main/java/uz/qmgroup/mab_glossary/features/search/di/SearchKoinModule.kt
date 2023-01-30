package uz.qmgroup.mab_glossary.features.search.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import uz.qmgroup.mab_glossary.features.search.SearchViewModel
import uz.qmgroup.mab_glossary.features.search.datasource.TermDataSource
import uz.qmgroup.mab_glossary.features.search.datasource.TermDataSourceImpl
import uz.qmgroup.mab_glossary.features.search.datasource.localDB.TermDatabase

val searchKoinModule = module {
    single { TermDatabase(get()) }

    singleOf(::TermDataSourceImpl) { bind<TermDataSource>() }

    viewModelOf(::SearchViewModel)
}