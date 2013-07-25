<%@ page import="org.nanocan.rppa.layout.CellLine" %>

<head>
    <r:require module="colorPicker"/>
</head>

<r:script>
                $('#colorPickDiv').ColorPicker({
                    color: '${cellLineInstance?.color}',
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

<div class="fieldcontain ${hasErrors(bean: cellLineInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="cellLine.name.label" default="Name"/>

    </label>
    <g:textField name="name" value="${cellLineInstance?.name}"/>
</div>

<div style="width:75px; padding-left:175px;" class="fieldcontain ${hasErrors(bean: cellLineInstance, field: 'color', 'error')} ">
    <label for="color">
        <g:message code="cellLine.color.label" default="Color"/>

    </label>
    <div id="colorPickDiv" style="float:right; background-color: ${cellLineInstance?.color}; border: 1px solid; width:25px; height:25px;">
        <input type="hidden" id="colorInput" name="color" value="${cellLineInstance?.color}">
    </div>
</div>

