
<%@ page import="org.nanocan.rppa.layout.LayoutSpot" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'layoutSpot.label', default: 'LayoutSpot')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-layoutSpot" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="show-layoutSpot" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list layoutSpot">
			
				<g:if test="${layoutSpotInstance?.numberOfCellsSeeded}">
				<li class="fieldcontain">
					<span id="numberOfCellsSeeded-label" class="property-label"><g:message code="layoutSpot.numberOfCellsSeeded.label" default="Number Of Cells Seeded" /></span>
					
						<span class="property-value" aria-labelledby="numberOfCellsSeeded-label"><g:link controller="numberOfCellsSeeded" action="show" id="${layoutSpotInstance?.numberOfCellsSeeded?.id}">${layoutSpotInstance?.numberOfCellsSeeded?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${layoutSpotInstance?.cellLine}">
				<li class="fieldcontain">
					<span id="cellLine-label" class="property-label"><g:message code="layoutSpot.cellLine.label" default="Cell Line" /></span>
					
						<span class="property-value" aria-labelledby="cellLine-label"><g:link controller="cellLine" action="show" id="${layoutSpotInstance?.cellLine?.id}">${layoutSpotInstance?.cellLine?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${layoutSpotInstance?.lysisBuffer}">
				<li class="fieldcontain">
					<span id="lysisBuffer-label" class="property-label"><g:message code="layoutSpot.lysisBuffer.label" default="Lysis Buffer" /></span>
					
						<span class="property-value" aria-labelledby="lysisBuffer-label"><g:link controller="lysisBuffer" action="show" id="${layoutSpotInstance?.lysisBuffer?.id}">${layoutSpotInstance?.lysisBuffer?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${layoutSpotInstance?.dilutionFactor}">
				<li class="fieldcontain">
					<span id="dilutionFactor-label" class="property-label"><g:message code="layoutSpot.dilutionFactor.label" default="Dilution Factor" /></span>
					
						<span class="property-value" aria-labelledby="dilutionFactor-label"><g:link controller="dilution" action="show" id="${layoutSpotInstance?.dilutionFactor?.id}">${layoutSpotInstance?.dilutionFactor?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${layoutSpotInstance?.inducer}">
				<li class="fieldcontain">
					<span id="inducer-label" class="property-label"><g:message code="layoutSpot.inducer.label" default="Inducer" /></span>
					
						<span class="property-value" aria-labelledby="inducer-label"><g:link controller="inducer" action="show" id="${layoutSpotInstance?.inducer?.id}">${layoutSpotInstance?.inducer?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${layoutSpotInstance?.sample}">
				<li class="fieldcontain">
					<span id="sample-label" class="property-label"><g:message code="layoutSpot.sample.label" default="Sample" /></span>
					
						<span class="property-value" aria-labelledby="sample-label"><g:link controller="sample" action="show" id="${layoutSpotInstance?.sample?.id}">${layoutSpotInstance?.sample?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${layoutSpotInstance?.spotType}">
				<li class="fieldcontain">
					<span id="spotType-label" class="property-label"><g:message code="layoutSpot.spotType.label" default="Spot Type" /></span>
					
						<span class="property-value" aria-labelledby="spotType-label"><g:link controller="spotType" action="show" id="${layoutSpotInstance?.spotType?.id}">${layoutSpotInstance?.spotType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${layoutSpotInstance?.treatment}">
				<li class="fieldcontain">
					<span id="treatment-label" class="property-label"><g:message code="layoutSpot.treatment.label" default="Treatment" /></span>
					
						<span class="property-value" aria-labelledby="treatment-label"><g:link controller="treatment" action="show" id="${layoutSpotInstance?.treatment?.id}">${layoutSpotInstance?.treatment?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${layoutSpotInstance?.block}">
				<li class="fieldcontain">
					<span id="block-label" class="property-label"><g:message code="layoutSpot.block.label" default="Block" /></span>
					
						<span class="property-value" aria-labelledby="block-label"><g:fieldValue bean="${layoutSpotInstance}" field="block"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${layoutSpotInstance?.col}">
				<li class="fieldcontain">
					<span id="col-label" class="property-label"><g:message code="layoutSpot.col.label" default="Col" /></span>
					
						<span class="property-value" aria-labelledby="col-label"><g:fieldValue bean="${layoutSpotInstance}" field="col"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${layoutSpotInstance?.layout}">
				<li class="fieldcontain">
					<span id="layout-label" class="property-label"><g:message code="layoutSpot.layout.label" default="Layout" /></span>
					
						<span class="property-value" aria-labelledby="layout-label"><g:link controller="slideLayout" action="show" id="${layoutSpotInstance?.layout?.id}">${layoutSpotInstance?.layout?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${layoutSpotInstance?.row}">
				<li class="fieldcontain">
					<span id="row-label" class="property-label"><g:message code="layoutSpot.row.label" default="Row" /></span>
					
						<span class="property-value" aria-labelledby="row-label"><g:fieldValue bean="${layoutSpotInstance}" field="row"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${layoutSpotInstance?.id}" />
					<g:link class="edit" action="edit" id="${layoutSpotInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
