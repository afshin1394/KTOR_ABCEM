package ir.irancell.di

import org.koin.dsl.module
import org.slf4j.Logger
import org.slf4j.LoggerFactory

//    val logModule = module {
//        single { (clazz: KClass<*>) -> LoggerFactory.getLogger(clazz.java) }
//    }
val logModule = module {
    single<Logger> {
        LoggerFactory.getLogger("ApplicationLogger")
    }
}