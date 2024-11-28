package ir.irancell.application.shared

import kotlin.reflect.KClass

    class CommandDispatcher{
        private val handlers: MutableMap<KClass<out Command>, CommandHandler<out Command>> = mutableMapOf()


        fun <T : Command> registerHandler(commandType: KClass<T>, handler: CommandHandler<T>) {
            handlers[commandType] = handler
        }

        @Suppress("UNCHECKED_CAST")
        suspend fun <C : Command> dispatch(command: C)   {
            val handler = handlers[command::class] as? CommandHandler<C>
                ?: throw IllegalArgumentException("No handler registered for ${command::class}")
            handler.handle(command)
        }
    }