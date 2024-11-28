package ir.irancell.infrastructure.shared

import ir.irancell.domain.repositories.IReadRepository
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement




abstract class AbstractReadDatabaseRepository<T : Any, ID>(
    private val dbQuery : DBQuery,
    private val kSerializer : KSerializer<T>,
    private val table: Table,
    private val idColumn: Column<ID>,
) : IReadRepository<T, ID> {

    abstract fun toEntity(row: ResultRow): T
    abstract fun fromEntity(entity: T): InsertStatement<Number>
    override suspend fun findById(id: ID): T? = dbQuery.exec(
        serializer =  kSerializer,
        cacheKey = "findById:${table.tableName}",
    ) {
        table.selectAll().where { idColumn eq id }.mapNotNull { toEntity(it) }.single()

    }
    override suspend fun findAll(): List<T> = dbQuery.exec(serializer = ListSerializer(kSerializer), cacheKey = "findAll${table.tableName}") {
        table.selectAll().map { toEntity(it) }?: emptyList()
    }
}
