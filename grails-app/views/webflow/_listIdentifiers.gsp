<a href="#list-identifier" class="skip" tabindex="-1"><g:message
		code="default.link.skip.label" default="Skip to content&hellip;" /></a>
<div id="list-identifier" class="content scaffold-list" role="main">
	<h1>
		<g:message code="default.list.label" args="[entityName]" />
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
					title="${message(code: 'identifier.name.label', default: 'Name')}" />

				<g:sortableColumn property="type"
					title="${message(code: 'identifier.type.label', default: 'Type')}" />

				<g:sortableColumn property="accessionNumber"
					title="${message(code: 'identifier.accessionNumber.label', default: 'Accession Number')}" />

				<th><g:message code="identifier.sample.label" default="Sample" /></th>

			</tr>
		</thead>
		<tbody>
			<g:each in="${identifierInstanceList}" status="i"
				var="identifierInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

					<td><g:link action="show" id="${identifierInstance.id}">
							${fieldValue(bean: identifierInstance, field: "name")}
						</g:link></td>

					<td>
						${fieldValue(bean: identifierInstance, field: "type")}
					</td>

					<td>
						${fieldValue(bean: identifierInstance, field: "accessionNumber")}
					</td>

					<td>
						${fieldValue(bean: identifierInstance, field: "sample")}
					</td>

				</tr>
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${identifierInstanceTotal}" />
	</div>
</div>
