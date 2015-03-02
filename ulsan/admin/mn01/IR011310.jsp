<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR011310.jsp
* 프로그램작성자 : (주)미르이즈 
* 프로그램작성일 : 2014-11-20
* 프로그램내용   : 세입 > 차량일계등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ page import = "java.text.*" %>
<%@include file="../menu/mn01.jsp" %>
<%
String ac_year = request.getParameter("ac_year");
if (ac_year.equals("")) {
    ac_year  = StringUtil.getDate().substring(0, 4);
}


String ac_date = request.getParameter("ac_date");
if (ac_date.equals("")) {
    ac_date  = TextHandler.formatDate(StringUtil.getDate().substring(0, 8),"yyyyMMdd","yyyy-MM-dd");
}

String SucMsg = (String)request.getAttribute("page.mn01.SucMsg");
if (SucMsg == null) {
    SucMsg = "";
}

List<CommonEntity> carDailyList = (List<CommonEntity>)request.getAttribute("page.mn01.carDailyList");

String db_date = "";
if (carDailyList != null && carDailyList.size() > 0) {
    db_date = carDailyList.get(0).getString("AC_DATE");
}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
    <title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/jquery-1.10.2.min.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>
  	<script language="javascript">
    
    function goSearch() {
    	var form = document.sForm;
    	form.cmd.value = "carDailyList";
    	form.action = "IR011310.etax";
    	form.submit();
    }
    
    
    function goExcel() {
        var form = document.sForm;
        window.open("", 'ozReport' ,'height=700,width=1000,top=10,left=10,scrollbars=no');
        form.action = "/admin/oz51/IR011310.etax";
        form.cmd.value = "none";
        form.target = "ozReport";
        form.submit();
        form.target = "";
    }
    
			
	function goInsert(){
        var form = document.sForm;
        if (confirm("회계일자("+form.ac_date.value+")분을 등록합니다.")) {
        	prc_Win.style.visibility = "visible";
            form.action = "IR011310.etax?cmd=carDailyInsert";
            form.submit();
        }
    }
	
	
	function goDelete() {
		var form = document.sForm;
		if (confirm("회계일자("+form.ac_date.value+")분을 삭제합니다")) {
			form.cmd.value = "carDailyDelete";
			form.action = "IR011310.etax";
			form.target = "";
			form.submit();
		}
	}
	
	
    $(document).ready(function(){
    	var form = document.sForm;
	    $('.input_box').keyup(function() {
	        var before_nugye0 = Number($('#before_nugye0').val().replace(/,/gi, "")); //취득세 전일누계
	        var before_nugye1 = Number($('#before_nugye1').val().replace(/,/gi, "")); //등록세 전일누계
	        var before_nugye2 = Number($('#before_nugye2').val().replace(/,/gi, "")); //지방교육세 전일누계
	        var before_nugye3 = Number($('#before_nugye3').val().replace(/,/gi, "")); //과년도수입 전일누계
	        var before_nugye4 = Number($('#before_nugye4').val().replace(/,/gi, "")); //농특세 전일누계
	        
	        var today_suip0 = Number($('#today_suip0').val().replace(/,/gi, "")); //취득세 금일수입
            var today_suip1 = Number($('#today_suip1').val().replace(/,/gi, "")); //등록세 금일수입
            var today_suip2 = Number($('#today_suip2').val().replace(/,/gi, "")); //지방교육세 금일수입
            var today_suip3 = Number($('#today_suip3').val().replace(/,/gi, "")); //과년도수입 금일수입
            var today_suip4 = Number($('#today_suip4').val().replace(/,/gi, "")); //농특세 금일수입
            
            var hwanbu_amt0 = Number($('#hwanbu_amt0').val().replace(/,/gi, "")); //취득세 환부
            var hwanbu_amt1 = Number($('#hwanbu_amt1').val().replace(/,/gi, "")); //등록세 환부
            var hwanbu_amt2 = Number($('#hwanbu_amt2').val().replace(/,/gi, "")); //지방교육세 환부
            var hwanbu_amt3 = Number($('#hwanbu_amt3').val().replace(/,/gi, "")); //과년도수입 환부
            var hwanbu_amt4 = Number($('#hwanbu_amt4').val().replace(/,/gi, "")); //농특세 환부
            
            var hwanbu_nugye0 = Number($('#hwanbu_nugye0').val().replace(/,/gi, "")); //취득세 환부누계
            var hwanbu_nugye1 = Number($('#hwanbu_nugye1').val().replace(/,/gi, "")); //등록세 환부누계
            var hwanbu_nugye2 = Number($('#hwanbu_nugye2').val().replace(/,/gi, "")); //지방교육세 환부누계
            var hwanbu_nugye3 = Number($('#hwanbu_nugye3').val().replace(/,/gi, "")); //과년도수입 환부누계
            var hwanbu_nugye4 = Number($('#hwanbu_nugye4').val().replace(/,/gi, "")); //농특세 환부누계
            
            var correct_amt0 = Number($('#correct_amt0').val().replace(/,/gi, "")); //취득세 정정
            var correct_amt1 = Number($('#correct_amt1').val().replace(/,/gi, "")); //등록세 정정
            var correct_amt2 = Number($('#correct_amt2').val().replace(/,/gi, "")); //지방교육세 정정
            var correct_amt3 = Number($('#correct_amt3').val().replace(/,/gi, "")); //과년도수입 정정
            var correct_amt4 = Number($('#correct_amt4').val().replace(/,/gi, "")); //농특세 정정
            
            
            //금일누계(전일누계 + 금일누계 - 정정)
            form.today_nugye0.value = inputComma(before_nugye0+today_suip0-hwanbu_amt0);//취득세 금일누계 합
            form.today_nugye1.value = inputComma(before_nugye1+today_suip1-hwanbu_amt1);//등록세 금일누계 합
            form.today_nugye2.value = inputComma(before_nugye2+today_suip2-hwanbu_amt2);//지방교육세 금일누계 합
            form.today_nugye3.value = inputComma(before_nugye3+today_suip3-hwanbu_amt3);//과년도수입 금일누계 합
            form.today_nugye4.value = inputComma(before_nugye4+today_suip4-hwanbu_amt4);//농특세 금일누계 합
            
            
            var today_nugye0 = Number($('#today_nugye0').val().replace(/,/gi, "")); //취득세 금일누계
            var today_nugye1 = Number($('#today_nugye1').val().replace(/,/gi, "")); //등록세 금일누계
            var today_nugye2 = Number($('#today_nugye2').val().replace(/,/gi, "")); //지방교육세 금일누계
            var today_nugye3 = Number($('#today_nugye3').val().replace(/,/gi, "")); //과년도수입 금일누계
            var today_nugye4 = Number($('#today_nugye4').val().replace(/,/gi, "")); //농특세 금일누계
            
            //합계
	        form.before_nugye_total.value = inputComma(before_nugye0+before_nugye1+before_nugye2+before_nugye3+before_nugye4); //전일누계 합
	        form.today_suip_total.value = inputComma(today_suip0+today_suip1+today_suip2+today_suip3+today_suip4); //금일수입 합
	        form.hwanbu_amt_total.value = inputComma(hwanbu_amt0+hwanbu_amt1+hwanbu_amt2+hwanbu_amt3+hwanbu_amt4); //환부 합
	        form.hwanbu_nugye_total.value = inputComma(hwanbu_nugye0+hwanbu_nugye1+hwanbu_nugye2+hwanbu_nugye3+hwanbu_nugye4); //환부누계 합
	        form.correct_amt_total.value = inputComma(correct_amt0+correct_amt1+correct_amt2+correct_amt3+correct_amt4); //정정 합
	        form.today_nugye_total.value = inputComma(today_nugye0+today_nugye1+today_nugye2+today_nugye3+today_nugye4); //금일누계 합
	    });
	     
	});
	
	//컴마찍기
	String.prototype.comma = function() {
	    tmp = this.split('.');
	    var str = new Array();
	    var v = tmp[0].replace(/,/gi,'');
	    for(var i=0; i<=v.length; i++) {
	        str[str.length] = v.charAt(v.length-i);
	        if(i%3==0 && i!=0 && i!=v.length) {
	            str[str.length] = '.';
	        }
	    }
	    str = str.reverse().join('').replace(/\./gi,',');
	    return (tmp.length==2) ? str + '.' + tmp[1] : str;
	}

	//input에 숫자만 허용
	function onlyNum(obj) {
	    var val = obj.value;
	    var re = /[^0-9\.\,\-]/gi;
	    obj.value = val.replace(re, '');
	}
	
	function inputComma(n) {
        var form= document.sForm;
        var reg = /(^[+-]?\d+)(\d{3})/;
        n+='';
        while (reg.test(n)) {
            n= n.replace(reg, '$1'+','+'$2');
        }
        return n;
}

    </script>
  </head>
  <body>
  <div id="prc_Win" style="visibility:  hidden; z-index:2; filter: alpha(opacity=50); -khtml-opacity: 0.5; -moz-opacity:0.5; opacity: 0.5; background-color:#747474; position: absolute; left: 0px; top: 0px; width: 1000px; height: 600px; text-align: center;">
    <table width="100%" border="0" cellpadding="2" cellspacing="2">
        <tr height="300">
            <td valign="middle"><font color="white" size="13pt">차량사업소 일계를 등록중입니다. 기다려주세요.</font><br></td>
        </tr>         
    </table>
  </div>
  <form name="sForm" method="post" action="IR011310.etax">
	<input type="hidden" name="cmd" value="carDailyInsert">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title1_41.gif"></td>
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
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center" height="30">
                  <tr> 
                    <td>
                      <span style="width:5"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8"> 
                      회계연도<input type="text" value="<%=ac_year%>" name="ac_year" size="4" class="box3">
                      <span style="width:5"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8"> 
					  회계일자<input type="text" value="<%=ac_date%>" name="ac_date" size="10" class="box3" userType="date">
					  <img src="../img/btn_calendar.gif" onclick="Calendar(this,'sForm','ac_date');" style="cursor:hand">
                    </td>
                    <td align="right">
                      <img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:pointer">
                      <img src="../img/btn_order.gif" alt="등록" onClick="goInsert()" style="cursor:pointer">
                      <img src="../img/btn_delete.gif" alt="삭제" onClick="goDelete()" style="cursor:pointer">
                      <img src="../img/btn_excel.gif" alt="엑셀" onClick="goExcel()" style="cursor:pointer">
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
             <td>
                 <table width="975" border="0" cellspacing="0" cellpadding="0" class="list">
                    <tr> 
                        <th >구분</th>
                        <th >회계명</th>
                        <th >전일누계</th>
                        <th >금일수입</th>
                        <th >환부</th>
                        <th >환부누계</th>
                        <th >정정</th>
                        <th >금일누계</th>
                    </tr>
                    <%
                    long BEFORE_NUGYE = 0L; //전일누계
                    long TODAY_SUIP = 0L; //금일수입
                    long HWANBU_AMT = 0L; //환부액
                    long HWANBU_NUGYE = 0L; //환부누계
                    long CORRECT_AMT = 0L; //정정액
                    long TODAY_NUGYE = 0L; //금일누계
                    if (db_date.equals(ac_date.replace("-", ""))) { //조회일자와 데이터상의 회계일자가 동일하면 자료만 조회
	                    for (int i=0; carDailyList != null && i<carDailyList.size(); i++) {
	                        CommonEntity data = (CommonEntity)carDailyList.get(i);
	                        BEFORE_NUGYE += data.getLong("BEFORE_NUGYE");
	                        TODAY_SUIP += data.getLong("TODAY_SUIP");
	                        HWANBU_AMT += data.getLong("HWANBU_AMT");
	                        HWANBU_NUGYE += data.getLong("HWANBU_NUGYE");
	                        CORRECT_AMT += data.getLong("CORRECT_AMT");
	                        TODAY_NUGYE += data.getLong("TODAY_NUGYE");
	                    %>
	                    <tr>
	                        <% if (i==0) { %>
	                        <td rowspan="<%=carDailyList.size()%>"><b>계</b></td>
	                        <% } %>
	                        <td class="left"><%=data.getString("AC_NAME") %></td>
	                        <td class="right"><%=TextHandler.formatNumber(data.getString("BEFORE_NUGYE")) %></td>
	                        <td class="right"><%=TextHandler.formatNumber(data.getString("TODAY_SUIP")) %></td>
	                        <td class="right"><%=TextHandler.formatNumber(data.getString("HWANBU_AMT")) %></td>
	                        <td class="right"><%=TextHandler.formatNumber(data.getString("HWANBU_NUGYE")) %></td>
	                        <td class="right"><%=TextHandler.formatNumber(data.getString("CORRECT_AMT")) %></td>
	                        <td class="right"><%=TextHandler.formatNumber(data.getString("TODAY_NUGYE")) %></td>
	                    </tr>
	                    <%
	                    }
                    %>
	                    <tr>
	                        <td><b>합계</b></td>
	                        <td class="left">&nbsp;</td>
	                        <td class="right"><%=TextHandler.formatNumber(BEFORE_NUGYE) %></td>
	                        <td class="right"><%=TextHandler.formatNumber(TODAY_SUIP) %></td>
	                        <td class="right"><%=TextHandler.formatNumber(HWANBU_AMT) %></td>
	                        <td class="right"><%=TextHandler.formatNumber(HWANBU_NUGYE) %></td>
	                        <td class="right"><%=TextHandler.formatNumber(CORRECT_AMT) %></td>
	                        <td class="right"><%=TextHandler.formatNumber(TODAY_NUGYE) %></td>
	                    </tr>
                    <% 
                    } else { //조회일자와 데이터상의 회계일자가 다르면 입력 가능하도록 
                        for (int i=0; carDailyList != null && i<carDailyList.size(); i++) {
                            CommonEntity data = (CommonEntity)carDailyList.get(i);
                            BEFORE_NUGYE += data.getLong("BEFORE_NUGYE");
                            TODAY_SUIP += data.getLong("TODAY_SUIP");
                            HWANBU_AMT += data.getLong("HWANBU_AMT");
                            HWANBU_NUGYE += data.getLong("HWANBU_NUGYE");
                            CORRECT_AMT += data.getLong("CORRECT_AMT");
                            TODAY_NUGYE += data.getLong("TODAY_NUGYE");
                        %>
                        <tr>
                            <input type="hidden" name="order_no" value="<%=data.getString("ORDER_NO")%>">
                            <input type="hidden" name="ac_name" value="<%=data.getString("AC_NAME")%>">
                            <% if (i==0) { %>
                            <td rowspan="<%=carDailyList.size()%>"><b>계</b></td>
                            <% } %>
                            <td class="left"><%=data.getString("AC_NAME") %></td>
                            <td class="right"><input type="text" class="input_box" id="before_nugye<%=i %>" name="before_nugye" value="<%=TextHandler.formatNumber(data.getString("BEFORE_NUGYE")) %>" size="13" style="text-align:right" onkeyup="onlyNum(this);this.value=this.value.comma();"></td>
                            <td class="right"><input type="text" class="input_box" id="today_suip<%=i %>" name="today_suip" value="<%=TextHandler.formatNumber(data.getString("TODAY_SUIP")) %>" size="13" style="text-align:right" onkeyup="onlyNum(this);this.value=this.value.comma();"></td>
                            <td class="right"><input type="text" class="input_box" id="hwanbu_amt<%=i %>" name="hwanbu_amt" value="<%=TextHandler.formatNumber(data.getString("HWANBU_AMT")) %>" size="13" style="text-align:right" onkeyup="onlyNum(this);this.value=this.value.comma();"></td>
                            <td class="right"><input type="text" class="input_box" id="hwanbu_nugye<%=i %>" name="hwanbu_nugye" value="<%=TextHandler.formatNumber(data.getString("HWANBU_NUGYE")) %>" size="13" style="text-align:right" onkeyup="onlyNum(this);this.value=this.value.comma();"></td>
                            <td class="right"><input type="text" class="input_box" id="correct_amt<%=i %>" name="correct_amt" value="<%=TextHandler.formatNumber(data.getString("CORRECT_AMT")) %>" size="13" style="text-align:right" onkeyup="onlyNum(this);this.value=this.value.comma();"></td>
                            <td class="right"><input type="text" class="input_box" id="today_nugye<%=i %>" name="today_nugye" value="<%=TextHandler.formatNumber(data.getString("TODAY_NUGYE")) %>" size="13" style="text-align:right" onkeyup="onlyNum(this);this.value=this.value.comma();" readonly></td>
                        </tr>
                        <%
                        }
                        %>
                        <tr>
                            <td><b>합계</b></td>
                            <td class="left">&nbsp;</td>
                            <td class="right"><input type="text" class="cal_box" name="before_nugye_total" value="<%=TextHandler.formatNumber(BEFORE_NUGYE) %>" size="13" style="text-align:right" onkeyup="onlyNum(this);this.value=this.value.comma();" readonly></td>
                            <td class="right"><input type="text" class="cal_box" name="today_suip_total" value="<%=TextHandler.formatNumber(TODAY_SUIP) %>" size="13" style="text-align:right" onkeyup="onlyNum(this);this.value=this.value.comma();" readonly></td>
                            <td class="right"><input type="text" class="cal_box" name="hwanbu_amt_total" value="<%=TextHandler.formatNumber(HWANBU_AMT) %>" size="13" style="text-align:right" onkeyup="onlyNum(this);this.value=this.value.comma();" readonly></td>
                            <td class="right"><input type="text" class="cal_box" name="hwanbu_nugye_total" value="<%=TextHandler.formatNumber(HWANBU_NUGYE) %>" size="13" style="text-align:right" onkeyup="onlyNum(this);this.value=this.value.comma();" readonly></td>
                            <td class="right"><input type="text" class="cal_box" name="correct_amt_total" value="<%=TextHandler.formatNumber(CORRECT_AMT) %>" size="13" style="text-align:right" onkeyup="onlyNum(this);this.value=this.value.comma();" readonly></td>
                            <td class="right"><input type="text" class="cal_box" name="today_nugye_total" value="<%=TextHandler.formatNumber(TODAY_NUGYE) %>" size="13" style="text-align:right" onkeyup="onlyNum(this);this.value=this.value.comma();" readonly></td>
                        </tr>
                   <%
                   }
                   %> 
                </table>
             </td>
             </tr>
    </table>
    <p>&nbsp;</p>
    
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
  <% if (!"".equals(SucMsg)) {%>
        <script type="text/javascript">
        prc_Win.style.visibility = "hidden";
        alert('<%=SucMsg%>');
        </script> 
  <% } %>
  </body>
</html>