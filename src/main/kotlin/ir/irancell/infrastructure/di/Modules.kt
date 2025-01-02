package ir.irancell.infrastructure.di

val appModules = listOf(
    jedisModule,
    kafkaModule,
    transactionModule,
    serviceModule,
    cqrsModule,
    repositoryModule,
    logModule,
    databaseModule,
    httpClientModule
//    configModule
)
