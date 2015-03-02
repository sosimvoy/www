<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR020210.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세출 > 수기분 조회/수정/삭제
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn02.jsp" %>
<%	
	List<CommonEntity> revWriteList = (List<CommonEntity>)request.getAttribute("page.mn02.revWriteList");
  List<CommonEntity> revDeptList  = (List<CommonEntity>)request.getAttribute("page.mn02.revDeptList");	 
  List<CommonEntity> revAccNmList  = (List<CommonEntity>)request.getAttribute("page.mn02.revAccNmList");
  CommonEntity dateInfo = (CommonEntity)request.getAttribute("page.mn01.fis_date");  //회계일자

  String delMsg =  (String)request.getAttribute("page.mn02.delMsg");
	if (delMsg == null) {
		delMsg = "";
	}

	int listSize = 0;
	if (revWriteList != null && revWriteList.size() != 0) {
		listSize = revWriteList.size();
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
		    typeInitialize();
      }	
	    
			function goSearch() {
	      var form = document.sForm;
		    form.action = "IR020210.etax";
		    form.cmd.value = "revWriteList2";
		    form.target = "_self";
		    eSubmit(form);
	    }
 
	    function goDelete(){
		    var form = document.sForm;
		    var listSize = <%=listSize%>;
		    var cnt = 0;

		    if (listSize == 0) {
		      alert("조회내역이 없습니다.");
			    return;
		    }
		    if (listSize == 1) {
		      if (!form.chk_yn.checked) {
			    alert("삭제할 건을 선택하세요");
			    return;
			    }
		    } else if (listSize > 1) {
		      for (var i=0; i<listSize; i++) {
			      if (form.chk_yn[i].checked) {
            cnt++;
			      }  
			    }
			 
			    if (cnt == 0) {
			      alert("삭제할 건을 선택하세요");
		  	    return;
			    }
		    }
  	  form.action = "IR020210.etax";  
      form.cmd.value ="revWriteDelete";
		  form.target = "_self";
		  eSubmit(form);
      }
			
	    function goView(){
        var form = document.sForm;
		    var listSize = <%=listSize%>;
		    var cnt = 0;

		    if (listSize == 0) {
		      alert("조회내역이 없습니다.");
			    return;
		    }

		    if (listSize == 1) {
	        if (!form.chk_yn.checked) {
			      alert("수정할 건을 선택하세요");
				    return;
			    }
        
          form.pop_acc_type.value = form.chk_acc_type.value;  
          form.pop_part_code.value = form.chk_part_code.value;
          form.pop_acc_code.value = form.chk_acc_code.value;

		    } else if (listSize > 1) {
			      for (var i=0; i<listSize; i++) {
				      if (form.chk_yn[i].checked) {
              cnt++;
              form.pop_acc_type.value = form.chk_acc_type[i].value;  
              form.pop_part_code.value = form.chk_part_code[i].value;
              form.pop_acc_code.value = form.chk_acc_code[i].value;
			        }
			      }
			 
			      if (cnt == 0) {
				      alert("수정할 건을 선택하세요");
				      return;
			      }

			      if (cnt > 1) {
				      alert("수정하고자하는 건을 하나만 선택하세요.");
				      return;
			      }
		      }
         window.open('IR020211.etax', 'popView' ,'height=470,width=440,top=100,left=100,scrollbars=no');
		     form.action = "IR020211.etax"
		     form.cmd.value = "revWriteView";
		     form.target = "popView";
		     eSubmit(form);
        }
			
			function chDept(acc_type) {
				var form = document.sForm;
				document.hidFrame.location = "IR020212.etax?cmd=sugibunList&acc_type="+acc_type+"&flag=0";
			}

			function chAcc(part_code) {
				var form = document.sForm;
				document.hidFrame.location = "IR020212.etax?cmd=sugibunList&acc_type="+form.acc_type.value+"&part_code="+part_code+"&flag=1";
			}

		  function chSemok(acc_code) {
				var form = document.sForm;
				document.hidFrame.location = "IR020212.etax?cmd=sugibunList&acc_type="+form.acc_type.value+"&part_code="+form.part_code.value+"&acc_code="+acc_code;
			}

    </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR020210.etax">
  <input type="hidden" name="cmd" value="revWriteList2">
	<input type="hidden" name="m030_seq" value="">
	<input type="hidden" name="org_year" value="">
  <!-- 팝업시 보내는 코드값 -->                                               
	<input type="hidden" name="pop_acc_type" value="">
	<input type="hidden" name="pop_part_code" value="">
	<input type="hidden" name="pop_acc_code" value="">

    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="15">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title2_2.gif"></td>
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
									    <span style="width:50px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
									    회계연도&nbsp;
									    <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>" userType="number">
										    <option value="<%=this_year%>"><%=this_year%></option>
										    <option value="<%=last_year%>"><%=last_year%></option>
                      </select>
									    
                      <span style="width:100px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">							
									    회계일자&nbsp; <input type="text" name="fis_date" class="box3" size="8" value="<%=fis_date%>" userType="date">
									    <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','fis_date');" style="cursor:hand">
									    
                      <span style="width:100px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 	
								      회계구분&nbsp; 
									    <select name="acc_type" iValue="<%=request.getParameter("acc_type")%>" onchange="chDept(this.value)">
										    <option value="">전체</option>
                        <option value="A">일반회계</option>
										    <option value="B">특별회계</option>
										    <option value="E">기금</option>
									    </select>
											
                      <span style="width:180px"></span>
                      <img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:hand">
											<br><br>
									    
                      <span style="width:50px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    부서&nbsp;
										  <select name="part_code" iValue="<%=request.getParameter("part_code")%>" onchange="chAcc(this.value)">
								        <option value="">전체</option>
                        <% for (int i=0; revDeptList != null && i < revDeptList.size(); i++) {
									         CommonEntity deptInfo = (CommonEntity)revDeptList.get(i); %>												    
										       <option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
									      <% } %>
								      </select>
									    
                      <span style="width:50px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 	
									    회계명&nbsp;
									    <select name="acc_code" iValue="<%=request.getParameter("acc_code")%>" onchange="chSemok(this.value)">
								        <option value="">전체</option>
                        <% for (int i=0; revAccNmList != null && i < revAccNmList.size(); i++) {
									         CommonEntity accCdInfo = (CommonEntity)revAccNmList.get(i); %>												    
										       <option value="<%=accCdInfo.getString("M360_ACCCODE")%>"><%=accCdInfo.getString("M360_ACCNAME")%></option>
									      <% } %>
											</select>
											
                      <span style="width:50px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 	
											세출구분&nbsp;
									    <select name="intype" iValue="<%=request.getParameter("intype")%>">
                        <option value="">전체</option>
                        <option value="I1">세출</option>
                        <option value="I2">반납</option>
                        <option value="I3">정정</option>
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
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
				    <tr> 
					    <th class="fstleft" width="4%">선택</th>
							<th width="7%">회계연도</th>
							<th width="8%">회계일자</th>
							<th width="8%">회계구분</th>
					    <th width="12%">부서</th>
					    <th width="12%">회계명</th>
					    <th width="12%">세목명</th>
					    <th width="7%">세출구분</th>
					    <th width="10%">채주성명</th>
							<th width="7%">지급번호</th>
					    <th width="13%">금액</th>
				    </tr>
				    <%
              long tot_amt = 0L;

					    for (int i=0; revWriteList != null && i < revWriteList.size(); i++) {
					  	  CommonEntity data = (CommonEntity)revWriteList.get(i);

              tot_amt += data.getLong("M030_AMT");
				    %>
				    <tr>
              <input type="hidden" name="chk_acc_type" value="<%=data.getString("M030_ACCTYPE")%>">  
              <input type="hidden" name="chk_part_code" value="<%=data.getString("M030_PARTCODE")%>">
              <input type="hidden" name="chk_acc_code" value="<%=data.getString("M030_ACCCODE")%>">
			  	    <td class="fstleft">&nbsp;<input type="checkbox" name="chk_yn" value="<%=i%>"></td>
					    <td class="center">&nbsp;<%=data.getString("M030_YEAR")%></td>
              <td class="center">&nbsp;
							<%=data.getString("M030_DATE").substring(0,4)%>-<%=data.getString("M030_DATE").substring(4,6)%>-<%=data.getString("M030_DATE").substring(6,8)%></td>
              <td class="center">&nbsp;<%=data.getString("M030_ACCTYPE_NM")%></td>
              <td class="center">&nbsp;<%=data.getString("M350_PARTNAME")%></td>
              <td class="center">&nbsp;<%=data.getString("M360_ACCNAME")%></td>
					    <td class="center">&nbsp;<%=data.getString("M370_SEMOKNAME")%></td>
              <td class="center">&nbsp;<%=data.getString("M030_INTYPE_NM")%></td>
							<td class="center">&nbsp;<%=data.getString("M030_ORDERNAME")%></td>
					    <td class="center">&nbsp;<%=data.getString("M030_ORDERNO")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("M030_AMT"))%>원</td>
					    <input type="hidden" name="year_list" value="<%=data.getString("M030_YEAR")%>">
					    <input type="hidden" name="seq_list" value="<%=data.getString("M030_SEQ")%>">
				    </tr>
				    <%				 
				      } if (revWriteList == null || revWriteList.size() == 0) { 
				    %>
				    <tr> 
              <td class="fstleft" colspan="11">&nbsp;</td>
            </tr>
				    <%
					  	} else {
						%>
						<tr>
					    <td class="fstleft" colspan="3">총 건 수</td>
					    <td class="center" colspan="2">&nbsp;<%=listSize%>건</td>
					    <td class="center" colspan="3">총 합 계</td>	
					    <td class="center" colspan="3">&nbsp;<%=TextHandler.formatNumber(tot_amt)%>원</td>
            </tr>
						<%}%>
			    </table>
			  </td>
		  </tr>
	    <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td>
                <img src="../img/btn_modify.gif" alt="수정" onClick="goView()" style="cursor:hand" align="absmiddle">
						    <img src="../img/btn_delete.gif" alt="삭제" onClick="goDelete()" style="cursor:hand" align="absmiddle">
					    </td>
				    </tr>
			    </table>
		    </td>
	    </tr>
    <table width="1000">
      <tr>
        <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	    </tr>
    </table>
  </form>
	<iframe name="hidFrame" width="0" height="0"></iframe>
	</body>
	<% if (!"".equals(delMsg)) { %>
	  <script>
	  alert("<%=delMsg%>");
		</script>
	<% } %>
</html>