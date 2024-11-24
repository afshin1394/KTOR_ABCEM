package ir.irancell.application.shared
interface CommandHandler<in C : Command> {
    suspend fun handle(command: C)
}