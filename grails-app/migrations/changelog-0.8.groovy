databaseChangeLog = {

	changeSet(author: "markus (generated)", id: "1398759793558-1") {
		renameColumn(tableName: "spot", schemaName: "dbo", newColumnName: "spotsignal", oldColumnName: "signal")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-2") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "block_col", schemaName: "dbo", tableName: "result_file_config")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-3") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "target", schemaName: "dbo", tableName: "sample")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-4") {
		dropForeignKeyConstraint(baseTableName: "experiment_slide", baseTableSchemaName: "dbo", constraintName: "FK4D05CAEFCBAE272B")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-5") {
		dropForeignKeyConstraint(baseTableName: "experiment_slide", baseTableSchemaName: "dbo", constraintName: "FK4D05CAEF6BD5E4E7")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-6") {
		dropForeignKeyConstraint(baseTableName: "experiment_slide_layout", baseTableSchemaName: "dbo", constraintName: "FKFB46D27A8F08CB68")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-7") {
		dropForeignKeyConstraint(baseTableName: "project_plate_layout", baseTableSchemaName: "dbo", constraintName: "FK6FF547D9ED9786A6")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-8") {
		dropForeignKeyConstraint(baseTableName: "project_plate_layout", baseTableSchemaName: "dbo", constraintName: "FK6FF547D979C2DE81")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-9") {
		dropForeignKeyConstraint(baseTableName: "project_slide", baseTableSchemaName: "dbo", constraintName: "FKC80B750B53BB22B3")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-10") {
		dropForeignKeyConstraint(baseTableName: "project_slide", baseTableSchemaName: "dbo", constraintName: "FKC80B750B6BD5E4E7")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-11") {
		dropForeignKeyConstraint(baseTableName: "project_slide_layout", baseTableSchemaName: "dbo", constraintName: "FKE50ED0DE3126D238")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-12") {
		dropForeignKeyConstraint(baseTableName: "project_slide_layout", baseTableSchemaName: "dbo", constraintName: "FKE50ED0DED6E665DC")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-14") {
		dropColumn(columnName: "experiment_layouts_id", schemaName: "dbo", tableName: "experiment_slide_layout")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-15") {
		dropColumn(columnName: "control_plate", schemaName: "dbo", tableName: "plate_layout")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-17") {
		dropTable(schemaName: "dbo", tableName: "experiment_slide")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-18") {
		dropTable(schemaName: "dbo", tableName: "project_plate_layout")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-19") {
		dropTable(schemaName: "dbo", tableName: "project_slide")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-20") {
		dropTable(schemaName: "dbo", tableName: "project_slide_layout")
	}

	changeSet(author: "markus (generated)", id: "1398759793558-22") {
		dropTable(schemaName: "dbo", tableName: "rfile")
	}
}
