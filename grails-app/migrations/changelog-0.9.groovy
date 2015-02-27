databaseChangeLog = {
    changeSet(author: "markus (generated)", id: "1409224782039-1") {
        addColumn(tableName: "slide_layout") {
            column(name: "deposition_direction", type: "varchar(11)", defaultValue: "row-wise") {
                constraints(nullable: "false")
            }
        }
    }
}