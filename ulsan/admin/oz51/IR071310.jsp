<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>


<%
String reportname = "";
String odinames = "";

String queprtty =  request.getParameter("queprtty");
String quepartCode = request.getParameter("quepartCode");
String queyear = request.getParameter("queyear");
String quemm = request.getParameter("quemm");
String que_date = request.getParameter("que_date").replaceAll("-","");;
String end_date = request.getParameter("end_date").replaceAll("-","");;
String quetaxgbn = request.getParameter("quetaxgbn");

String FileName = "";
odinames = "IR071310";

if("1".equals(queprtty)) {  //¼öÀÔÀÏ°èÇ¥
reportname = "/Dform/admin/IR071310_DAY.ozr";
FileName = "ÃÑ°ý ¼öÀÔÀÏ°èÇ¥";

} else {
reportname = "/Dform/admin/IR071310_MONTH.ozr";
FileName = "ÃÑ°ý ¼öÀÔ¿ù°èÇ¥";

}


%>

<html>
    <body>
        <object id="ZTransferX" width="0" height="0" CLASSID="CLSID:C7C7225A-9476-47AC-B0B0-FF3B79D55E67" codebase="http://193.40.23.12/oz51/ozrviewer51/ZTransferX_2,2,2,5.cab#version=2,2,2,5">
            <param name="download.Server" value="http://193.40.23.12/oz51/ozrviewer51">
            <param name="download.Port" value="80">
            <param name="download.Instruction" value="ozrviewer.idf">
            <param name="install.Base" value="<PROGRAMS>/Forcs">
            <param name="install.NameSpace" value="ulsancity51">
        </object>
        <object id="ozviewer" width="100%" height="100%" CLASSID="CLSID:0DEF32F8-170F-46f8-B1FF-4BF7443F5F25">
            <param name="connection.servlet" value="http://193.40.23.12/oz51/server">
            <param name="connection.reportname" value="<%=reportname%>">
            <param name="viewer.configmode" value="html">
            <param name="odi.odinames" value="<%=odinames%>">

			<param name="odi.<%=odinames%>.pcount" value="7">
			<param name="odi.<%=odinames%>.args1" value="QUEPRTTY=<%=queprtty%>">
			<param name="odi.<%=odinames%>.args2" value="QUEPARTCODE=<%=quepartCode%>">
			<param name="odi.<%=odinames%>.args3" value="QUEYEAR=<%=queyear%>">
			<param name="odi.<%=odinames%>.args4" value="QUEMM=<%=quemm%>">
			<param name="odi.<%=odinames%>.args5" value="QUE_DATE=<%=que_date%>">
			<param name="odi.<%=odinames%>.args6" value="END_DATE=<%=end_date%>">
			<param name="odi.<%=odinames%>.args7" value="QUETAXGBN=<%=quetaxgbn%>">
			
			<param name="viewer.isframe" value="false">
			<param name="viewer.namespace" value="ulsancity51\ozviewer">

			<param name="excel.filename" value="<%=FileName%>.xls">
			<param name="export.applyformat" value="xls,pdf,hwp,doc">
			<param name="excel.savenumbertype" value="true">
			<param name="excel.removeblank" value="true">
			<param name="export.executefile" value="true">
<!--param name="toolbar.all" value="false"-->
        </object>
    </body>
</html>