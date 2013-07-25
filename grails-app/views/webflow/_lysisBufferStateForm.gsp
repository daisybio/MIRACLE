<%@ page import="org.nanocan.rppa.layout.LysisBuffer" %>


<head>
    <r:require module="colorPicker"/>
</head>

<r:script>
                $('#colorPickDiv').ColorPicker({
                    color: '${lysisBufferInstance?.color}',
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


<div class="fieldcontain ${hasErrors(bean: lysisBufferInstance, field: 'concentrationUnit', 'error')} ">
    <label for="concentrationUnit">
        <g:message code="lysisBuffer.concentrationUnit.label" default="Concentration Unit"/>

    </label>
    <g:select name="concentrationUnit" from="${lysisBufferInstance.constraints.concentrationUnit.inList}"
              value="${lysisBufferInstance?.concentrationUnit}" valueMessagePrefix="lysisBuffer.concentrationUnit"
              noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lysisBufferInstance, field: 'concentration', 'error')} required">
    <label for="concentration">
        <g:message code="lysisBuffer.color.label" default="Concentration"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="concentration" required="" value="${lysisBufferInstance?.concentration}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lysisBufferInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="lysisBuffer.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" required="" value="${lysisBufferInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lysisBufferInstance, field: 'color', 'error')} required">
    <label for="color">
        <g:message code="lysisBuffer.concentration.label" default="Color"/>
        <span class="required-indicator">*</span>
    </label>
    <div id="colorPickDiv" style="float:right; margin-right:600px; background-color: ${lysisBufferInstance?.color}; border: 1px solid; width:25px; height:25px;">
        <input type="hidden" id="colorInput" name="color" value="${lysisBufferInstance?.color}">
    </div>
</div>

