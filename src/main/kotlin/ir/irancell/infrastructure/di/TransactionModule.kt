package ir.irancell.infrastructure.di

import ir.irancell.infrastructure.InMemoryCaching
import ir.irancell.infrastructure.shared.DBCommand
import ir.irancell.infrastructure.shared.DBQuery
import org.koin.core.qualifier.named
import org.koin.dsl.module

val transactionModule = module {
    single { InMemoryCaching(get()) }
    single { DBQuery(get(named("readDatabase")),get()) }
    single { DBCommand(get(named("readDatabase"))) }
}