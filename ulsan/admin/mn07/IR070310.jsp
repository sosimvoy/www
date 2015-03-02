<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	    : IR070310.jsp
* 프로그램작성자    : (주)미르이즈 
* 프로그램작성일    : 2010-10-01
* 프로그램내용	    : 일계/보고서 > 회계연도이월
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn07.jsp" %>
<%
	List<CommonEntity> transList = (List<CommonEntity>)request.getAttribute("page.mn07.transList");
	String retMsg = (String)request.getAttribute("page.mn07.retMsg");
    //out.println("transList::"+transList);

    String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
    String next_year = request.getParameter("next_year");

    if(next_year.equals("")){
        next_year = String.valueOf(Integer.parseInt(this_year) + 1);
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
        typeInitialize();
    }

    function getRadioValue(){
        var form = document.sForm;
        var len = form.chk.length;
        var radioValue = "";

        if (len == undefined) {
                radioValue = form.chk.value;
        } else {
            for(i=0; i<len; i++){
                 if(form.chk[i].checked){
                    radioValue = form.chk[i].value;
                    break;
                 }
            }
        }
        return radioValue;
    }


    function chYear(acc_year){  // 이월회계년도 = 선택한 회계년도 + 1
        var form = document.sForm;
        form.next_year.value = parseInt(acc_year) + 1;
    }

    function goSearch(){
        var form = document.sForm;
        form.cmd.value = "nextYearList";
        eSubmit(form);
    }

    function goInsert(){
        var form = document.sForm;
        var transData = getRadioValue();
        
        if (transData == '' ){
            alert('선택된 이월자료가 존재하지 않습니다.');
            return;
        }
        
        if(form.today.value < transData.substring(2,10)){
            alert("기준일이 현재일보다 큰 경우 이월이 불가 합니다");
            return;
        }else if (transData.substring(10,11) == 'N'){
            alert("미마감 상태 입니다.\n마감된 자료만 이월 가능합니다.");
            return;
        }else if (transData.substring(11,12) == 'Y'){
            alert("이월된 자료 입니다.");
            return;
        }
        form.trans_data.value = transData;
        form.cmd.value = "nextYearProc";
        eSubmit(form);
    }

    function goCancel(){
        var form = document.sForm;
        var transData = getRadioValue();
        
        if (transData == '' ){
            alert('선택된 이월자료가 존재하지 않습니다.');
            return;
        }
        
        if(form.today.value < transData.substring(2,10)){
            alert("기준일이 현재일보다 큰 경우 취소 불가 합니다");
            return;
        }else if (transData.substring(10,11) == 'N'){
            alert("미마감 상태 입니다.\n마감된 자료만 이월 가능합니다.");
            return;
        }else if (transData.substring(11,12) == 'N'){
            alert("이월 전 또는 취소된 자료 입니다.\n이월 후 취소 가능합니다.");
            return;
        }
        form.trans_data.value = transData;
        form.cmd.value = "nextYearProcCancel";
        eSubmit(form);
    }

