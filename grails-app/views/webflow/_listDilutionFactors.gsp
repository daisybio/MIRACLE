<%@ page import="org.nanocan.rppa.layout.Dilution"%>
<a href="#list-dilution" class="skip" tabindex="-1"><g:message
		code="default.link.skip.label" default="Skip to content&hellip;" /></a>
<div id="list-dilution" class="content scaffold-list" role="main">
	<h1>
		List of Dilution-Factor's
	</h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">
			${flash.message}
		</div>
	</g:if>
	<table>
		<thead>
			<tr>

				<g:sortableColumn property="dilutionFactor"
					title="${message(code: 'dilution.dilutionFactor.label', default: 'Dilution Factor')}" />

				<g:sortableColumn property="color"
					title="${message(code: 'dilution.color.label', default: 'Color')}" />

			</tr>
		</thead>
		<tbody>
			<g:each in="${dilutionInstanceList}" status="i"
				var="dilutionInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

					<td><g:link action="show" id="${dilutionInstance.id}">
							${dilutionInstance?.dilutionFactor}
						</g:link></td>

					<td><div id="colorPickDiv"
							style="float:left; background-color: ${dilutionInstance?.color}; border: 1px solid; width:25px; height:25px;" /></td>

				</tr>
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${dilutionInstanceTotal}" />
	</div>
</div>
