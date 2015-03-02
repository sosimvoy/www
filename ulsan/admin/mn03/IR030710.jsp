<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR030710.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세출 > 수기분 일괄등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn03.jsp" %>
<%	

  String sucMsg =  (String)request.getAttribute("page.mn02.SucMsg");
  String b_day =  (String)request.getAttribute("page.mn02.b_day");
	if (sucMsg == null) {
		sucMsg = "";
	}

	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);

  String fis_date = request.getParameter("fis_date");
  if (fis_date == null || "".equals(fis_date)) { 
    fis_date = TextHandler.formatDate(b_day,"yyyyMMdd","yyyy-MM-dd");
  }
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
	    
			function goSearch() {
	      var form = document.sForm;
		    form.action = "IR030710.etax";
		    form.cmd.value = "batchcashsugiint";
		    form.target = "_self";
		    eSubmit(form);
	    }
     </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR030710.etax">
  <input type="hidden" name="cmd" value="revWriteList2">
	<input type="hidden" name="m030_seq" value="">
	<input type="hidden" name="org_year" value="">
	<input type="hidden" name="work_log" value="A03">
	<input type="hidden" name="trans_gubun" value="111">
  <!-- 팝업시 보내는 코드값 -->                                               
	<input type="hidden" name="pop_acc_type" value="">
	<input type="hidden" name="pop_part_code" value="">
	<input type="hidden" name="pop_acc_code" value="">

    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="15">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title3_14.gif"></td>
      </tr>
      <tr> 
        <td width="15">&nbsp;</td>
        <td width="975" height="40"> 
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
            <tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
							    <tr>
								    <td>
									    <span style="width:50px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
									    회계연도&nbsp;
									    <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>" userType="number">
										    <option value="<%=this_year%>"><%=this_year%></option>
										    <option value="<%=last_year%>"><%=last_year%></option>
                      </select>
									    
                      <span style="width:100px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">							
									    회계일자&nbsp; <input type="text" name="fis_date" class="box3" size="8" value="<%=fis_date%>" userType="date">
									    <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','fis_date');" style="cursor:hand">
                                        			
                      <span style="width:180px"></span>
                      <img src="../img/btn_ilorder.gif" alt="일괄처리" onClick="goSearch()" style="cursor:hand">
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
		  <tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="20"> &nbsp;</td>
		  </tr>

    <table width="1000">
      <tr>
        <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	    </tr>
    </table>
  </form>
	<iframe name="hidFrame" width="0" height="0"></iframe>
	</body>
	<% if (!"".equals(sucMsg)) { %>
	  <script>
	  alert("<%=sucMsg%>");
		</script>
	<% } %>
</html>