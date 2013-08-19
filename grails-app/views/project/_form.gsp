<%@ page import="org.nanocan.rppa.project.Project" %>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'projectTitle', 'error')} ">
    <label for="projectTitle">
        <g:message code="project.projectTitle.label" default="Project Title" />

    </label>
    <g:textField name="projectTitle" value="${projectInstance?.projectTitle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'projectDescription', 'error')} ">
	<label for="projectDescription">
		<g:message code="project.projectDescription.label" default="Project Description" />
		
	</label>
	<g:textField name="projectDescription" value="${projectInstance?.projectDescription}"/>
</div>


