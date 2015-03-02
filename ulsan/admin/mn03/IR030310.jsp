<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR030310.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-07-30
* 프로그램내용	   : 세입세출외현금 > 수입증지불출현황 등록/조회/삭제
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn03.jsp" %>

<%
  List<CommonEntity> stampList =    (List<CommonEntity>)request.getAttribute("page.mn03.stampList");
  
  int stampListSize = 0;
  if (stampList != null) {
    stampListSize = stampList.size();
  }
  
	String SucMsg = (String)request.getAttribute("page.mn03.SucMsg");
	if (SucMsg == null ) {
		SucMsg = "";
	}

	String date = request.getParameter("date");
	if ("".equals(date) ) {
    date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

  String to_day = TextHandler.getCurrentDate();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
      var to_day = "<%=to_day%>";
		  function init() {
  	    typeInitialize();
      }
     
      function goSearch() {
				var form = document.sForm;
	      form.action = "IR030310.etax";
				form.cmd.value = "stampList"; 
				form.target = "_self";
				eSubmit(form);
			}
      
		  function gosaveData()	{
				var form = document.sForm;
        
        if (replaceStr(form.date.value, "-", "") > to_day) {
          alert("등록일이 금일을 지날수는 없습니다.");
          return;
        }
       
        if (confirm("기입사항을 등록하겠습니까?")) {
          form.action = "IR030310.etax";
				  form.cmd.value = "insertStamp";
				  form.target = "_self";
				  eSubmit(form);
        }				
			}
      
			function goUpdate(){
				var form = document.sForm;
        if (confirm(form.date.value + "일 부터 기입사항으로 수정하겠습니까?")) {
          form.action = "IR030310.etax";
				  form.cmd.value = "updateStamp";
			  	form.target = "_self";
			  	eSubmit(form);
        }
				
		  }

			function goBulchul(){
			  var form = document.sForm;				
				form.cmd.value = "suipStampView";
				form.action = "IR030311.etax";
        window.open('IR030311.etax', 'popView' ,'height=700,width=1100,top=100,left=100,scrollbars=yes');
				form.target = "popView";
				eSubmit(form);
			}	

      //당일신규제작 합계 구하기
			function sumCreate(inputObj) {
				var form = document.sForm;
				var temp_val = 0;
        form.createALL.value = "";

				for (var i=0; i<9; i++) {
					if (form.create[i].value != "0" || form.create[i].value != null) {
						temp_val = temp_val + parseInt(form.create[i].value.replace(/,/g, ""), 10);
					} else {
            form.create[i].value = "0";
          }
				}
				form.createALL.value = formatCurrency(temp_val);
			}

      //당일서손 합계 구하기
			function sumLdisuse(inputObj) {
				var form = document.sForm;
				var temp_val = 0;
        form.ldisuseALL.value = "";

				for (var i=0; i<9; i++) {
					if (form.ldisuse[i].value != "0" || form.ldisuse[i].value != null) {
						temp_val = temp_val + parseInt(form.ldisuse[i].value.replace(/,/g, ""), 10);
					} else {
            form.ldisuse[i].value = "0";
          }
				}
				form.ldisuseALL.value = formatCurrency(temp_val);
			}

      //당일시금고 합계 구하기
			function sumGumgosale(inputObj) {
				var form = document.sForm;
				var temp_val = 0;
        form.gumgosaleALL.value = "";

				for (var i=0; i<9; i++) {
					if (form.gumgosale[i].value != "0" || form.gumgosale[i].value != null) {
						temp_val = temp_val + parseInt(form.gumgosale[i].value.replace(/,/g, ""), 10);
					} else {
            form.gumgosale[i].value = "0";
          }
				}
				form.gumgosaleALL.value = formatCurrency(temp_val);
			}

      //당일시청지점판매 합계 구하기
			function sumCitysale(inputObj) {
				var form = document.sForm;
				var temp_val = 0;
        form.citysaleALL.value = "";

				for (var i=0; i<9; i++) {
					if (form.citysale[i].value != "0" || form.citysale[i].value != null) {
						temp_val = temp_val + parseInt(form.citysale[i].value.replace(/,/g, ""), 10);
					} else {
            form.citysale[i].value = "0";
          }
				}
				form.citysaleALL.value = formatCurrency(temp_val);
			}

      //당일시청지점배분 합계 구하기
			function sumCitydivide(inputObj) {
				var form = document.sForm;
				var temp_val = 0;
        form.citydivideALL.value = "";

				for (var i=0; i<9; i++) {
					if (form.citydivide[i].value != "0" || form.citydivide[i].value != null) {
						temp_val = temp_val + parseInt(form.citydivide[i].value.replace(/,/g, ""), 10);
					} else {
            form.citydivide[i].value = "0";
          }
				}
				form.citydivideALL.value = formatCurrency(temp_val);
			}
	
	</script>
  </head>
  <body topmargin="0" leftmargin="0">
		<form name="sForm" method="post" action="IR030310.etax">
		<input type="hidden" name="cmd" value="stampList">
		<input type="hidden" name="work_log" value="A03">
    <input type="hidden" name="trans_gubun" value="131">
	 
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title3_4.gif"></td>
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
										<td width="210">&nbsp;</td>
										<td width="490">
											<span style="width:50px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
                      회계일자&nbsp;
										  <input type="text" class="box3" size="8" name="date" value="<%=date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','date')" align="absmiddle">
										 </td>
										 <td width="200" align="right"> 
											<img src="../img/btn_search.gif" onClick="goSearch()" style="cursor:hand" align="absmiddle">&nbsp;&nbsp;&nbsp;
                      <img src="../img/btn_bullist.gif" alt="불출현황"  onClick="goBulchul()" style="cursor:hand" align="absmiddle">
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
              <th class="fstleft" width="5%">권종</th>
					    <th width="19%">당일신규제작</th>
					    <th width="19%">당일서손(폐기)</th>
					    <th width="19%">당일시금고판매</th>
					    <th width="19%">당일시청지점판매</th>
					    <th width="19%">당일시청지점배분</th>	
            </tr>
	           <%
            String gueonjong = "";
						if (stampListSize != 9) {
							for (int i=0; i < 9; i++) {
								if (i==0){
									gueonjong   = "10";
								} else if (i == 1){
									gueonjong = "50";
								} else if (i == 2){
									gueonjong = "100";
                } else if (i == 3){
									gueonjong = "200";
                } else if (i == 4){
									gueonjong = "300";
								} else if (i == 5){
									gueonjong = "500";
                } else if (i == 6){
									gueonjong = "1000";
						    } else if (i == 7){
									gueonjong = "5000";
								} else if (i == 8){
									gueonjong = "10000";
								}		 
						%>
            <tr>                 
               <td class="fstleft">&nbsp;<input type="text" name="gueonjong" class="box3" size="1" style="text-align:right;border:0;" value="<%=gueonjong%>"tabindex="-1"></td>
							 <td>&nbsp;<input type="text" name="create" class="box3" size="20" value="0" onKeyup="sumCreate(this); keyupCurrencyObj(this);" userType="number"></td>
							 <td>&nbsp;<input type="text" name="ldisuse"  class="box3" size="20" value="0" onKeyup="sumLdisuse(this); keyupCurrencyObj(this);" userType="number"></td>
							 <td>&nbsp;<input type="text" name="gumgosale"  class="box3" size="20" value="0" onKeyup="sumGumgosale(this); keyupCurrencyObj(this);" userType="number"></td>
							 <td>&nbsp;<input type="text" name="citysale" class="box3" size="20" value="0" onKeyup="sumCitysale(this); keyupCurrencyObj(this);" userType="number"></td>
							 <td>&nbsp;<input type="text" name="citydivide" class="box3" size="20"value="0" onKeyup="sumCitydivide(this); keyupCurrencyObj(this);" userType="number"></td>				 
            </tr>
						<% }
						} else if (stampList.size() == 9) {
						for (int i=0; i < stampList.size(); i++) {
							CommonEntity data = (CommonEntity)stampList.get(i);								 
						%>
            <tr>                 
               <td class="fstleft">&nbsp;<input type="text" name="gueonjong" class="box3" size="1" style="text-align:right;border:0;" value="<%=data.getString("M050_GUEONJONG")%>" tabindex="-1"></td>
							 <td>&nbsp;<input type="text" name="create"  class="box3" size="20" value="<%=TextHandler.formatNumber(data.getString("M050_CREATE"))%>" onKeyup="sumCreate(this); keyupCurrencyObj(this);" userType="number"></td>
							 <td>&nbsp;<input type="text" name="ldisuse"  class="box3" size="20" value="<%=TextHandler.formatNumber(data.getString("M050_LDISUSE"))%>"    onKeyup="sumLdisuse(this); keyupCurrencyObj(this);" userType="number"></td>
							 <td>&nbsp;<input type="text" name="gumgosale" style="text-align:right;" class="box3" size="20" value="<%=TextHandler.formatNumber(data.getString("M050_GUMGOSALE"))%>"  onKeyup="sumGumgosale(this); keyupCurrencyObj(this);" userType="number"></td>
							 <td>&nbsp;<input type="text" name="citysale"  style="text-align:right;" class="box3" size="20" value="<%=TextHandler.formatNumber(data.getString("M050_CITYSALE"))%>"   onKeyup="sumCitysale(this); keyupCurrencyObj(this);" userType="number"></td>
							 <td>&nbsp;<input type="text" name="citydivide" style="text-align:right;" class="box3" size="20" value="<%=TextHandler.formatNumber(data.getString("M050_CITYDIVIDE"))%>" onKeyup="sumCitydivide(this); keyupCurrencyObj(this);" userType="number"></td>
            </tr>
						<% }
            }
            %>
            <tr>                 
               <td class="fstleft">&nbsp;<input type="text" readonly name="total_amt" class="box3" size="1" style="text-align:right;border:0;" value="합계" tabindex="-1"></td>
							   <td>&nbsp;<input type="text" readonly name="createALL"  class="box3" size="20" value="" userType="number" readonly></td>
							   <td>&nbsp;<input type="text" readonly name="ldisuseALL"  class="box3" size="20" value="" userType="number"></td>
							   <td>&nbsp;<input type="text" readonly name="gumgosaleALL"  class="box3" size="20" value="" userType="number"></td>
							   <td>&nbsp;<input type="text" readonly name="citysaleALL"  class="box3" size="20" value="" userType="number"></td>
							   <td>&nbsp;<input type="text" readonly name="citydivideALL"  class="box3" size="20" value="" userType="number"></td>
							 
            </tr> 
		  </table>
        </td>
      </tr>
				<td width="18">&nbsp;</td>
         <td width="975"> 
           <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
				     <tr>
							 <td>
							   <img src="../img/btn_order.gif" alt="등록"  onClick="gosaveData()" style="cursor:hand" align="absmiddle">
								 <img src="../img/btn_modify.gif" alt="수정" align="absmiddle" onclick="goUpdate()" style="cursor:hand">
					     </td>
				     </tr>
			     </table>
		     </td>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
		<% if (!"".equals(SucMsg)) { %>
	     <script>
         alert("<%=SucMsg%>");
		 </script>
  <% } %>
  <% if (stampList != null && stampList.size() ==9) { %>
     <script>
       sumCreate();
       sumLdisuse();
       sumGumgosale();
       sumCitysale();
       sumCitydivide();
     </script>
   <% } %>
  </body>
</html>