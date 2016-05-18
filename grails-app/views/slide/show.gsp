
<%@ page import="org.nanocan.rppa.scanner.Antibody; liquibase.util.file.FilenameUtils; org.nanocan.rppa.scanner.Slide" %>
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


    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">
                <ul class="nav">
                    <g:render template="/templates/navmenu"></g:render>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>

                <li><g:link class="create" action="addBlockShifts" id="${slideInstance?.id}">Modify Block Shifts</g:link></li>
                <li><g:link class="list" controller="spotExport" action="exportAsCSV" id="${slideInstance?.id}">Export CSV</g:link></li>
                <li><g:link class="list" controller="spotExport" action="createUrlForR" id="${slideInstance?.id}">Export to R</g:link></li>
                <g:if test="${slideInstance.spots.size() > 0}">
                    <li>
                        <a href="#" class="heatmap" onclick="window.open('${g.createLink(controller:"slide", action:"heatmap", id:slideInstance.id, params:[shiny:false])}', '_blank', 'height=500,width=1200,toolbar=0,location=0,menubar=0');">Heatmap</a>
                    </li>
                    <!--<li>
                        <a href="#" class="heatmap" onclick="window.open('${g.createLink(controller:"slide", action:"heatmap", id:slideInstance.id, params:[shiny:true])}', '_blank', 'height=800,width=1200,toolbar=0,location=0,menubar=0');">Heatmap</a>
                    </li>-->
                    <!--<li>
                        <a href="#" class="plot" onclick="window.open('${g.createLink(controller:"slide", action:"analysis", id:slideInstance.id)}', '_blank', 'height=800,width=1200,toolbar=0,location=0,menubar=0');">Shiny Analysis</a>
                    </li>-->
                </g:if>
                </ul>
            </div>
        </div>
    </div>
		<div id="show-slide" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			    <div class="message" role="status">${flash.message}</div>
			</g:if>
            <g:if test="${slideInstance.blockShifts.size() > 0}">
                <div class="message" role="status">Note: This slide has block shifts</div>
            </g:if>

            <div id="accordion" style="margin: 25px; width: 90%;">
                <h3><a href="#">Properties</a></h3>
                <div>
                    <ol class="property-list slide">

                        <g:if test="${slideInstance?.barcode}">
                            <li class="fieldcontain">
                                <span id="barcode-label" class="property-label"><g:message code="slide.barcode.label" default="Barcode" /></span>

                                <span class="property-value" aria-labelledby="barcode-label">${slideInstance.barcode}</span>

                            </li>
                        </g:if>

                        <g:if test="${slideInstance?.title}">
                            <li class="fieldcontain">
                                <span id="title-label" class="property-label"><g:message code="slide.title.label" default="Title" /></span>

                                <span class="property-value" aria-labelledby="title-label">${slideInstance.title}</span>

                            </li>
                        </g:if>

                        <g:if test="${slideInstance?.comments}">
                            <li class="fieldcontain">
                                <span id="comments-label" class="property-label"><g:message code="slide.comments.label" default="Comments" /></span>

                                <span class="property-value" aria-labelledby="comments-label">${slideInstance.comments}</span>

                            </li>
                        </g:if>

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

                        <li class="fieldcontain">
                            <span id="photoMultiplierTube-label" class="property-label"><g:message code="slide.photoMultiplierTube.label" default="Photo Multiplier Tube (PMT)" /></span>

                            <span class="property-value" aria-labelledby="photoMultiplierTube-label"><g:fieldValue bean="${slideInstance}" field="photoMultiplierTube"/></span>

                        </li>

                        <li class="fieldcontain">
                            <span id="photoMultiplierTubeSetting-label" class="property-label"><g:message code="slide.photoMultiplierTubeSetting.label" default="Photo Multiplier Tube setting (PMT)" /></span>

                            <span class="property-value" aria-labelledby="photoMultiplierTubeSetting-label"><g:fieldValue bean="${slideInstance}" field="photoMultiplierTubeSetting"/></span>

                        </li>

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
                <h3><a href="#">Other slides using this layout</a></h3>
                <div>
                    <ul>
                        <g:each in="${Slide.findAllByLayout(slideInstance.layout) - slideInstance}" var="slide">
                            <li>
                                <g:link controller="slide" action="show" id="${slide.id}">${slide}</g:link>
                            </li>
                        </g:each>
                    </ul>
                </div>
                <h3><a href="#">Copy this slide</a></h3>
                <div>
                    <g:form action="copySlide" name="copySlideForm" enctype="multipart/form-data">
                        <g:hiddenField name="id" value="${slideInstance.id}"/>
                        <fieldset class="form">
                            <div class="fieldcontain">
                                <label for="copyName">
                                    <g:message code="slide.copy.label" default="Title for the copy" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <g:textField name="title" value="${slideInstance.title} (copy)"/>
                            </div>
                            <div class="fieldcontain">
                                <label for="barcode">
                                    <g:message code="slide.barcode.label" default="Barcode" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <g:textField name="barcode" value="${slideInstance.barcode}"></g:textField>
                            </div>
                            <div class="fieldcontain">
                                <label for="copyComments">
                                    <g:message code="slide.copy.comments" default="Comments" />
                                </label>
                                <g:textArea name="comments" value="${slideInstance.comments}"/>
                            </div>
                            <div class="fieldcontain">
                                <label for="dateOfStaining">
                                    <g:message code="slide.dateOfStaining.label" default="Date Of Staining" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <g:jqDatePicker name="dateOfStaining" value="${slideInstance?.dateOfStaining}"/>
                            </div>
                            <div class="fieldcontain">
                                <label for="copyPMT">
                                    <g:message code="slide.copy.pmt" default="PMT for the copy" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <g:field type="number" name="photoMultiplierTube" required="" value="${fieldValue(bean: slideInstance, field: 'photoMultiplierTube')}"/>
                            </div>
                            <div class="fieldcontain">
                                <label for="copyAntibody">
                                    <g:message code="slide.copy.antibody" default="Antibody for the copy" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <g:select id="antibody" name="antibody.id" from="${org.nanocan.rppa.scanner.Antibody.list()}" optionKey="id" required="" value="${slideInstance?.antibody?.id}" class="many-to-one"/>
                            </div>
                            <div class="fieldcontain">
                                <label for="copyResultFile">
                                    <g:message code="slide.copy.resultFile" default="Resultfile for the copy" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <input type="file" id="resultFile.input" name="resultFileInput"/>
                            </div>
                            <div class="fieldcontain">
                                <label for="copyResultImage">
                                    <g:message code="slide.copy.resultImage" default="Image for the copy" />
                                </label>
                                <input type="file" id="resultImage.input" name="resultImageInput"/>
                            </div>
                            
                        </fieldset>
                        <fieldset class="buttons">
                            <g:actionSubmit class="save" action="copySlide" value="create quick copy"/>
                            <g:actionSubmit class="edit" action="createFromTemplate" value="create using this slide as template"/>
                        </fieldset>

                    </g:form>
                </div>
            </div>

			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${slideInstance?.id}" />
					<g:link class="edit" action="edit" id="${slideInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>

            <br/><h1>Slide Viewer</h1>

            <g:if test="${slideInstance.resultImage}">
                <div id="zoomable" style="height:400px;"/>
                <g:openSeaDragon file="${slideInstance.resultImage.filePath}" target="zoomable" showNavigator="true"/>
            </g:if>

		</div>
	</body>
</html>
