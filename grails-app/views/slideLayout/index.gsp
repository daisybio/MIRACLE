
<%@ page import="org.nanocan.layout.SlideLayout" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'slideLayout.label', default: 'SlideLayout')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-slideLayout" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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



    <div id="list-slideLayout" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="title" title="${message(code: 'slideLayout.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="columnsPerBlock" title="${message(code: 'slideLayout.columnsPerBlock.label', default: 'Columns Per Block')}" />
					
						<g:sortableColumn property="rowsPerBlock" title="${message(code: 'slideLayout.rowsPerBlock.label', default: 'Rows Per Block')}" />
					
						<g:sortableColumn property="numberOfBlocks" title="${message(code: 'slideLayout.numberOfBlocks.label', default: 'Number Of Blocks')}" />
					
						<g:sortableColumn property="depositionPattern" title="${message(code: 'slideLayout.depositionPattern.label', default: 'Deposition Pattern')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${slideLayoutInstanceList}" status="i" var="slideLayoutInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${slideLayoutInstance.id}">${fieldValue(bean: slideLayoutInstance, field: "title")}</g:link></td>
					
						<td>${fieldValue(bean: slideLayoutInstance, field: "columnsPerBlock")}</td>
					
						<td>${fieldValue(bean: slideLayoutInstance, field: "rowsPerBlock")}</td>
					
						<td>${fieldValue(bean: slideLayoutInstance, field: "numberOfBlocks")}</td>
					
						<td>${fieldValue(bean: slideLayoutInstance, field: "depositionPattern")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">        ${controller}
				<g:paginate total="${slideLayoutInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
