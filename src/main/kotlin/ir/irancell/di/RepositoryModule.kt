package ir.irancell.di


import ir.irancell.domain.repositories.write.IUserReadRepository
import ir.irancell.domain.repositories.write.IUserWriteRepository
import ir.irancell.infrastructure.database.repository_impl.UserReadRepositoryImpl
import ir.irancell.infrastructure.database.repository_impl.UserWriteRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<IUserWriteRepository> { UserWriteRepositoryImpl(get(named("writeDatabase"))) }
    single<IUserReadRepository> { UserReadRepositoryImpl(get(named("readDatabase"))) }
}