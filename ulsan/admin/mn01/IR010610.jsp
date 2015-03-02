<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR010610.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : ���� > �����ڷ���/���
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn01.jsp" %>
<%

  List<CommonEntity> nongHyupList  = (List<CommonEntity>)request.getAttribute("page.mn01.nongHyupList");  //�����ü������ϰ�ǥ

	List<CommonEntity> nongHyupGwaList  = (List<CommonEntity>)request.getAttribute("page.mn01.nongHyupGwaList");  //������ȯ���ϰ�ǥ
  
  String ago_acc_date  = (String)request.getAttribute("page.mn01.ago_acc_date");    // �������� �޾ƿ���
	String data_type =  (String)request.getAttribute("page.mn01.data_type");
	String SucMsg = (String)request.getAttribute("page.mn01.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
	int nongHyupListSize = 0;
	if (nongHyupList != null ) {
		nongHyupListSize = nongHyupList.size();
	}
  String acc_date = request.getParameter("acc_date");
	if (acc_date.equals("")) {
    acc_date  = TextHandler.formatDate(ago_acc_date,"yyyyMMdd","yyyy-MM-dd");
  } 

	String last_year = "";
  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }

			function goCancel() {
				var form = document.dForm;
        var data_type = form.data_type.value;
        if (data_type == "G1") {
          form.trans_gubun.value = "161";
        } else if (data_type == "G2") {
          form.trans_gubun.value = "162";
        } else if (data_type == "G3") {
          form.trans_gubun.value = "163";
        } else if (data_type == "G4") {
          form.trans_gubun.value = "164";
        }

				if (confirm("������ �ڷᱸ�а� ȸ�����ڿ� ���� ����� �ڷḦ �����մϴ�. ��� �ϰڽ��ϱ�?")) {
					form.fis_year.value = document.sForm.fis_year.value;
				  form.acc_date.value = replaceStr(document.sForm.acc_date.value, "-", "");
					form.data_type.value = document.sForm.data_type.value;
          form.action ="IR010610.etax";
          form.target = "_self";
			  	eSubmit(form);
				}				
			}

			function goTmpUpload(){
        var form = document.sForm;
        var data_type = form.data_type.value;
        if (data_type == "G1") {
          data_type = "�����ü� �����ϰ�ǥ";
        } else if (data_type == "G2") {
          data_type = "������ȯ���ϰ�ǥ(������)";
        } else if (data_type == "G3") {
          data_type = "������ȯ���ϰ�ǥ(����)";
        } else if (data_type == "G4") {
          data_type = "������ϻ���Ҽ����ϰ�ǥ";
        }

        idx1 = form.upfile.value.lastIndexOf('.');
        idx2 = form.upfile.length;
        if(form.upfile.value == ""){
          alert("���� ������ ÷���ϼ���.");
          return;
        }
        if(form.upfile.value.substring(idx1+1, idx2) != "xls"){
          alert("���� ���ϸ� ���ε� �����մϴ�.");
          form.upfile.select();
          document.selection.clear();
          return;
        }
				form.flag.value = "Y";
				if (confirm("÷���� ��������("+form.upfile.value+")�� �ڷᱸ��("+data_type+")�� ��ġ�մϱ�?")) {
					form.action = "IR010611.etax?cmd=nongHyupTmpUp&uploadDir=excel&flag=Y";
          form.target = "hidFrame";
          eSubmit(form);
				}
      } 
			
			
			function goRegister(){
        var form = document.sForm;
        
        var data_type = form.data_type.value;

        if (data_type == "G1") {
          form.trans_gubun.value = "161";
        } else if (data_type == "G2") {
          form.trans_gubun.value = "162";
        } else if (data_type == "G3") {
          form.trans_gubun.value = "163";
        } else if (data_type == "G4") {
          form.trans_gubun.value = "164";
        }

        if (data_type == "G1") {
          data_type = "�����ü� �����ϰ�ǥ";
        } else if (data_type == "G2") {
          data_type = "������ȯ���ϰ�ǥ(������)";
        } else if (data_type == "G3") {
          data_type = "������ȯ���ϰ�ǥ(����)";
        } else if (data_type == "G4") {
          data_type = "������ϻ���Ҽ����ϰ�ǥ";
        }


				if (form.flag.value != "Y")	{
					alert("�̸����� �� ���������� ����Ͻʽÿ�.");
					return;
				}
        idx1 = form.upfile.value.lastIndexOf('.');
        idx2 = form.upfile.length;
        if(form.upfile.value == ""){
          alert("���� ������ ÷���ϼ���.");
          return;
        }
        if(form.upfile.value.substring(idx1+1, idx2) != "xls"){
          alert("���� ���ϸ� ���ε� �����մϴ�.");
          form.upfile.select();
          document.selection.clear();
          return;
        }
				form.flag.value = "N";
				if (confirm("÷���� ��������("+form.upfile.value+")�� �ڷᱸ��("+data_type+")�� ��ġ�մϱ�?")) {
					form.action = "IR010610.etax?cmd=nongHyupReg&uploadDir=excel&flag=N&trans_gubun="+form.trans_gubun.value;
          form.target = "_self";
          eSubmit(form);
				}        
      }          
      
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
	<form name="dForm" method="post" action="IR010611.etax">
	<input type="hidden" name="cmd" value="nongHyupDel">
	<input type="hidden" name="fis_year">
	<input type="hidden" name="acc_date">
	<input type="hidden" name="data_type">
	<input type="hidden" name="trans_gubun" value="161">
	<input type="hidden" name="work_log" value="A01">
	</form>
  <form name="sForm" method="post" enctype="multipart/form-data"  action="IR010610.etax">
	<input type="hidden" name="cmd" value="nongHyupList">
	<input type="hidden" name="flag" value="<%=request.getParameter("flag")%>">
	<input type="hidden" name="trans_gubun" value="161">
	<input type="hidden" name="work_log" value="A01">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title1_6excel.gif"></td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"> 
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
            <tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center" height="100">
                  <tr> 
                    <td width="760"><span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�迬��
											<select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select><span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ������
										  <input type="text" class="box3" size="8" name="acc_date" value="<%=acc_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','acc_date')" align="absmiddle">
											<span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �ڷᱸ��
											<select name="data_type" iValue="<%=request.getParameter("data_type")%>">
												<option value="G1">�����ü� �����ϰ�ǥ</option>
												<option value="G3">������ ȯ���ϰ�ǥ(����)</option>
                        <option value="G2">������ ȯ���ϰ�ǥ(������)</option>
                        <option value="G4">������ϻ����(��üOCR������)</option>
                        <option value="G4">������ϻ����(�ݰ��ī�������)</option>
                        <option value="G4">������ϻ����(�ݰ���Ϲݼ�����)</option>
											</select>
											<br><br>
											<span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<input type="file" name="upfile" value="<%=request.getParameter("upfile")%>" size="40">
											<img src="../img/btn_load.gif" alt="�ҷ�����" style="cursor:hand" onClick="goTmpUpload()" align="absmiddle">
                    <td width="200"> 
										  <br><br>
                      <img src="../img/btn_order.gif" alt="���" style="cursor:hand" onClick="goRegister()" align="absmiddle">
											<img src="../img/btn_cancel.gif" alt="���" style="cursor:hand" onClick="goCancel()" align="absmiddle">
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr> 
              <td><img src="../img/box_bt.gif" width="964" height="10"></td>
            </tr>
          </table>
        </td>
			</tr>      
    </table>
    <iframe name="hidFrame" width="1000" height="500" frameborder="0"></iframe>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </body>
	<%
  if (!"".equals(SucMsg)) { %>
	<script>
	alert("<%=SucMsg%>");
	</script>
	<%
	} %>
</html>