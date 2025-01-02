package ir.irancell.infrastructure.serializer

import ir.irancell.application.commands.batch_user.BatchInsertCommand
import ir.irancell.application.commands.create_user.CreateUserCommand
import ir.irancell.application.shared.Command
import ir.irancell.domain.events.BatchInsertEvent
import ir.irancell.domain.events.Event
import ir.irancell.domain.events.UserCreatedEvent
import kotlinx.serialization.KSerializer
object SerializationRegistry {

    val commandSerializers: Map<String, KSerializer<out Command>> = mapOf(
        CreateUserCommand::class.simpleName.toString() to CreateUserCommand.serializer(),
        BatchInsertCommand::class.simpleName.toString() to BatchInsertCommand.serializer()
    )

    val eventSerializers: Map<String, KSerializer<out Event>> = mapOf(
        UserCreatedEvent::class.simpleName.toString() to UserCreatedEvent.serializer(),
        BatchInsertEvent::class.simpleName.toString() to BatchInsertEvent.serializer()
    )
}