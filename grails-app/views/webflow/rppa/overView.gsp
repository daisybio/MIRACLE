<%@ page import="org.nanocan.savanah.plates.PlateLayout" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="webflow">
    <g:set var="entityName" value="${message(code: 'overView.label', default: 'Overview')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>
<body>
	<div id="listOfPlateLayouts" style="overflow: auto;">
		<g:render template="listPlateLayouts"
		model="${[plateLayoutInstanceList:rppaFlow.listOfPlateLayouts]}">
		</g:render>
	</div>
	<g:link class="create" action="addPlatelayout"><g:message code="default.new.label" args="[entityName]" default="add Platelayouts"/></g:link>
	<!--
	<div id="listOfSlideLayouts" style="overflow: auto;">
		<g:render template="listSlideLayouts"
		model="${[:]}">
		</g:render>
		
	</div>
	<div id="listOfSlides" style="overflow: auto;">
		<g:render template="listPlates"
		model="${[:]}">
		</g:render>
		<g:render template="addSlide"
		model="${[:]}">
		</g:render>
	</div>
	<div id="listOfSpots" style="overflow: auto;">
		<g:render template="listSpots"
		model="${[:]}">
		</g:render>
		<g:render template="addSpot"
		model="${[:]}">
		</g:render>
	</div>
	<div id="listOfBlockShifts" style="overflow: auto;">
		<g:render template="listBlockShifts"
		model="${[:]}">
		</g:render>
		<g:render template="addBlockShift"
		model="${[:]}">
		</g:render>
	</div>
	<div id="listOfAnalysis" style="overflow: auto;">
		<g:render template="listAnalysis"
		model="${[:]}">
		</g:render>
		<g:render template="addAnalysis"
		model="${[:]}">
		</g:render>
	</div>
	<div id="saveLocalCopy" style="overflow: auto;">
		<g:render template="saveLocalCopy"
		model="${[:]}">
		</g:render>
	</div>
	-->
</body>
</html>