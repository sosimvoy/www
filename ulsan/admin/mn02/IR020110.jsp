<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR020110.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세출 > 수기분등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@include file="../menu/mn02.jsp" %>																	
<%	
    List<CommonEntity> revDeptList  = (List<CommonEntity>)request.getAttribute("page.mn02.revDeptList");	 
		List<CommonEntity> revAccNmList  = (List<CommonEntity>)request.getAttribute("page.mn02.revAccNmList");
		List<CommonEntity> revSemokList  = (List<CommonEntity>)request.getAttribute("page.mn02.revSemokList");
    CommonEntity dateInfo = (CommonEntity)request.getAttribute("page.mn01.fis_date");  //회계일자
    
		String SucMsg = (String)request.getAttribute("page.mn02.SucMsg");
		if (SucMsg == null ) {
		  SucMsg = "";
	  }

    String FailMsg = (String)request.getAttribute("page.mn02.FailMsg");
		if (FailMsg == null ) {
		  FailMsg = "";
	  }

    String save_order_no  = request.getParameter("save_order_no");
      save_order_no  = request.getParameter("order_no");

    
    String save_amt = request.getParameter("save_amt");
      save_amt  = request.getParameter("amt");   

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
  	    typeInitialize();
        form.flag.value = "Y";
        form.order_no.focus();
       // form.save.disabled = false;
      }
			  
	    function saveData()	{
			  var form = document.sForm;
        if (form.flag.value == "N" || form.flag.value == "") {
          alert("수기분 등록 중입니다. 잠시만 기다려주세요.");
          return;
        }
        if (confirm("지급번호 : " + form.order_no.value + "\n 금액 : " + form.amt.value + "\n 이 맞습니까?") ) {
          eSubmit(form);
          form.flag.value = "N";
        }
        //form.save.disabled = true;
      }
      
        function chDept(acc_type) {
            var form = document.sForm;
            document.hidFrame.location = "IR020111.etax?cmd=sugibunList&acc_type="+acc_type+"&flag=0&fis_year="+form.fis_year.value;
            form.order_no.focus();
        }

        function chAcc(part_code) {
            var form = document.sForm;
            document.hidFrame.location = "IR020111.etax?cmd=sugibunList&acc_type="+form.acc_type.value+"&part_code="+part_code+"&flag=1&fis_year="+form.fis_year.value;
            form.order_no.focus();
        }

      function chSemok(acc_code) {
            var form = document.sForm;
            document.hidFrame.location = "IR020111.etax?cmd=sugibunList&acc_type="+form.acc_type.value+"&part_code="+form.part_code.value+"&acc_code="+acc_code+"&fis_year="+form.fis_year.value;
            form.order_no.focus();
        }
        
       function enterKeyAmt() {
        if(isEnterKey()) {
	        document.sForm.amt.focus();
	      }
      }
      
      function chIntype() {
        var form = document.sForm;
        form.order_no.focus();
      }

      function enterKey() {
        if(isEnterKey()) {
	        saveData();
	      }
      }

	  </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR020110.etax">
  <input type="hidden" name="cmd" value="revWriteInsert">
  <input type="hidden" name="flag" value="">
	<input type="hidden" name="work_log" value="A02">
	<input type="hidden" name="trans_gubun" value="111">
	  <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="15">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title2_1.gif"></td>
      </tr>
      <tr> 
        <td width="15">&nbsp;</td>
        <td width="978" height="40"> 
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
            <tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr>
                    <td>
								      <span style="width:270px"></span>
								      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                      회계연도&nbsp;
									    <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
										    <option value="<%=this_year%>"><%=this_year%></option>
										    <option value="<%=last_year%>"><%=last_year%></option>
									    </select>
										  <br><br>
									    <span style="width:270px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
                      회계일자&nbsp; <input type="text" value="<%=fis_date%>" class="box3" name="fis_date" size="8" userType="date">
									    <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','fis_date');" style="cursor:hand">
									    <br><br>
									    <span style="width:270px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
                      회계구분&nbsp;
									    <select name="acc_type" iValue="<%=request.getParameter("acc_type")%>" onchange="chDept(this.value)">
                        <option value="A">일반회계</option>
                        <option value="B">특별회계</option>
                        <option value="E">기금</option>
                      </select>
											<br><br>
									    <span style="width:270px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    부&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;서&nbsp;
										  <select name="part_code" iValue="<%=request.getParameter("part_code")%>" onchange="chAcc(this.value)">
								        <% for (int i=0; revDeptList != null && i < revDeptList.size(); i++) {
									         CommonEntity deptInfo = (CommonEntity)revDeptList.get(i); %>												    
										       <option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
									      <% } %>
								      </select>
									    <br><br>
									    <span style="width:270px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회&nbsp;&nbsp;계&nbsp;&nbsp;명&nbsp;
									    <select name="acc_code" iValue="<%=request.getParameter("acc_code")%>" onchange="chSemok(this.value)">
								        <% for (int i=0; revAccNmList != null && i < revAccNmList.size(); i++) {
									         CommonEntity accCdInfo = (CommonEntity)revAccNmList.get(i); %>												    
										       <option value="<%=accCdInfo.getString("M360_ACCCODE")%>"><%=accCdInfo.getString("M360_ACCNAME")%></option>
									      <% } %>
											</select>
										  <br><br>
											<span style="width:270px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											세&nbsp;&nbsp;목&nbsp;&nbsp;명&nbsp; 
								      <select name="semok_code" iValue="<%=request.getParameter("semok_code")%>">
								        <% for (int i=0; revSemokList != null && i < revSemokList.size(); i++) {
									         CommonEntity semokInfo = (CommonEntity)revSemokList.get(i); %>
										    <option value="<%=semokInfo.getString("M370_SEMOKCODE")%>"><%=semokInfo.getString("M370_SEMOKNAME")%></option>
									      <% } %>
								      </select>
									    <br><br>
									    <span style="width:270px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    입력구분&nbsp;
									    <select name="intype" iValue="<%=request.getParameter("intype")%>" onchange="chIntype(this.value)">
                        <option value="I1">세출</option>
                        <option value="I2">반납</option>
                        <option value="I3">정정</option>
                      </select>
											<br><br>
									    <span style="width:270px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    채주성명&nbsp; <input type="text" name="order_name" class="box3" size="19" value="" >
									    <br><br>
									    <span style="width:270px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    지급번호&nbsp; <input type="text" name="order_no" class="box3" size="6" value="" maxlength="5" userType="number" required desc="지급번호" onkeyDown="enterKeyAmt()">
                      <input type="hidden" name="save_order_no" value="<%=save_order_no%>" readonly>
                      <span style="width:120px"></span>
                      <% if (!"".equals(save_order_no)) { %>
                      <font color="blue">지급번호 : <%=save_order_no%></font>
	                    <% } %>
                      
									    <br><br>
											<span style="width:270px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    금&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;액&nbsp; <input type="text" name="amt" class="box3" size="15" value="" required desc="금액" userType="sp_money" onkeyDown="enterKey()">&nbsp;원  
                      <input type="hidden" name="save_amt" value="<%=save_amt%>" readonly>
                      <span style="width:50px"></span>
                      <% if (!"".equals(save_amt)) { %>
                      <font color="blue">금액 : <%=save_amt%>원</font>
                      <% }%>
                    </td>
							    <tr>
								    <td width="880"> 
									  <div align="right">
                       <img src="../img/btn_order.gif" alt="등록" onClick="saveData();return false;" style="cursor:hand">
                                                                                                                                                   
                        <!--<input type="button" name="save" value="등록" src="../img/btn_order.gif" alt="등록" onClick="saveData()" style="cursor:hand"> -->
                        <!-- <input type="image" name="save" src="../img/btn_order.gif" onClick="saveData();" style="cursor:hand"> -->
                    </div>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr> 
              <td><img src="../img/box_bt.gif" width="964" height="11"></td>
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
	</body>
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

