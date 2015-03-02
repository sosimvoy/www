<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR090511.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-08-09
* 프로그램내용	   : 세외수입 > 징수결의 수정
****************************************************************/
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>

<% 
    CommonEntity jingsuView = (CommonEntity)request.getAttribute("page.mn04.jingsuView");

		String SucMsg = (String)request.getAttribute("page.mn04.SucMsg");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">
  function init() {
		typeInitialize();
		<% if (SucMsg != null && !"".equals(SucMsg)) { %>
       alert("<%=SucMsg%>");
	     self.close();	
   <% } %>
  }
	
  function goUpdate()	{
  	var form = document.sForm;
		form.action = "IR040411.etax";
		form.cmd.value = "jingsuUpdate";
		form.target = "_self";
  	eSubmit(form);
  }

function addComma(price){
  
  var str = new String(price);
 var len = str.length;
 
  var s1 = "";
  var s2 = "";
 var i = 0;
 
  if(len <= 3){
   if(isNaN(str)){
    str = '0'; 
   }
    return str;
  }else{
   
    for(i = len-1 ; i >= 0; i--){
      s1 += str.charAt(i);
      
     }
    
    for(i = len-1 ; i >= 0; i--){
      s2 += s1.charAt(i);
      
      if(i % 3 == 0 && i != 0){
       s2 += "";
      }
    }    
    return s2;
  }
}

function cal(){
 var bonAmt = parseInt(document.getElementById("bonAmt").value, 10);
 var gasanAmt = parseInt(document.getElementById("gasanAmt").value, 10);
 var interestAmt = parseInt(document.getElementById("interestAmt").value, 10);
 
 var tot_value = 0;
 tot_value = bonAmt + gasanAmt + interestAmt;
 
 document.getElementById("totalamt").innerText = addComma(tot_value);
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0"  oncontextmenu="return false">
<form name="sForm" method="post" action="IR040411.etax">
<input type="hidden" name="cmd" value="jingsuUpdate">
<% if (jingsuView != null && jingsuView.size() > 0) {%>
<input type="hidden" name="seq" value="<%=jingsuView.getString("M090_SEQ")%>">
<input type="hidden" name="year" value="<%=jingsuView.getString("M090_M080_YEAR")%>">
<input type="hidden" name="m80_seq" value="<%=jingsuView.getString("M090_M080_SEQ")%>">
<input type="hidden" name="acc_date" value="<%=jingsuView.getString("M090_DATE")%>">
<% } %>
<table width="500" border="0" cellspacing="0" cellpadding="5">
  <tr> 
    <td> 
      <table width="81%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
					<td width="18">&nbsp;</td>
					<td width="482" height="50"><img src="../img/title4_5.gif"></td>
				</tr>
      </table>
    </td>
  </tr>
	<% if (jingsuView != null && jingsuView.size() > 0) {%>
  <tr>
    <td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
           <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
							<tr>
								<td>
									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									발의일자&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="baluiDate" class="box3" size="15" value="<%=jingsuView.getString("M090_BALUIDATE")%>" userType="date" >
									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','baluiDate');" style="cursor:hand">
									<span style="width:10"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									고지서발행&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="gojiseoPublish" class="box3" size="15" value="<%=jingsuView.getString("M090_GOJISEOPUBLISH")%>" userType="date" >
									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','gojiseoPublish');" style="cursor:hand">
									<br><br>

									<span style="width:10"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									납입기한&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="napibDate" class="box3" size="15" value="<%=jingsuView.getString("M090_NAPIBDATE")%>" userType="date" >
									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','napibDate');" style="cursor:hand">
									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									징수부기재&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="jingsubuWrite" class="box3" size="15" value="<%=jingsuView.getString("M090_NAPIBDATE")%>" userType="date" >
									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','jingsubuWrite');" style="cursor:hand">
									<br><br>

									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									관&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="gwan" class="box3" size="15" value="<%=jingsuView.getString("M090_GWAN")%>">
									<span style="width:30px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									항&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="hang" class="box3" size="15" value="<%=jingsuView.getString("M090_HANG")%>">
									<br><br>
									
									 <span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									목&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="mok" class="box3" size="15" value="<%=jingsuView.getString("M090_MOK")%>">
									<span style="width:30px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									세목&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="semok" class="box3" size="15" value="<%=jingsuView.getString("M090_SEMOKCODE")%>">
									<br><br>

									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									본세&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="bonAmt" class="box3" size="15" onkeyup="cal()"value="<%=jingsuView.getString("M090_BONAMT")%>">
									<span style="width:30px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									가산금&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="gasanAmt" class="box3" size="15" onkeyup="cal()" value="<%=jingsuView.getString("M090_GASANAMT")%>">
									<br><br>

									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									이자&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="interestAmt" class="box3" size="15" onkeyup="cal()" value="<%=jingsuView.getString("M090_INTERESTAMT")%>">
									<span style="width:30px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									합계금액&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="totalAmt" class="box3" size="15" value="<%=jingsuView.getString("M090_TOTALAMT")%>">
                  <input type="hidden" name="orgAmt" value="<%=jingsuView.getString("M090_TOTALAMT")%>">
									<br><br>

									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									납부자성명
									<span style="width:10px"></span>
									<input type="text" name="napbujaName" class="box3" size="15" value="<%=jingsuView.getString("M090_NAPBUJANAME")%>">
									<span style="width:30px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									주민등록번호
									<span style="width:10px"></span>
									<input type="text" name="juminNo" class="box3" size="15" value="<%=jingsuView.getString("M090_JUMINNO")%>">
									<br><br>

									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									주소&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="address" class="box3" size="15" value="<%=jingsuView.getString("M090_NAPBUJAADDRESS")%>">
									<span style="width:30px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									물건명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="width:10px"></span>
									<input type="text" name="businessName" class="box3" size="15" value="<%=jingsuView.getString("M090_BUSINESSNAME")%>">
									<br><br>

									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
									시청담당자
									<span style="width:10px"></span>
									<input type="text" name="userName" class="box3" size="15" value="<%=jingsuView.getString("M090_USERNAME")%>">
									<span style="width:190px"></span>				    
									<img src="../img/btn_modify.gif" alt="수정" onClick="goUpdate();" style="cursor:hand">
								</td>
							</tr>
				   </table>
				</td>
			</tr>
	<% } %>
</table>
</form>
</body>
</html>