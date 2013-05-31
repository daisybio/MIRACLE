<%@ page import="org.nanocan.rppa.layout.SlideLayout"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="webflow">
<g:set var="entityName"
	value="${message(code: 'slideLayout.label', default: 'SlideLayout')}" />
<title><g:message code="default.create.label"
		args="[entityName]" /></title>
</head>
<body>
	<a href="#create-slideLayout" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div id="create-slideLayout" class="content scaffold-create"
		role="main">
		<h1>
			<g:message code="default.create.label" args="[entityName]" />
		</h1>
		<div class="message" role="status">
			${flash.message?:"Important: Column means \"deposition blocks\" here, e.g. 6 different depositions spotted twice result in 2 columns."}
		</div>
		<g:hasErrors bean="${slideLayoutInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${slideLayoutInstance}" var="error">
					<li
						<g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
							error="${error}" /></li>
				</g:eachError>
			</ul>
		</g:hasErrors>
		<g:form action="rppa">
			<fieldset class="form">
				<g:render template="slideLayoutStateForm" />
			</fieldset>
			<fieldset class="buttons">
				<g:submitButton name="save" class="save"
					value="${message(code: 'default.button.create.label', default: 'Create')}" />
			</fieldset>
		</g:form>
	</div>
</body>
</html>