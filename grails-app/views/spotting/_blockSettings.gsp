<li class="fieldcontain">
<span class="property-label">

<g:if test="${rowWise}">
    Number of layout columns per block
</g:if>
<g:else>
    Number of layout rows per block:
</g:else>

</span>
<span class="property-value">
    This is not the number of actually spotted rows or columns, but rather this number divided by the number of depositions, e.g.
    12 columns with deposition pattern [4,4,2,2,1,1] result in 2 layout columns.<br/><br/>
    <g:field type="number" name="xPerBlock" min="1" max="18" value="${xPerBlock}"/>
</span>
</li>


