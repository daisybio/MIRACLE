<!-- Progress bar -->
<g:jprogressDialog message="Adding spots to database..." progressId="${progressId}" trigger="buttonAddSpots" />

<div style="padding:50px;">
    <h3>Match columns and database properties:</h3><br/><br/>

    <table>
        <g:set var="colCounter" value="${0}"/>
        <g:each in="${header}" var="col">

            <g:if test="${!col.equals("")}">
            <tr>
                <td>
                    ${col}
                </td>
                <td>
                    <g:select name="column_${colCounter}" noSelection="['':'Do not use']" value="${matchingMap[col]}" from="${spotProperties}"/>
                </td>
            </tr>
            </g:if>


            <g:set var="colCounter" value="${++colCounter}" />
        </g:each>

    </table>

    <fieldset class="buttons">
        <g:submitToRemote onLoading="\$('#formDiv').hide() ; \$('#updateDiv').hide();" onSuccess="\$('#updateDiv').show();"
                              update="updateDiv" action="processResultFile" name="buttonAddSpots" value="Add spots from selected sheet to slide"/>
    </fieldset>
</div>