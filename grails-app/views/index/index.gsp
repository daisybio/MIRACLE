<%@ page import="org.nanocan.rppa.scanner.Slide" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to MIRACLE!</title>
		<style type="text/css" media="screen">
			#status {
				background-color: #eee;
				border: .2em solid #fff;
				margin: 2em 2em 1em;
				padding: 1em;
				width: 12em;
				float: left;
				-moz-box-shadow: 0px 0px 1.25em #ccc;
				-webkit-box-shadow: 0px 0px 1.25em #ccc;
				box-shadow: 0px 0px 1.25em #ccc;
				-moz-border-radius: 0.6em;
				-webkit-border-radius: 0.6em;
				border-radius: 0.6em;
			}

			.ie6 #status {
				display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
			}

			#status ul {
				font-size: 0.9em;
				list-style-type: none;
				margin-bottom: 0.6em;
				padding: 0;
			}
            
			#status li {
				line-height: 1.3;
			}

			#status h1 {
				text-transform: uppercase;
				font-size: 1.1em;
				margin: 0 0 0.3em;
			}

			#page-body {
				margin: 2em 1em 1.25em 18em;
			}

			h2 {
				margin-top: 1em;
				margin-bottom: 0.3em;
				font-size: 1em;
			}

			p {
				line-height: 1.5;
				margin: 0.25em 0;
			}

			#controller-list ul {
				list-style-position: inside;
			}

			#controller-list li {
				line-height: 1.3;
				list-style-position: inside;
				margin: 0.25em 0;
			}

			@media screen and (max-width: 480px) {
				#status {
					display: none;
				}

				#page-body {
					margin: 0 1em 1em;
				}

				#page-body h1 {
					margin-top: 0;
				}
			}
		</style>
	</head>
	<body>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		        <sec:ifLoggedIn>

                <div class="navbar">
                    <div class="navbar-inner">
                        <div class="container">
                            <ul class="nav">
                                <g:render template="/templates/navmenu"></g:render>
                            </ul>
                        </div>
                    </div>
                </div>

                <div id="status" role="complementary">

                <h1>Application Status</h1>
                    <ul>
                        <li>App version: <g:meta name="app.version"/></li>
                        <li>Grails version: <g:meta name="app.grails.version"/></li>
                        <li>Groovy version: ${org.codehaus.groovy.runtime.InvokerHelper.getVersion()}</li>
                        <li>JVM version: ${System.getProperty('java.version')}</li>
                        <li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>   <br/>
                        <li>Slides in the database: ${org.nanocan.rppa.scanner.Slide.count()}</li>
                        <li>Spots in the database: ${org.nanocan.rppa.scanner.Spot.count()}</li>
                    </ul>
                </div>
                <div id="page-body" role="main">
                    <h1>Welcome to MIRACLE</h1>

                    <p>MIRACLE is a web-tool for <b>MI</b>croarray <b>R</b>-based <b>A</b>nalysis of <b>C</b>omplex <b>L</b>ysate Array <b>E</b>xperiments.

                    It allows you to upload microarray files produced by a scanner (in either XLS, CSV or TXT format) and to add the experimental information, e.g. the spot signal, to a SQL database.
                    In order to organize your results, you can assign layout information for each spot and perform further analysis by using inbuilt R functionality for plotting, normalization and
                    quantification of the results. If you wish to perform further analysis you can export the combined layout / signal information as CSV file or directly to R, where a number of
                    R functions help the user in performing a manual analysis of the data.</p>
                </div>

                <img width="400" src="<g:resource dir="images" file="chip.png"/>"/>
                </sec:ifLoggedIn>

            <sec:ifNotLoggedIn>
                    <div><g:include controller="login" action="auth"></g:include></div>
            </sec:ifNotLoggedIn>


	</body>
</html>
