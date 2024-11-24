package ir.irancell.infrastructure.database

import org.jetbrains.exposed.sql.Table

object UsersTable : Table() {
    val id = uuid("uuid").autoGenerate().uniqueIndex("idx_user_uuid")
    val name = varchar("name", length = 50)
    val age = integer("age")

    override val primaryKey = PrimaryKey(id)
}


