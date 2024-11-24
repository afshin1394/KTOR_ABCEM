package ir.irancell.application.shared

import kotlin.reflect.KClass

    class CommandDispatcher(
        private val handlers: Map<KClass<out Command>, CommandHandler<*>>
    ) {


        @Suppress("UNCHECKED_CAST")
        suspend fun <C : Command> dispatch(command: C) {
            val handler = handlers[command::class] as? CommandHandler<C>
                ?: throw IllegalArgumentException("No handler registered for ${command::class}")
            handler.handle(command)
        }
    }