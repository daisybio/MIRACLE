
<%@ page import="org.nanocan.plates.Readout; org.nanocan.layout.SlideLayout" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'slideLayout.label', default: 'SlideLayout')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>

        <r:require module="colorPicker"/>

        <r:script>$(function() {
            $("#accordion").accordion({
                collapsible:true,
                autoHeight: false,
                active: 4
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
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </ul>
            </div>
        </div>
    </div>

    <div id="show-slideLayout" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /> ${slideLayoutInstance.title}</h1>
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

                        <g:if test="${slideLayoutInstance?.depositionDirection}">
                            <li class="fieldcontain">
                                <span id="depositionDirection-label" class="property-label"><g:message code="slideLayout.depositionDirection.label" default="Deposition Direction" /></span>

                                <span class="property-value" aria-labelledby="depositionDirection-label"><g:fieldValue bean="${slideLayoutInstance}" field="depositionDirection"/></span>

                            </li>
                        </g:if>

                        <g:if test="${experiments}">
                            <li class="fieldcontain">
                                <span id="experiments-label" class="property-label"><g:message code="slideLayout.experiments.label" default="Experiments" /></span>

                                <span class="property-value" aria-labelledby="experiments-label">
                                    <ul class="property-list">
                                        <g:each in="${experiments}">
                                            <li>
                                                <g:link controller="experiment" action="show" id="${it.id}">${it.title}</g:link>
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
                <h3><a href="#">Slides using this layout</a></h3>
                <div>
                    <ul>
                        <g:each in="${slidesWithThisLayout}" var="slide">
                            <li>
                                <g:link controller="slide" action="show" id="${slide.id}">${slide}</g:link>
                            </li>
                        </g:each>
                    </ul>
                </div>
                <g:set var="plates" value="${slideLayoutInstance.sourcePlates}"/>
                <g:if test="${plates}">
                <h3><a href="#">Created using the following plates</a></h3>
                <div>
                    <ul>
                        <g:each in="${plates}" var="plate">
                            <li>
                                <g:link controller="plate" action="show" id="${plate.id}">${plate}</g:link><br/>
                                Readouts:
                                <ul>
                                    <g:each in="${Readout.findAllByPlate(plate)}" var="readout">
                                        <li>
                                            <g:link controller="readout" action="show" id="${readout.id}">${readout}</g:link>
                                        </li>
                                    </g:each>
                                </ul>
                            </li>
                        </g:each>
                    </ul>
                </div>
                </g:if>
                <h3><a href="#">Copy this layout</a></h3>
                <div>
                    <g:form action="copySlideLayout" name="copySlideLayoutForm">
                        <g:hiddenField name="id" value="${slideLayoutInstance.id}"/>
                        <fieldset class="form">
                            <div class="fieldcontain">
                                <label for="copyName">
                                    <g:message code="slideLayout.experiments.label" default="Name for the copy" />
                                </label>
                                <g:textField name="name" value="${slideLayoutInstance.title} (copy)"/>
                            </div>


                        </fieldset>
                        <fieldset class="buttons">
                            <g:submitButton class="save" name="create copy"/>
                        </fieldset>

                    </g:form>
                </div>
                <h3><a href="#">Spot Properties</a></h3>
                <div>
                    <div>
                            Select a property: <g:select name="sampleProperty" optionKey="key" optionValue="value" value="${sampleProperty}"
                                                         from="${["cellLine":"CellLine", "dilutionFactor":"Dilution Factor", "inducer":"Inducer", "lysisBuffer":"Lysis Buffer", "spotType": "Spot Type", "treatment":"Treatment", "numberOfCellsSeeded":"Number of cells seeded", "sample":"Sample"]}"
                                                         onchange="
                                                   var selectValue = this.value;
                                                   if(unsavedChanges){
                                                             jQuery('#dialog-spot-confirm').dialog({
                                                                 resizable: false,
                                                                 height:140,
                                                                 modal: true,
                                                                 buttons: {
                                                                    'Save now': function() {
                                                                        jQuery.ajax({type:'POST',data:jQuery('#spotPropertiesForm').serialize(), url:'/MIRACLE/slideLayout/updateSpotProperty',success:function(data,textStatus){jQuery('#message').html(data);window.onbeforeunload = null;unsavedChanges=false;},error:function(XMLHttpRequest,textStatus,errorThrown){}});
                                                                        ${remoteFunction(update: 'spotProperties', action:'sampleSpotTable', id: slideLayoutInstance?.id, params: "\'nobanner=true&sampleProperty=\'+selectValue")};
                                                                        jQuery(this).dialog( 'close' );
                                                                    },
                                                                    'Ignore': function() {
                                                                        unsavedChanges = false;
                                                                        ${remoteFunction(update: 'spotProperties', action:'sampleSpotTable', id: slideLayoutInstance?.id, params: "\'nobanner=true&sampleProperty=\'+selectValue")};
                                                                        jQuery( this ).dialog( 'close' );
                                                                    }
                                                               }
                                                            });
                                                   }
                                                   else ${remoteFunction(update: 'spotProperties', action:'sampleSpotTable', id: slideLayoutInstance?.id, params: "\'nobanner=true&sampleProperty=\'+selectValue")};"
                        />
                    </div>
                    <div id="dialog-spot-confirm" title="You have unsaved changes!" style="display: none;">
                        <p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>You have unsaved changes. What do you want to do?</p>
                    </div>
                    <div id="spotProperties">
                        <g:include action="sampleSpotTable" id="${slideLayoutInstance?.id}" params="${[sampleProperty: 'cellLine']}"/>
                    </div>
                </div>
            </div>
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
