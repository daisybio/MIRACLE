databaseChangeLog = {

	changeSet(author: "markus (generated)", id: "1386241194013-1") {
		addNotNullConstraint(columnDataType: "int", columnName: "replicate", schemaName: "dbo", tableName: "layout_spot", defaultNullValue: 1)
	}

	changeSet(author: "markus (generated)", id: "1386241194013-2") {
		addNotNullConstraint(columnDataType: "bit", columnName: "control_plate", schemaName: "dbo", tableName: "plate_layout", defaultNullValue: false)
	}

	changeSet(author: "markus (generated)", id: "1386241194013-3") {
		addNotNullConstraint(columnDataType: "varchar(15)", columnName: "type", schemaName: "dbo", tableName: "sample", defaultNullValue:"unknown")
	}

	changeSet(author: "markus (generated)", id: "1386241194013-5") {
		createIndex(indexName: "name_unique_1386241191865", tableName: "number_of_cells_seeded", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "markus (generated)", id: "1386241194013-6") {
		dropColumn(columnName: "experiment_layouts_id", schemaName: "dbo", tableName: "experiment_slide_layout")
	}

	changeSet(author: "markus (generated)", id: "1386241194013-7") {
		dropColumn(columnName: "target_gene", schemaName: "dbo", tableName: "sample")
	}

	changeSet(author: "markus (generated)", id: "1386241194013-8") {
		dropColumn(columnName: "spot_type_id", schemaName: "dbo", tableName: "well_layout")
	}

	changeSet(author: "markus (generated)", id: "1386241194013-9") {
		dropTable(schemaName: "dbo", tableName: "experiment_slide")
	}

	changeSet(author: "markus (generated)", id: "1386241194013-10") {
		dropTable(schemaName: "dbo", tableName: "project_plate_layout")
	}

	changeSet(author: "markus (generated)", id: "1386241194013-11") {
		dropTable(schemaName: "dbo", tableName: "project_slide")
	}

	changeSet(author: "markus (generated)", id: "1386241194013-12") {
		dropTable(schemaName: "dbo", tableName: "project_slide_layout")
	}

}
