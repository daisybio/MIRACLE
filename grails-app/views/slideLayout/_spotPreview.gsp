<div id="show-layoutSpot" class="content scaffold-show" role="main">
            <h1>Spot ${layoutSpotInstance}</h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list" style="margin: 0; padding: 0;">

    <g:if test="${layoutSpotInstance?.numberOfCellsSeeded}">
        <li class="fieldcontain">
            <span id="numberOfCellsSeeded-label" class="property-label"><g:message code="layoutSpot.numberOfCellsSeeded.label" default="Number Of Cells Seeded" /></span>

            <span class="property-value" aria-labelledby="numberOfCellsSeeded-label"><g:link controller="numberOfCellsSeeded" action="show" id="${layoutSpotInstance?.numberOfCellsSeeded?.id}">${layoutSpotInstance?.numberOfCellsSeeded?.encodeAsHTML()}</g:link></span>

        </li>
    </g:if>

    <g:if test="${layoutSpotInstance?.cellLine}">
        <li class="fieldcontain">
            <span id="cellLine-label" class="property-label"><g:message code="layoutSpot.cellLine.label" default="Cell Line" /></span>

            <span class="property-value" aria-labelledby="cellLine-label"><g:link controller="cellLine" action="show" id="${layoutSpotInstance?.cellLine?.id}">${layoutSpotInstance?.cellLine?.encodeAsHTML()}</g:link></span>

        </li>
    </g:if>

    <g:if test="${layoutSpotInstance?.lysisBuffer}">
        <li class="fieldcontain">
            <span id="lysisBuffer-label" class="property-label"><g:message code="layoutSpot.lysisBuffer.label" default="Lysis Buffer" /></span>

            <span class="property-value" aria-labelledby="lysisBuffer-label"><g:link controller="lysisBuffer" action="show" id="${layoutSpotInstance?.lysisBuffer?.id}">${layoutSpotInstance?.lysisBuffer?.encodeAsHTML()}</g:link></span>

        </li>
    </g:if>

    <g:if test="${layoutSpotInstance?.dilutionFactor}">
        <li class="fieldcontain">
            <span id="dilutionFactor-label" class="property-label"><g:message code="layoutSpot.dilutionFactor.label" default="Dilution Factor" /></span>

            <span class="property-value" aria-labelledby="dilutionFactor-label"><g:link controller="dilution" action="show" id="${layoutSpotInstance?.dilutionFactor?.id}">${layoutSpotInstance?.dilutionFactor?.encodeAsHTML()}</g:link></span>

        </li>
    </g:if>

    <g:if test="${layoutSpotInstance?.inducer}">
        <li class="fieldcontain">
            <span id="inducer-label" class="property-label"><g:message code="layoutSpot.inducer.label" default="Inducer" /></span>

            <span class="property-value" aria-labelledby="inducer-label"><g:link controller="inducer" action="show" id="${layoutSpotInstance?.inducer?.id}">${layoutSpotInstance?.inducer?.encodeAsHTML()}</g:link></span>

        </li>
    </g:if>

    <g:if test="${layoutSpotInstance?.sample}">
        <li class="fieldcontain">
            <span id="sample-label" class="property-label"><g:message code="layoutSpot.sample.label" default="Sample" /></span>

            <span class="property-value" aria-labelledby="sample-label"><g:link controller="sample" action="show" id="${layoutSpotInstance?.sample?.id}">${layoutSpotInstance?.sample?.encodeAsHTML()}</g:link></span>

        </li>
    </g:if>

    <g:if test="${layoutSpotInstance?.spotType}">
        <li class="fieldcontain">
            <span id="spotType-label" class="property-label"><g:message code="layoutSpot.spotType.label" default="Spot Type" /></span>

            <span class="property-value" aria-labelledby="spotType-label"><g:link controller="spotType" action="show" id="${layoutSpotInstance?.spotType?.id}">${layoutSpotInstance?.spotType?.encodeAsHTML()}</g:link></span>

        </li>
    </g:if>

    <g:if test="${layoutSpotInstance?.treatment}">
        <li class="fieldcontain">
            <span id="treatment-label" class="property-label"><g:message code="layoutSpot.treatment.label" default="Treatment" /></span>

            <span class="property-value" aria-labelledby="treatment-label"><g:link controller="treatment" action="show" id="${layoutSpotInstance?.treatment?.id}">${layoutSpotInstance?.treatment?.encodeAsHTML()}</g:link></span>

        </li>
    </g:if>

    <g:if test="${layoutSpotInstance?.layout}">
        <li class="fieldcontain">
            <span id="layout-label" class="property-label"><g:message code="layoutSpot.layout.label" default="Layout" /></span>

            <span class="property-value" aria-labelledby="layout-label"><g:link controller="slideLayout" action="show" id="${layoutSpotInstance?.layout?.id}">${layoutSpotInstance?.layout?.encodeAsHTML()}</g:link></span>

        </li>
    </g:if>

</ol>
</div>