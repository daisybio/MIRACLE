<style>
#draggableUser { width:250px; padding: 0.5em; background-color: #ffffff; position: fixed;
    z-index: 10000; opacity: 0.95; top: 10px; right: 10px;
    border-color: #e6e6e6; border-bottom-width: 1px; border-style: solid;}
</style>
<r:script>
    $(function(){
        $( "#draggableUser").draggable();
    });
</r:script>

<div id="draggableUser">
    You are logged in as <sec:username/><br/><br/>
    <g:link controller="logout">Logout</g:link>
    <sec:ifAllGranted roles="ROLE_ADMIN"> | <g:link controller="person">Manage Users</g:link></sec:ifAllGranted>
</div>