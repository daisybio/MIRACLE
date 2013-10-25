<%@ page import="org.nanocan.rppa.scanner.Antibody"%>

<a href="#list-antibody" class="skip" tabindex="-1"><g:message
		code="default.link.skip.label" default="Skip to content&hellip;" /></a>

<div id="list-antibody" class="content scaffold-list" role="main">
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
					title="${message(code: 'antibody.name.label', default: 'Name')}" />

				<g:sortableColumn property="concentration"
					title="${message(code: 'antibody.concentration.label', default: 'Concentration')}" />

				<g:sortableColumn property="concentrationUnit"
					title="${message(code: 'antibody.concentrationUnit.label', default: 'Concentration Unit')}" />

				<g:sortableColumn property="comments"
					title="${message(code: 'antibody.comments.label', default: 'Comments')}" />

			</tr>
		</thead>
		<tbody>
			<g:each in="${antibodyInstanceList}" status="i"
				var="antibodyInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

					<td><g:link action="show" id="${antibodyInstance.id}">
							${fieldValue(bean: antibodyInstance, field: "name")}
						</g:link></td>

					<td>
						${fieldValue(bean: antibodyInstance, field: "concentration")}
					</td>

					<td>
						${fieldValue(bean: antibodyInstance, field: "concentrationUnit")}
					</td>

					<td>
						${fieldValue(bean: antibodyInstance, field: "comments")}
					</td>

				</tr>
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${antibodyInstanceTotal}" />
	</div>
</div>

