package ir.irancell.domain.repositories

interface IWriteRepository<T, ID> {
    suspend fun save(entity: T): T
    suspend fun deleteById(id: ID)
}