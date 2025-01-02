package ir.irancell.infrastructure.di

import ir.irancell.application.services.impl.AuthorizationServiceImpl
import ir.irancell.application.services.impl.UserServiceImpl
import ir.irancell.application.services.interfaces.IAuthorizationService
import ir.irancell.application.services.interfaces.IUserService

import org.koin.dsl.module

val serviceModule = module {
       single<IUserService> {   UserServiceImpl(get(),get())   }
       single<IAuthorizationService> { AuthorizationServiceImpl(get()) }
}