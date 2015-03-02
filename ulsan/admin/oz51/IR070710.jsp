<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>


<%
String reportname = "";
String odinames = "";

String acc_year = request.getParameter("acc_year");		 //회계연도
String acc_month = request.getParameter("acc_month");  //회계월

String first_date = acc_year + acc_month + "01";    //월초일		
String last_date = acc_year + acc_month + TextHandler.getDaysOfMonth(acc_year, acc_month);  //월말일
String last_day = TextHandler.getDaysOfMonth(acc_year, acc_month);  //마지막일자

String first_business_date = (String)request.getAttribute("page.mn07.first_business_date");
String last_business_date = (String)request.getAttribute("page.mn07.last_business_date");


reportname = "/Dform/admin/IR070710.ozr";
odinames = "IR070710";


acc_month = acc_year + acc_month;
//out.println("acc_month="+acc_month);
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
			<param name="odi.<%=odinames%>.args1" value="ACC_YEAR=<%=acc_year%>">
			<param name="odi.<%=odinames%>.args2" value="ACC_MONTH=<%=acc_month%>">
			<param name="odi.<%=odinames%>.args3" value="FIRST_BUSINESS_DATE=<%=first_business_date%>">
			<param name="odi.<%=odinames%>.args4" value="LAST_BUSINESS_DATE=<%=last_business_date%>">
			<param name="odi.<%=odinames%>.args5" value="FIRST_DATE=<%=first_date%>">
			<param name="odi.<%=odinames%>.args6" value="LAST_DATE=<%=last_date%>">
			<param name="odi.<%=odinames%>.args7" value="LAST_DAY=<%=last_day%>">
			
			<param name="viewer.isframe" value="false">
			<param name="viewer.namespace" value="ulsancity51\ozviewer">

			<param name="excel.filename" value="국세수납총합계표.xls">
			<param name="export.applyformat" value="xls,pdf,hwp,doc">
			<param name="excel.savenumbertype" value="true">
			<param name="excel.removeblank" value="true">
			<param name="export.executefile" value="true">
<!--param name="toolbar.all" value="false"-->
        </object>
    </body>
</html>