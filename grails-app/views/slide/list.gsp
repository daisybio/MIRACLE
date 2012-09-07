
<%@ page import="org.nanocan.rppa.scanner.Slide" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'slide.label', default: 'Slide')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-slide" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="list-slide" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
                        <th><g:message code="slide.title.label" default="Title" /></th>
					
						<th><g:message code="slide.antibody.label" default="Antibody" /></th>
					
						<g:sortableColumn property="dateOfStaining" title="${message(code: 'slide.dateOfStaining.label', default: 'Date Of Staining')}" />
					
						<th><g:message code="slide.experimenter.label" default="Experimenter" /></th>
					
						<g:sortableColumn property="laserWavelength" title="${message(code: 'slide.laserWavelength.label', default: '~')}" />

                        <g:sortableColumn property="photoMultiplierTube" title="${message(code: 'slide.photoMultiplierTube.label', default: 'PMT')}" />
					
						<th><g:message code="slide.resultFile.label" default="Result File" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${slideInstanceList}" status="i" var="slideInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                        <td><g:link action="show" id="${slideInstance.id}">${slideInstance?.toString()}</g:link></td>

						<td>${fieldValue(bean: slideInstance, field: "antibody")}</td>
					
						<td><g:formatDate type="date" date="${slideInstance.dateOfStaining}" /></td>
					
						<td>${fieldValue(bean: slideInstance, field: "experimenter")}</td>

						<td>${fieldValue(bean: slideInstance, field: "laserWavelength")}</td>

                        <td>${fieldValue(bean: slideInstance, field: "photoMultiplierTube")}</td>
					
						<td>${fieldValue(bean: slideInstance, field: "resultFile")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${slideInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
