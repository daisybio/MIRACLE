<div class="fieldcontain ${hasErrors(bean: identifierInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="identifier.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${identifierInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: identifierInstance, field: 'type', 'error')} ">
	<label for="type">
		<g:message code="identifier.type.label" default="Type" />
		
	</label>
	<g:select name="type" from="${identifierInstance.constraints.type.inList}" value="${identifierInstance?.type}" valueMessagePrefix="identifier.type" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: identifierInstance, field: 'accessionNumber', 'error')} ">
	<label for="accessionNumber">
		<g:message code="identifier.accessionNumber.label" default="Accession Number" />
		
	</label>
	<g:textField name="accessionNumber" value="${identifierInstance?.accessionNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: identifierInstance, field: 'sample', 'error')} required">
	<label for="sample">
		<g:message code="identifier.sample.label" default="Sample" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="sample" name="sample.id" from="${sampleInstanceList}" optionKey="id" required="" value="${identifierInstance?.sample?.id}" class="many-to-one"/>
</div>

