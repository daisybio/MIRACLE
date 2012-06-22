<%@ page import="org.nanocan.rppa.security.Person; org.nanocan.rppa.scanner.Antibody; org.nanocan.rppa.layout.SlideLayout; org.nanocan.rppa.scanner.Slide" %>


<head>
    <r:script>
            $(document).ready(function(){

               $('#resultFileAjax').autocomplete({
                    source: '<g:createLink controller='resultFile' action='ajaxResultFileFinder'/>',
                    minLength: 0,
                    select: function(event, ui)
                    {
                        document.getElementById('resultFile.id').setAttribute('value', ui.item.id);
                    }
                });

               $('#resultImageAjax').autocomplete({
                    source: '<g:createLink controller='resultFile' action='ajaxResultImageFinder'/>',
                    minLength: 0,
                    select: function(event, ui)
                    {
                        document.getElementById('resultImage.id').setAttribute('value', ui.item.id);
                    }
                });

               $('#protocolAjax').autocomplete({
                    source: '<g:createLink controller='resultFile' action='ajaxProtocolFinder'/>',
                    minLength: 0,
                    select: function(event, ui)
                    {
                        document.getElementById('protocol.id').setAttribute('value', ui.item.id);
                    }
                });
            });
    </r:script>
</head>

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
	<g:select id="experimenter" name="experimenter.id" from="${Person.list()}" optionKey="id" required="" value="${slideInstance?.experimenter?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'laserWavelength', 'error')} required">
	<label for="laserWavelength">
		<g:message code="slide.laserWavelength.label" default="Laser Wavelength (nm)" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="laserWavelength" required="" min="1" value="${fieldValue(bean: slideInstance, field: 'laserWavelength')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'photoMultiplierTube', 'error')} required">
    <label for="photoMultiplierTube">
        <g:message code="slide.photoMultiplierTube.label" default="PhotoMultiplierTube (PMT)" />
        <span class="required-indicator">*</span>
    </label>
    <g:field type="number" name="photoMultiplierTube" required="" value="${fieldValue(bean: slideInstance, field: 'photoMultiplierTube')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'antibody', 'error')} required">
	<label for="antibody">
		<g:message code="slide.antibody.label" default="Antibody" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="antibody" name="antibody.id" from="${Antibody.list()}" optionKey="id" required="" value="${slideInstance?.antibody?.id}" class="many-to-one"/>
</div>


<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'layout', 'error')} required">
    <label for="layout">
        <g:message code="slide.layout.label" default="SlideLayout" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="layout" name="layout.id" from="${SlideLayout.list()}" optionKey="id" required="" value="${slideInstance?.layout?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain">
    <label for="projects">
        <g:message code="slideLayout.projects.label" default="Projects" />
    </label>
    <g:select name="projectsSelected" multiple="${true}" size="10" from="${projects}" optionKey="id"/>
</div>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'resultFile', 'error')} required">
	<label for="resultFile">
		<g:message code="slide.resultFile.label" default="Result File" />
		<span class="required-indicator">*</span>
	</label>
    <div style="float:right; padding-right: 120px;"><table><tr><td>Choose existing file: </td>
        <td> <input type="text" id="resultFileAjax"></td></tr>
        <input type="hidden" id="resultFile.id" name="resultFile.id" value="${slideInstance?.resultFile?.id}">
	<tr><td>...or upload new file: </td><td><input type="file" id="resultFile.input" name="resultFileInput"/></td></tr></table></div>
</div><br/><br/><br/><br/>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'resultImage', 'error')} ">
	<label for="resultImage">
		<g:message code="slide.resultImage.label" default="Result Image" />
		<span class="required-indicator">*</span>
	</label>
    <div style="float:right; padding-right: 120px;"><table><tr><td>Choose existing file: </td>
        <td> <input type="text" id="resultImageAjax"></td></tr>
        <input type="hidden" id="resultImage.id" name="resultImage.id" value="${slideInstance?.resultImage?.id}">
     <tr><td>...or upload new file: </td><td><input type="file" id="resultImage.input" name="resultImageInput"/></td></tr></table></div>
</div> <br/><br/><br/><br/>

<div class="fieldcontain ${hasErrors(bean: slideInstance, field: 'protocol', 'error')} ">
    <label for="protocol">
        <g:message code="slide.protocol.label" default="Experiment Protocol" />
        <span class="required-indicator">*</span>
    </label>
    <div style="float:right; padding-right: 120px;"><table><tr><td>Choose existing file: </td>
        <td> <input type="text" id="protocolAjax"></td></tr>
        <input type="hidden" id="protocol.id" name="protocol.id" value="${slideInstance?.protocol?.id}">
        <tr><td>...or upload new file: </td><td><input type="file" id="protocol.input" name="protocolInput"/></td></tr></table></div>
</div>

