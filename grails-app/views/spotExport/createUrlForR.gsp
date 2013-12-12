<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'create.url.for.R.title', default: 'Create URL for R')}" />
    <r:require module="syntaxhighlighter"/>
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
                <li><g:link class="list" controller="slide" action="show" id="${slideInstanceId}">Back to slide</g:link></li>
            </ul>
        </div>
    </div>
</div>


<div class="content">
    <h1>Export slide data to R</h1>

    <div style="margin: 20px;">There are two ways to import this slide into R:</div>

    <div style="margin: 20px;">You can either create a connection object that you can then reuse for up to one hour...</div>
    <pre class="brush: r">connection <- rppa.authenticate(baseUrl="${baseUrl.toString()}", user="<sec:username/>", password="xxx")
rppa.load(connection, baseUrl="${baseUrl.toString()}", slideIndex=${slideInstanceId})</pre>

    <div style="margin: 20px;">...or you use a security token unique to each slide. These commands don't require a user account to work.</div>

    <pre class="brush: r">rppa.load(securityToken="${securityToken}", baseUrl="${baseUrl.toString()}")</pre>

    <div style="margin: 20px;">Copy one of the commands above and paste it in the R command line interface.
    To do this, double click on the command (not directly on the link, but next to it) to select it, then use CTRL+C to copy to the clipboard and
    paste in the R console. Make sure you replace 'xxx' with your password if you choose user authentication.<br/>You probably want to assign your data to a variable like this:
    </div>

    <pre class="brush: r">myDataName <- rppa.load(...) //above command</pre>

    <script type="text/javascript">
        $(document).ready(function() { SyntaxHighlighter.all() });
    </script>

</div>
</body>
</html>