<%@ page
	import="org.nanocan.rppa.scanner.Slide; org.nanocan.rppa.layout.SlideLayout"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="webflow">
<g:set var="entityName"
	value="${message(code: 'slideLayout.label', default: 'SlideLayout')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>

<r:require module="colorPicker" />

<r:script>$(function() {
            $("#accordion").accordion({
                collapsible:true,
                autoHeight: false
            });

        });</r:script>
</head>
<body>
	<a href="#show-slideLayout" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>

	<div id="show-slideLayout" class="content scaffold-show" role="main">
		<h1>
			<g:message code="default.show.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>

		<div id="accordion" style="margin: 25px; width: 90%;">
			<h3>
				<a href="#">Properties</a>
			</h3>
			<div>
				<ol class="property-list slideLayout">

					<g:if test="${slideLayoutInstance?.title}">
						<li class="fieldcontain"><span id="title-label"
							class="property-label"><g:message
									code="slideLayout.title.label" default="Title" /></span> <span
							class="property-value" aria-labelledby="title-label"><g:fieldValue
									bean="${slideLayoutInstance}" field="title" /></span></li>
					</g:if>

					<g:if test="${slideLayoutInstance?.columnsPerBlock}">
						<li class="fieldcontain"><span id="columnsPerBlock-label"
							class="property-label"><g:message
									code="slideLayout.columnsPerBlock.label"
									default="Columns Per Block" /></span> <span class="property-value"
							aria-labelledby="columnsPerBlock-label"><g:fieldValue
									bean="${slideLayoutInstance}" field="columnsPerBlock" /></span></li>
					</g:if>

					<g:if test="${slideLayoutInstance?.rowsPerBlock}">
						<li class="fieldcontain"><span id="rowsPerBlock-label"
							class="property-label"><g:message
									code="slideLayout.rowsPerBlock.label" default="Rows Per Block" /></span>

							<span class="property-value" aria-labelledby="rowsPerBlock-label"><g:fieldValue
									bean="${slideLayoutInstance}" field="rowsPerBlock" /></span></li>
					</g:if>

					<g:if test="${slideLayoutInstance?.numberOfBlocks}">
						<li class="fieldcontain"><span id="numberOfBlocks-label"
							class="property-label"><g:message
									code="slideLayout.numberOfBlocks.label"
									default="Number Of Blocks" /></span> <span class="property-value"
							aria-labelledby="numberOfBlocks-label"><g:fieldValue
									bean="${slideLayoutInstance}" field="numberOfBlocks" /></span></li>
					</g:if>

					<g:if test="${slideLayoutInstance?.blocksPerRow}">
						<li class="fieldcontain"><span id="blocksPerRow-label"
							class="property-label"><g:message
									code="slideLayout.blocksPerRow.label" default="Blocks PerRow" /></span>

							<span class="property-value" aria-labelledby="blocksPerRow-label"><g:fieldValue
									bean="${slideLayoutInstance}" field="blocksPerRow" /></span></li>
					</g:if>

					<g:if test="${slideLayoutInstance?.depositionPattern}">
						<li class="fieldcontain"><span id="depositionPattern-label"
							class="property-label"><g:message
									code="slideLayout.depositionPattern.label"
									default="Deposition Pattern" /></span> <span class="property-value"
							aria-labelledby="depositionPattern-label"><g:fieldValue
									bean="${slideLayoutInstance}" field="depositionPattern" /></span></li>
					</g:if>
				</ol>
			</div>
			<h3>
				<a href="#">Spot Properties</a>
			</h3>
			<div>
				<div>
					Select a property:
					<g:select name="sampleProperty" optionKey="key" optionValue="value"
						value="${sampleProperty}"
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
                                                   else ${remoteFunction(update: 'spotProperties', action:'sampleSpotTable', id: slideLayoutInstance?.id, params: "\'nobanner=true&sampleProperty=\'+selectValue")};" />
				</div>
				<div id="dialog-spot-confirm" title="You have unsaved changes!"
					style="display: none;">
					<p>
						<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"></span>You have
						unsaved changes. What do you want to do?
					</p>
				</div>
				<div id="spotProperties">
				<g:render template="slideLayoutSampleSpotTable" model="${[slideLayout:slideLayoutInstance]}"/>
				</div>
			</div>
		</div>
		<g:form>
			<fieldset class="buttons">
				<g:hiddenField name="id" value="${slideLayoutInstance?.id}" />
				<g:link class="edit" action="edit" id="${slideLayoutInstance?.id}">
					<g:message code="default.button.edit.label" default="Edit" />
				</g:link>
				<g:actionSubmit class="delete" action="delete"
					value="${message(code: 'default.button.delete.label', default: 'Delete')}"
					onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
			</fieldset>
		</g:form>
	</div>
</body>
</html>
