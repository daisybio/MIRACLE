/*
 * Copyright (C) 2014
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:			http://www.nanocan.org/miracle/
 * ###########################################################################
 *	
 *	This file is part of MIRACLE.
 *
 *  MIRACLE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
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
