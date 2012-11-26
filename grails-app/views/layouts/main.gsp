<%@ page import="org.nanocan.rppa.project.Project" %>
<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="MIRACLE"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'rppa-fav-icon.png')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'rppa-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'rppa-touch-icon-retina.png')}">
        <imagezoom:resources player="openzoom" />

        <r:require module="jquery-ui"/>
        <r:require module="bootstrap"/>

        <g:javascript library="application"/>
        <g:layoutHead/>
        <r:layoutResources />
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'navigation.css')}" type="text/css">

	</head>
	<body>
        <g:if test="${params.nobanner=='true' || nobanner==true}"></g:if>
		<g:else>
            <div id="logo" role="banner">
                <a href="${createLink(uri: '/')}">
                    <img src="${resource(dir: 'images', file: 'miracle_banner.png')}" alt="MIRACLE">
                </a>

                <sec:ifLoggedIn>
                    <div id="logout" style="float:right; padding-right:10px; padding-top:10px;">
                        You are logged in as<br/><b><sec:username/></b><br/><br/>
                        <g:link controller="logout">Logout</g:link>
                        <sec:ifAllGranted roles="ROLE_ADMIN"> | <g:link controller="person">Manage</g:link></sec:ifAllGranted>
                    </div>
                </sec:ifLoggedIn>
            </div>
        </g:else>

        <g:layoutBody/>
		<g:if test="${nobanner != true}"><div class="footer" role="contentinfo"></div></g:if>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>

    <r:script>
        $('.dropdown-toggle').dropdown();
    </r:script>
        <r:layoutResources />
	</body>
</html>