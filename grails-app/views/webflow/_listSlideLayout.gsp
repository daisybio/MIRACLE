<%@ page import="org.nanocan.rppa.layout.SlideLayout"%>

<a href="#list-slideLayout" class="skip" tabindex="-1"><g:message
		code="default.link.skip.label" default="Skip to content&hellip;" /></a>

<div id="list-slideLayout" class="content scaffold-list" role="main">
	<h1>
		List of Slide-Layout's	<!-- g:message code="default.list.label" args="[entityName]" / -->
	</h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">
			${flash.message}
		</div>
	</g:if>
	<table>
		<thead>
			<tr>

				<g:sortableColumn property="title"
					title="${message(code: 'slideLayout.title.label', default: 'Title')}" />

				<g:sortableColumn property="columnsPerBlock"
					title="${message(code: 'slideLayout.columnsPerBlock.label', default: 'Columns Per Block')}" />

				<g:sortableColumn property="rowsPerBlock"
					title="${message(code: 'slideLayout.rowsPerBlock.label', default: 'Rows Per Block')}" />

				<g:sortableColumn property="numberOfBlocks"
					title="${message(code: 'slideLayout.numberOfBlocks.label', default: 'Number Of Blocks')}" />

				<g:sortableColumn property="depositionPattern"
					title="${message(code: 'slideLayout.depositionPattern.label', default: 'Deposition Pattern')}" />

			</tr>
		</thead>
		<tbody>
			<g:each in="${slideLayoutInstanceList}" status="i"
				var="slideLayoutInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

					<td><g:link action="show" id="${slideLayoutInstance.id}">
							${fieldValue(bean: slideLayoutInstance, field: "title")}
						</g:link></td>

					<td>
						${fieldValue(bean: slideLayoutInstance, field: "columnsPerBlock")}
					</td>

					<td>
						${fieldValue(bean: slideLayoutInstance, field: "rowsPerBlock")}
					</td>

					<td>
						${fieldValue(bean: slideLayoutInstance, field: "numberOfBlocks")}
					</td>

					<td>
						${fieldValue(bean: slideLayoutInstance, field: "depositionPattern")}
					</td>

				</tr>
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${slideLayoutInstanceTotal}" />
	</div>
</div>