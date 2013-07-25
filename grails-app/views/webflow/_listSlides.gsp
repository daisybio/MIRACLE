<%@ page import="org.nanocan.rppa.scanner.Slide"%>
<a href="#list-slide" class="skip" tabindex="-1"><g:message
		code="default.link.skip.label" default="Skip to content&hellip;" /></a>
<div id="list-slide" class="content scaffold-list" role="main">
	<h1>
		List of Slide's	<!-- g:message code="default.list.label" args="[entityName]" / -->
	</h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">
			${flash.message}
		</div>
	</g:if>
	<table>
		<thead>
			<tr>
				<g:sortableColumn property="barcode" title="Barcode"
					titleKey="slide.barcode.label">
					<g:message code="slide.barcode.label" default="Barcode" />
				</g:sortableColumn>

				<g:sortableColumn property="title" title="Title"
					titleKey="slide.title.label">
					<g:message code="slide.title.label" default="Title" />
				</g:sortableColumn>

				<g:sortableColumn property="antibody.name" title="Antibody"
					titleKey="slide.antibody.label">
					<g:message code="slide.antibody.label" default="Antibody" />
				</g:sortableColumn>

				<g:sortableColumn property="dateOfStaining"
					title="${message(code: 'slide.dateOfStaining.label', default: 'Date')}" />

				<g:sortableColumn property="experimenter.username"
					title="Experimenter" titleKey="slide.experimenter.label">
					<g:message code="slide.experimenter.label" default="Experimenter" />
				</g:sortableColumn>

				<g:sortableColumn property="laserWavelength"
					title="${message(code: 'slide.laserWavelength.label', default: '~')}" />

				<g:sortableColumn property="photoMultiplierTube"
					title="${message(code: 'slide.photoMultiplierTube.label', default: 'PMT')}" />

				<g:sortableColumn property="resultFile.fileName" title="Result File"
					titleKey="slide.resultFile.label">
					<g:message code="slide.resultFile.label" default="Result File" />
				</g:sortableColumn>

			</tr>
		</thead>
		<tbody>
			<g:each in="${slideInstanceList}" status="i" var="slideInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

					<td><g:link action="show" id="${slideInstance.id}">
							${slideInstance?.barcode}
						</g:link></td>

					<td><g:link action="show" id="${slideInstance.id}">
							${slideInstance?.toString()}
						</g:link></td>

					<td>
						${fieldValue(bean: slideInstance, field: "antibody")}
					</td>

					<td><g:formatDate type="date"
							date="${slideInstance.dateOfStaining}" /></td>

					<td>
						${fieldValue(bean: slideInstance, field: "experimenter")}
					</td>

					<td>
						${fieldValue(bean: slideInstance, field: "laserWavelength")}
					</td>

					<td>
						${fieldValue(bean: slideInstance, field: "photoMultiplierTube")}
					</td>

					<td>
						${fieldValue(bean: slideInstance, field: "resultFile")}
					</td>

				</tr>
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${slideInstanceTotal}" />
	</div>
</div>
