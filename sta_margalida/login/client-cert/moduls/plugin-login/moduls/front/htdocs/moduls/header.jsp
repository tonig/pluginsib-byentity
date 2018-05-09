<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>

	<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
	<bean:define id="urlPortal">
		<bean:write name="<%=es.sistra.clientcert.front.Constants.ORGANISMO_URL%>"/>
	</bean:define>
	
	<!-- logo illes balears -->
	<div id="cap">
		<html:link href="<%=urlPortal%>" paramId="lang" paramName="lang" accesskey="0" >
			<img id="logoCAIB" class="logo" src="<bean:write name="<%=es.sistra.clientcert.front.Constants.ORGANISMO_LOGO%>"/>" alt="<bean:write name="<%=es.sistra.clientcert.front.Constants.ORGANISMO_NAME%>"/>" />
		</html:link>
	</div>
	<!-- /logo illes balears -->
	
	<!-- titol aplicacio -->
	<p id="titolAplicacio"><bean:message key="tituloAplicacion"/></p>
	<!-- /titol aplicacio -->		