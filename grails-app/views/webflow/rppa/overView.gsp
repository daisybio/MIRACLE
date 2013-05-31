<%@ page import="org.nanocan.rppa.layout.PlateLayout"%>
<%@ page import="org.nanocan.rppa.layout.SlideLayout"%>

<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'overView.label', default: 'Overview')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>
<body>
<g:form action="rppa">
	<div id="listOfPlateLayouts" style="overflow: auto;">
		<g:render template="listPlateLayouts" model="${[plateLayoutInstanceList: listOfPlateLayouts?.values()?.asList(), plateLayoutInstanceTotal: listOfPlateLayouts?.values()?.asList()?.size()?:0]}"/>
	</div>

	<g:submitButton class="create" name="addPlatelayouts" value="Add plate layout" />

	<div id="listOfSlideLayouts" style="overflow: auto;">
		<g:render template="listSlideLayout" model="${[slideLayoutInstanceList:listOfSlideLayouts,slideLayoutInstanceTotal: listOfSlideLayouts?.size()?:0] }"/>
	</div>
	
	<g:submitButton class="create" name="addSlidelayout" value="Add slide layout"/>
	
	<div id="listOfSlides" style="overflow: auto;">
		<g:render template="listSlides" model="${[slideInsatanceList:listOfSlides,slideInstanceTotal:listOfSlides?.size()?:0] }"/>
	</div>
	
	<g:submitButton class="create" name="addSlide" value="Add slide"/>
	
	<div id="listOfSpots" style="overflow: auto;">
		<g:render template="listSpots" model="${[spotTypeInstanceList:[],spotTypeInstanceTotal:0] }"/>
	</div>
	
	<g:submitButton class="create" name="addSpot" value="Add Spot"/>
	
	<div id="listOfBlockShifts" style="overflow: auto;">
		<g:render template="listBlockShifts"/>
	</div>
	
	<div id="listOfAnalysis" style="overflow: auto;">
		<g:render template="listAnalysis"/>
	</div>
	
	<div id="saveLocalCopy" style="overflow: auto;">

	</div>
</g:form>
</body>
</html>