<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR050511.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 > 계좌등록-등록팝업
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
   String errMsg = (String)request.getAttribute("page.mn05.ErrMsg");
	 if (errMsg == null) {
		 errMsg = "";
	 }

	 String sucMsg = (String)request.getAttribute("page.mn05.SucMsg");
	 if (sucMsg == null) {
		 sucMsg = "";
	 }

	 CommonEntity result = (CommonEntity)request.getAttribute("page.mn05.result");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="../js/base.js"></script>
  	<script language="javascript"> 
			function goRegister(){
				var form = document.sForm;

				if (form.owner_nm.value == "") {
					alert("예금주명은 필수입니다.");
					return;
				}
				if (form.send_yn.value == "Y") {
					alert("수취인조회 후 등록하시기바랍니다.");
					return;
				}
				form.action = "IR050511.etax";
				form.cmd.value = "bankAcctReg";
				eSubmit(form);
				form.send_yn.value = "N";
			}

			function goTrans() {
				var form = document.sForm;
				form.cmd.value = "bankAcctTrans";
				form.action = "IR050511.etax";
				form.send_yn.value = "Y";
				eSubmit(form);
			}

			function goClose() {
				var form = document.sForm;
				if (form.send_yn.value == "Y") {
					alert("은행과 통신중입니다. 기다리시기 바랍니다.");
					return;
				}
				self.close();
			}
    </script>
  </head>
  <body topmargin="0" leftmargin="10">
	<form name="sForm" method="post" action="IR050511.etax">
	<input type="hidden" name="cmd" value="bankAcctReg">
	<input type="hidden" name="send_yn" value="N">
	<input type="hidden" name="trans_gubun" value="593">
	<input type="hidden" name="work_log" value="A05">
	<input type="hidden" name="bank_cd" value="<%=request.getParameter("bank_cd")%>">
	<input type="hidden" name="fis_year" value="<%=request.getParameter("fis_year")%>">
	<input type="hidden" name="dept_cd" value="<%=request.getParameter("dept_cd")%>">
	<input type="hidden" name="acct_nm" value="<%=request.getParameter("acct_nm")%>">
	<input type="hidden" name="acct_gubun" value="<%=request.getParameter("acct_gubun")%>">
	<input type="hidden" name="acc_cd" value="<%=request.getParameter("acc_cd")%>">
	<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
	<input type="hidden" name="juhaeng" value="<%=request.getParameter("juhaeng")%>">
    <table width="300" border="0" cellspacing="0" cellpadding="0">
			<tr>
        <td>&nbsp;</td>
			</tr>
			<tr>
        <td>&nbsp;</td>
			</tr>
			<tr>
        <td width="290">
				  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" background="../img/box_bg_acct.gif">
					  <tr> 
              <td><img src="../img/box_top.gif" width="280" height="10"></td>
            </tr>
						<tr> 
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>
							  <span style="width:20"></span>
							  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
						    은행<span style="width:24"></span>
								<% if ("039".equals(request.getParameter("bank_cd")) ) { %>
								<input type="text" class="box3" size="10" name="bank_nm" value="경남은행">
								<% } else if ("011".equals(request.getParameter("bank_cd")) ) { %>
								<input type="text" class="box3" size="10" name="bank_nm" value="농협">
								<% } %>
							</td>
            </tr>
						<tr> 
              <td>&nbsp;</td>
            </tr>
						<tr> 
              <td>
							  <span style="width:20"></span>
							  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
						    계좌번호
								<input type="text" class="box3" size="20" name="acct_no" value="<%=request.getParameter("acct_no")%>" userType="account"> 
							</td>
            </tr>
						<tr> 
              <td>&nbsp;</td>
            </tr>
						<tr> 
              <td>
							  <span style="width:20"></span>
							  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
						    예금주명
								<% if (result != null && "0000".equals(result.getString("common10")) ) { %>
								<input type="text" class="box3" size="15" name="owner_nm" value="<%=result.getString("detail05")%>" readonly> 
								<% } else { %>
								<input type="text" class="box3" size="15" name="owner_nm" value="" readonly> 
								<% } %>
							</td>
            </tr>
						<tr> 
              <td>&nbsp;</td>
            </tr>
						<tr> 
              <td><img src="../img/box_bt.gif" width="280" height="10"></td>
            </tr>
          </table>
				</td>
			</tr>
			<tr> 
        <td>&nbsp;</td>
      </tr>
			<tr> 
        <td>&nbsp;</td>
      </tr>
			<tr> 
        <td align="left">
				  <img src="../img/btn_receiveinfo1.gif" alt="수취인조회" style="cursor:hand" onClick="goTrans()" align="absmiddle">
					<span style="width:70"></span>
				  <img src="../img/btn_order.gif" alt="등록" style="cursor:hand" onClick="goRegister()" align="absmiddle">
					<img src="../img/btn_close.gif" alt="닫기" style="cursor:hand" onClick="goClose()" align="absmiddle">
        </td>
      </tr>
    </table>
		<% if (result != null) {
			   if (!"0000".equals(result.getString("common10")) ) { %>
				   <script>
					 alert("수취조회결과 계좌가 일치하지 않습니다. 다시 등록하시기 바랍니다.");
				   self.close();
					 </script>
		<%   } else if ("0000".equals(result.getString("common10")) ) { %>
				   <script>
						 var form= document.sForm;
					   form.send_yn.value="N";
					 </script>
		<%   }
		   } %>
		</form>
  </body>
	<% if (!"".equals(errMsg)) { %>
	<script>
	  alert("<%=errMsg%>");
		self.close();
	</script>
	<% } %>

	<% if (!"".equals(sucMsg)) { %>
	<script>
	  alert("<%=sucMsg%>");
		self.close();
	</script>
	<% } %>
</html>