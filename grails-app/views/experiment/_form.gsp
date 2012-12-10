<%@ page import="org.nanocan.rppa.project.Experiment" %>



<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'title', 'error')} ">
    <label for="title">
        <g:message code="experiment.title.label" default="Title"/>

    </label>
    <g:textField name="title" value="${experimentInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'createdBy', 'error')} required">
    <label for="createdBy">
        <g:message code="experiment.createdBy.label" default="Created By"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="createdBy" name="createdBy.id" from="${org.nanocan.rppa.security.Person.list()}" optionKey="id"
              required="" value="${experimentInstance?.createdBy?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'description', 'error')} ">
    <label for="description">
        <g:message code="experiment.description.label" default="Description"/>

    </label>
    <g:textField name="description" value="${experimentInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'lastUpdatedBy', 'error')} required">
    <label for="lastUpdatedBy">
        <g:message code="experiment.lastUpdatedBy.label" default="Last Updated By"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="lastUpdatedBy" name="lastUpdatedBy.id" from="${org.nanocan.rppa.security.Person.list()}"
              optionKey="id" required="" value="${experimentInstance?.lastUpdatedBy?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'layouts', 'error')} ">
    <label for="layouts">
        <g:message code="experiment.layouts.label" default="Layouts"/>

    </label>
    <g:select name="layouts" from="${org.nanocan.rppa.layout.SlideLayout.list()}" multiple="multiple" optionKey="id"
              size="5" value="${experimentInstance?.layouts*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'plateLayouts', 'error')} ">
    <label for="plateLayouts">
        <g:message code="experiment.plateLayouts.label" default="Plate Layouts"/>

    </label>
    <g:select name="plateLayouts" from="${org.nanocan.rppa.layout.PlateLayout.list()}" multiple="multiple"
              optionKey="id" size="5" value="${experimentInstance?.plateLayouts*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'project', 'error')} required">
    <label for="project">
        <g:message code="experiment.project.label" default="Project"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="project" name="project.id" from="${org.nanocan.rppa.project.Project.list()}" optionKey="id"
              required="" value="${experimentInstance?.project?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'slides', 'error')} ">
    <label for="slides">
        <g:message code="experiment.slides.label" default="Slides"/>

    </label>
    <g:select name="slides" from="${org.nanocan.rppa.scanner.Slide.list()}" multiple="multiple" optionKey="id" size="5"
              value="${experimentInstance?.slides*.id}" class="many-to-many"/>
</div>

