package ir.irancell.infrastructure.shared

import ir.irancell.domain.repositories.IReadRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction




abstract class AbstractReadDatabaseRepository<T : Any, ID>(
    private val table: Table,
    private val idColumn: Column<ID>,
    private val database: Database
) : IReadRepository<T, ID> {

    abstract fun toEntity(row: ResultRow): T
    abstract fun fromEntity(entity: T): InsertStatement<Number>
    override suspend fun findById(id: ID): T? = dbQuery(database) {
        table.selectAll().where { idColumn eq id }.mapNotNull { toEntity(it) }.singleOrNull()
    }

    override suspend fun findAll(): List<T> = dbQuery(database) {
        table.selectAll().map { toEntity(it) }
    }
}
