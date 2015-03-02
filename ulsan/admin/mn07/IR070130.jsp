<%
/*************************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070130.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-09
* ���α׷�����		: �ϰ�/���� > ������ȸ > ���Կ���ǥ(�Ϲ�ȸ�輼��)
*************************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");

	List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");   // (�ߺ�)
	List<CommonEntity> reportList2 = (List<CommonEntity>)request.getAttribute("page.mn07.reportList2"); // (����)

    String semok_name = "";   
    String saveFile = request.getParameter("saveFile");
  
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=���Կ���ǥ(�Ϲ�ȸ�輼��)_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
        response.setHeader("Content-Description", "JSP Generated Data");
    }	 
%>

<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<%if(saveFile.equals("")){%>
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<%}%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%if(saveFile.equals("1")){%>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">

<%}%>

<style>
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333; text-decoration: underline}
-->

    :root p {font-size:20pt; padding-left:340px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html p {font-size:20pt; padding-left:340px;} /*IE8 ���� */   
 * + html p {font-size:20pt; padding-left:340px;} /*IE7 ���� */   
 *   html p {font-size:20pt; padding-left:340px;} /*IE6 ���� */ 

@media print {
  .noprint {
	  display:none;
	}
}

</style>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>
<script language="javascript" src="../js/tax_common.js"></script>	
<script language="javascript" src="../js/report.js"></script>	
<script language="javascript">

function init() {
    typeInitialize();
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.target = "_self";
    form.action = "IR070130.etax";
    form.submit();
}

function goPrint() {
    factory.printing.header = ""; // Header�� �� ����  
		factory.printing.footer = ""; // Footer�� �� ����  
		factory.printing.portrait = false; // false �� �����μ�, true �� ���� �μ�  
		factory.printing.leftMargin = 1.0; // ���� ���� ������  
		factory.printing.topMargin = 1.0; // �� ���� ������  
		factory.printing.rightMargin = 1.0; // ������ ���� ������  
		factory.printing.bottomMargin = 0.5; // �Ʒ� ���� ������  
		factory.printing.Print(true); // ����ϱ� 
}
    
</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" onload="init();">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070130.etax">
<input type="hidden" name="cmd" value="dayReport20">      
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<div class="noprint">
<table width="1000" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="980"> 
            <%if(saveFile.equals("")){%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr> 
					<td>
						<img src="../img/btn_excel.gif" alt="Excel" onClick="goSave('1')" style="cursor:hand" align="absmiddle">
						<img src="../img/btn_print.gif" alt="�μ�"  onClick="goPrint();" style="cursor:hand" align="absmiddle">		  
					</td>
				</tr>
			</table>
            <%}%>
		</td>
	</tr>
</table>
</div>

<%
if (reportList.size() > 0 && reportList != null) { 
%>

<div id="box" align="center">
<table width="980" border="0" cellspacing="0" cellpadding="1">
  <tr>
    <td>
      <table width="960" border="0" cellspacing="0" cellpadding="1" class="report">
        <tr height="100"> 
          <td> 
            <p><b><u>
              <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_year"),"-",""),"yyyyMMdd","yyyy")%>�� <%=(Integer.parseInt(request.getParameter("acc_date").substring(0,4))-Integer.parseInt(request.getParameter("acc_year")))*12+Integer.parseInt(TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM"))%>�� ���� ����ǥ
            </u></b></p>
          </td>
          <td colspan="2" width="200" align="right" valign="top" rowspan="2"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ��
            %>
            <table width="100" height="50" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="25">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
                </tr>
                <tr height="25">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
          </td>
		    </tr>
		    <tr height="40">
		      <td class="re01">
		        <div align="left">
              <%if(request.getParameter("part_code").equals("02130") || request.getParameter("part_code").equals("")){
                    out.println("�ߺμҹ漭");
                }else if(request.getParameter("part_code").equals("02140")){
                    out.println("���μҹ漭");
                }%>
              (<%=request.getParameter("acc_year")%>�⵵)
            </div>
		      </td>  
          <td class="re01">
		        <div align="right">(����: ��)</div>
		      </td>   
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
	    <table width="960" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
        <tr height="45">
          <td rowspan="2" width="140" class="re01"> 
            <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;��</div>
          </td>
          <td rowspan="2" width="120" class="re01">
            <div align="center">��&nbsp;&nbsp;��&nbsp;&nbsp;��</div>
          </td>
          <td colspan="4" width="520" class="re01">
            <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</div>
          </td>
          <td rowspan="2" width="140" class="re01">
            <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;��</div>
          </td>
          <td rowspan="2" width="40" class="re01">
            <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;��</div>
          </td>  
        </tr>
        <tr height="45">
          <td width="130" class="re01">
            <div align="center">�� �� ��</div>
          </td>
          <td width="130" class="re01">
            <div align="center">�� �� �� ��</div>
          </td>
          <td width="130" class="re01">
            <div align="center">�� �� �� ��</div>
          </td>
          <td width="130" class="re01">
            <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</div>
          </td>
        </tr>
        <% 
          for (int i=0; i < reportList.size(); i++) {
            CommonEntity rowData = (CommonEntity)reportList.get(i);
            
            if(rowData.getString("M370_SEMOKNAME").equals("")){
              semok_name = "��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��"; 
            }else{
              semok_name = rowData.getString("M370_SEMOKNAME"); 
          }
        %>
        <tr height="30">
          <td class="re01"> 
            <div align="center"><%=semok_name%></div>     
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("DIFFERENCE"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("TOT_AMT"))%></div>
          </td>
          <td class="re02"> 
            <div align="center">&nbsp;</div>
          </td>    
        </tr>  
        <%}%>
      </table>
    </td>
  </tr>
  <tr height="70">
    <td>&nbsp;</td>
  </tr>
  <tr height="100">
    <td>
      <table width="960" border="0" cellspacing="0" cellpadding="4" align="center">
        <tr height="25"> 
          <td colspan="17" style="font-size:11pt;"> 
            <div align="center">���� ���� ������.
              <br><%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>��
                  <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>��
                  <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>��
            </div>
          </td>
          <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����) %>
          <td rowspan="2" width="70">
          <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="70" height="70" align="right">
          </td>
          <% }%>
        </tr>
        <tr height="200">
          <td colspan="10" style="font-size:12pt;">��걤���� ����¡���� ����</td>
          <td colspan="7" style="font-size:12pt;"> 
            <div align="right">�泲���� ����û����<br>��&nbsp; ��&nbsp; ��&nbsp; ��&nbsp;&nbsp;��&nbsp; ��&nbsp; ��</div>
          </td>
        </tr>
      </table>
	  </td>
  </tr>
