<%@ page import="org.nanocan.rppa.layout.PlateLayout"%>
<a href="#list-plateLayout" class="skip" tabindex="-1"><g:message
		code="default.link.skip.label" default="Skip to content&hellip;" /></a>

<div id="list-plateLayout" class="content scaffold-list" role="main">
	<h1>
		List of Plate-layout's		<!-- g:message code="default.list.label" args="[entityName]"/ -->
	</h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">
			${flash.message}
		</div>
	</g:if>
	<table>
		<thead>
			<tr>
				<g:sortableColumn property="name"
					title="${message(code: 'plateLayout.name.label', default: 'Name')}" />

				<g:sortableColumn property="format"
					title="${message(code: 'plateLayout.format.label', default: 'Format')}" />
			</tr>
		</thead>
		<tbody>
			<g:each in="${plateLayoutInstanceList}" status="i"
				var="plateLayoutInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					<td><g:link action="rppa" event="editPlateLayout" id="${plateLayoutInstance.id}">
							${fieldValue(bean: plateLayoutInstance, field: "name")}
						</g:link></td>
					<td>
						${fieldValue(bean: plateLayoutInstance, field: "format")}
					</td>
				</tr>
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${plateLayoutInstanceTotal}" />
	</div>
</div>