<%@ page import="org.nanocan.rppa.scanner.Antibody" %>



<div class="fieldcontain ${hasErrors(bean: antibodyInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="antibody.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${antibodyInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: antibodyInstance, field: 'concentration', 'error')} ">
	<label for="concentration">
		<g:message code="antibody.concentration.label" default="Concentration" />
		
	</label>
	<g:field type="number" step="any" name="concentration" value="${fieldValue(bean: antibodyInstance, field: 'concentration')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: antibodyInstance, field: 'concentrationUnit', 'error')} ">
	<label for="concentrationUnit">
		<g:message code="antibody.concentrationUnit.label" default="Concentration Unit" />
		
	</label>
	<g:select name="concentrationUnit" from="${antibodyInstance.constraints.concentrationUnit.inList}" value="${antibodyInstance?.concentrationUnit}" valueMessagePrefix="antibody.concentrationUnit" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: antibodyInstance, field: 'comments', 'error')} ">
	<label for="comments">
		<g:message code="antibody.comments.label" default="Comments" />
		
	</label>
	<g:textField name="comments" value="${antibodyInstance?.comments}"/>
</div>

