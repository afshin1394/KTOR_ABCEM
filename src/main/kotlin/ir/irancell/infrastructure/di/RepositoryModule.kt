package ir.irancell.infrastructure.di


import io.ktor.client.*
import ir.irancell.domain.repositories.read.IUserReadRepository
import ir.irancell.domain.repositories.remote.IIpInfoRepository
import ir.irancell.domain.repositories.write.IUserWriteRepository
import ir.irancell.infrastructure.database.repository_impl.UserReadRepositoryImpl
import ir.irancell.infrastructure.database.repository_impl.UserWriteRepositoryImpl
import ir.irancell.infrastructure.remotes.repository_impl.IpInfoRepositoryImpl
import ir.irancell.infrastructure.shared.DBCommand
import ir.irancell.infrastructure.shared.DBQuery
import org.koin.dsl.module

val repositoryModule = module {
    single<IUserWriteRepository> { UserWriteRepositoryImpl(get<DBCommand>()) }
    single<IUserReadRepository> { UserReadRepositoryImpl(get<DBQuery>()) }
    single<IIpInfoRepository> { IpInfoRepositoryImpl(get<HttpClient>()) }
}