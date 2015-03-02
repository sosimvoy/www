<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR081010.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-16
* 프로그램내용	   : 시스템운영 > 마감일 등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>
<%@include file="../menu/mn08.jsp" %>

<%
	CommonEntity endWorkDate = (CommonEntity)request.getAttribute("page.mn08.endWorkDate");

	String chDateIlban = request.getParameter("chDateIlban"); 
    if (chDateIlban.equals("")) {
		chDateIlban  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
    }
	String chDateTekbeyl = request.getParameter("chDateTekbeyl"); 
    if (chDateTekbeyl.equals("")) {
		chDateTekbeyl  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
		}
	String chDateGigeum = request.getParameter("chDateGigeum"); 
    if (chDateGigeum.equals("")) {
		chDateGigeum  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
		}
	String chDateHyungeum = request.getParameter("chDateHyungeum"); 
    if (chDateHyungeum.equals("")) {
		chDateHyungeum  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
		}
	String chDateJeungji = request.getParameter("chDateJeungji"); 
    if (chDateJeungji.equals("")) {
		chDateJeungji  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
		}
	String chDateJuhaengse = request.getParameter("chDateJuhaengse"); 
    if (chDateJuhaengse.equals("")) {
		chDateJuhaengse  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
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

			function gosaveData(){
        var form = document.sForm;
				 form.action = "IR081110.etax"; 
				 form.cmd.value = "insEndWorkDate";
			   eSubmit(form);		
      }
			 
			 function goSearch() {
				var form = document.sForm;
		    form.cmd.value = "endWorkDate";
		    form.action    = "IR081110.etax";
				eSubmit(form);
			}
    </script>
  </head>
  <body topmargin="0" leftmargin="0"  oncontextmenu="return false">
  <form name="sForm" method="post" action="IR081110.etax">
	<input type="hidden" name="cmd" value="endWorkDate">

    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_19.gif"></td>
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
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr> 
                    <td><span style="width:700"></span></td>
                    <td width="150"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">회계연도</span>
											<select name="year" iValue="<%=request.getParameter("year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
											</td>
										<td width="100"> 
                      <img src="../img/btn_search.gif" alt="조회" style="cursor:hand" onClick="goSearch()" align="absmiddle">
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
              <th width="33%">구분</th>
							<th width="33%">마감일기준</th>
							<th width="34%">수정마감일</th>
            </tr>
            <tr>                 
              <td>&nbsp;일반회계</td>
							<% if (endWorkDate != null ) { %> 
							<td><input type="text" value="<%=TextHandler.formatDate(endWorkDate.getString("M320_DATEILBAN"), "yyyyMMdd", "yyyy년 MM월 dd일")%>" name="dateIlban" size="20" maxlength="20" class="box1" readonly tabindex="-1">
							</td>
							<% } %>
							<td>						
							 <input type="text" name="chDateIlban" class="box3" size="16" value="" userType="date">
							 <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','chDateIlban');" style="cursor:hand">
							</td>
            </tr>		
						 <tr>                 
              <td>&nbsp;특별회계</td>
							<% if (endWorkDate != null ) { %>
							<td><input type="text" value="<%=TextHandler.formatDate(endWorkDate.getString("M320_DATETEKBEYL"), "yyyyMMdd", "yyyy년 MM월 dd일")%>" name="dateTekbeyl" size="20" maxlength="20" class="box1" readonly tabindex="-1">
							</td>
							<% } %>
							<td>						
							 <input type="text" name="chDateTekbeyl" class="box3" size="16" value="" userType="date">
							 <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','chDateTekbeyl');" style="cursor:hand">
							</td>
            </tr>
						 <tr>                 
              <td>&nbsp;기금</td>
							<% if (endWorkDate != null ) { %>
							<td><input type="text" value="<%=TextHandler.formatDate(endWorkDate.getString("M320_DATEGIGEUM"), "yyyyMMdd", "yyyy년 MM월 dd일")%>" name="dateGigeum" size="20" maxlength="20" class="box1" readonly tabindex="-1"></td>
							<% } %>
							<td>						
							 <input type="text" name="chDateGigeum" class="box3" size="16" value="" userType="date">
							 <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','chDateGigeum');" style="cursor:hand">
							</td>
            </tr>
						 <tr>                 
              <td>&nbsp;세입세출외현금</td>
							<% if (endWorkDate != null ) { %>
							<td><input type="text" value="<%=TextHandler.formatDate(endWorkDate.getString("M320_DATEHYUNGEUM"), "yyyyMMdd", "yyyy년 MM월 dd일")%>" name="dateHyungeum" size="20" maxlength="20" class="box1" readonly tabindex="-1"></td>
							<% } %>
							<td>						
							 <input type="text" name="chDateHyungeum" class="box3" size="16" value="" userType="date">
							 <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','chDateHyungeum');" style="cursor:hand">
							</td>
            </tr>
						 <tr>                 
              <td>&nbsp;수입증지</td>
							<% if (endWorkDate != null ) { %>
							<td><input type="text" value="<%=TextHandler.formatDate(endWorkDate.getString("M320_DATEJEUNGJI"), "yyyyMMdd", "yyyy년 MM월 dd일")%>" name="dateJeungji" size="20" maxlength="20" class="box1" readonly tabindex="-1">
							</td>
							<% } %>
							<td>						
							 <input type="text" name="chDateJeungji" class="box3" size="16" value="" userType="date">
							 <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','chDateJeungji');" style="cursor:hand">
							</td>
            </tr>
						 <tr>                 
              <td>&nbsp;주행세</td>
							<% if (endWorkDate != null ) { %>
							<td><input type="text" value="<%=TextHandler.formatDate(endWorkDate.getString("M320_DATEJUHAENGSE"), "yyyyMMdd", "yyyy년 MM월 dd일")%>" name="dateJuhaengse" size="20" maxlength="20" class="box1" readonly tabindex="-1">
							</td>
							<% } %>
							<td>						
							 <input type="text" name="chDateJuhaengse" class="box3" size="16" value="" userType="date">
							 <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','chDateJuhaengse');" style="cursor:hand">
							</td>
            </tr>
          </table>
        </td>
      </tr>
			<table width="980" border="0" cellspacing="1" cellpadding="1">
				<tr> 
					<td height="50"> 
						<div align="right"><img src="../img/btn_modify.gif" alt="수정" width="55" height="21" border="0" onClick="gosaveData()" style="cursor:hand">
					  </div>
					</td>
				</tr>
			</table>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
        </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
  </body>
</html>