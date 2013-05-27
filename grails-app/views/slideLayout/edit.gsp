<%@ page import="org.nanocan.rppa.layout.SlideLayout" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'slideLayout.label', default: 'SlideLayout')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-slideLayout" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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

    <div id="edit-slideLayout" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${slideLayoutInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${slideLayoutInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${slideLayoutInstance?.id}" />
				<g:hiddenField name="version" value="${slideLayoutInstance?.version}" />
				<fieldset class="form">
                    <div class="fieldcontain ${hasErrors(bean: slideLayoutInstance, field: 'title', 'error')} ">
                        <label for="title">
                            <g:message code="slideLayout.title.label" default="Title" />

                        </label>
                        <g:textField name="title" value="${slideLayoutInstance?.title}"/>
                    </div>

                    <div class="fieldcontain">
                        <label for="experiments">
                            <g:message code="slideLayout.experiments.label" default="Experiments" />
                        </label>
                        <g:select name="experimentsSelected" multiple="${true}" size="10" from="${experiments}" value="${selectedExperiments?.collect{it.id}}" optionKey="id"/>
                    </div>


                </fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
