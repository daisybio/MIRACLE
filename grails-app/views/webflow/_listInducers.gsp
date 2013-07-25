<%@ page import="org.nanocan.rppa.layout.Inducer"%>

<div id="list-inducer" class="content scaffold-list" role="main">
	<h1>List of Inducer's</h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">
			${flash.message}
		</div>
	</g:if>
	<table>
		<thead>
			<tr>

				<g:sortableColumn property="name"
					title="${message(code: 'inducer.name.label', default: 'Name')}" />

				<g:sortableColumn property="concentration"
					title="${message(code: 'inducer.concentration.label', default: 'Concentration')}" />

				<g:sortableColumn property="concentrationUnit"
					title="${message(code: 'inducer.concentrationUnit.label', default: 'Concentration Unit')}" />

				<g:sortableColumn property="color"
					title="${message(code: 'inducer.color.label', default: 'Color')}" />

			</tr>
		</thead>
		<tbody>
			<g:each in="${inducerInstanceList}" status="i" var="inducerInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

					<td><g:link action="show" id="${inducerInstance.id}">
							${fieldValue(bean: inducerInstance, field: "name")}
						</g:link></td>

					<td>
						${fieldValue(bean: inducerInstance, field: "concentration")}
					</td>

					<td>
						${fieldValue(bean: inducerInstance, field: "concentrationUnit")}
					</td>

					<td><div id="colorPickDiv"
							style="float:left; background-color: ${inducerInstance?.color}; border: 1px solid; width:25px; height:25px;" /></td>

				</tr>
			</g:each>
		</tbody>
	</table>

	<div class="pagination">
		<g:paginate total="${inducerInstanceTotal}" />
	</div>
</div>
</body>
</html>