</table>

<%}%>

<%
  if (reportList2 != null) { 
      if(reportList2.size() > 0){
%>

<table width="980" border="0" cellspacing="0" cellpadding="1">
  <tr>
    <td>
      <table width="960" border="0" cellspacing="0" cellpadding="1" class="report">
        <tr height="100"> 
          <td> 
            <p><b><u>
              <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>�� <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>�� ���� ����ǥ
            </u></b></p>
          </td>
          <td colspan="2" width="200" align="right" valign="top" rowspan="2"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ��
            %>
            <table width="100" height="50" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="25">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
                </tr>
                <tr height="25">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
          </td>
		    </tr>
		    <tr height="40">
		      <td class="re01">
		        <div align="left">
              <%//=request.getParameter("part_name")%>���μҹ漭(<%=request.getParameter("acc_year")%>�⵵)
            </div>
		      </td>  
          <td class="re01">
		        <div align="right">(����: ��)</div>
		      </td>   
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
	    <table width="960" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
        <tr height="45">
          <td rowspan="2" width="140" class="re01">
            <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;��</div>
          </td>
          <td rowspan="2" width="120" class="re01">
            <div align="center">��&nbsp;&nbsp;��&nbsp;&nbsp;��</div>
          </td>
          <td colspan="4" width="520" class="re01">
            <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</div>
          </td>
          <td rowspan="2" width="140" class="re01">
            <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;��</div>
          </td>
          <td rowspan="2" width="40" class="re01">
            <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;��</div>
          </td>  
        </tr>
        <tr height="45">
          <td width="130" class="re01">
            <div align="center">�� �� ��</div>
          </td>
          <td width="130" class="re01">
            <div align="center">�� �� �� ��</div>
          </td>
          <td width="130" class="re01">
            <div align="center">�� �� �� ��</div>
          </td>
          <td width="130" class="re01">
            <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</div>
          </td>
        </tr>
        <% 
          for (int i=0; i < reportList2.size(); i++) {
            CommonEntity rowData = (CommonEntity)reportList2.get(i);
            
            if(rowData.getString("M370_SEMOKNAME").equals("")){
              semok_name = "��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��"; 
            }else{
              semok_name = rowData.getString("M370_SEMOKNAME"); 
          }
        %>
        <tr height="30">
          <td class="re01"> 
            <div align="center"><%=semok_name%></div>     
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("DIFFERENCE"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("TOT_AMT"))%></div>
          </td>
          <td class="re02"> 
            <div align="center">&nbsp;</div>
          </td>    
        </tr>  
        <%}%>
      </table>
    </td>
  </tr>
  <tr height="70">
    <td>&nbsp;</td>
  </tr>
  <tr height="100">
    <td>
      <table width="960" border="0" cellspacing="0" cellpadding="4" class="basic" align="center">
        <tr height="25"> 
          <td colspan="17" style="font-size:11pt;"> 
            <div align="center">���� ���� ������.
              <br><%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>��
                  <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>��
                  <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>��
            </div>
          </td>
          <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����) %>
          <td rowspan="2" width="70">
          <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="70" height="70" align="right">
          </td>
          <% }%>
        </tr>
        <tr height="200">
          <td colspan="10" style="font-size:12pt;">��걤���� ����¡���� ����</td>
          <td colspan="7" style="font-size:12pt;"> 
            <div align="right">�泲���� ����û����<br>��&nbsp; ��&nbsp; ��&nbsp; ��&nbsp;&nbsp;��&nbsp; ��&nbsp; ��</div>
          </td>
        </tr>
      </table>
	  </td>
  </tr>
</table>

</div>
<%  }
  }%>
 
<%
if (reportList.size() == 0) { 
%>
<table width="1000" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
<%}%>

</form>
</body>
</html>
