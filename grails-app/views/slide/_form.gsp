<%@ page import="rppascanner.Slide" %>


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
	<g:select id="experimenter" name="experimenter.id" from="${rppascanner.Experimenter.list()}" optionKey="id" required="" value="${slideInstance?.experimenter?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'laserWavelength', 'error')} required">
	<label for="laserWavelength">
		<g:message code="slide.laserWavelength.label" default="Laser Wavelength" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="laserWavelength" required="" value="${fieldValue(bean: slideInstance, field: 'laserWavelength')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'primaryAntibody', 'error')} required">
	<label for="primaryAntibody">
		<g:message code="slide.primaryAntibody.label" default="Primary Antibody" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="primaryAntibody" name="primaryAntibody.id" from="${rppascanner.PrimaryAntibody.list()}" optionKey="id" required="" value="${slideInstance?.primaryAntibody?.id}" class="many-to-one"/>
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

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'secondaryAntibody', 'error')} required">
	<label for="secondaryAntibody">
		<g:message code="slide.secondaryAntibody.label" default="Secondary Antibody" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="secondaryAntibody" name="secondaryAntibody.id" from="${rppascanner.SecondaryAntibody.list()}" optionKey="id" required="" value="${slideInstance?.secondaryAntibody?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'spots', 'error')} ">
	<label for="spots">
		<g:message code="slide.spots.label" default="Spots" />
		
	</label>
	<g:select name="spots" from="${rppascanner.Spot.list()}" multiple="multiple" optionKey="id" size="5" value="${slideInstance?.spots*.id}" class="many-to-many"/>
</div>

