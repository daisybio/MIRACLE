
<%@ page import="org.nanocan.rppa.layout.SpotType"%>
<a href="#list-spotType" class="skip" tabindex="-1"><g:message
		code="default.link.skip.label" default="Skip to content&hellip;" /></a>

<div id="list-spotType" class="content scaffold-list" role="main">
	<h1>
		List of Spot Type's	<!-- g:message code="default.list.label" args="[entityName]" / -->
	</h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">
			${flash.message}
		</div>
	</g:if>
	<table>
		<thead>
			<tr>

				<g:sortableColumn property="color"
					title="${message(code: 'spotType.color.label', default: 'Color')}" />

				<g:sortableColumn property="type"
					title="${message(code: 'spotType.type.label', default: 'Type')}" />

				<g:sortableColumn property="name"
					title="${message(code: 'spotType.name.label', default: 'Name')}" />

			</tr>
		</thead>
		<tbody>
			<g:each in="${spotTypeInstanceList}" status="i"
				var="spotTypeInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

					<td><g:link action="show" id="${spotTypeInstance.id}">
							${fieldValue(bean: spotTypeInstance, field: "name")}
						</g:link></td>

					<td>
						${fieldValue(bean: spotTypeInstance, field: "type")}
					</td>

					<td><div id="colorPickDiv"
							style="float:left; background-color: ${spotTypeInstance?.color}; border: 1px solid; width:25px; height:25px;" /></td>

				</tr>
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${spotTypeInstanceTotal}" />
	</div>
</div>
