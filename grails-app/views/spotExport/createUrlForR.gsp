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
    <h1>Export spots to R</h1>

    <div style="margin: 20px;">In order to import this slide into R copy the following command and paste it in the R command line interface</div>

    <pre class="brush: r">connection <- rppa.authenticate(baseUrl="${baseUrl.toString()}", user="<sec:username/>", password="xxx")
rppa.load(connection, baseUrl="${baseUrl.toString()}", slideIndex=${slideInstanceId})</pre>

    <div style="margin: 20px;">Double click on the command (not on the link, but next to it) to select it, then use CTRL+C to copy to the clipboard and
    paste in the R console. Make sure you replace 'xxx' with your password.<br/>You can assign your data to a variable like this:
    </div>

    <pre class="brush: r">myDataName <- rppa.load(...) //above command</pre>

    <script type="text/javascript">
        $(document).ready(function() { SyntaxHighlighter.all() });
    </script>

</div>
</body>
</html>