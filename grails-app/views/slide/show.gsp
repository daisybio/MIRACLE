
<%@ page import="rppascanner.Slide" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'slide.label', default: 'Slide')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-slide" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-slide" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list slide">
			
				<g:if test="${slideInstance?.dateOfStaining}">
				<li class="fieldcontain">
					<span id="dateOfStaining-label" class="property-label"><g:message code="slide.dateOfStaining.label" default="Date Of Staining" /></span>
					
						<span class="property-value" aria-labelledby="dateOfStaining-label"><g:formatDate date="${slideInstance?.dateOfStaining}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${slideInstance?.experimenter}">
				<li class="fieldcontain">
					<span id="experimenter-label" class="property-label"><g:message code="slide.experimenter.label" default="Experimenter" /></span>
					
						<span class="property-value" aria-labelledby="experimenter-label"><g:link controller="experimenter" action="show" id="${slideInstance?.experimenter?.id}">${slideInstance?.experimenter?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${slideInstance?.laserWavelength}">
				<li class="fieldcontain">
					<span id="laserWavelength-label" class="property-label"><g:message code="slide.laserWavelength.label" default="Laser Wavelength" /></span>
					
						<span class="property-value" aria-labelledby="laserWavelength-label"><g:fieldValue bean="${slideInstance}" field="laserWavelength"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${slideInstance?.primaryAntibody}">
				<li class="fieldcontain">
					<span id="primaryAntibody-label" class="property-label"><g:message code="slide.primaryAntibody.label" default="Primary Antibody" /></span>
					
						<span class="property-value" aria-labelledby="primaryAntibody-label"><g:link controller="primaryAntibody" action="show" id="${slideInstance?.primaryAntibody?.id}">${slideInstance?.primaryAntibody?.encodeAsHTML()}</g:link></span>
					
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
					
						<span class="property-value" aria-labelledby="resultImage-label"><g:link controller="resultImage" action="show" id="${slideInstance?.resultImage?.id}">${slideInstance?.resultImage?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${slideInstance?.secondaryAntibody}">
				<li class="fieldcontain">
					<span id="secondaryAntibody-label" class="property-label"><g:message code="slide.secondaryAntibody.label" default="Secondary Antibody" /></span>
					
						<span class="property-value" aria-labelledby="secondaryAntibody-label"><g:link controller="secondaryAntibody" action="show" id="${slideInstance?.secondaryAntibody?.id}">${slideInstance?.secondaryAntibody?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${slideInstance?.spots}">
				<li class="fieldcontain">
					<span id="spots-label" class="property-label"><g:message code="slide.spots.label" default="Spots" /></span>
					
						<g:each in="${slideInstance.spots}" var="s">
						<span class="property-value" aria-labelledby="spots-label"><g:link controller="spot" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
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
