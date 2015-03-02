<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR030211.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입세출외현금 > 수기분등록 수정
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  CommonEntity cashView = (CommonEntity)request.getAttribute("page.mn03.cashView");
  CommonEntity endChk = (CommonEntity)request.getAttribute("page.mn03.endChk");
  List<CommonEntity> accNameList  = (List<CommonEntity>)request.getAttribute("page.mn03.accNameList");
	List<CommonEntity> cashDeptList  = (List<CommonEntity>)request.getAttribute("page.mn03.cashDeptList");
	
	int listSize = 0;
	if (cashView != null && cashView.size() != 0) {
		listSize = cashView.size();
	}

  String SucMsg = (String)request.getAttribute("page.mn03.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

  String FailMsg = (String)request.getAttribute("page.mn02.FailMsg");
	if (FailMsg == null) {
	  FailMsg = "";  
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
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link href="../css/class.css" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../js/calendar.js"></script>	
    <script language="javascript" src="../js/base.js"></script>
    <script language="javascript">
      function init() {
		    typeInitialize();
      }
	
      function saveData()	{
  	    var form = document.sForm;
		    form.action = "IR030211.etax";
		    form.cmd.value = "cashUpdate";
		    form.target = "_self";
  	    eSubmit(form);
      }

      function chorder(val) {
        var form = document.sForm;
        if (val== "G1") {
          form.order_no.disabled = true;
        } else {
          form.order_no.disabled = false;
        }
      }

	  	function changeDept(dept) {
				document.hidFrame.location = "IR030111.etax?cmd=cashList&part_code="+dept;
			}
    </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR030211.etax">
  <input type="hidden" name="cmd" value="cashUpdate">
  <% if (cashView != null && cashView.size() > 0) {%>
  <input type="hidden" name="m040_seq" value="<%=cashView.getString("M040_SEQ")%>">
  <input type="hidden" name="fis_year" value="<%=cashView.getString("M040_YEAR")%>">
  <input type="hidden" name="fis_date" value="<%=cashView.getString("M040_DATE")%>">
  <% } %>
    <table width="500" border="0" cellspacing="0" cellpadding="5">
		  <tr> 
        <td> 
          <table width="81%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
					    <td width="18">&nbsp;</td>
					    <td width="482" height="50"><img src="../img/title3_2.gif"></td>
				    </tr>
          </table>
        </td>
      </tr>
	    <% if (cashView != null && cashView.size() > 0) {%>
      <tr>
        <td>
			    <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
							  <span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                <b>회계연도&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <%=cashView.getString("M040_YEAR")%>
                <br><br>

								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
                <b>회계일자&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <%=cashView.getString("M040_DATE").substring(0,4)%>-<%=cashView.getString("M040_DATE").substring(4,6)%>-<%=cashView.getString("M040_DATE").substring(6,8)%>
                <br><br>

								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
								<b>부&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;서&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                    <%=cashView.getString("PARTCODE")%>
                <% } else { %>
								<select name="part_code" iValue="<%=cashView.getString("M040_PARTCODE")%>" onchange="changeDept(this.value)">
								  <% for (int i=0; cashDeptList != null && i < cashDeptList.size(); i++) {
									  CommonEntity deptInfo = (CommonEntity)cashDeptList.get(i); %>												    
										<option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
									<% } %>
								</select>
                <% 
                  }  
                %>

								<br><br>
								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
								<b>회&nbsp;&nbsp;계&nbsp;&nbsp;명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
								<% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                   <%=cashView.getString("ACCCODE")%>
                <% } else { %>
                <select name="acc_code" iValue="<%=cashView.getString("M040_ACCCODE")%>">
								  <% for (int i=0; accNameList != null && i < accNameList.size(); i++) {
									  CommonEntity nameInfo = (CommonEntity)accNameList.get(i); %>												    
										<option value="<%=nameInfo.getString("M360_ACCCODE")%>"><%=nameInfo.getString("M360_ACCNAME")%></option>
									<% } %>
								</select>
                <% 
                  }  
                %>

								<br><br>
							  <span style="width:50px"></span>
							  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
							  <b>입금지급구분&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b> 
                <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                   <%=cashView.getString("M040_DWTYPE_NM")%>
                <% } else { %>
							  <select name="dwtype" iValue="<%=cashView.getString("M040_DWTYPE")%>" onchange="chorder(this.value)">
								  <option value="G1">입금</option>
                  <option value="G2">지급</option>
								</select>
                <% 
                  }  
                %>

								<br><br>
								<span style="width:50px"></span>
							  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
							  <b>입력구분&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                   <%=cashView.getString("M040_INTYPE_NM")%>
                <% } else { %>
							  <select name="intype" iValue="<%=cashView.getString("M040_INTYPE")%>">
								  <option value="I1">입금지급</option>
                  <option value="I2">반납</option>
                  <option value="I3">정정</option>
								</select>
                <% 
                  }  
                %>

								<br><br>
								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
								<b>현금구분&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b> 
                <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                   <%=cashView.getString("M040_CASHTYPE_NM")%>
                <% } else { %>
								<select name="cash_type" iValue="<%=cashView.getString("M040_CASHTYPE")%>">
								  <option value="C1">보증금</option>
                  <option value="C2">보관금</option>
                  <option value="C3">잡종금</option>
									<option value="C4">부가가치세</option>
								</select>
                <% 
                  }  
                %>

								<br><br>
								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
								<b>예금종류&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b> 
                <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                   <%=cashView.getString("M040_DEPOSITTYPE_NM")%>
                <% } else { %>
								<select name="deposit_type" iValue="<%=cashView.getString("M040_DEPOSITTYPE")%>">
								  <option value="D1">정기예금</option>
                  <option value="D2">별단예금</option>
                  <option value="D3">공금예금</option>
								</select>
                <% 
                  }  
                %>

								<br><br>
								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
								<b>채주성명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <input type="text" name="order_name" class="box3" size="19" value="<%=cashView.getString("M040_ORDERNAME")%>">
								
                <br><br>
								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
								<b>비&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;고&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <input type="text" name="note" class="box3" size="30"  value="<%=cashView.getString("M040_NOTE")%>">
								
                <br><br>
								<span style="width:50px"></span>
								<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
								<b>지급번호&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <input type="text" name="order_no" class="box3" size="6" value="<%=cashView.getString("M040_ORDERNO")%>" maxlength="4" userType="number">
                <input type="hidden" name="org_no" value="<%=cashView.getString("M040_ORDERNO")%>">
							  
                <br><br>
						    <span style="width:50px"></span>
							  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
							  <b>건&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;수&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <input type="text" name="cnt" class="box3" size="3"   value="<%=cashView.getString("M040_CNT")%>" userType="number" maxlength="5" required desc="건수">
							  
                <br><br>
								<span style="width:50px"></span>
				 			  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
								<b>금&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;액&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
                <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                   <%=TextHandler.formatNumber(cashView.getString("M040_AMT"))%>&nbsp;원
                <% } else { %>
                <input type="text" name="amt" class="box3" size="15" value="<%=cashView.getString("M040_AMT")%>" required desc="금액" userType="money">&nbsp;원
                <% 
                  }  
                %>
              </td>
            </tr>
						<tr>
						  <td width="460" height="60"> 
							  <div align="center"><img src="../img/btn_modify.gif" alt="수정" onClick="saveData()" style="cursor:hand"></div>
							</td>
            </tr>
          </table>
        </td>
      </tr>
	  <% } %>
    </table>
  </form>
	<iframe name="hidFrame" width="0" height="0"></iframe>
	<% if (!"".equals(SucMsg)) { %>
	  <script>
	    alert("<%=SucMsg%>");
      opener.goSearch();
			//self.close();	
		  </script>
	<% } %>
  <% if (!"".equals(FailMsg)) { %>
	  <script>
	    alert("<%=FailMsg%>");
    </script>
	<% } %>
  </body>
</html>