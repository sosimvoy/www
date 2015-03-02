<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.net.InetAddress"%>

<%
String reportname = "";
String odinames = "";

String acc_year = request.getParameter("acc_year");
String acc_date = request.getParameter("acc_date").replaceAll("-","");
String report_gubun = request.getParameter("report_gubun");

String hostUrl = InetAddress.getLocalHost().getHostAddress();


reportname = "/Dform/admin/IR070420.ozr";
odinames = "IR070420";

%>

<html>
    <body>
        <object id="ZTransferX" width="0" height="0" CLASSID="CLSID:C7C7225A-9476-47AC-B0B0-FF3B79D55E67" codebase="http://<%=hostUrl%>/oz51/ozrviewer51/ZTransferX_2,2,2,5.cab#version=2,2,2,5">
            <param name="download.Server" value="http://<%=hostUrl%>/oz51/ozrviewer51">
            <param name="download.Port" value="80">
            <param name="download.Instruction" value="ozrviewer.idf">
            <param name="install.Base" value="<PROGRAMS>/Forcs">
            <param name="install.NameSpace" value="ulsancity51">
        </object>
        <object id="ozviewer" width="100%" height="100%" CLASSID="CLSID:0DEF32F8-170F-46f8-B1FF-4BF7443F5F25">
            <param name="connection.servlet" value="http://<%=hostUrl%>/oz51/server">
            <param name="connection.reportname" value="<%=reportname%>">
            <param name="viewer.configmode" value="html">
            <param name="odi.odinames" value="<%=odinames%>">

			<param name="odi.<%=odinames%>.pcount" value="3">
			<param name="odi.<%=odinames%>.args1" value="ACC_YEAR=<%=acc_year%>">
			<param name="odi.<%=odinames%>.args2" value="ACC_DATE=<%=acc_date%>">
			<param name="odi.<%=odinames%>.args3" value="REPORT_GUBUN=<%=report_gubun%>">
			
			<param name="viewer.isframe" value="false">
			<param name="viewer.namespace" value="ulsancity51\ozviewer">

			<param name="excel.filename" value="<%=acc_year%>/<%=acc_date%>_세입일계표조정내역.xls">
			<param name="export.applyformat" value="xls,pdf,hwp,doc">
			<param name="excel.savenumbertype" value="true">
			<param name="excel.removeblank" value="true">
			<param name="export.executefile" value="true">
<!--param name="toolbar.all" value="false"-->
        </object>
    </body>
</html>