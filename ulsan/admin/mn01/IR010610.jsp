<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR010610.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 세입 > 농협자료등록/취소
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn01.jsp" %>
<%

  List<CommonEntity> nongHyupList  = (List<CommonEntity>)request.getAttribute("page.mn01.nongHyupList");  //광역시세세입일계표

	List<CommonEntity> nongHyupGwaList  = (List<CommonEntity>)request.getAttribute("page.mn01.nongHyupGwaList");  //과오납환부일계표
  
  String ago_acc_date  = (String)request.getAttribute("page.mn01.ago_acc_date");    // 전영업일 받아오기
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
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
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

				if (confirm("선택한 자료구분과 회계일자에 맞춰 등록한 자료를 삭제합니다. 계속 하겠습니까?")) {
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
          data_type = "광역시세 세입일계표";
        } else if (data_type == "G2") {
          data_type = "과오납환부일계표(과오납)";
        } else if (data_type == "G3") {
          data_type = "과오납환부일계표(세입)";
        } else if (data_type == "G4") {
          data_type = "차량등록사업소세입일계표";
        }

        idx1 = form.upfile.value.lastIndexOf('.');
        idx2 = form.upfile.length;
        if(form.upfile.value == ""){
          alert("엑셀 파일을 첨부하세요.");
          return;
        }
        if(form.upfile.value.substring(idx1+1, idx2) != "xls"){
          alert("엑셀 파일만 업로드 가능합니다.");
          form.upfile.select();
          document.selection.clear();
          return;
        }
				form.flag.value = "Y";
				if (confirm("첨부한 엑셀파일("+form.upfile.value+")과 자료구분("+data_type+")과 일치합니까?")) {
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
          data_type = "광역시세 세입일계표";
        } else if (data_type == "G2") {
          data_type = "과오납환부일계표(과오납)";
        } else if (data_type == "G3") {
          data_type = "과오납환부일계표(세입)";
        } else if (data_type == "G4") {
          data_type = "차량등록사업소세입일계표";
        }


				if (form.flag.value != "Y")	{
					alert("미리보기 후 엑셀파일을 등록하십시오.");
					return;
				}
        idx1 = form.upfile.value.lastIndexOf('.');
        idx2 = form.upfile.length;
        if(form.upfile.value == ""){
          alert("엑셀 파일을 첨부하세요.");
          return;
        }
        if(form.upfile.value.substring(idx1+1, idx2) != "xls"){
          alert("엑셀 파일만 업로드 가능합니다.");
          form.upfile.select();
          document.selection.clear();
          return;
        }
				form.flag.value = "N";
				if (confirm("첨부한 엑셀파일("+form.upfile.value+")과 자료구분("+data_type+")과 일치합니까?")) {
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
									    회계연도
											<select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select><span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계일자
										  <input type="text" class="box3" size="8" name="acc_date" value="<%=acc_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','acc_date')" align="absmiddle">
											<span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    자료구분
											<select name="data_type" iValue="<%=request.getParameter("data_type")%>">
												<option value="G1">광역시세 세입일계표</option>
												<option value="G3">과오납 환부일계표(세입)</option>
                        <option value="G2">과오납 환부일계표(과오납)</option>
                        <option value="G4">차량등록사업소(자체OCR수납분)</option>
                        <option value="G4">차량등록사업소(금결원카드수납분)</option>
                        <option value="G4">차량등록사업소(금결원일반수납분)</option>
											</select>
											<br><br>
											<span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<input type="file" name="upfile" value="<%=request.getParameter("upfile")%>" size="40">
											<img src="../img/btn_load.gif" alt="불러오기" style="cursor:hand" onClick="goTmpUpload()" align="absmiddle">
                    <td width="200"> 
										  <br><br>
                      <img src="../img/btn_order.gif" alt="등록" style="cursor:hand" onClick="goRegister()" align="absmiddle">
											<img src="../img/btn_cancel.gif" alt="취소" style="cursor:hand" onClick="goCancel()" align="absmiddle">
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