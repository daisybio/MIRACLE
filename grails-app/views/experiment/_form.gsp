<%@ page import="org.nanocan.rppa.project.Experiment" %>



<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="experiment.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${experimentInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'firstDayOfTheExperiment', 'error')} ">
	<label for="firstDayOfTheExperiment">
		<g:message code="experiment.firstDayOfTheExperiment.label" default="First Day Of The Experiment" />
		
	</label>
	<g:jqDatePicker name="firstDayOfTheExperiment" value="${experimentInstance?.firstDayOfTheExperiment}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="experiment.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${experimentInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'project', 'error')} required">
	<label for="project">
		<g:message code="experiment.project.label" default="Project" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="project" name="project.id" from="${org.nanocan.rppa.project.Project.list()}" optionKey="id" required="" value="${experimentInstance?.project?.id}" class="many-to-one"/>
</div>



