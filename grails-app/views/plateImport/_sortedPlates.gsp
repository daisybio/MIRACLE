<g:if test="${!sortedPlates}">
    <div class="error-details">No plates have been selected!</div>
</g:if>

<g:else>
    Selected plates in order:   <br/> <br/>
    <g:form action="importSettings" name="submitForm">
        <g:select selected="true" multiple="true" from="${sortedPlates}"
                  value="${sortedPlates}" editable="false" name="plateOrder"/>
        <div class="buttons"><g:submitButton class="save" name="continue" value="Continue"/></div>
    </g:form>
</g:else>