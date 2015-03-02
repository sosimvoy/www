<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR040310.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-08-05
* 프로그램내용	   : 세외수입 > 징수결의 등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn04.jsp" %>
<%
 CommonEntity budgetView = (CommonEntity)request.getAttribute("page.mn04.budgetView");
  List<CommonEntity> managerList = (List<CommonEntity>)request.getAttribute("page.mn04.managerList");

  List<CommonEntity> semokcodeList = (List<CommonEntity>)request.getAttribute("page.mn04.semokcodeList");
  List<CommonEntity> sesemokcode = (List<CommonEntity>)request.getAttribute("page.mn04.sesemokcode");
  CommonEntity sangweesemokList = (CommonEntity)request.getAttribute("page.mn04.sangweesemokList");

  List<CommonEntity> napbuList = (List<CommonEntity>)request.getAttribute("page.mn04.napbuList");
  CommonEntity napbuaddressList = (CommonEntity)request.getAttribute("page.mn04.napbuaddressList");


  String SucMsg = (String)request.getAttribute("page.mn04.SucMsg");
	
	String baluiDate             = request.getParameter("baluiDate"); //발의일자
  if (baluiDate.equals("")) {
			baluiDate  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
	}  

	String gojiseoPublish        = request.getParameter("gojiseoPublish"); //고지서 발행 일자
  if (gojiseoPublish.equals("")) {
			gojiseoPublish  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
	} 
	
	String napibDate             = request.getParameter("napibDate"); //납입기한
  if (napibDate.equals("")) {
			napibDate  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
	}  

	String jingsubuWrite        = request.getParameter("jingsubuWrite"); //납입기한
  if (jingsubuWrite.equals("")) {
			jingsubuWrite  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
	}  

  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(), "yyyyMMdd", "yyyy");
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
        //document.sForm.businessName.focus();
		    typeInitialize();
				<% if (SucMsg != null && !"".equals(SucMsg)) { %>
          alert("<%=SucMsg%>");
        <% } %>
		  }


			 function selmok(selmok)	{
				var form = document.sForm;

				form.action = "IR040310.etax";
				form.cmd.value = "jingsuManList";
				eSubmit(form);
			}

                        function changeYear(fis_year) {
        var form = document.sForm;

        form.action = "IR040310.etax";
        form.cmd.value = "jingsuManList";
        eSubmit(form);
      }

			 function seladdress(address)	{
				var form = document.sForm;

				form.action = "IR040310.etax";
				form.cmd.value = "jingsuManList";
				eSubmit(form);
			}

		  function saveData()	{
        var form = document.sForm;

        if (form.totalAmt.value == "0" || form.totalAmt.value == "") {
          alert("금액을 입력하세요.");
          form.bonAmt.focus();
          return;
        }
        if (form.businessName.value == "") {
          alert("물건명을 입력하세요.");
          form.businessName.focus();
          return;
        }
        if (form.year.value != "" && form.year.value.length == 4) {
          if (form.fis_year.value != form.year.value) {
            alert("예산서 회계연도는 " + form.year.value +"년 입니다.");
            form.fis_year.value = form.year.value;
            return;
          }
        }
        
    		  form.action ="IR040310.etax";
			    form.cmd.value = "jingsuInsert";
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
  var form = document.sForm;
  var temp_val = 0;
  
  if (form.bonAmt.value == "") {
    form.bonAmt.value = "0";
  }

  if (form.gasanAmt.value == "") {
    form.gasanAmt.value = "0";
  }

  if (form.interestAmt.value == "") {
    form.interestAmt.value = "0";
  }

  temp_val = parseInt(form.bonAmt.value.replace(/,/g, ""), 10) + parseInt(form.gasanAmt.value.replace(/,/g, ""), 10)
           + parseInt(form.interestAmt.value.replace(/,/g, ""), 10);
	form.totalAmt.value = formatCurrency(temp_val);
}

    </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
 <form name="sForm" method="post" action="IR040310.etax">
		<input type="hidden" name="cmd" value="jingsuInsert">
		<input type="hidden" name="work_log" value="A04">
    <input type="hidden" name="trans_gubun" value="131">
    <input type="hidden" name="seq" value="<%=request.getParameter("seq")%>">
    <input type="hidden" name="year" value="<%=request.getParameter("year")%>">
	  <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="15">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title4_11.gif"></td>
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
                      <span style="width:150px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    회계연도&nbsp;&nbsp;&nbsp;&nbsp;
									    <span style="width:10px"></span>
									    <select name="fis_year" class="box3" iValue="<%=request.getParameter("fis_year")%>" onchange="changeYear(this.value)">
                        <option value="<%=this_year%>"><%=this_year%></option>
                        <option value="<%=Integer.parseInt(this_year)-1%>"><%=Integer.parseInt(this_year)-1%></option>
                      </select>
                      <br><br>
									    <span style="width:150px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    발의일자&nbsp;&nbsp;&nbsp;&nbsp;
									    <span style="width:10px"></span>
									    <input type="text" name="baluiDate" class="box3" size="15" value="<%=baluiDate%>" userType="date" >
								      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','baluiDate');" style="cursor:hand">
											<span style="width:100px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    고지서발행&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span style="width:10px"></span>
									    <input type="text" name="gojiseoPublish" class="box3" size="15" value="<%=gojiseoPublish%>" userType="date" >
								      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','gojiseoPublish');" style="cursor:hand">
									    <br><br>

									    <span style="width:150px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    납입기한&nbsp;&nbsp;&nbsp;&nbsp;
									    <span style="width:10px"></span>
									    <input type="text" name="napibDate" class="box3" size="15" value="<%=napibDate%>" userType="date" >
								      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','napibDate');" style="cursor:hand">
											<span style="width:100px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    징수부기재&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span style="width:10px"></span>
											<input type="text" name="jingsubuWrite" class="box3" size="15" value="<%=jingsubuWrite%>" userType="date" >
								      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','jingsubuWrite');" style="cursor:hand">
									    <br><br>

									    <span style="width:150px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    관&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									    <span style="width:10px"></span>
									    <input type="text" name="gwan" class="box3" size="20" value="<%=sangweesemokList.getString("GOANNAME")%>" maxlength="20" desc="관" readonly>
											<span style="width:90px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    항&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span style="width:10px"></span>
									    <input type="text" name="hang" class="box3" size="20" value="<%=sangweesemokList.getString("HANGNAME")%>" maxlength="20" desc="항" readonly>
									    <br><br>
											
											 <span style="width:150px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    목&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span style="width:10px"></span>
												 <select name="mok" iValue="<%=request.getParameter("mok")%>" style="width:20%" onChange="selmok(this.value)">
											  <option value="">--선택--</option>
								        <% for (int i=0; semokcodeList != null && i < semokcodeList.size(); i++) {
									         CommonEntity semokcodeInfo = (CommonEntity)semokcodeList.get(i); %>												    
								        <option value="<%=semokcodeInfo.getString("M370_SEMOKCODE")%>"><%=semokcodeInfo.getString("M370_SEMOKCODE")%>-<%=semokcodeInfo.getString("M370_SEMOKNAME")%></option>
									      <% } %>
								        </select>
											<span style="width:50px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    세목&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span style="width:10px"></span>
												 <select name="semok" iValue="<%=request.getParameter("semok")%>" style="width:20%">
											  <option value="">--선택--</option>
								        <% for (int i=0; sesemokcode != null && i < sesemokcode.size(); i++) {
									         CommonEntity sesemokcodeInfo = (CommonEntity)sesemokcode.get(i); %>												    
								        <option value="<%=sesemokcodeInfo.getString("M420_SEMOKNAME")%>"><%=sesemokcodeInfo.getString("M420_STANDARDSEMOKCODE")%>-<%=sesemokcodeInfo.getString("M420_SEMOKNAME")%></option>
									      <% } %>
								        </select>
									    <br><br>

											<span style="width:150px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    납부자성명
									    <span style="width:10px"></span>
												 <select name="napbujaName" iValue="<%=request.getParameter("napbujaName")%>" style="width:20%" onChange="seladdress(this.value)">
											  <option value="">--선택--</option>
								        <% for (int i=0; napbuList != null && i < napbuList.size(); i++) {
									         CommonEntity napbuInfo = (CommonEntity)napbuList.get(i); %>												    
								        <option value="<%=napbuInfo.getString("M560_NAPBUJA")%>"><%=napbuInfo.getString("M560_NAPBUJA")%></option>
									      <% } %>
								        </select>
											<span style="width:50px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    주민등록번호
											<span style="width:10px"></span>
									    <input type="text" name="juminNo" class="box3" size="20" value="111111-1111111">
									    <br><br>

											<span style="width:150px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    주소&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									    <span style="width:10px"></span>
									    <input type="text" name="address" class="box3" size="80" value="<%=napbuaddressList.getString("M560_ADDRESS")%>" maxlength="100">
											 <br><br>
											<span style="width:150px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    물건명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span style="width:10px"></span>
											<% if (budgetView != null ) { %>
									     <input type="text" name="businessName" class="box3" size="80" value="<%=budgetView.getString("M080_BUSINESSNAME")%>" maxlength="25">
											<%}%>
											<% if (budgetView == null ) { %>
									    <input type="text" name="businessName" class="box3" size="80" value="" maxlength="25" desc="물건명">
											<%}%>
									    <br><br>
											<span style="width:150px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    본세&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									    <span style="width:10px"></span>
									    <input type="text" name="bonAmt" class="box3" style="text-align:right;" size="20" value="" onkeyup="cal(); keyupCurrencyObj(this)"  userType="number">
											<span style="width:90px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" style="text-align:right;" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    가산금&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span style="width:10px"></span>
									    <input type="text" name="gasanAmt"   style="text-align:right;" class="box3" size="20" value="" onkeyup="cal(); keyupCurrencyObj(this)" userType="number">
									    <br><br>

											<span style="width:150px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    이자&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									    <span style="width:10px"></span>
									    <input type="text" name="interestAmt" class="box3"  style="text-align:right;" size="20" value="" onkeyup="cal(); keyupCurrencyObj(this)" userType="number">
											<span style="width:90px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    합계금액&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span style="width:10px"></span>
									    <input type="text" name="totalAmt"  style="text-align:right;"class="box3" size="20" value="" desc="합계금액" readonly>
									    <br><br>

									    <span style="width:150px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;&nbsp;		
								 	    시청담당자&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									    <span style="width:10px"></span>
									    <select name="userName" class="box3">
                      <% for (int i=0; managerList != null && i < managerList.size(); i++) {
                         CommonEntity managerInfo = (CommonEntity)managerList.get(i);
                      %>
                        <option value="<%=managerInfo.getString("M260_USERID")%>"><%=managerInfo.getString("M260_USERNAME")%></option>
                      <% } %>
                      </select>
											<span style="width:285px"></span>				    
                      <img src="../img/btn_order.gif" alt="등록" onClick="saveData();" style="cursor:hand">
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
	  <table width="1000">
	    <tr>
		    <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	    </tr>
    </table>
  </form>
  </body>
</html>
