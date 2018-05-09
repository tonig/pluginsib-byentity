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
<p class="alerta">	
	<bean:message name="texto" />
	<logic:notEmpty name="respuesta">
		<bean:write name="respuesta" />
	</logic:notEmpty>
</p>

<!--  Enlace volver  -->
<p class="volver">
	<html:link href="<%=urlPortal%>">
		<bean:message key="pago.volver.asistenteTramitacion" />
	</html:link>
</p>