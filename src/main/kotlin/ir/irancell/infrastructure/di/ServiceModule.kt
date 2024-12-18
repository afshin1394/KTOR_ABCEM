package ir.irancell.infrastructure.di

import ir.irancell.application.services.impl.IUserServiceImpl
import ir.irancell.application.services.interfaces.IUserService

import org.koin.dsl.module

val serviceModule = module {
       single<IUserService> {   IUserServiceImpl(get(),get())   }
}