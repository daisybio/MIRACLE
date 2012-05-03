<%@ page import="rppa.org.nanocan.rppa.layout.SlideLayout; org.nanocan.rppa.scanner.Slide" %>


<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'dateOfStaining', 'error')} required">
	<label for="dateOfStaining">
		<g:message code="slide.dateOfStaining.label" default="Date Of Staining" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateOfStaining" precision="day"  value="${slideInstance?.dateOfStaining}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'experimenter', 'error')} required">
	<label for="experimenter">
		<g:message code="slide.experimenter.label" default="Experimenter" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="experimenter" name="experimenter.id" from="${scanner.Experimenter.list()}" optionKey="id" required="" value="${slideInstance?.experimenter?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'laserWavelength', 'error')} required">
	<label for="laserWavelength">
		<g:message code="slide.laserWavelength.label" default="Laser Wavelength" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="laserWavelength" required="" value="${fieldValue(bean: slideInstance, field: 'laserWavelength')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'antibody', 'error')} required">
	<label for="antibody">
		<g:message code="slide.antibody.label" default="Antibody" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="antibody" name="antibody.id" from="${scanner.Antibody.list()}" optionKey="id" required="" value="${slideInstance?.antibody?.id}" class="many-to-one"/>
</div>


<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'layout', 'error')} required">
    <label for="layout">
        <g:message code="slide.layout.label" default="SlideLayout" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="layout" name="layout.id" from="${SlideLayout.list()}" optionKey="id" required="" value="${slideInstance?.layout?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'resultFile', 'error')} required">
	<label for="resultFile">
		<g:message code="slide.resultFile.label" default="Result File" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="resultFile.input" name="resultFile.input"/>
</div>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'resultImage', 'error')} required">
	<label for="resultImage">
		<g:message code="slide.resultImage.label" default="Result Image" />
		<span class="required-indicator">*</span>
	</label>
    <input type="file" id="resultImage.input" name="resultImage.input"/>
</div>


