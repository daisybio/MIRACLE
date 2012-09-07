
<%@ page import="org.nanocan.rppa.project.Project" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>

        <r:script>$(function() {
            $("#accordion").accordion({
                collapsible:true,
                autoHeight: false
            });

        });</r:script>
	</head>
	<body>
		<a href="#show-project" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">
                <ul class="nav">
                    <g:render template="/templates/navmenu"></g:render>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </ul>
            </div>
        </div>
    </div>
		<div id="show-project" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>

            <div id="accordion" style="margin: 25px; width: 90%;">
                <h3><a href="#">Properties</a></h3>
                <div>
                    <ol class="property-list project">

                        <g:if test="${projectInstance?.projectTitle}">
                            <li class="fieldcontain">
                                <span id="projectTitle-label" class="property-label"><g:message code="project.projectTitle.label" default="Project Title" /></span>

                                <span class="property-value" aria-labelledby="projectTitle-label"><g:fieldValue bean="${projectInstance}" field="projectTitle"/></span>

                            </li>
                        </g:if>

                        <g:if test="${projectInstance?.projectDescription}">
                        <li class="fieldcontain">
                            <span id="projectDescription-label" class="property-label"><g:message code="project.projectDescription.label" default="Project Description" /></span>

                                <span class="property-value" aria-labelledby="projectDescription-label"><g:fieldValue bean="${projectInstance}" field="projectDescription"/></span>

                        </li>
                        </g:if>

                        <g:if test="${projectInstance?.layouts}">
                            <li class="fieldcontain">
                                <span id="layouts-label" class="property-label"><g:message code="project.layouts.label" default="Layouts" /></span>

                                <g:each in="${projectInstance.layouts}" var="l">
                                    <span class="property-value" aria-labelledby="layouts-label"><g:link controller="slideLayout" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></span>
                                </g:each>

                            </li>
                        </g:if>

                        <g:if test="${projectInstance?.slides}">
                        <li class="fieldcontain">
                            <span id="slides-label" class="property-label"><g:message code="project.slides.label" default="Slides" /></span>

                                <g:each in="${projectInstance.slides}" var="s">
                                <span class="property-value" aria-labelledby="slides-label"><g:link controller="slide" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
                                </g:each>

                        </li>
                        </g:if>

                    </ol>
            </div>
            <h3><a href="#">History</a></h3>
            <div>
                <ol class="property-list">
                <g:if test="${projectInstance?.createdBy}">
                    <li class="fieldcontain">
                        <span id="createdBy-label" class="property-label"><g:message code="project.createdBy.label" default="Created By" /></span>

                        <span class="property-value" aria-labelledby="createdBy-label"><g:link controller="person" action="show" id="${projectInstance?.createdBy?.id}">${projectInstance?.createdBy?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${projectInstance?.dateCreated}">
                    <li class="fieldcontain">
                        <span id="dateCreated-label" class="property-label"><g:message code="project.dateCreated.label" default="Date Created" /></span>

                        <span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${projectInstance?.dateCreated}" /></span>

                    </li>
                </g:if>

                <g:if test="${projectInstance?.lastUpdatedBy}">
                    <li class="fieldcontain">
                        <span id="lastUpdatedBy-label" class="property-label"><g:message code="project.lastUpdatedBy.label" default="Last Updated By" /></span>

                        <span class="property-value" aria-labelledby="lastUpdatedBy-label"><g:link controller="person" action="show" id="${projectInstance?.lastUpdatedBy?.id}">${projectInstance?.lastUpdatedBy?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${projectInstance?.lastUpdated}">
                    <li class="fieldcontain">
                        <span id="lastUpdated-label" class="property-label"><g:message code="project.lastUpdated.label" default="Last Updated" /></span>

                        <span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${projectInstance?.lastUpdated}" /></span>

                    </li>
                </g:if>
                </ol>
            </div>
        </div>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${projectInstance?.id}" />
					<g:link class="edit" action="edit" id="${projectInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
