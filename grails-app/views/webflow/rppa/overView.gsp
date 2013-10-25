<%@ page import="org.nanocan.rppa.layout.PlateLayout"%>
<%@ page import="org.nanocan.rppa.layout.SlideLayout"%>
<%@ page import="org.nanocan.rppa.layout.CellLine"%>

<!doctype html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'overView.label', default: 'Overview')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
	<g:form action="rppa">
		<p>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<g:submitButton class="create" name="XMLgen"
			value="XML filegen Test" />
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<div id="listOfPlateLayouts" style="overflow: auto;">
			<g:render template="listPlateLayouts"
				model="${[plateLayoutInstanceList: listOfPlateLayouts?.values()?.asList(), plateLayoutInstanceTotal: listOfPlateLayouts?.values()?.asList()?.size()?:0]}" />
		</div>

		<g:submitButton class="create" name="addPlatelayouts"
			value="Add plate layout" />

		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		

		<div id="listOfSlideLayouts" style="overflow: auto;">
			<g:render template="listSlideLayout"
				model="${[slideLayoutInstanceList:listOfSlideLayouts?.values()?.asList(),slideLayoutInstanceTotal: listOfSlideLayouts?.values()?.asList()?:0] }" />
		</div>

		<g:submitButton class="create" name="addSlidelayout"
			value="Add slide layout" />

		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		

		<div id="listOfSlides" style="overflow: auto;">
			<g:render template="listSlides"
				model="${[slideInstanceList:listOfSlides?.values()?.asList(),slideInstanceTotal:listOfSlides?.values()?.asList()?.size()?:0] }" />
		</div>

		<g:submitButton class="create" name="addSlide" value="Add slide data" />
		
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		
		
		<div id="listOfAntibodys" style="overflow: auto;">
			<g:render template="listAntibodys"
				model="${[antibodyInstanceList:listOfAntibodys?.values()?.asList(),antibodyInstanceTotal:listOfAntibodys?.values()?.asList()?.size()?:0]}" />
		</div>

		<g:submitButton class="create" name="addAntibody"
			value="Add Antibody" />

		<br>
		<br>
		

		<div id="listOfBlockShifts" style="overflow: auto;">
			<g:render template="listBlockShifts" />
		</div>

		<br>
		<br>

		<div id="listOfAnalysis" style="overflow: auto;">
			<g:render template="listAnalysis" />
		</div>

		<br>
		<br>

		<div id="listOfCellLines" style="overflow: auto;">
			<g:render template="listCellLines"
				model="${[cellLineInstanceList:listOfCellLines?.values()?.asList(),cellLineInstanceTotal:cellLineInstanceList?.values()?.asList()?.size()?:0]}" />
		</div>

		<g:submitButton class="create" name="addCellLine" value="Add CellLine" />

		<br>
		<br>

		<div id="listOfDilutionsFactors" style="overflow: auto;">
			<g:render template="listDilutionFactors"
				model="${[dilutionInstanceList:listOfDilutionFactors?.values()?.asList(),dilutionInstanceTotal:dilutionInstanceList?.values()?.asList()?.size()?:0]}" />
		</div>

		<g:submitButton class="create" name="addDilution" value="Add Dilution" />

		<br>
		<br>

		<div id="listOfInducers" style="overflow: auto;">
			<g:render template="listInducers"
				model="${[inducerInstanceList:listOfInducers?.values()?.asList(),inducerInstanceTotal:inducerInstanceList?.values()?.asList()?.size()?:0]}" />
		</div>

		<g:submitButton class="create" name="addInducer" value="Add Inducer" />

		<br>
		<br>

		<div id="listOfLysisBuffers" style="overflow: auto;">
			<g:render template="listLysisBuffers"
				model="${[lysisBufferInstanceList:listOfLysisBuffers?.values()?.asList(),lysisBufferInstanceTotal:lysisBufferInstanceList?.values()?.asList()?.size()?:0]}" />
		</div>

		<g:submitButton class="create" name="addLysisBuffer"
			value="Add Lysis-Buffer" />

		<br>
		<br>

		<div id="listOfNumberOfCellsSeeded" style="overflow: auto;">
			<g:render template="listNumberOfCellsSeeded"
				model="${[numberOfCellsSeededInstanceList:listOfNumberOfCellsSeeded?.values()?.asList(),numberOfCellsSeededInstanceTotal:numberOfCellsSeededInstanceList?.values()?.asList()?.size()?:0]}" />
		</div>

		<g:submitButton class="create" name="addNumberOfCellsSeeded"
			value="Add Number Of Cell's Seeded" />

		<br>
		<br>

		<div id="listOfSpotTypes" style="overflow: auto;">
			<g:render template="listSpotTypes"
				model="${[spotTypeInstanceList:listOfSpotTypes?.values()?.asList(),spotTypeInstanceTotal:spotTypeInstanceList?.values()?.asList()?.size()?:0]}" />
		</div>

		<g:submitButton class="create" name="addSpotType"
			value="Add Spot-type" />

		<br>
		<br>

		<div id="listOfTreatments" style="overflow: auto;">
			<g:render template="listTreatments"
				model="${[treatmentInstanceList:listOfTreatments?.values()?.asList(),treatmentInstanceTotal:treatmentInstanceList?.values()?.asList()?.size()?:0]}" />
		</div>

		<g:submitButton class="create" name="addTreatment" 
			value="Add Treatment" />

		<br>
		<br>

		<div id="listOfSamples" style="overflow: auto;">
			<g:render template="listSamples"
				model="${[sampleInstanceList:listOfSamples?.values()?.asList(),sampleInstanceTotal:sampleInstanceList?.values()?.asList()?.size()?:0]}" />
		</div>

		<g:submitButton class="create" name="addSample"
			value="Add Sample" />


		<div id="listOfIdentifiers" style="overflow: auto;">
			<g:render template="listIdentifiers"
				model="${[identifierInstanceList:listOfIdentifiers?.values()?.asList(),identifierInstanceTotal:listOfIdentifiers?.values()?.asList()?.size()?:0]}" />
		</div>

		<g:submitButton class="create" name="addIdentifier"
			value="Add Identifier" />
	

		<div id="saveLocalCopy" style="overflow: auto;"></div>
	</g:form>
</body>
</html>