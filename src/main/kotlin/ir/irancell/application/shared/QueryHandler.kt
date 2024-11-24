package ir.irancell.application.shared

interface QueryHandler<Q : Query<R>, R> {
    suspend fun handle(query: Q): R
}