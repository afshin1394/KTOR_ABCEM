package ir.irancell.infrastructure.shared
import ir.irancell.domain.repositories.IWriteRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction



abstract class AbstractWriteDatabaseRepository<T : Any, ID>(
    private val table: Table,
    private val idColumn: Column<ID>,
    private val database: Database
) : IWriteRepository<T, ID> {


    abstract fun toEntity(row: ResultRow): T
    abstract fun fromEntity(entity: T): InsertStatement<Number>

    override suspend fun save(entity: T): T = dbTransaction(database) {
        val insertStatement = fromEntity(entity)
        insertStatement.resultedValues?.singleOrNull()?.let { toEntity(it) } ?: entity
    }
    override suspend fun deleteById(id: ID) {
        dbTransaction(database) {
            table.deleteWhere { idColumn eq id }
        }
    }
}



