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
		<g:render template="listPlateLayouts" model="${[plateLayoutInstanceList: [], plateLayoutInstanceTotal: 0]}"/>
	</div>

	<g:submitButton class="create" name="addPlatelayout" value="Add plate layout"/>

	<div id="listOfSlideLayouts" style="overflow: auto;">
		<g:render template="listSlideLayout"/>

	</div>
	<div id="listOfSlides" style="overflow: auto;">
		<g:render template="listPlates"/>
	</div>
	<div id="listOfSpots" style="overflow: auto;">
		<g:render template="listSpots"/>
	</div>
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