package ir.irancell.domain.repositories

interface IReadRepository<T, ID> {
    suspend fun findById(id: ID): T?
    suspend fun findAll(): List<T>
}