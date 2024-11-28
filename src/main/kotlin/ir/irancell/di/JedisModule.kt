package ir.irancell.di

import ir.irancell.infrastructure.createRedisClient
import org.koin.dsl.module

val jedisModule = module {
    single { createRedisClient() }
}