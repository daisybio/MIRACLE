<%@ page import="rppa.org.nanocan.rppa.layout.Lorg.nanocan.rppa.layouorg.nanocan.layout.layout.CellLine" %>
<head>
    <r:require module="colorPicker"/>
</head>
<body>
    <table width="100">
    <g:if test="${sampleProperty == 'cellLine'}"><g:set var="samplePropertyList" value="${CellLine.list()}"/></g:if>
    <g:elseif test="${sampleProperty == 'lysisBuffer'}"><g:set var="samplePropertyList" value="${LysisBuffer.list()}"/></g:elseif>

    <tr>
        <td><input type="radio" onchange="changeColor('#ffffff', '')" name="radioSampleTypes" value="none"/> None
        </td>
        <td>
            <div id="colorPickNull" style="background-color: '#ffffff'; border: 1px solid; width:25px; height:25px;"></div>
        </td>
    </tr>

    <g:each in="${samplePropertyList}">
        <tr>
            <td>
                <input type="radio" onchange="changeColor('${it.color}', '${it}')" name="radioSampleTypes" value="${it.id}"/>     ${it}
            </td>
            <td>
                <div id="colorPick${it.id}" style="background-color: ${it.color}; border: 1px solid; width:25px; height:25px;"></div>
            </td>
            <r:script>
                $('#colorPick${it.id}').ColorPicker({
                    color: '${it.color}',
                    onShow: function (colpkr) {
                        $(colpkr).fadeIn(500);
                        return false;
                    },
                    onHide: function (colpkr) {
                        $(colpkr).fadeOut(500);
                        return false;
                    },
                    onChange: function (hsb, hex, rgb) {
                        $('#colorPick${it.id} div').css('backgroundColor', '#' + hex);
                    }
                });
            </r:script>

        </tr>
    </g:each>
    </table>

    <r:script>

    function changeColor(color, name){
        selColor = color
        selName = name
    }
    </r:script>

</body>