
<%@ page import="org.nanocan.rppa.layout.SlideLayout" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'slideLayout.label', default: 'SlideLayout')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>

        <r:script>$(function() {
            $("#accordion").accordion({
                collapsible:true,
                autoHeight: false
            });

        });</r:script>
	</head>
	<body>
		<a href="#show-slideLayout" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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

    <div id="show-slideLayout" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>

            <div id="accordion" style="margin:25px; width:90%;">
            <h3><a href="#">Properties</a></h3>
                <div>
                    <ol class="property-list slideLayout">

                        <g:if test="${slideLayoutInstance?.title}">
                        <li class="fieldcontain">
                            <span id="title-label" class="property-label"><g:message code="slideLayout.title.label" default="Title" /></span>

                                <span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${slideLayoutInstance}" field="title"/></span>

                        </li>
                        </g:if>

                        <g:if test="${slideLayoutInstance?.columnsPerBlock}">
                        <li class="fieldcontain">
                            <span id="columnsPerBlock-label" class="property-label"><g:message code="slideLayout.columnsPerBlock.label" default="Columns Per Block" /></span>

                                <span class="property-value" aria-labelledby="columnsPerBlock-label"><g:fieldValue bean="${slideLayoutInstance}" field="columnsPerBlock"/></span>

                        </li>
                        </g:if>

                        <g:if test="${slideLayoutInstance?.rowsPerBlock}">
                        <li class="fieldcontain">
                            <span id="rowsPerBlock-label" class="property-label"><g:message code="slideLayout.rowsPerBlock.label" default="Rows Per Block" /></span>

                                <span class="property-value" aria-labelledby="rowsPerBlock-label"><g:fieldValue bean="${slideLayoutInstance}" field="rowsPerBlock"/></span>

                        </li>
                        </g:if>

                        <g:if test="${slideLayoutInstance?.numberOfBlocks}">
                        <li class="fieldcontain">
                            <span id="numberOfBlocks-label" class="property-label"><g:message code="slideLayout.numberOfBlocks.label" default="Number Of Blocks" /></span>

                                <span class="property-value" aria-labelledby="numberOfBlocks-label"><g:fieldValue bean="${slideLayoutInstance}" field="numberOfBlocks"/></span>

                        </li>
                        </g:if>

                        <g:if test="${slideLayoutInstance?.blocksPerRow}">
                            <li class="fieldcontain">
                                <span id="blocksPerRow-label" class="property-label"><g:message code="slideLayout.blocksPerRow.label" default="Blocks PerRow" /></span>

                                <span class="property-value" aria-labelledby="blocksPerRow-label"><g:fieldValue bean="${slideLayoutInstance}" field="blocksPerRow"/></span>

                            </li>
                        </g:if>

                        <g:if test="${slideLayoutInstance?.depositionPattern}">
                        <li class="fieldcontain">
                            <span id="depositionPattern-label" class="property-label"><g:message code="slideLayout.depositionPattern.label" default="Deposition Pattern" /></span>

                                <span class="property-value" aria-labelledby="depositionPattern-label"><g:fieldValue bean="${slideLayoutInstance}" field="depositionPattern"/></span>

                        </li>
                        </g:if>

                        <g:if test="${projects}">
                            <li class="fieldcontain">
                                <span id="projects-label" class="property-label"><g:message code="slideLayout.projects.label" default="Projects" /></span>

                                <span class="property-value" aria-labelledby="projects-label">
                                    <ul class="property-list">
                                        <g:each in="${projects}">
                                            <li>
                                                <g:link controller="project" action="show" id="${it.id}">${it.projectTitle}</g:link>
                                            </li>
                                        </g:each>
                                    </ul>
                                </span>
                            </li>
                        </g:if>
                    </ol>
                </div>

                <h3><a href="#">History</a></h3>
                <div>
                    <g:render template="/templates/history" model="[
                            createdBy: slideLayoutInstance.createdBy,
                            dateCreated: slideLayoutInstance.dateCreated,
                            lastUpdated: slideLayoutInstance.lastUpdated,
                            lastUpdatedBy: slideLayoutInstance.lastUpdatedBy]"/>
                </div>
            </div>

            <h1>Change Spot Properties</h1>
            <ul style="padding:20px;padding-left:100px;">
                <li><g:link action="sampleSpotTable" id="${slideLayoutInstance?.id}" params="${[sampleProperty: 'cellLine']}"> Cellline </g:link></li>
                <li><g:link action="sampleSpotTable" id="${slideLayoutInstance?.id}" params="${[sampleProperty: 'dilutionFactor']}"> Dilution </g:link></li>
                <li><g:link action="sampleSpotTable" id="${slideLayoutInstance?.id}" params="${[sampleProperty: 'inducer']}"> Inducer </g:link></li>
                <li><g:link action="sampleSpotTable" id="${slideLayoutInstance?.id}" params="${[sampleProperty: 'lysisBuffer']}"> Lysis Buffer </g:link></li>
                <li><g:link action="sampleSpotTable" id="${slideLayoutInstance?.id}" params="${[sampleProperty: 'sample']}"> Sample Information </g:link></li>
                <li><g:link action="sampleSpotTable" id="${slideLayoutInstance?.id}" params="${[sampleProperty: 'spotType']}"> Spot Type </g:link></li>
                <li><g:link action="sampleSpotTable" id="${slideLayoutInstance?.id}" params="${[sampleProperty: 'treatment']}"> Treatment </g:link></li>
            </ul>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${slideLayoutInstance?.id}" />
					<g:link class="edit" action="edit" id="${slideLayoutInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
