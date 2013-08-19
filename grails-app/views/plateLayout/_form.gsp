<%@ page import="org.nanocan.rppa.layout.PlateLayout" %>



<div class="fieldcontain ${hasErrors(bean: plateLayoutInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="plateLayout.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${plateLayoutInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: plateLayoutInstance, field: 'format', 'error')} required">
	<label for="format">
		<g:message code="plateLayout.format.label" default="Format" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="format" from="${plateLayoutInstance.constraints.format.inList}" required="" value="${plateLayoutInstance?.format}" valueMessagePrefix="plateLayout.format"/>
</div>

<div class="fieldcontain">
    <label for="experiments">
        <g:message code="slideLayout.experiments.label" default="Experiments" />
    </label>
    <g:select name="experimentsSelected" multiple="${true}" size="10" from="${experiments}" optionKey="id"/>
</div>

<div class="fieldcontain">
    <label for="controlPlate">
        <g:message code="slideLayout.controlPlate.label" default="Control Plate?" />
    </label>
    <g:checkBox name="controlPlate"/>
</div>
