
databaseChangeLog = {
  changeSet(id: "modifyDescriptionColumnInExperiment", author: "mlist"){
		modifyDataType(tableName: "Experiment", columnName: "description", newDataType: "text")
  }
  changeSet(id: "modifyDescriptionColumnInProject", author: "mlist"){
		modifyDataType(tableName: "Project", columnName: "project_description", newDataType: "text")
  }
}


