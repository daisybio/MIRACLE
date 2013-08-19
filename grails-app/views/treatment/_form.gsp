<%@ page import="org.nanocan.rppa.layout.Treatment" %>

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

<div class="fieldcontain ${hasErrors(bean: treatmentInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="treatment.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" required="" value="${treatmentInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: treatmentInstance, field: 'color', 'error')} required">
    <label for="color">
        <g:message code="treatment.color.label" default="Color"/>
        <span class="required-indicator">*</span>
    </label>
    <div id="colorPickDiv" style="float:right; margin-right: 600px; background-color: ${treatmentInstance?.color}; border: 1px solid; width:25px; height:25px;">
        <input type="hidden" id="colorInput" name="color" value="${treatmentInstance?.color}">
    </div>
</div>

<div class="fieldcontain ${hasErrors(bean: treatmentInstance, field: 'comments', 'error')} ">
    <label for="comments">
        <g:message code="treatment.comments.label" default="Comments"/>

    </label>
    <g:textField name="comments" value="${treatmentInstance?.comments}"/>
</div>

