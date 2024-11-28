package ir.irancell.application.shared
interface CommandHandler<T : Command> {
    suspend fun handle(command: T): Any
}