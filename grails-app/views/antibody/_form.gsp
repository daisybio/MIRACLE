<%@ page import="org.nanocan.rppa.scanner.Antibody" %>



<div class="fieldcontain ${hasErrors(bean: antibodyInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="antibody.name.label" default="Name"/>

    </label>
    <g:textField name="name" value="${antibodyInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: antibodyInstance, field: 'concentration', 'error')} ">
    <label for="concentration">
        <g:message code="antibody.concentration.label" default="Concentration"/>

    </label>
    <g:field type="number" step="any" name="concentration" min="0.0"
             value="${fieldValue(bean: antibodyInstance, field: 'concentration')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: antibodyInstance, field: 'concentrationUnit', 'error')} ">
    <label for="concentrationUnit">
        <g:message code="antibody.concentrationUnit.label" default="Concentration Unit"/>

    </label>
    <g:select name="concentrationUnit" from="${antibodyInstance.constraints.concentrationUnit.inList}"
              value="${antibodyInstance?.concentrationUnit}" valueMessagePrefix="antibody.concentrationUnit"
              noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: antibodyInstance, field: 'comments', 'error')} ">
    <label for="comments">
        <g:message code="antibody.comments.label" default="Comments"/>

    </label>
    <g:textField name="comments" value="${antibodyInstance?.comments}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: antibodyInstance, field: 'supplier', 'error')} ">
    <label for="supplier">
        <g:message code="antibody.supplier.label" default="Supplier"/>

    </label>
    <g:textField name="supplier" value="${antibodyInstance?.supplier}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: antibodyInstance, field: 'catalogNr', 'error')} ">
    <label for="catalogNr">
        <g:message code="antibody.catalogNr.label" default="Catalog Nr"/>

    </label>
    <g:textField name="catalogNr" value="${antibodyInstance?.catalogNr}"/>
</div>

