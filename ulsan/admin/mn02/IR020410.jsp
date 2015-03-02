<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR020410.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2015-02-04
* 프로그램내용	   : 세출 > 수기분 조회/수정/삭제
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn02.jsp" %>
<%	
	List<CommonEntity> misechulList = (List<CommonEntity>)request.getAttribute("page.mn02.misechulList");
	  
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");

    String acc_date = request.getParameter("acc_date");
    if (acc_date == null || "".equals(acc_date)) { 
      acc_date = TextHandler.formatDate(StringUtil.getDate().substring(0, 8),"yyyyMMdd","yyyy-MM-dd");
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
		form.action = "IR020410.etax";
		form.cmd.value = "misechulList";
		form.target = "_self";
		eSubmit(form);
	}

    </script>
  </head>
  <body>
  <form name="sForm" method="post" action="IR020410.etax">
  <input type="hidden" name="cmd" value="misechulList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="15">&nbsp;</td>
        <td width="975" height="40"><font size="4px"><b>세출-일괄등록미처리분</b></font></td>
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
									    <select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
									    <%
									    for (int i=Integer.parseInt(this_year); i>=2010; i--) {
									    %>
										    <option value="<%=i%>"><%=i%></option>
										<%
										}%>
                      </select>
                      <span style="width:100px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									    회계일자&nbsp; <input type="text" name="acc_date" class="box3" size="8" value="<%=acc_date%>" userType="date">
									    <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','acc_date');" style="cursor:hand">
									    
								    </td>
								    <td align="right">
								    <img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:hand">
								    &nbsp;&nbsp;
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
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
				    <tr> 
					    <th class="fstleft">부서</th>
						<th>금액</th>
						<th>건수</th>
				    </tr>
				    <%
					    for (int i=0; misechulList != null && i < misechulList.size(); i++) {
					  	  CommonEntity data = (CommonEntity)misechulList.get(i);
				    %>
				    <tr>
			  	    <td class="left"><%=data.getString("GCC_DEPT_NM")%></td>
					    <td class="right"><%=TextHandler.formatNumber(data.getString("TRNX_AMT"))%></td>
                        <td class="right"><%=TextHandler.formatNumber(data.getString("CNT"))%></td>
				    </tr>
				    <%				 
				      } if (misechulList == null || misechulList.size() == 0) { 
				    %>
				    <tr> 
              <td class="fstleft" colspan="3">&nbsp;</td>
            </tr>
				    <%
					  	} %>
			    </table>
			  </td>
		  </tr>
	</table>
    <table width="1000">
      <tr>
        <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	    </tr>
    </table>
  </form>
	</body>
</html>