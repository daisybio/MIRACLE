
<%@ page import="org.nanocan.savanah.experiment.Experiment" %>
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
			
				<g:if test="${experimentInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="experiment.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${experimentInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${experimentInstance?.experimenter}">
				<li class="fieldcontain">
					<span id="experimenter-label" class="property-label"><g:message code="experiment.experimenter.label" default="Experimenter" /></span>
					
						<span class="property-value" aria-labelledby="experimenter-label"><g:link controller="person" action="show" id="${experimentInstance?.experimenter?.id}">${experimentInstance?.experimenter?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${experimentInstance?.dateOfExperiment}">
				<li class="fieldcontain">
					<span id="dateOfExperiment-label" class="property-label"><g:message code="experiment.dateOfExperiment.label" default="Date Of Experiment" /></span>
					
						<span class="property-value" aria-labelledby="dateOfExperiment-label"><g:formatDate date="${experimentInstance?.dateOfExperiment}" /></span>
					
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
			
				<g:if test="${experimentInstance?.plates}">
				<li class="fieldcontain">
					<span id="plates-label" class="property-label"><g:message code="experiment.plates.label" default="Plates" /></span>
					
						<g:each in="${experimentInstance.plates}" var="p">
						<span class="property-value" aria-labelledby="plates-label"><g:link controller="plate" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
		</div>
	</body>
</html>
