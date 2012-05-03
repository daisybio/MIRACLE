<%@ page import="org.nanocan.rppa.scanner.ResultFileConfig" %>



<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'bgCol', 'error')} ">
    <label for="bgCol">
        <g:message code="resultFileConfig.bgCol.label" default="Bg Col"/>

    </label>
    <g:textField name="bgCol" value="${resultFileConfigInstance?.bgCol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'blockCol', 'error')} ">
    <label for="blockCol">
        <g:message code="resultFileConfig.blockCol.label" default="Block Col"/>

    </label>
    <g:textField name="blockCol" value="${resultFileConfigInstance?.blockCol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'columnCol', 'error')} ">
    <label for="columnCol">
        <g:message code="resultFileConfig.columnCol.label" default="Column Col"/>

    </label>
    <g:textField name="columnCol" value="${resultFileConfigInstance?.columnCol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'diameterCol', 'error')} ">
    <label for="diameterCol">
        <g:message code="resultFileConfig.diameterCol.label" default="Diameter Col"/>

    </label>
    <g:textField name="diameterCol" value="${resultFileConfigInstance?.diameterCol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'fgCol', 'error')} ">
    <label for="fgCol">
        <g:message code="resultFileConfig.fgCol.label" default="Fg Col"/>

    </label>
    <g:textField name="fgCol" value="${resultFileConfigInstance?.fgCol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'flagCol', 'error')} ">
    <label for="flagCol">
        <g:message code="resultFileConfig.flagCol.label" default="Flag Col"/>

    </label>
    <g:textField name="flagCol" value="${resultFileConfigInstance?.flagCol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="resultFileConfig.name.label" default="Name"/>

    </label>
    <g:textField name="name" value="${resultFileConfigInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'rowCol', 'error')} ">
    <label for="rowCol">
        <g:message code="resultFileConfig.rowCol.label" default="Row Col"/>

    </label>
    <g:textField name="rowCol" value="${resultFileConfigInstance?.rowCol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'skipLines', 'error')} required">
    <label for="skipLines">
        <g:message code="resultFileConfig.skipLines.label" default="Skip Lines"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field type="number" name="skipLines" required=""
             value="${fieldValue(bean: resultFileConfigInstance, field: 'skipLines')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'xCol', 'error')} ">
    <label for="xCol">
        <g:message code="resultFileConfig.xCol.label" default="X Col"/>

    </label>
    <g:textField name="xCol" value="${resultFileConfigInstance?.xCol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resultFileConfigInstance, field: 'yCol', 'error')} ">
    <label for="yCol">
        <g:message code="resultFileConfig.yCol.label" default="Y Col"/>

    </label>
    <g:textField name="yCol" value="${resultFileConfigInstance?.yCol}"/>
</div>

