databaseChangeLog = {


	changeSet(author: "markus (generated)", id: "1409224782039-2") {
		dropForeignKeyConstraint(baseTableName: "entry", baseTableSchemaName: "dbo", constraintName: "FK5C3087235F1F382")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-3") {
		dropForeignKeyConstraint(baseTableName: "library", baseTableSchemaName: "dbo", constraintName: "FK9E824BB94364173")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-4") {
		dropForeignKeyConstraint(baseTableName: "library", baseTableSchemaName: "dbo", constraintName: "FK9E824BB104DDF1D")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-5") {
		dropForeignKeyConstraint(baseTableName: "library_library_plate", baseTableSchemaName: "dbo", constraintName: "FK8C513E0EFBE6B204")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-6") {
		dropForeignKeyConstraint(baseTableName: "library_library_plate", baseTableSchemaName: "dbo", constraintName: "FK8C513E0ED78E4913")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-7") {
		dropForeignKeyConstraint(baseTableName: "library_plate_entry", baseTableSchemaName: "dbo", constraintName: "FKE92960859821AA99")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-8") {
		dropForeignKeyConstraint(baseTableName: "library_plate_entry", baseTableSchemaName: "dbo", constraintName: "FKE92960852D3D6B13")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-9") {
		dropForeignKeyConstraint(baseTableName: "slide_slide", baseTableSchemaName: "dbo", constraintName: "FKDD3815A36BD5E4E7")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-10") {
		dropForeignKeyConstraint(baseTableName: "slide_slide", baseTableSchemaName: "dbo", constraintName: "FKDD3815A3A6CD4372")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-11") {
		dropForeignKeyConstraint(baseTableName: "well_readout", baseTableSchemaName: "dbo", constraintName: "FKBA9E7F8732166BBF")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-14") {
		dropColumn(columnName: "well_layout_id", schemaName: "dbo", tableName: "well_readout")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-15") {
		dropTable(schemaName: "dbo", tableName: "entry")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-16") {
		dropTable(schemaName: "dbo", tableName: "library")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-17") {
		dropTable(schemaName: "dbo", tableName: "library_library_plate")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-18") {
		dropTable(schemaName: "dbo", tableName: "library_plate")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-19") {
		dropTable(schemaName: "dbo", tableName: "library_plate_entry")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-20") {
		dropTable(schemaName: "dbo", tableName: "library_to_experiment_exception")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-21") {
		dropTable(schemaName: "dbo", tableName: "registration_code")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-22") {
		dropTable(schemaName: "dbo", tableName: "rfile")
	}

	changeSet(author: "markus (generated)", id: "1409224782039-23") {
		dropTable(schemaName: "dbo", tableName: "slide_slide")
	}

}
