
<%@ page import="org.nanocan.savanah.experiment.Experiment" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'experiment.label', default: 'Experiment')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-experiment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="list-experiment" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'experiment.name.label', default: 'Name')}" />
					
						<th><g:message code="experiment.experimenter.label" default="Experimenter" /></th>
					
						<g:sortableColumn property="dateOfExperiment" title="${message(code: 'experiment.dateOfExperiment.label', default: 'Date Of Experiment')}" />
					
						<th><g:message code="experiment.createdBy.label" default="Created By" /></th>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'experiment.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'experiment.lastUpdated.label', default: 'Last Updated')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${experimentInstanceList}" status="i" var="experimentInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${experimentInstance.id}">${fieldValue(bean: experimentInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: experimentInstance, field: "experimenter")}</td>
					
						<td><g:formatDate date="${experimentInstance.dateOfExperiment}" /></td>
					
						<td>${fieldValue(bean: experimentInstance, field: "createdBy")}</td>
					
						<td><g:formatDate date="${experimentInstance.dateCreated}" /></td>
					
						<td><g:formatDate date="${experimentInstance.lastUpdated}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${experimentInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
