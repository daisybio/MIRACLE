databaseChangeLog = {

	changeSet(author: "mlist (generated)", id: "1340373645634-1") {
		createTable(tableName: "person") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "personPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
		sql(''' insert into person values (0, 0, 0, 1, 'password', 0, 'mlpedersen') ''') 
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-7") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
		sql(''' insert into role values(0, 'ROLE_ADMIN') ''')
		sql(''' insert into role values(0, 'ROLE_USER') ''')
	}
	
	changeSet(author: "mlist (generated)", id: "1340373645634-2") {
		createTable(tableName: "person_role") {
			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "person_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
		sql(''' insert into person_role values(1, 1) ''')
		sql(''' insert into person_role values(2, 1) ''')
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-3") {
		createTable(tableName: "project") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "projectPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "created_by_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime2") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime2") {
				constraints(nullable: "false")
			}

			column(name: "last_updated_by_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "project_description", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "project_title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-4") {
		createTable(tableName: "project_slide") {
			column(name: "project_slides_id", type: "bigint")

			column(name: "slide_id", type: "bigint")
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-5") {
		createTable(tableName: "project_slide_layout") {
			column(name: "project_layouts_id", type: "bigint")

			column(name: "slide_layout_id", type: "bigint")
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-6") {
		createTable(tableName: "registration_code") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "registration_PK")
			}

			column(name: "date_created", type: "datetime2") {
				constraints(nullable: "false")
			}

			column(name: "token", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-8") {
		addColumn(tableName: "antibody") {
			column(name: "comments", type: "varchar(255)")
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-9") {
		addColumn(tableName: "slide") {
			column(name: "comments", type: "varchar(255)")
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-10") {
		addColumn(tableName: "slide") {
			column(name: "created_by_id", type: "bigint")				
		}
		sql(''' update slide set created_by_id = 1 ''')
		addNotNullConstraint(columnDataType: "bigint", columnName: "created_by_id", schemaName: "dbo", tableName: "slide")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-11") {
		addColumn(tableName: "slide") {
			column(name: "date_created", type: "datetime2") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-12") {
		addColumn(tableName: "slide") {
			column(name: "last_updated", type: "datetime2") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-13") {
		addColumn(tableName: "slide") {
			column(name: "last_updated_by_id", type: "bigint") 	
		}
		sql(''' update slide set last_updated_by_id = 1 ''')
		addNotNullConstraint(columnDataType: "bigint", columnName: "last_updated_by_id", schemaName: "dbo", tableName: "slide")	
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-14") {
		addColumn(tableName: "slide_layout") {
			column(name: "created_by_id", type: "bigint")
		}
		sql(''' update slide_layout set created_by_id = 1 ''')
		addNotNullConstraint(columnDataType: "bigint", columnName: "created_by_id", schemaName: "dbo", tableName: "slide_layout")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-15") {
		addColumn(tableName: "slide_layout") {
			column(name: "date_created", type: "datetime2") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-16") {
		addColumn(tableName: "slide_layout") {
			column(name: "last_updated", type: "datetime2") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-17") {
		addColumn(tableName: "slide_layout") {
			column(name: "last_updated_by_id", type: "bigint")
			sql(''' update slide_layout set last_updated_by_id = 1 ''')
			addNotNullConstraint(columnDataType: "bigint", columnName: "last_updated_by_id", schemaName: "dbo", tableName: "slide_layout")
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-18") {
		dropNotNullConstraint(columnDataType: "double precision(19)", columnName: "concentration", schemaName: "dbo", tableName: "antibody")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-19") {
		dropNotNullConstraint(columnDataType: "varchar(2)", columnName: "concentration_unit", schemaName: "dbo", tableName: "antibody")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-20") {
		dropNotNullConstraint(columnDataType: "bigint", columnName: "layout_spot_id", schemaName: "dbo", tableName: "spot")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-21") {
		addPrimaryKey(columnNames: "role_id, person_id", constraintName: "person_rolePK", tableName: "person_role")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-22") {
		dropForeignKeyConstraint(baseTableName: "slide", baseTableSchemaName: "dbo", constraintName: "FK6873DB1F058D40D")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-23") {
		addForeignKeyConstraint(baseColumnNames: "person_id", baseTableName: "person_role", constraintName: "FKE6A16B207D7505C5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-24") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "person_role", constraintName: "FKE6A16B20C939F4E5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-25") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "project", constraintName: "FKED904B19BEF1F16C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-26") {
		addForeignKeyConstraint(baseColumnNames: "last_updated_by_id", baseTableName: "project", constraintName: "FKED904B193B098F16", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-27") {
		addForeignKeyConstraint(baseColumnNames: "project_slides_id", baseTableName: "project_slide", constraintName: "FKC80B750B53BB22B3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "project", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-28") {
		addForeignKeyConstraint(baseColumnNames: "slide_id", baseTableName: "project_slide", constraintName: "FKC80B750B6BD5E4E7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "slide", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-29") {
		addForeignKeyConstraint(baseColumnNames: "project_layouts_id", baseTableName: "project_slide_layout", constraintName: "FKE50ED0DE3126D238", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "project", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-30") {
		addForeignKeyConstraint(baseColumnNames: "slide_layout_id", baseTableName: "project_slide_layout", constraintName: "FKE50ED0DED6E665DC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "slide_layout", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-31") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "slide", constraintName: "FK6873DB1BEF1F16C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-32") {
		addForeignKeyConstraint(baseColumnNames: "experimenter_id", baseTableName: "slide", constraintName: "FK6873DB124200F90", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-33") {
		addForeignKeyConstraint(baseColumnNames: "last_updated_by_id", baseTableName: "slide", constraintName: "FK6873DB13B098F16", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-34") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "slide_layout", constraintName: "FKBD452178BEF1F16C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-35") {
		addForeignKeyConstraint(baseColumnNames: "last_updated_by_id", baseTableName: "slide_layout", constraintName: "FKBD4521783B098F16", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-36") {
		createIndex(indexName: "username_unique_1340373639887", tableName: "person", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-37") {
		createIndex(indexName: "project_title_unique_1340373639891", tableName: "project", unique: "true") {
			column(name: "project_title")
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-38") {
		createIndex(indexName: "authority_unique_1340373639904", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "mlist (generated)", id: "1340373645634-39") {
		dropTable(schemaName: "dbo", tableName: "experimenter")
	}
}
