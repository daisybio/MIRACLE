<%@ page import="org.nanocan.rppa.layout.Dilution" %>

<head>
    <r:require module="colorPicker"/>
</head>

<r:script>
                $('#colorPickDiv').ColorPicker({
                    color: '${dilutionInstance?.color}',
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

<div class="fieldcontain ${hasErrors(bean: dilutionInstance, field: 'dilutionFactor', 'error')} required">
    <label for="dilutionFactor">
        <g:message code="dilution.dilutionFactor.label" default="Dilution Factor"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="dilutionFactor" required="" value="${dilutionInstance?.dilutionFactor}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dilutionInstance, field: 'color', 'error')} required">
    <label for="color">
        <g:message code="dilution.color.label" default="Color"/>
        <span class="required-indicator">*</span>
    </label>
    <div id="colorPickDiv" style="float: right; margin-right:600px; background-color: ${dilutionInstance?.color}; border: 1px solid; width:25px; height:25px;">
        <input type="hidden" id="colorInput" name="color" value="${dilutionInstance?.color}">
    </div>
</div>

