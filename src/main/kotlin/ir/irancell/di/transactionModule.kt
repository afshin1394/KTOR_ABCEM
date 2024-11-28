package ir.irancell.di

import ir.irancell.application.shared.CacheUtil
import ir.irancell.infrastructure.shared.DBQuery
import org.koin.core.qualifier.named
import org.koin.dsl.module

val transactionModule = module {
    single { CacheUtil(get()) }
    single { DBQuery(get(named("readDatabase")),get(),60) }
}