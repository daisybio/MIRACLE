<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="RPPAscanner"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'rppa-fav-icon.png')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'rppa-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'rppa-touch-icon-retina.png')}">
        <nav:resources override="true"/>
        <imagezoom:resources player="openzoom" />
        <r:require module="jquery-ui"/>
        <g:javascript library="application"/>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'navigation.css')}" type="text/css">
        <g:layoutHead/>
        <r:layoutResources />

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
	</head>
	<body>
		<div id="logo" role="banner"><img src="${resource(dir: 'images', file: 'rppascanner_logo_small.png')}" alt="RPPA-Scanner"/>

        <sec:ifLoggedIn>

            <div id="draggableUser">
                You are logged in as <sec:username/><br/><br/>
                <g:link controller="logout">Logout</g:link>
            </div>

            <div style="float:right; border: 0px none; box-shadow: none;"><nav:render group="main"/><br/>

            <div style="float:right;width:600px;"><hr></div><br/>

            <g:if test="${controllerName in ['slideLayout', 'cellLine', 'dilution', 'lysisBuffer', 'inducer', 'spotType']}"><nav:render group="layout"/></g:if>
            <g:if test="${controllerName in ['slide', 'antibody', 'experimenter', 'resultFileConfig']}"><nav:render group="scanner"/></g:if>
        </sec:ifLoggedIn>
        </div>
        </div>

        <g:layoutBody/>
		<div class="footer" role="contentinfo"></div>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
        <r:layoutResources />
	</body>
</html>