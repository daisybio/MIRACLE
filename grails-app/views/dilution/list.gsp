
<%@ page import="org.nanocan.rppa.layout.Dilution" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dilution.label', default: 'Dilution')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-dilution" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-dilution" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>

                        <g:sortableColumn property="dilutionFactor" title="${message(code: 'dilution.dilutionFactor.label', default: 'Dilution Factor')}" />

						<g:sortableColumn property="color" title="${message(code: 'dilution.color.label', default: 'Color')}" />

					</tr>
				</thead>
				<tbody>
				<g:each in="${dilutionInstanceList}" status="i" var="dilutionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link action="show" id="${dilutionInstance.id}">${dilutionInstance?.dilutionFactor}</g:link></td>

						<td><div id="colorPickDiv" style="float:left; background-color: ${dilutionInstance?.color}; border: 1px solid; width:25px; height:25px;"/></td>

					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${dilutionInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
