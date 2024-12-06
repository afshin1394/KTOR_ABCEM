package ir.irancell.infrastructure.di

import ir.irancell.application.shared.InMemoryCaching
import ir.irancell.infrastructure.shared.DBQuery
import org.koin.core.qualifier.named
import org.koin.dsl.module

val transactionModule = module {
    single { InMemoryCaching(get()) }
    single { DBQuery(get(named("readDatabase")),get()) }
}