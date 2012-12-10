
<%@ page import="org.nanocan.rppa.project.Experiment" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'experiment.label', default: 'Experiment')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-experiment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="show-experiment" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list experiment">
			
				<g:if test="${experimentInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="experiment.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${experimentInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${experimentInstance?.createdBy}">
				<li class="fieldcontain">
					<span id="createdBy-label" class="property-label"><g:message code="experiment.createdBy.label" default="Created By" /></span>
					
						<span class="property-value" aria-labelledby="createdBy-label"><g:link controller="person" action="show" id="${experimentInstance?.createdBy?.id}">${experimentInstance?.createdBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${experimentInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="experiment.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${experimentInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${experimentInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="experiment.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${experimentInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${experimentInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="experiment.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${experimentInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${experimentInstance?.lastUpdatedBy}">
				<li class="fieldcontain">
					<span id="lastUpdatedBy-label" class="property-label"><g:message code="experiment.lastUpdatedBy.label" default="Last Updated By" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdatedBy-label"><g:link controller="person" action="show" id="${experimentInstance?.lastUpdatedBy?.id}">${experimentInstance?.lastUpdatedBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${experimentInstance?.layouts}">
				<li class="fieldcontain">
					<span id="layouts-label" class="property-label"><g:message code="experiment.layouts.label" default="Layouts" /></span>
					
						<g:each in="${experimentInstance.layouts}" var="l">
						<span class="property-value" aria-labelledby="layouts-label"><g:link controller="slideLayout" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${experimentInstance?.plateLayouts}">
				<li class="fieldcontain">
					<span id="plateLayouts-label" class="property-label"><g:message code="experiment.plateLayouts.label" default="Plate Layouts" /></span>
					
						<g:each in="${experimentInstance.plateLayouts}" var="p">
						<span class="property-value" aria-labelledby="plateLayouts-label"><g:link controller="plateLayout" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${experimentInstance?.project}">
				<li class="fieldcontain">
					<span id="project-label" class="property-label"><g:message code="experiment.project.label" default="Project" /></span>
					
						<span class="property-value" aria-labelledby="project-label"><g:link controller="project" action="show" id="${experimentInstance?.project?.id}">${experimentInstance?.project?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${experimentInstance?.slides}">
				<li class="fieldcontain">
					<span id="slides-label" class="property-label"><g:message code="experiment.slides.label" default="Slides" /></span>
					
						<g:each in="${experimentInstance.slides}" var="s">
						<span class="property-value" aria-labelledby="slides-label"><g:link controller="slide" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${experimentInstance?.id}" />
					<g:link class="edit" action="edit" id="${experimentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
