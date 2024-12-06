package ir.irancell.application.shared
interface CommandHandler<out T : Command> {
    suspend fun handle(command:@UnsafeVariance T)
    suspend fun deserializeCommand(json: String): T

}

