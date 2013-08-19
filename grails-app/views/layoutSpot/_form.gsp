<%@ page import="org.nanocan.rppa.layout.LayoutSpot" %>



<div class="fieldcontain ${hasErrors(bean: layoutSpotInstance, field: 'numberOfCellsSeeded', 'error')} ">
	<label for="numberOfCellsSeeded">
		<g:message code="layoutSpot.numberOfCellsSeeded.label" default="Number Of Cells Seeded" />
		
	</label>
	<g:select id="numberOfCellsSeeded" name="numberOfCellsSeeded.id" from="${org.nanocan.rppa.layout.NumberOfCellsSeeded.list()}" optionKey="id" value="${layoutSpotInstance?.numberOfCellsSeeded?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: layoutSpotInstance, field: 'cellLine', 'error')} ">
	<label for="cellLine">
		<g:message code="layoutSpot.cellLine.label" default="Cell Line" />
		
	</label>
	<g:select id="cellLine" name="cellLine.id" from="${org.nanocan.rppa.layout.CellLine.list()}" optionKey="id" value="${layoutSpotInstance?.cellLine?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: layoutSpotInstance, field: 'lysisBuffer', 'error')} ">
	<label for="lysisBuffer">
		<g:message code="layoutSpot.lysisBuffer.label" default="Lysis Buffer" />
		
	</label>
	<g:select id="lysisBuffer" name="lysisBuffer.id" from="${org.nanocan.rppa.layout.LysisBuffer.list()}" optionKey="id" value="${layoutSpotInstance?.lysisBuffer?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: layoutSpotInstance, field: 'dilutionFactor', 'error')} ">
	<label for="dilutionFactor">
		<g:message code="layoutSpot.dilutionFactor.label" default="Dilution Factor" />
		
	</label>
	<g:select id="dilutionFactor" name="dilutionFactor.id" from="${org.nanocan.rppa.layout.Dilution.list()}" optionKey="id" value="${layoutSpotInstance?.dilutionFactor?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: layoutSpotInstance, field: 'inducer', 'error')} ">
	<label for="inducer">
		<g:message code="layoutSpot.inducer.label" default="Inducer" />
		
	</label>
	<g:select id="inducer" name="inducer.id" from="${org.nanocan.rppa.layout.Inducer.list()}" optionKey="id" value="${layoutSpotInstance?.inducer?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: layoutSpotInstance, field: 'sample', 'error')} ">
	<label for="sample">
		<g:message code="layoutSpot.sample.label" default="Sample" />
		
	</label>
	<g:select id="sample" name="sample.id" from="${org.nanocan.rppa.rnai.Sample.list()}" optionKey="id" value="${layoutSpotInstance?.sample?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: layoutSpotInstance, field: 'spotType', 'error')} ">
	<label for="spotType">
		<g:message code="layoutSpot.spotType.label" default="Spot Type" />
		
	</label>
	<g:select id="spotType" name="spotType.id" from="${org.nanocan.rppa.layout.SpotType.list()}" optionKey="id" value="${layoutSpotInstance?.spotType?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: layoutSpotInstance, field: 'treatment', 'error')} ">
	<label for="treatment">
		<g:message code="layoutSpot.treatment.label" default="Treatment" />
		
	</label>
	<g:select id="treatment" name="treatment.id" from="${org.nanocan.rppa.layout.Treatment.list()}" optionKey="id" value="${layoutSpotInstance?.treatment?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: layoutSpotInstance, field: 'block', 'error')} required">
	<label for="block">
		<g:message code="layoutSpot.block.label" default="Block" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="block" required="" value="${fieldValue(bean: layoutSpotInstance, field: 'block')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: layoutSpotInstance, field: 'col', 'error')} required">
	<label for="col">
		<g:message code="layoutSpot.col.label" default="Col" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="col" required="" value="${fieldValue(bean: layoutSpotInstance, field: 'col')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: layoutSpotInstance, field: 'layout', 'error')} required">
	<label for="layout">
		<g:message code="layoutSpot.layout.label" default="Layout" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="layout" name="layout.id" from="${org.nanocan.rppa.layout.SlideLayout.list()}" optionKey="id" required="" value="${layoutSpotInstance?.layout?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: layoutSpotInstance, field: 'row', 'error')} required">
	<label for="row">
		<g:message code="layoutSpot.row.label" default="Row" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="row" required="" value="${fieldValue(bean: layoutSpotInstance, field: 'row')}"/>
</div>

