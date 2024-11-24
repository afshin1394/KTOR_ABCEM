package ir.irancell.infrastructure.database.repository_impl

import ir.irancell.domain.models.UserDomain
import ir.irancell.domain.repositories.write.IUserReadRepository
import ir.irancell.infrastructure.database.UsersTable
import ir.irancell.infrastructure.shared.AbstractReadDatabaseRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.util.UUID

class UserReadRepositoryImpl(readDataBase: Database) : AbstractReadDatabaseRepository<UserDomain, UUID>(database = readDataBase,table = UsersTable, idColumn = UsersTable.id),
    IUserReadRepository {
    override fun toEntity(row: ResultRow): UserDomain {
        return UserDomain(
            name = row[UsersTable.name],
            age = row[UsersTable.age]
        )
    }

    override fun fromEntity(entity: UserDomain): InsertStatement<Number> {
        return UsersTable.insert {
            it[id] = UUID.randomUUID()
            it[name] = entity.name
            it[age] = entity.age
        }
    }
}
