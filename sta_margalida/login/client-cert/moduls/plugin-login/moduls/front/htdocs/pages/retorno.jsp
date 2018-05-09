<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="locale" name="org.apache.struts.action.LOCALE" scope="session" />

<bean:define id="urlPortal">
	<bean:write name="<%=es.sistra.clientcert.front.Constants.ORGANISMO_URL%>"/>
</bean:define>

<!-- informacio -->
<p>	
	Realizando autenticación...
</p>

<form id="retorno" action="<bean:write name="urlCallbackLogin"/>" method="post">	
	<input type="hidden" name="ticket" value="<bean:write name="ticket"/>"/>
	<input type="hidden" name="language" value="<bean:write name="idioma"/>"/>
</form>

<script type="text/javascript">
$(document).ready(function() {
	$('#retorno').submit();
});
</script>