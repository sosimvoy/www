<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR010210.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입 > 수기분등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn01.jsp" %>
<%
  List<CommonEntity> accSemokList  = (List<CommonEntity>)request.getAttribute("page.mn01.accSemokList");
	List<CommonEntity> accNmList  = (List<CommonEntity>)request.getAttribute("page.mn01.accNmList");
	List<CommonEntity> accDeptList  = (List<CommonEntity>)request.getAttribute("page.mn01.accDeptList");		
  List<CommonEntity> gigwanList  = (List<CommonEntity>)request.getAttribute("page.mn01.gigwanList");
  CommonEntity dateInfo = (CommonEntity)request.getAttribute("page.mn01.fis_date");     //회계일자

	String SucMsg = (String)request.getAttribute("page.mn01.SucMsg");
	if (SucMsg == null ) {
		SucMsg = "";
	}

  String FailMsg = (String)request.getAttribute("page.mn01.FailMsg");
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
    <title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
      function init() {
        var form = document.sForm;
		    typeInitialize();
        form.flag.value = "Y";
        form.amt.focus();
      }
     
			function saveData()	{
	      var form = document.sForm;
				if (form.amt.length == 1)	{
					if (form.amt.value == "")	{
						alert("금액을 입력하세요.");
						form.amt.focus();
            form.save.disabled;
						return;
					}
				} else {																																			
					for (var i=0; i<form.amt.length; i++)	{
						if (form.amt[i].value == "") {
							alert("금액을 입력하세요.");
							form.amt[i].focus();
							return;
						}
					}
				}

        if (form.flag.value == "N" || form.flag.value == "") {
          alert("수기분 등록 중입니다. 잠시만 기다려주세요.");
          return;
        }
				form.action = "IR010210.etax?cmd=writeInsert";  
		    eSubmit(form);
        form.flag.value = "N";
      }

			var rowIndex = 1;
			//행추가
      function addFile(form,k){
        if(rowIndex > (100-k)) return false;
        var oCurrentRow,oCurrentCell;
        oCurrentRow = rowPlay.insertRow();
        rowIndex = oCurrentRow.rowIndex;
        oCurrentCell = oCurrentRow.insertCell();
        rowIndex++;
				oCurrentCell.innerHTML = "<tr><td><br><br><span style=width:50px></span>"
                                + "<img src=../img/board_icon1.gif width=8 height=8 align=absmiddle> 세목명&nbsp;&nbsp;" 
                                + "<select name='semok_code' iValue='<%=request.getParameter("semok_code")%>'><% for (int i=0; accSemokList != null && i < accSemokList.size(); i++) {CommonEntity semokInfo = (CommonEntity)accSemokList.get(i); %>"
                                + "<option value='<%=semokInfo.getString("M370_SEMOKCODE")%>'><%=semokInfo.getString("M370_SEMOKNAME")%></option><% } %></select>"
                                + "<span style=width:53px></span><img src=../img/board_icon1.gif width=8 height=8 align=absmiddle>"
                                + "금액&nbsp;&nbsp; <input type=\"text\" name=\"amt\" onKeyUp=\"keyupCurrencyObj(this);\" onKeyPress=\"keyNumericDash();\"  class=\"box3\" size=\"15\" style=\"text-align:right;ime-mode:disabled;\">&nbsp;&nbsp;원" 
                                + "<span style=width:50px></span><img src=../img/board_icon1.gif width=8 height=8 align=absmiddle> 건수"
                                + "&nbsp;&nbsp; <input type=text name='cnt' onKeyPress=\"keyNumericDash();\" class=box3 size=3 style=\"text-align:right;ime-mode:disabled;\" maxlength=\"5\">&nbsp;건 </td></tr>";
        form.rowCount.value = rowIndex;
      }
   
      //행삭제 
      function deleteFile(form){
        if(rowIndex<2){
          return false;
        }else{
           form.rowCount.value = form.rowCount.value - 1;
           rowIndex--;
           rowPlay.deleteRow(rowIndex);
        }
      }

		  function changeDept(dept) {
				var form = document.sForm;
				/*document.hidFrame.location = "IR010211.etax?cmd=sugiList&part_code="+dept;*/
        document.location = "IR010210.etax?cmd=sugiList&acc_code="+form.acc_code.value+"&part_code="+form.part_code.value+"&array_row="+form.rowCount.value+"&gigwan="+form.gigwan.value+"&intype="+form.intype.value;
       /* document.hidFrame2.location = "/admin/common/semok_list.etax?cmd=sugiList&acc_code="+form.acc_code.value+"&part_code="+form.part_code.value+"&array_row="+form.rowCount.value;*/
        form.amt.focus();
			}


			function getObj() {
         var obj = event.srcElement
         while (obj.tagName != 'TD') obj = obj.parentElement
         return obj;
      }

			function changeSemok(acc_code) {
				var form = document.sForm;
				document.hidFrame.location = "/admin/common/semok_list.etax?cmd=sugiList&acc_code="+acc_code+"&part_code="+form.part_code.value+"&array_row="+form.rowCount.value;
        form.amt.focus();
			}

      function enterKeyAmt() {
        if(isEnterKey()) {
	        document.sForm.cnt.focus();
	      }
      }

      function enterKey() {
        if(isEnterKey()) {
	        saveData();
	      }
      }

      function changeAmt(amt, array) {
        var form = document.sForm;
        form.amt.focus();
      }
  
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR010210.etax">
	<input type="hidden" name="cmd" value="writeInsert">
  <input type="hidden" name="flag" value="">
	<input type="hidden" name="work_log" value="A01">
	<input type="hidden" name="trans_gubun" value="121">
	<input type="hidden" name="array_row">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title1_18.gif"></td>
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
                    <td width="860">
										  <span style="width:50px"></span>
								      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
								 	    회계연도&nbsp;
									    <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
										    <option value="<%=this_year%>"><%=this_year%></option>
										    <option value="<%=last_year%>"><%=last_year%></option>
									    </select>
									    <span style="width:150px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
                      회계일자&nbsp; <input type="text" value="<%=fis_date%>" class="box3" name="fis_date" size="8" userType="date">
									    <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','fis_date');" style="cursor:hand">
											<span style="width:100px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    입력구분&nbsp;
										  <select name="intype" iValue="<%=request.getParameter("intype")%>">
								        <option value="I1">세입</option>
											  <option value="I2">과오납</option>
											  <option value="I3">정정</option>
										  </select>
											<br><br>
											<span style="width:50px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											수납기관&nbsp;
											<select name="gigwan" iValue="<%=request.getParameter("gigwan")%>">
								        <% for (int i=0; gigwanList != null && i < gigwanList.size(); i++) {
									         CommonEntity gigwanInfo = (CommonEntity)gigwanList.get(i); %>												    
										       <option value="<%=gigwanInfo.getString("M430_SUNAPGIGWANCODE")%>"><%=gigwanInfo.getString("M430_SUNAPGIGWANNAME")%></option>
									      <% } %>
								      </select>
									    <span style="width:61px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    부서&nbsp;    
											<select name="part_code" iValue="<%=request.getParameter("part_code")%>"  onchange="changeDept(this.value)">
								        <% for (int i=0; accDeptList != null && i < accDeptList.size(); i++) {
									         CommonEntity deptInfo = (CommonEntity)accDeptList.get(i); %>												    
										       <option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
									      <% } %>
								      </select>
											<span style="width:146px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle" > 
											회계명&nbsp; 
								      <select name="acc_code" iValue="<%=request.getParameter("acc_code")%>" onchange="changeSemok(this.value, 0)">
								        <% for (int i=0; accNmList != null && i < accNmList.size(); i++) {
									         CommonEntity accCdInfo = (CommonEntity)accNmList.get(i); %>												    
								        <option value="<%=accCdInfo.getString("M360_ACCCODE")%>"><%=accCdInfo.getString("M360_ACCNAME")%></option>
									      <% } %>
								      </select>
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
          <table name="rowPlay" id="rowPlay" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
						  <td>
							  <span style="width:830px"></span>
							  <img src="../img/btn_rowplus.gif" alt="행추가(+)" onClick="addFile(sForm,1)" style="cursor:hand">
							  <img src="../img/btn_rowminus.gif" alt="행삭제(-)" onClick="deleteFile(sForm)" style="cursor:hand">
								<br><br>
                <span style="width:50px"></span>
					  	  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
							  세목명&nbsp; 
								<select name="semok_code" iValue="<%=request.getParameter("semok_code")%>" onchange="changeAmt(this.value, 0)">
								  <% for (int i=0; accSemokList != null && i < accSemokList.size(); i++) {
									  CommonEntity semokInfo = (CommonEntity)accSemokList.get(i); %>												    
										<option value="<%=semokInfo.getString("M370_SEMOKCODE")%>"><%=semokInfo.getString("M370_SEMOKNAME")%></option>
									<% } %>
								</select>
								<span style="width:50px"></span>	
							  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
                금액&nbsp; <input type="text" name="amt" class="box3" size="15" value="" userType="sp_money" required desc="금액" onkeyDown="enterKeyAmt()">&nbsp;원
							  <span style="width:50px"></span>
							  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">									
                건수&nbsp; <input type="text" name="cnt" class="box3" size="3" value="" userType="number" maxlength="5" required desc="건수" onkeyDown="enterKey()">&nbsp;건 		 
						  </td>	
					  </tr>
          </table>
					<input type="hidden" name="rowCount" value="1">
        </td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td>
							  <img src="../img/btn_order.gif" alt="등록" onClick="saveData();return false;" style="cursor:hand">			  
							</td>
            </tr>
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
	<iframe name="hidFrame" width="0" height="0"></iframe>      
	<iframe name="hidFrame2" width="0" height="0"></iframe>
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