</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0"">
<form name="sForm" method="post" action="IR070310.etax">
<input type="hidden" name="cmd"             value="nextYearList">
<input type="hidden" name="work_log"        value="A07">
<input type="hidden" name="trans_gubun"     value="131">
<input type="hidden" name="today"           value="<%=TextHandler.getCurrentDate()%>">
<input type="hidden" name="trans_data"      value="">
<table width="1000" border="0" cellspacing="0" cellpadding="5">
    <tr> 
	    <td> 
		    <table width="81%" border="0" cellspacing="0" cellpadding="0">
			    <tr>
                    <td width="15">&nbsp;</td>
                    <td width="975" height="40"><img src="../img/title7_3.gif"></td>
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
                								<span style="width:20px"></span>
                                                <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                								마감대상 회계연도 
                                                <select name="acc_year" iValue="<%=request.getParameter("acc_year")%>" onChange="chYear(this.value)">
                                                    <option value="<%=this_year%>"><%=this_year%></option>
                                                    <option value="<%=last_year%>"><%=last_year%></option>
                                                </select>

                								<br><br>
                								<span style="width:20px"></span>
                                                <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                								이월 회계연도 
                                                <input type="text" name="next_year" value="<%=next_year%>" size="4" readonly>

                                                <span style="width:50px"></span>
                                                <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									            회계구분
                                                <select name="acc_type" desc="회계구분" iValue="<%=request.getParameter("acc_type")%>">
                                                    <option value="A">일반회계</option>
                                                    <option value="B">특별회계</option>
                                                    <!-- <option value="C">공기업특별회계</option> -->
                                                    <option value="D">세입세출외현금</option>
                                                    <option value="E">기금</option>
                                                </select>

                								<span style="width:400px"> </span>
                								<img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:hand" valign="middle" align="absmiddle">
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
                        <table width="975" border="0" cellspacing="0" cellpadding="0" class="list">
                    		<tr>
                    		    <th width="60" class="fstleft">선택</th>
                    			<th>회계명</th>
                    			<th width="100">기준일</th>
                    			<th width="80">마감여부</th>
                    			<th width="80">이월여부</th>
                    			<th width="120">잉여금총액</th>
                    			<th width="120">기이입액</th>
                    			<th width="120">당일이입액</th>
                    		</tr>
                            <%
                            if(transList != null && transList.size() > 0){
                                for (int i=0; i < transList.size(); i++) {
                                    CommonEntity rowData = (CommonEntity)transList.get(i);
                            %>
                    		<tr>
                                <input type="hidden" name="workendstate" value="<%=rowData.getString("WORKENDSTATE")%>">
                                <input type="hidden" name="acctransferyn" value="<%=rowData.getString("ACCTRANSFERYN")%>">
                    			<td class="fstleft">
                                    <!-- 12 = 회계코드(2) + 기준일(8) + 마감여부(1) + 이월여부(1) -->
                                    <input type="radio" name="chk" value="<%=rowData.getString("ACCCODE")+rowData.getString("ACCDATE")+rowData.getString("WORKENDSTATE")+rowData.getString("ACCTRANSFERYN")%>" <%=(i==0)? "checked ":""%>>
                                </td>
                    			<td class="left">[<%=rowData.getString("ACCCODE")%>] <%=rowData.getString("ACCNAME")%></td>
                    			<td><%=TextHandler.formatDate(rowData.getString("ACCDATE"),"yyyyMMdd","yyyy-MM-dd")%></td>
                    			<td><%=("Y".equals(rowData.getString("WORKENDSTATE")))? "마감":"미마감" %></td>
                    			<td><%=("Y".equals(rowData.getString("ACCTRANSFERYN")))? "이월":"미이월" %></td>
                    			<td class="right"><%=StringUtil.formatNumber(rowData.getString("ING_AMT"))%></td>
                    			<td class="right"><%=StringUtil.formatNumber(rowData.getString("PRE_SUR_AMT"))%></td>
                    			<% if ("D".equals(request.getParameter("acc_type")) ) { %>  
                    			<td class="right"><%=StringUtil.formatNumber(rowData.getString("ING_AMT"))%></td>
                    			<% } else { %>
                    			<td class="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_SUR_AMT"))%></td>
                    			<% } %>
                    		</tr>
                            <%  
                                }
                            }else{
                            %>
                    		<tr>
                                <td colspan="8">조회된 자료가 없습니다.</td>
                    		</tr>
                            <%}%>
                    	</table>
                    </td>
                </tr>
                <tr> 
                    <td width="18">&nbsp;</td>
                    <td width="975"> 
                        <table width="975" border="0" cellspacing="0" cellpadding="0" class="btn">
                            <tr> 
                                <td>
                            		<img src="../img/btn_carry.gif" alt="이월" onClick="goInsert()" style="cursor:hand" align="absmiddle">
                            		<img src="../img/btn_cancel.gif" alt="취소" onClick="goCancel()" style="cursor:hand" align="absmiddle">
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
        </td>
    </tr>
</table>
</form>
</body>
</html>


<% if (!"".equals(retMsg) && retMsg != null) { %>
<script language=javascript>
    alert('<%=retMsg%>');
</script>
<% } %>
