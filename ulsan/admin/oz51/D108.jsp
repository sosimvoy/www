<%
String reportname = "";
String odinames = "";

String acc_year = request.getParameter("acc_year");
String acc_date = request.getParameter("acc_date").replaceAll("-","");
String acc_type = request.getParameter("acc_type");
String part_code = request.getParameter("part_code");
String acc_code = request.getParameter("acc_code");


reportname = "/Dform/admin/D108.ozr";
odinames = "D108";


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

			<param name="odi.<%=odinames%>.pcount" value="5">
			<param name="odi.<%=odinames%>.args1" value="ACC_YEAR=<%=acc_year%>">
			<param name="odi.<%=odinames%>.args2" value="ACC_DATE=<%=acc_date%>">
			<param name="odi.<%=odinames%>.args3" value="ACC_TYPE=<%=acc_type%>">
			<param name="odi.<%=odinames%>.args4" value="PART_CODE=<%=part_code%>">
			<param name="odi.<%=odinames%>.args5" value="ACC_CODE=<%=acc_code%>">
			
			<param name="viewer.isframe" value="false">
			<param name="viewer.namespace" value="ulsancity51\ozviewer">

			<param name="export.applyformat" value="xls,pdf,hwp,doc">
			<param name="excel.savenumbertype" value="true">
			<param name="excel.removeblank" value="true">
			<param name="export.executefile" value="true">
<!--param name="toolbar.all" value="false"-->
        </object>
    </body>
</html>