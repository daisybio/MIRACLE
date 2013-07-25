<%@ page import="org.nanocan.rppa.layout.Inducer" %>

<head>
    <r:require module="colorPicker"/>
</head>

<r:script>
                $('#colorPickDiv').ColorPicker({
                    color: '${inducerInstance?.color}',
                    onShow: function (colpkr) {
                        $(colpkr).fadeIn(500);
                        return false;
                    },
                    onHide: function (colpkr) {
                        $(colpkr).fadeOut(500);
                        return false;
                    },
                    onChange: function (hsb, hex, rgb) {
                        $('#colorPickDiv').css('backgroundColor', '#' + hex);
                        $('#colorInput').attr("value", "#" + hex);
                    }
                });
</r:script>


<div class="fieldcontain ${hasErrors(bean: inducerInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="inducer.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" required="" value="${inducerInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: inducerInstance, field: 'concentration', 'error')} required">
    <label for="concentration">
        <g:message code="inducer.concentration.label" default="Concentration"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field type="number" name="concentration" required=""
             value="${fieldValue(bean: inducerInstance, field: 'concentration')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: inducerInstance, field: 'concentrationUnit', 'error')} ">
    <label for="concentrationUnit">
        <g:message code="inducer.concentrationUnit.label" default="Concentration Unit"/>

    </label>
    <g:select name="concentrationUnit" from="${inducerInstance.constraints.concentrationUnit.inList}"
              value="${inducerInstance?.concentrationUnit}" valueMessagePrefix="inducer.concentrationUnit"
              noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: inducerInstance, field: 'color', 'error')} required">
    <label for="color">
        <g:message code="inducer.color.label" default="Color"/>
        <span class="required-indicator">*</span>
    </label>
    <div id="colorPickDiv" style="float:right; margin-right:600px; background-color: ${inducerInstance?.color}; border: 1px solid; width:25px; height:25px;">
        <input type="hidden" id="colorInput" name="color" value="${inducerInstance?.color}">
    </div>
</div>

