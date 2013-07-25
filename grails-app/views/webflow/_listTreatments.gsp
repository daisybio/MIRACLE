<%@ page import="org.nanocan.rppa.layout.Treatment"%>

<a href="#list-treatment" class="skip" tabindex="-1"><g:message
		code="default.link.skip.label" default="Skip to content&hellip;" /></a>


<div id="list-treatment" class="content scaffold-list" role="main">
	<h1>
		List Of Treatment's
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
					title="${message(code: 'treatment.name.label', default: 'Name')}" />

				<g:sortableColumn property="color"
					title="${message(code: 'treatment.color.label', default: 'Color')}" />

				<g:sortableColumn property="comments"
					title="${message(code: 'treatment.comments.label', default: 'Comments')}" />

			</tr>
		</thead>
		<tbody>
			<g:each in="${treatmentInstanceList}" status="i"
				var="treatmentInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

					<td><g:link action="show" id="${treatmentInstance.id}">
							${fieldValue(bean: treatmentInstance, field: "name")}
						</g:link></td>

					<td><div id="colorPickDiv"
							style="float:left; background-color: ${treatmentInstance?.color}; border: 1px solid; width:25px; height:25px;" /></td>

					<td>
						${fieldValue(bean: treatmentInstance, field: "comments")}
					</td>

				</tr>
			</g:each>
		</tbody>
	</table>

	<div class="pagination">
		<g:paginate total="${treatmentInstanceTotal}" />
	</div>
</div>
