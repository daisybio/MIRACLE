<%@ page import="org.nanocan.layout.NumberOfCellsSeeded; org.nanocan.layout.Treatment; org.nanocan.layout.SpotType; org.nanocan.layout.Inducer; org.nanocan.layout.Dilution; org.nanocan.layout.LysisBuffer; org.nanocan.layout.CellLine" %>
<head>
    <style>
        #draggableLegend { width:250px; height: 400px; overflow-y:auto; padding: 0.5em; background-color: #ffffff; position: fixed;
            z-index: 10000; opacity: 0.95; top: 135px; right: 190px;
            border-color: #e6e6e6; border-bottom-width: 1px; border-style: solid;}
    </style>
    <script type="text/javascript">
        $(function(){
            $( "#draggableLegend").draggable();
        });
    </script>
</head>
<body>
    <div id="draggableLegend" style="position: fixed;">

    <table>
    <g:if test="${sampleProperty == 'cellLine'}"><g:set var="samplePropertyList" value="${CellLine.list()}"/></g:if>
    <g:elseif test="${sampleProperty == 'lysisBuffer'}"><g:set var="samplePropertyList" value="${LysisBuffer.list()}"/></g:elseif>
    <g:elseif test="${sampleProperty == 'dilutionFactor'}"><g:set var="samplePropertyList" value="${Dilution.list()}"/></g:elseif>
    <g:elseif test="${sampleProperty == 'inducer'}"><g:set var="samplePropertyList" value="${Inducer.list()}"/></g:elseif>
    <g:elseif test="${sampleProperty == 'spotType'}"><g:set var="samplePropertyList" value="${SpotType.list()}"/></g:elseif>
    <g:elseif test="${sampleProperty == 'treatment'}"><g:set var="samplePropertyList" value="${Treatment.list()}"/></g:elseif>
    <g:elseif test="${sampleProperty == 'numberOfCellsSeeded'}"><g:set var="samplePropertyList" value="${NumberOfCellsSeeded.list()}"/></g:elseif>

    <thead>
    <tr><th>${sampleProperty.toString().capitalize()}</th><th>Color</th></tr>
    </thead>

    <tr>
        <td><input type="radio" onchange="changeColor('#ffffff', '', '-1')" name="radioSampleTypes" value="none"/> None
        </td>
        <td>
            <div id="colorPickNull" style="background-color: '#ffffff'; border: 1px solid; width:25px; height:25px;"></div>
        </td>
    </tr>

    <g:each in="${samplePropertyList}">
        <tr>
            <td>
                <input type="radio" onchange="changeColor('${it.color}', '${it}', ${it.id})" name="radioSampleTypes" value="${it.id}"/>     ${it}
            </td>
            <td>
                <div id="colorPick${it.id}" style="background-color: ${it.color}; border: 1px solid; width:25px; height:25px;"></div>
            </td>
            <script type="text/javascript">
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
            </script>

        </tr>
    </g:each>
    </table>
    </div>

    <script type="text/javascript">

    function changeColor(color, name, id){
        selColor = color
        selName = name
        selId = id
    }
    </script>

</body>