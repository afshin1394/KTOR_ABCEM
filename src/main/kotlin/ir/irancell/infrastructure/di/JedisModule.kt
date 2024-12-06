package ir.irancell.infrastructure.di

import ir.irancell.infrastructure.JedisFactory.createRedisClient
import org.koin.dsl.module

val jedisModule = module {
    single { createRedisClient() }
}