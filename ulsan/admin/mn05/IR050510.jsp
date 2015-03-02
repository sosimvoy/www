<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR050510.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 > 계좌등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> bankAcctInfoList  = (List<CommonEntity>)request.getAttribute("page.mn05.bankAcctInfoList");

	List<CommonEntity> deptList  = (List<CommonEntity>)request.getAttribute("page.mn05.deptList");  //부서명조회
 
	List<CommonEntity> accList   = (List<CommonEntity>)request.getAttribute("page.mn05.accList");  //회계명조회

	String delMsg =  (String)request.getAttribute("page.mn05.DelMsg");
	if (delMsg == null) {
		delMsg = "";
	}

	int bankAcctInfoListSize = 0;
	if (bankAcctInfoList != null ) {
		bankAcctInfoListSize = bankAcctInfoList.size();
	}

	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	String last_year = "";
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
		<script language="javascript" src="../js/utility.js"></script>
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }

			function chAcc(dept_cd) {
				var form = document.sForm;
				document.hidFrame.location = "IR050512.etax?cmd=bankAcctInfoList&acc_type="+form.acc_type.value+"&dept_cd="+dept_cd;
			}

			function chDept(acc_type) {
				var form = document.sForm;
				document.hidFrame.location = "IR050512.etax?cmd=bankAcctInfoList&acc_type="+acc_type+"&flag=1";
			}
      

			function goAcct(acct_kind) {
				var form = document.sForm;
				var fake_allot = form.allot_kind;
				if (acct_kind == "A") {
          deleteOptions(fake_allot);
					addOption(fake_allot, "3", "배정반납");
					addOption(fake_allot, "4", "재배정반납");
				} else {
					deleteOptions(fake_allot);
					addOption(fake_allot, "1", "배정");
					addOption(fake_allot, "2", "재배정");
					addOption(fake_allot, "3", "배정반납");
					addOption(fake_allot, "4", "재배정반납");
				}
			}

			function chBank(acct_gubun) {
				var form = document.sForm;
				var fake_bank_cd = form.bank_cd;

				if (acct_gubun == "01") {
					deleteOptions(fake_bank_cd);
					addOption(fake_bank_cd, "039", "경남은행");
					addOption(fake_bank_cd, "011", "농협");
				} else {
					deleteOptions(fake_bank_cd);
					addOption(fake_bank_cd, "039", "경남은행");
				}
			}
     
      function goSearch() {
				var form = document.sForm;
				form.action = "IR050510.etax";
				form.cmd.value = "bankAcctInfoList";
				form.target = "_self";
      	eSubmit(form);
      }

			function goRegister() {
				var form = document.sForm;
				if (form.acct_nm.value == "")	{
					alert("계좌명은 필수입력입니다.");
					form.acct_nm.focus();
					return;
				}

				if (form.acct_no.value == "")	{
					alert("계좌번호는 필수입력입니다.");
					form.acct_no.focus();
					return;
				}

				if (form.juhaeng.value == "Y") {
					if (form.acct_gubun.value != "03") {
						alert("주행세는 조회계좌를 선택하셔야 합니다.");
						form.acct_gubun.focus();
						return;
					} else if (form.acct_no.value.substring(0,3) != "632") {
						alert("주행세는 계좌번호의 개설점이 시청점(632)이어야 합니다.");
            form.acct_no.focus();
						return;
					} else if (form.acct_no.value.substring(3,5) != "07") {
						alert("주행세는 계좌번호가 보통예금(07)이어야 합니다.");
            form.acct_no.focus();
						return;
					} else if (form.bank_cd.value != "039") {
            alert("주행세는 경남은행계좌만 가능합니다.");
            form.bank_cd.focus();
            return;
          }
				}

        if (form.bank_cd.value == "039" && form.acct_no.value.substring(3,5) == "07") {
          if (form.acct_gubun.value != "03") {
            alert("경남은행 보통예금은 조회계좌만 선택하셔야 합니다.");
            form.acct_gubun.value = "03";
            return;
          } else {
            form.acct_gubun.value = "03";
            form.acc_type.value = "";
            form.acc_cd.value = "";
            form.dept_cd.value = "";
          }
        }

				window.open('IR050511.etax', 'ownerPop', 'height=260,width=340,top=100,left=200,scrollbars=yes');
				form.action = "IR050511.etax";
				form.cmd.value = "bankAcctInfoView";
				form.target = "ownerPop";
      	eSubmit(form);
			}


			function goDelete(){
				var form = document.sForm;
				var listSize = <%=bankAcctInfoListSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.chk_p.checked)	{
					  alert("삭제할 건을 체크하세요.");
					  return;
				  }
				} else if (listSize > 1) {
					for (var i=0; i<form.chk_p.length; i++)	{
					  if (form.chk_p[i].checked)	{
						  cnt++;
					  }
				  }

				  if (cnt == 0)	{
					  alert("삭제할 건을 체크하세요.");
					  return;
				  }
				}
        if (confirm("선택 건(들)을 삭제하겠습니까?")) {
          form.action = "IR050510.etax";
			  	form.cmd.value = "bankAcctDel";
			  	form.target = "_self";
				  eSubmit(form);
        }
				
			}

			function change_check_box_status(obj, isChecked) {
        if (obj)  {
          if( obj.type == 'checkbox' ){
            obj.checked = isChecked;
          } else {
            for( var i = 0; i< obj.length; i++ ) {
							if (obj[i].type == 'checkbox') {
								obj[i].checked = isChecked;
							}
            }
          }
        }
      }

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR050510.etax">
	<input type="hidden" name="cmd" value="bankAcctInfoList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_19.gif"></td>
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
                    <td width="860"><span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계연도
										  <select name="fis_year" value="<%=request.getParameter("fis_year")%>">
												<% for (int i=Integer.parseInt(this_year); i>=2010; i--) { %>
											<option value="<%=i%>"><%=i%></option>
											<% } %>
											</select>
											<span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계구분
										  <select name="acc_type" iValue="<%=request.getParameter("acc_type")%>" onchange="chDept(this.value)">
												<option value="A">일반회계</option>
												<option value="B">특별회계</option>
												<option value="D">세입세출외현금</option>
												<option value="E">기금</option>
											</select><span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    부서
										  <select name="dept_cd" iValue="<%=request.getParameter("dept_cd")%>" onchange="chAcc(this.value)">
												<% for (int i=0; deptList != null && i < deptList.size(); i++) {
													CommonEntity deptInfo = (CommonEntity)deptList.get(i); %>												    
												<option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
												<% } %>
											</select>
											<br><br>
											<span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계명
										  <select name="acc_cd" iValue="<%=request.getParameter("acc_cd")%>">
												<% for (int i=0; accList != null && i < accList.size(); i++) {
													CommonEntity accInfo = (CommonEntity)accList.get(i); %>												    
												<option value="<%=accInfo.getString("M360_ACCCODE")%>"><%=accInfo.getString("M360_ACCNAME")%></option>
												<% } %>
											</select>
											<span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    계좌구분
										  <select name="acct_gubun" iValue="<%=request.getParameter("acct_gubun")%>" onchange="chBank(this.value)">
												<option value="01">입금계좌</option>
												<option value="02">출금계좌</option>
												<option value="03">조회계좌</option>
                        <option value="04">일상경비</option>
											</select><span style="width:122"></span>										  
										  <br><br>
											<span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    은행
										  <select name="bank_cd" iValue="<%=request.getParameter("bank_cd")%>">
												<option value="039">경남은행</option>
												<option value="011">농협</option>
											</select>
											<span style="width:20"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    계좌번호
										  <input type="text" class="box3" size="20" name="acct_no" value="<%=request.getParameter("acct_no")%>" userType="number" maxlength="20">
											<span style="width:20"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    계좌명
										  <input type="text" class="box3" size="30" name="acct_nm" value="<%=request.getParameter("acct_nm")%>" maxlength="30">
											<span style="width:20"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    주행세계좌
										  <select name="juhaeng" iValue="<%=request.getParameter("juhaeng")%>">
												<option value="N">No</option>
												<option value="Y">Yes</option>
										  </select>
										</td>
                    <td width="100">
										  <br><br>
                      <img src="../img/btn_order.gif" alt="등록" style="cursor:hand" onClick="goRegister()">
											<br><br>
											<img src="../img/btn_search.gif" alt="조회" style="cursor:hand" onClick="goSearch()">
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
              <th class="fstleft" width="3%">
							<input type="checkbox" name="all_chk" onClick="change_check_box_status(sForm.chk_p, this.checked);"></th>
              <th width="5%">연도</th>
							<th width="5%">구분</th>
							<th width="15%">부서</th>
              <th width="7%">회계구분</th>
              <th width="15%">회계명</th>
							<th width="14%">계좌명</th>
							<th width="7%">은행</th>
							<th width="12%">계좌번호</th>
							<th width="10%">예금주명</th>
							<th width="7%">주행세<br>계좌</th>
            </tr>
						<% if (bankAcctInfoListSize > 0 && bankAcctInfoList != null) { 
							   for (int i=0; i < bankAcctInfoListSize; i++) {
									 CommonEntity rowData = (CommonEntity)bankAcctInfoList.get(i);
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="chk_p" value="<%=i%>"></td>
              <td class="center"><%=rowData.getString("M300_YEAR")%></td>
              <td class="center"><%=rowData.getString("TYPE_NAME")%></td>
							<td class="center">&nbsp;<%=rowData.getString("M350_PARTNAME")%></td>
              <td class="center">&nbsp;<%=rowData.getString("ACC_GUBUN")%></td>
              <td class="center">&nbsp;<%=rowData.getString("M360_ACCNAME")%></td>
							<td class="center"><%=rowData.getString("M300_ACCNAME")%></td>
              <td class="center"><%=rowData.getString("M300_BANKCODE")%></td>
							<td class="center"><%=TextHandler.formatAccountNo(rowData.getString("M300_ACCOUNTNO"))%></td>
							<td class="center"><%=rowData.getString("M300_NAME")%></td>
							<td class="center"><%=rowData.getString("M300_JUHANGACCYN")%></td>
							<input type="hidden" name="acctNo_list" value="<%=rowData.getString("M300_ACCOUNTNO")%>">
              <input type="hidden" name="acc_code" value="<%=rowData.getString("M300_ACCCODE")%>">
            </tr>
						<%   }
						%>
		        <% }else { %>
						<tr> 
              <td class="fstleft" colspan="9">&nbsp;</td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td>
							  <img src="../img/btn_delete.gif" alt="삭제" style="cursor:hand" onClick="goDelete()" align="absmiddle">
							</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
		<iframe name="hidFrame" width="0" height="0"></iframe>
  </form>
	<% if (!"".equals(delMsg)) { %>
	  <script>
	    alert("<%=delMsg%>");
		</script>
	<% } %>
  </body>
</html>