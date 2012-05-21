<%@ page import="org.nanocan.rppa.layout.SpotType" %>


<head>
    <r:require module="colorPicker"/>
</head>

<r:script>
                $('#colorPickDiv').ColorPicker({
                    color: '${spotTypeInstance?.color}',
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

<div class="fieldcontain ${hasErrors(bean: spotTypeInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="spotType.name.label" default="Name" />

    </label>
    <g:textField name="name" value="${spotTypeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: spotTypeInstance, field: 'type', 'error')} ">
	<label for="type">
		<g:message code="spotType.type.label" default="Type" />
		
	</label>
	<g:select name="type" from="${spotTypeInstance.constraints.type.inList}" value="${spotTypeInstance?.type}" valueMessagePrefix="spotType.type" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: spotTypeInstance, field: 'color', 'error')} required">
    <label for="color">
        <g:message code="spotType.color.label" default="Color" />
        <span class="required-indicator">*</span>
    </label>
    <div id="colorPickDiv" style="float:right; margin-right: 600px; background-color: ${spotTypeInstance?.color}; border: 1px solid; width:25px; height:25px;">
        <input type="hidden" id="colorInput" name="color" value="${spotTypeInstance?.color}">
    </div>
</div>

