<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR030110.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입세출외현금 > 수기분등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@include file="../menu/mn03.jsp" %>
<%	
		List<CommonEntity> accNameList  = (List<CommonEntity>)request.getAttribute("page.mn03.accNameList");
		List<CommonEntity> cashDeptList  = (List<CommonEntity>)request.getAttribute("page.mn03.cashDeptList");
    CommonEntity dateInfo = (CommonEntity)request.getAttribute("page.mn01.fis_date");  //회계일자

    String SucMsg = (String)request.getAttribute("page.mn03.SucMsg");
		if (SucMsg == null ) {
		  SucMsg = "";
	  }

    String FailMsg = (String)request.getAttribute("page.mn03.FailMsg");
		if (FailMsg == null ) {
		  FailMsg = "";
	  }

		String last_year = "";
		String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		int last_int = Integer.parseInt(this_year) - 1;
		last_year = String.valueOf(last_int);
		
    String fis_date = request.getParameter("fis_date");
    if (fis_date == null || "".equals(fis_date)) { 
      fis_date = TextHandler.formatDate(dateInfo.getString("FIS_DATE"),"yyyyMMdd","yyyy-MM-dd");
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리 시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
      function init() {
				var form = document.sForm;
        //form.order_no.disabled = true;
  	    typeInitialize();
        form.flag.value = "Y";
        form.cnt.focus();  
      }
			  
			function saveData()	{
			  var form = document.sForm;
        var dwtype;
        
				if (form.dwtype.value == "G2" && form.order_no.value == "") {
					alert("지급번호를 입력하십시오.");
					form.order_no.focus();
					return;
				}

        if (form.flag.value == "N" || form.flag.value == "") {
          alert("수기분 등록 중입니다. 잠시만 기다려주세요.");
          return;
        }
				form.action = "IR030110.etax";
				form.cmd.value = "cashInsert"
        eSubmit(form);
        form.flag.value = "N";
			}

			function changeAcc(dept) {
        var form = document.sForm;
				document.hidFrame.location = "IR030111.etax?cmd=cashSugi&part_code="+dept;
        form.cnt.focus();
			}

      function chorder(val) {
        var form = document.sForm;
        if (val== "G1") {
          form.order_no.disabled = true;
        } else {
          form.order_no.disabled = false;
          form.order_no.focus();  
        }
      } 

      function enterKeyCnt() {
        if(isEnterKey()) {
	        document.sForm.cnt.focus();
	      }
      }

      function enterKeyAmt() {
        if(isEnterKey()) {
	        document.sForm.amt.focus();
	      }
      }

      function enterKey() {
        if(isEnterKey()) {
	        saveData();
	      }
      }

      function chAcc_code() {
        var form = document.sForm;
          form.cnt.focus();
      }

      function chIntype() {
        var form = document.sForm;
        var dwtype = form.dwtype.value;
        if (dwtype == 'G2') { 
          form.order_no.focus();
        } else {
          form.cnt.focus();
        }
      }

      function chCash() {
        var form = document.sForm;
        var dwtype = form.dwtype.value;
        if (dwtype == 'G2') { 
          form.order_no.focus();
        } else {
          form.cnt.focus(); 
        }
      }

      function chDeposit() {
        var form = document.sForm;
        var dwtype = form.dwtype.value;
        if (dwtype == 'G2') { 
          form.order_no.focus();
        } else {
          form.cnt.focus(); 
        }
      }

	  </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR030110.etax">
  <input type="hidden" name="cmd" value="cashInsert">
  <input type="hidden" name="flag" value="">
	<input type="hidden" name="work_log" value="A03">
	<input type="hidden" name="trans_gubun" value="111">
	
     <table width="993" border="0" cellspacing="0" cellpadding="0">
       <tr>
         <td width="15">&nbsp;</td>
         <td width="975" height="40"><img src="../img/title3_1.gif"></td>
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
								       <span style="width:60px"></span>
								       <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                       회계연도&nbsp;
									     <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
										     <option value="<%=this_year%>"><%=this_year%></option>
										     <option value="<%=last_year%>"><%=last_year%></option>
									     </select>
									     <span style="width:300px"></span>
									     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
                       회계일자&nbsp; <input type="text" value="<%=fis_date%>" class="box3" name="fis_date" size="8" userType="date">
									     <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','fis_date');" style="cursor:hand">
									     <br><br>
									     <span style="width:60px"></span>
									     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									     부&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;서&nbsp;
											 <select name="part_code" iValue="<%=request.getParameter("part_code")%>" onchange="changeAcc(this.value)">
								        <% for (int i=0; cashDeptList != null && i < cashDeptList.size(); i++) {
									         CommonEntity deptInfo = (CommonEntity)cashDeptList.get(i); %>												    
										       <option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
									      <% } %>
								       </select>
									     <span style="width:159px"></span>
									     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									     회&nbsp;&nbsp;계&nbsp;&nbsp;명&nbsp; 
											  <select name="acc_code" iValue="<%=request.getParameter("acc_code")%>" onchange="chAcc_code(this.value)">
								        <% for (int i=0; accNameList != null && i < accNameList.size(); i++) {
									         CommonEntity nameInfo = (CommonEntity)accNameList.get(i); %>												    
										       <option value="<%=nameInfo.getString("M360_ACCCODE")%>"><%=nameInfo.getString("M360_ACCNAME")%></option>
									      <% } %>
								       </select>
											 <br><br>
											 <span style="width:60px"></span>
									     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									     입금지급구분&nbsp; 
									     <select name="dwtype" iValue="<%=request.getParameter("dwtype")%>" onchange="chorder(this.value)">
										     <option value="G1">입금</option>
                         <option value="G2">지급</option>
									     </select>
									     <span style="width:278px"></span>
									     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									     입력구분&nbsp; 
									     <select name="intype" iValue="<%=request.getParameter("intype")%>" onchange="chIntype(this.value)">
										     <option value="I1">입금지급</option>
                         <option value="I2">반납</option>
                         <option value="I3">정정</option>
									     </select>
											 <br><br>
									     <span style="width:60px"></span>
									     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									     현금구분&nbsp; 
									     <select name="cash_type" iValue="<%=request.getParameter("cash_type")%>" onchange="chCash(this.value)">
										     <option value="C1">보증금</option>
                         <option value="C2">보관금</option>
                         <option value="C3">잡종금</option>
										     <option value="C4">부가가치세</option>
									     </select>
									     <span style="width:263px"></span>
									     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									     예금종류&nbsp; 
									     <select name="deposit_type" iValue="<%=request.getParameter("deposit_type")%>" onchange="chDeposit(this.value)">
										     <option value="D1">정기예금</option>
                         <option value="D2">별단예금</option>
                         <option value="D3">공금예금</option>
									     </select>
											 <br><br>
									     <span style="width:60px"></span>
									     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									     채주성명&nbsp; <input type="text" name="order_name" class="box3" size="19" value="">
									     <span style="width:207px"></span>
									     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									     비&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;고&nbsp; 
											 <input type="text" name="note" class="box3" size="40" value="">
									     <br><br>
											 <span style="width:60px"></span>
									     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									     지급번호&nbsp; <input type="text" name="order_no" class="box3" size="6" value="" maxlength="4" userType="number" onkeyDown="enterKeyCnt()" <%if(!request.getParameter("dwtype").equals("G2")){out.println("disabled");}%>>
									     <span style="width:285px"></span>
									     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									     건&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;수&nbsp; 
											 <input type="text" name="cnt" class="box3" size="3" align="center" value="" required desc="건수" userType="number" maxlength="5" onkeyDown="enterKeyAmt()">&nbsp;건
									     <br><br>
											 <span style="width:60px"></span>
									     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									     금&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;액&nbsp; 
											 <input type="text" name="amt" class="box3" size="15" value="" required desc="금액" userType="sp_money" onkeyDown="enterKey()">&nbsp;원
                     </td>
								   </tr>
									 <tr>
								     <td width="850"> 
									     <div align="right"><img src="../img/btn_order.gif" alt="등록" onClick="saveData();return false;" style="cursor:hand"></div>
                     </td>
                   </tr>
                 </table>
               </td>
             </tr>
             <tr> 
              <td><img src="../img/box_bt.gif" width="964" height="10"></td>
             </tr>
           </table>
		     </tr>
	    </td>
    </table>
	  <table width="1000">
      <tr>
        <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	    </tr>
    </table>
  </form>
	<iframe name="hidFrame" width="0" height="0"></iframe>
	<% if (!"".equals(SucMsg)) { %>
	     <script>
         alert("<%=SucMsg%>");
			 </script>
  <% } %>
  <% if (!"".equals(FailMsg)) { %>
	     <script>
         alert("<%=FailMsg%>");
			 </script>
  <% } %> 
</html>
  </body>
</html>