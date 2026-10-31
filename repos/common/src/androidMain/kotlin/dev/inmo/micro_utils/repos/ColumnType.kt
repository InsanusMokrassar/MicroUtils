package dev.inmo.micro_utils.repos

sealed class ColumnType(
    typeName: String,
    nullable: Boolean
) {
    open val asType: String = "$typeName${if (!nullable) " not null" else ""}"
    sealed class Text(
        nullable: Boolean
    ) : ColumnType("text", nullable) {
        object NULLABLE : Text(true)
        object NOT_NULLABLE : Text(false)
    }
    sealed class Numeric(
        typeName: String,
        autoincrement: Boolean = false,
        primaryKey: Boolean = false,
        nullable: Boolean = false
    ) : ColumnType(
        typeName,
        nullable
    ) {
        override val asType: String = "${super.asType}${if (primaryKey) " primary key" else ""}${if (autoincrement) " autoincrement" else ""}"

        class INTEGER(
            autoincrement: Boolean = false,
            primaryKey: Boolean = false,
            nullable: Boolean = false
        ) : Numeric(
            "integer",
            autoincrement,
            primaryKey,
            nullable
        )
        class DOUBLE(autoincrement: Boolean = false, primaryKey: Boolean = false, nullable: Boolean = false) : Numeric(
            "double",
            autoincrement,
            primaryKey,
            nullable
        )
    }

    override fun toString(): String {
        return asType
    }
}