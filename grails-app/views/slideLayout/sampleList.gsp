<r:script>

 var changeSampleFromSampleList = function(color, id, name)
 {
     document.getElementById('legendSample').innerHTML = "<table><tr><td>" + name + "</td><td>" +
             "<div id='colorPickDiv' style=' background-color: " + color + "; " +
             "border: 1px solid; width:25px; height:25px;'/></td></tr></table>";

     selColor = color
     selName = name;
     selId = id;
 }
</r:script>

<table>

<g:each in="${samples}">
    <g:if test="${it}">
        <tr onclick="changeSampleFromSampleList('${it.color}', ${it.id}, '${it.name}');">
            <td>${it}</td>
            <td>
                <div style="background-color: ${it?.color}; border: 1px solid; width:25px; height:25px;"/>
            </td>
        </tr>
    </g:if>
</g:each>

</table>