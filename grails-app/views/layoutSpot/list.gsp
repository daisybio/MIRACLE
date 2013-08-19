
<%@ page import="org.nanocan.rppa.layout.LayoutSpot" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'layoutSpot.label', default: 'LayoutSpot')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-layoutSpot" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
            <div class="navbar">
                <div class="navbar-inner">
                    <div class="container">
                        <ul class="nav">
                            <g:render template="/templates/navmenu"></g:render>
                            <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                        </ul>
                    </div>
                </div>
            </div>
		<div id="list-layoutSpot" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="layoutSpot.numberOfCellsSeeded.label" default="Number Of Cells Seeded" /></th>
					
						<th><g:message code="layoutSpot.cellLine.label" default="Cell Line" /></th>
					
						<th><g:message code="layoutSpot.lysisBuffer.label" default="Lysis Buffer" /></th>
					
						<th><g:message code="layoutSpot.dilutionFactor.label" default="Dilution Factor" /></th>
					
						<th><g:message code="layoutSpot.inducer.label" default="Inducer" /></th>
					
						<th><g:message code="layoutSpot.sample.label" default="Sample" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${layoutSpotInstanceList}" status="i" var="layoutSpotInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${layoutSpotInstance.id}">${fieldValue(bean: layoutSpotInstance, field: "numberOfCellsSeeded")}</g:link></td>
					
						<td>${fieldValue(bean: layoutSpotInstance, field: "cellLine")}</td>
					
						<td>${fieldValue(bean: layoutSpotInstance, field: "lysisBuffer")}</td>
					
						<td>${fieldValue(bean: layoutSpotInstance, field: "dilutionFactor")}</td>
					
						<td>${fieldValue(bean: layoutSpotInstance, field: "inducer")}</td>
					
						<td>${fieldValue(bean: layoutSpotInstance, field: "sample")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${layoutSpotInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
