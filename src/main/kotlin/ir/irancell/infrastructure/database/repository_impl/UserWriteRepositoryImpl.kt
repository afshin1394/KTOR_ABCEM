package ir.irancell.infrastructure.database.repository_impl

import ir.irancell.domain.models.UserDomain
import ir.irancell.domain.repositories.write.IUserWriteRepository
import ir.irancell.infrastructure.database.UsersTable
import ir.irancell.infrastructure.shared.AbstractWriteDatabaseRepository
import ir.irancell.infrastructure.shared.dbTransaction
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserWriteRepositoryImpl(private val writeDatabase: Database) : AbstractWriteDatabaseRepository<UserDomain, UUID>(
    database = writeDatabase,
    table = UsersTable,
    idColumn = UsersTable.id
),
    IUserWriteRepository {
    override fun toEntity(row: ResultRow): UserDomain {
        return UserDomain(
            name = row[UsersTable.name],
            age = row[UsersTable.age]
        )
    }


    override fun fromEntity(entity: UserDomain): InsertStatement<Number> {
        return UsersTable.insert {
            it[name] = entity.name
            it[age] = entity.age
        }
    }

    override suspend fun batchInsert(users: List<UserDomain>) {

        dbTransaction(database = writeDatabase) {
            UsersTable.batchInsert(
                data = users,
                ignore = false,
                shouldReturnGeneratedValues = false
            ) {user->
                this[UsersTable.name] = user.name
                this[UsersTable.age] = user.age
            }
        }

    }
}