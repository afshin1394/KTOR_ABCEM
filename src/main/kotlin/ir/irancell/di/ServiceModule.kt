package ir.irancell.di

import io.ktor.server.application.*
import ir.irancell.domain.services.HelloService
import ir.irancell.infrastructure.orm.UserService
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val serviceModule = module {

        single<HelloService> {
            val environment = get<Application>().environment

            HelloService {
                println(environment.log.info("HelloService provided"))
            }

        }

       single {   UserService(get<Database>()) }

}