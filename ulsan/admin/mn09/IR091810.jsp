<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR091810.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2011-11-23
* 프로그램내용	   : 시스템운영 > 세출계좌등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn09.jsp" %>

<%
	List<CommonEntity>tefAccountList =    (List<CommonEntity>)request.getAttribute("page.mn09.tefAccountList");

  List<CommonEntity>usePartList =    (List<CommonEntity>)request.getAttribute("page.mn09.usePartList");
	List<CommonEntity>useAcccodeList =    (List<CommonEntity>)request.getAttribute("page.mn09.useAcccodeList");
 
	
	String SucMsg = (String)request.getAttribute("page.mn09.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}
  
	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
 
  int listSize = 0;
	if (tefAccountList != null && tefAccountList.size() > 0) {
		listSize = tefAccountList.size();
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
				typeInitialize();
				<% if (SucMsg != null && !"".equals(SucMsg)) { %>
					alert("<%=SucMsg%>");
				<% } %>
			}

      function goSearch() {
				var form = document.sForm;
			
		    form.cmd.value = "chulAccSel";
		    form.action    = "IR091810.etax";
				form.target = "_self";
				eSubmit(form);
			}

      function selAcccode(acccode) {
				var form = document.sForm;
			
        document.hidFrame.location = "IR091811.etax?cmd=chulAccSel2&year="+form.year.value+"&stdAcccode="+form.stdAcccode.value;
		    //form.cmd.value = "stdSemokSel2";
		    //form.action    = "IR091810.etax";
				//form.target = "_self";
				//eSubmit(form);
			}

      function selPartcode(partcode) {
				var form = document.sForm;
			
        document.hidFrame.location = "IR091813.etax?cmd=chulAccSel2&year="+form.year.value+"&stdAcccode="+form.stdAcccode.value+"&sysPartcode="+form.sysPartcode.value;
		    //form.cmd.value = "stdSemokSel2";
		    //form.action    = "IR091810.etax";
				//form.target = "_self";
				//eSubmit(form);
			}
			 function gosaveData()	{
				var form = document.sForm;
        
				if (form.year.value == "") {
          alert("년도를 기입하세요.");
          form.year.focus();
          return;
        }
				if (form.stdAcccode.value == "") {
          alert("회계구분을 기입하세요.");
          form.stdAcccode.focus();
          return;
        }
				if (form.acct_no.value == "") {
          alert("세출계좌번호를 기입하세요.");
          form.acct_no.focus();
          return;
        }
				if (form.sysPartcode.value == "") {
          alert("부서를 기입하세요.");
          form.sysPartcode.focus();
          return;
        }

				if (form.sysAccount.value == "") {
          alert("회계코드를 기입하세요.");
          form.sysAccount.focus();
          return;
        }
        
				if (form.send_yn.value != "Y") {
          alert("계좌 수취인조회 확인후 등록하세요.");
          return;
        }
				form.action = "IR091810.etax";
				form.cmd.value = "chulAccInt";
				form.target = "_self";
				form.send_yn.value = "N";
				eSubmit(form);
			}


			function goTrans() {
				var form = document.sForm;
        var bankcd = "039";

				if (form.acct_no.value == "")	{
					alert("계좌번호는 필수입력입니다.");
					form.acct_no.focus();
					return;
				}

        //form.acct_gubun.value = "03";
          //  form.acc_type.value = "";
          //  form.acc_cd.value = "";
          //  form.dept_cd.value = "";

				window.open('IR091812.etax', 'ownerPop', 'height=260,width=340,top=100,left=200,scrollbars=yes');
				form.action = "IR091812.etax";
				form.cmd.value = "bankAcctInfoView";
				form.send_yn.value = "Y";
				form.target = "ownerPop";
      	eSubmit(form);
			}

		  function goCancel(){
				var form = document.sForm;
				var listSize = <%=listSize%>;
				var cnt = 0;

				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}
				if (listSize == 1) {
					if (!form.userChk.checked) {
						alert("삭제할 건을 선택하세요");
						return;
					}
				} else if (listSize > 1) {
					for (var i=0; i<listSize; i++) {
						if (form.userChk[i].checked) {
							cnt++;
						}
					}
					 
						if (cnt == 0) {
						alert("삭제할 건을 선택하세요");
						return;
						}
					}
				 form.action = "IR091810.etax";  
				 form.cmd.value ="chulAccDel";
				 form.target = "_self";
				 eSubmit(form);
			 }
     
     function ltrim(stdSemokName) {
			 var form = document.sForm
			return form.stdSemokName.replace(/^\s+/,"");
		}

    </script>
  </head>
  <body topmargin="0" leftmargin="0" oncontextmenu="return false">
		<form name="sForm" method="post" action="IR091810.etax">
    <input type="hidden" name="cmd" value="partCodeList">
    <input type="hidden" name="bank_cd" value="039">
    <input type="hidden" name="send_yn" value="N">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_37.gif"></td>
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
										<td width="60">&nbsp;</td>
										<td width="740"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">회계연도</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<select name="year" iValue="<%=request.getParameter("year")%>">
															<option value="<%=this_year%>"><%=this_year%></option>
															<option value="<%=last_year%>"><%=last_year%></option>
														</select>
													<span style="width:20px"></span>
                          <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
													<span class="point">회계구분</span>
														<select name="stdAcccode" iValue="<%=request.getParameter("stdAcccode")%>" onChange="selAcccode(this.value)">
                              <option value="">-- 선택 --</option>
															<option value="A">일반회계</option>
															<option value="B">특별회계</option>
															<option value="D">세입세출외현금</option>
															<option value="E">기금</option>
													</select>
													<span style="width:20px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">계좌번호</span>
													<input type="text" name="acct_no" class="box3" size="20" value="" maxlength="20">
                                                    <img src="../img/btn_receiveinfo1.gif" alt="수취인조회" style="cursor:hand" onClick="goTrans()" align="absmiddle">
													<br><br>
                          <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">부서코드</span>
														<select name="sysPartcode" iValue="<%=request.getParameter("sysPartcode")%>" onChange="selPartcode(this.value)" style="width:150px">
															<option value="">-- 선택 --</option>
														<% for (int i=0; usePartList != null && i < usePartList.size(); i++) {
															 CommonEntity deptInfo = (CommonEntity)usePartList.get(i); %>												    
															 <option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
														<% } %>
														</select>
										 	    <span style="width:20px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">회계코드</span>
														<select name="sysAccount" iValue="<%=request.getParameter("sysAccount")%>" style="width:250px">
															<option value="">-- 선택 --</option>
														<% for (int i=0; useAcccodeList != null && i < useAcccodeList.size(); i++) {
															 CommonEntity accountInfo = (CommonEntity)useAcccodeList.get(i); %>												    
															 <option value="<%=accountInfo.getString("M360_ACCCODE")%>"><%=accountInfo.getString("M360_ACCNAME")%></option>
														<% } %>
													</select>
											 <td width="50"> 
											<div align="right"><img src="../img/btn_order.gif" width="55" height="21" border="0" onClick="gosaveData()" style="cursor:hand">
											</div>
										 </td>
									</tr>
                </table>
              </td>
            </tr>
            <tr> 
              <td><img src="../img/box_bt.gif" width="964" height="10"></td>
            </tr>
          </table>
	        <br>
				  	<hr>
					<br>
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
						<tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
									<tr>
										<td width="110">&nbsp;</td>
										<td width="600">
								  	<span style="width:150px"></span>
                    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">회계연도</span>
											<select name="queyear" iValue="<%=request.getParameter("queyear")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
								  	<span style="width:30px"></span>
                          <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
													<span class="point">표준회계</span>
														<select name="questdAcccode" iValue="<%=request.getParameter("questdAcccode")%>">
                                                            <option value="">-- 선택 --</option>
															<option value="A">일반회계</option>
															<option value="B">특별회계</option>
															<option value="D">세입세출외현금</option>
															<option value="E">기금</option>
													</select>
								  	<span style="width:30px"></span>
												<td width="150"> 
											<div align="right">
                      <img src="../img/btn_search.gif" border="0" onClick="goSearch()" style="cursor:hand">
											</div>
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
              <th class="fstleft" width="4%">선택</th>
							<th width="8%">회계연도</th>
							<th width="8%">회계구분</th>
							<th width="12%">계좌번호</th>
							<th width="18%">부서코드</th>
							<th width="18%">회계코드</th>
            </tr>
         	 <%
						for (int i=0; tefAccountList != null && i < tefAccountList.size(); i++) {
							CommonEntity data = (CommonEntity)tefAccountList.get(i);
                            String acc_no = StringUtil.decrypt("ETS.TRANS_TEF_EFAM026","OUT_ACCT_ACCT_NO",data.getString("account_no"));
						%>

            <tr>                 
              <td class="fstleft"><input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=data.getString("fis_year")%></td>
							<td>&nbsp;<%=data.getString("accgbn_nm")%></td>
							<td>&nbsp;<%=acc_no%></td>
							<td>&nbsp;<%=data.getString("part_name")%></td>
							<td>&nbsp;<%=data.getString("acc_name")%></td>
							<input type="hidden" name="delyear" value="<%=data.getString("fis_year")%>">
							<input type="hidden" name="delacccodeno" value="<%=data.getString("account_no")%>">
            </tr>
					   <%				 
						} if (tefAccountList == null) { 
				    	%>
						<tr>
							<td class="fstleft" colspan="6">조회 내역이 없습니다.</td>
						</tr>
						<% 
				    } 
					  %>
          </table>
					<table class="btn">
						<tr>
							<td>
								<img src="../img/btn_delete2.gif" alt="삭제" align="absmiddle" onclick="goCancel()" style="cursor:hand">
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
  </body>
</html>