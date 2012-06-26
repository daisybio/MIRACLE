
<%@ page import="org.nanocan.rppa.scanner.Slide" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'slide.label', default: 'Slide')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>

        <g:set var="spotsDetected" value="${slideInstance.spots.size()}"/>


        <g:if test="${spotsDetected == 0}">
            <r:script>
                $(function() {
                     $( "#dialog-confirm" ).dialog({
                        position: 'top',
                        modal: true,
                        resizable: false,
                        height:140,
                        modal: true,
                        buttons: {
                            "Yes, add spots": function() {
                            window.location = "<g:createLink controller="slide" action="addSpots" id="${slideInstance.id}"></g:createLink>";
                            },
                            "No, thanks": function() {
                            $( this ).dialog( "close" );
                            }
                        }
                    });
                });
            </r:script>
        </g:if>
        <g:elseif test="${!imagezoomFolderExists && slideInstance?.resultImage}">
            <r:script>
                $(function() {
                     $( "#image-process-confirm" ).dialog({
                        position: 'top',
                        modal: true,
                        resizable: false,
                        height:230,
                        modal: true,
                        buttons: {
                            "Yes, process image now": function() {
                                $('#slideZoomPanel').text('Image is being processed in the background...');
                                <g:remoteFunction action="zoomifyImage" id="${slideInstance.id}" update="slideZoomPanel"/>;
                                $( this ).dialog( "close" );
                                $('#image-process-confirm').hide(true);
                            },
                            "No, thanks": function() {
                            $( this ).dialog( "close" );
                            }
                        }
                    });
                });
            </r:script>
        </g:elseif>

        <r:script>$(function() {
            $("#accordion").accordion({
                collapsible:true,
                autoHeight: false
            });

        });</r:script>

	</head>
	<body>
		<a href="#show-slide" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

    <g:if test="${spotsDetected == 0}">
        <div id="dialog-confirm" title="Would you like to add spots?">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>The result file for this slide has not yet been processed. Proceed now?</p>
        </div>
    </g:if>

    <g:elseif test="${!imagezoomFolderExists && slideInstance?.resultImage}">
        <div id="image-process-confirm" title="Would you like to process the image file?">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>The image file for this slide has not yet been processed. Proceed now?</p>
        </div>
    </g:elseif>

        <div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>

                <li><g:link class="create" action="addBlockShifts" id="${slideInstance?.id}">Modify Block Shifts</g:link></li>
                <li><g:link class="list" controller="spotExport" action="exportAsCSV" id="${slideInstance?.id}">Export (R / CSV)</g:link></li>
			</ul>
		</div>
		<div id="show-slide" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>

            <div id="accordion" style="margin: 25px; width: 90%;">
                <h3><a href="#">Properties</a></h3>
                <div>
                    <ol class="property-list slide">

                        <g:if test="${slideInstance?.antibody}">
                        <li class="fieldcontain">
                            <span id="antibody-label" class="property-label"><g:message code="slide.antibody.label" default="Antibody" /></span>

                                <span class="property-value" aria-labelledby="antibody-label"><g:link controller="antibody" action="show" id="${slideInstance?.antibody?.id}">${slideInstance?.antibody?.encodeAsHTML()}</g:link></span>

                        </li>
                        </g:if>

                        <g:if test="${slideInstance?.layout}">
                            <li class="fieldcontain">
                                <span id="layout-label" class="property-label"><g:message code="slide.layout.label" default="Layout" /></span>

                                <span class="property-value" aria-labelledby="layout-label"><g:link controller="slideLayout" action="show" id="${slideInstance?.layout?.id}">${slideInstance?.layout?.encodeAsHTML()}</g:link></span>

                            </li>
                        </g:if>

                        <g:if test="${slideInstance?.dateOfStaining}">
                        <li class="fieldcontain">
                            <span id="dateOfStaining-label" class="property-label"><g:message code="slide.dateOfStaining.label" default="Date Of Staining" /></span>

                                <span class="property-value" aria-labelledby="dateOfStaining-label"><g:formatDate type="date" date="${slideInstance?.dateOfStaining}" /></span>

                        </li>
                        </g:if>

                        <g:if test="${slideInstance?.experimenter}">
                        <li class="fieldcontain">
                            <span id="experimenter-label" class="property-label"><g:message code="slide.experimenter.label" default="Experimenter" /></span>

                                <span class="property-value" aria-labelledby="experimenter-label"><g:link controller="Person" action="show" id="${slideInstance?.experimenter?.id}">${slideInstance?.experimenter?.encodeAsHTML()}</g:link></span>

                        </li>
                        </g:if>

                        <g:if test="${slideInstance?.laserWavelength}">
                        <li class="fieldcontain">
                            <span id="laserWavelength-label" class="property-label"><g:message code="slide.laserWavelength.label" default="Laser Wavelength (nm)" /></span>

                                <span class="property-value" aria-labelledby="laserWavelength-label"><g:fieldValue bean="${slideInstance}" field="laserWavelength"/></span>

                        </li>
                        </g:if>

                        <g:if test="${slideInstance?.photoMultiplierTube}">
                            <li class="fieldcontain">
                                <span id="photoMultiplierTube-label" class="property-label"><g:message code="slide.photoMultiplierTube.label" default="Laser Wavelength" /></span>

                                <span class="property-value" aria-labelledby="photoMultiplierTube-label"><g:fieldValue bean="${slideInstance}" field="photoMultiplierTube"/></span>

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

                        <g:if test="${slideInstance?.resultFile}">
                        <li class="fieldcontain">
                            <span id="resultFile-label" class="property-label"><g:message code="slide.resultFile.label" default="Result File" /></span>

                                <span class="property-value" aria-labelledby="resultFile-label"><g:link controller="resultFile" action="show" id="${slideInstance?.resultFile?.id}">${slideInstance?.resultFile?.encodeAsHTML()}</g:link></span>

                        </li>
                        </g:if>

                        <g:if test="${slideInstance?.resultImage}">
                        <li class="fieldcontain">
                            <span id="resultImage-label" class="property-label"><g:message code="slide.resultImage.label" default="Result Image" /></span>

                                <span class="property-value" aria-labelledby="resultImage-label"><g:link controller="resultFile" action="show" id="${slideInstance?.resultImage?.id}">${slideInstance?.resultImage?.encodeAsHTML()}</g:link></span>

                        </li>
                        </g:if>

                        <g:if test="${slideInstance?.protocol}">
                            <li class="fieldcontain">
                                <span id="protocol-label" class="property-label"><g:message code="slide.protocol.label" default="Protocol" /></span>

                                <span class="property-value" aria-labelledby="protocol-label"><g:link controller="resultFile" action="show" id="${slideInstance?.protocol?.id}">${slideInstance?.protocol?.encodeAsHTML()}</g:link></span>

                            </li>
                        </g:if>

                        <li class="fieldcontain">
                        <span id="spots-counter" class="property-label">Spots read in from file:</span>
                        <span class="property-value">
                        <g:if test="${spotsDetected > 0}">
                            ${spotsDetected}
                                <g:link action="deleteSpots" id="${slideInstance?.id}">Delete Spots</g:link>
                        </g:if>
                        <g:else>0 <g:link action="addSpots" id="${slideInstance?.id}">Add Spots</g:link></g:else>
                        </span>
                        </li>

                    </ol>
                </div>

                <h3><a href="#">History</a></h3>
                <div>
                    <g:render template="/templates/history" model="[
                            createdBy: slideInstance.createdBy,
                            dateCreated: slideInstance.dateCreated,
                            lastUpdated: slideInstance.lastUpdated,
                            lastUpdatedBy: slideInstance.lastUpdatedBy]"/>
                </div>

                <g:if test="${slideInstance?.resultImage}">
                    <h3><a href="#">Slide Image</a></h3>
                    <div id="slideZoomPanel">
                        <g:if test="${imagezoomFolderExists}">
                            <g:render template="slideZoom" model="${[imagezoomFolder: imagezoomFolder]}"/>
                        </g:if>
                        <g:else>
                            It seems that the image file of this slide has not been processed yet.

                            <g:remoteLink action="zoomifyImage" id="${slideInstance?.id}"
                              before="\$('#slideZoomPanel').html('Image processing is running in the background and will take a while. This page will update when the process is complete. You can also navigate away and return later if you do not wish to wait.');"
                              update="slideZoomPanel">Process now</g:remoteLink>
                        </g:else>
                    </div>
                </g:if>
            </div>

			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${slideInstance?.id}" />
					<g:link class="edit" action="edit" id="${slideInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>

		</div>
	</body>
</html>
