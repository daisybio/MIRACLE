<%@ page import="org.nanocan.project.Project" %>
<style>
#draggableProjects { width:250px; padding: 0.5em; background-color: #ffffff; position: fixed;
    z-index: 10000; opacity: 0.95; top: 100px; right: 10px;
    border-color: #e6e6e6; border-bottom-width: 1px; border-style: solid;}
</style>
<r:script>
    $(function(){
        $( "#draggableProjects").draggable();
    });
</r:script>

<div id="draggableProjects">
    <h3>Select a specific project:</h3><br/>
    <g:form name="projectForm" controller="project" action="updateSelectedProject">
        <g:hiddenField name="returnPage" value="${createLink(action:actionName, params:params, absolute: true)}"/>
        <g:select from="${Project.list()}" value="${session.projectSelected?:""}" optionKey="id" noSelection="['':'All projects']" name="projectSelect" onchange="\$('#projectForm').submit();"/>
    </g:form> <br/>
    <g:link controller="project" action="list">Manage projects</g:link>
</div>