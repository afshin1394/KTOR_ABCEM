package ir.irancell.application.shared

import ir.irancell.application.queries.GetAllUsersQuery
import kotlin.reflect.KClass


class QueryDispatcher {
    private val handlers: MutableMap<Class<out Query<*>>, QueryHandler<out Query<*>, *>> = mutableMapOf()

    fun <TQuery : Query<TResult>, TResult> registerHandler(
        queryType: KClass<GetAllUsersQuery>,
        handler: QueryHandler<TQuery, TResult>
    ) {
        handlers[queryType.java] = handler
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun <TQuery : Query<TResult>, TResult> dispatch(query: TQuery): TResult? {
        val handler = handlers[query::class.java] as? QueryHandler<TQuery, TResult>
            ?: throw IllegalArgumentException("No handler registered for ${query::class}")
        return handler.handle(query)
    }


}