
<%@ page import="org.nanocan.rppa.layout.NumberOfCellsSeeded"%>

<div id="list-numberOfCellsSeeded" class="content scaffold-list"
	role="main">
	<h1>
		List Of Number Of Cell's Seeded
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
					title="${message(code: 'numberOfCellsSeeded.color.label', default: 'Color')}" />

				<g:sortableColumn property="name"
					title="${message(code: 'numberOfCellsSeeded.name.label', default: 'Name')}" />

			</tr>
		</thead>
		<tbody>
			<g:each in="${numberOfCellsSeededInstanceList}" status="i"
				var="numberOfCellsSeededInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

					<td><g:link action="show"
							id="${numberOfCellsSeededInstance.id}">
							${fieldValue(bean: numberOfCellsSeededInstance, field: "name")}
						</g:link></td>
					<td><div id="colorPickDiv"
							style="float:left; background-color: ${numberOfCellsSeededInstance?.color}; border: 1px solid; width:25px; height:25px;" /></td>

				</tr>
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${numberOfCellsSeededInstanceTotal}" />
	</div>
</div>