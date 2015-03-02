<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR010810.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입 > 영수필확인서 조회
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn01.jsp" %>
<%
  List<CommonEntity> accDateList  = (List<CommonEntity>)request.getAttribute("page.mn01.accDateList");
	List<CommonEntity> semokList    = (List<CommonEntity>)request.getAttribute("page.mn01.semokList");
	List<CommonEntity> partList     = (List<CommonEntity>)request.getAttribute("page.mn01.partList");

	int listSize = 0;
	if (accDateList != null && accDateList.size() != 0) {
		listSize = accDateList.size();
	}

  String start_acc         = request.getParameter("start_acc");           //시작회계일자
  String end_acc           = request.getParameter("end_acc");             //종료회계일자
	String start_sunap       = request.getParameter("start_sunap");				  //시작수납일자
	String end_sunap         = request.getParameter("end_sunap");						//종료수납일자

	if (start_acc.equals("")) {
    start_acc  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
  }  
  if (end_acc.equals("")) {
    end_acc  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
  } 
	if (start_sunap.equals("")) {
	  start_sunap  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
  }
	if (end_sunap.equals("")) {
	  end_sunap  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
  } 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<style>
    @media print {
      .noprint {
	     display:none;
	    }
    }
    </style>
    <script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>
    <script language="javascript" src="../js/tax_common.js"></script>
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }

			function goSearch() {
				var form = document.sForm;
				if (form.start_acc.value == "" && form.end_acc.value == "" && form.start_sunap.value == "" && form.end_sunap.value == "") {
					alert("회계일자 또는 수납일자는 필수입력입니다.");
					form.start_acc.focus();
					return;
				}

				if ((form.start_acc.value == "" && form.end_acc.value !="") || (form.start_sunap.value == "" && form.end_sunap.value !="") )	{
					alert("시작일이 입력되면 종료일도 입력되어야 합니다.");
					return;
				}
				form.action = "IR010810.etax"; 
				form.cmd.value ="accDateList";
				form.target = "_self";
				eSubmit(form);
			}

			function changeAcc(semok) {
				document.hidFrame.location = "IR010811.etax?cmd=accDateList&acc_code="+semok;
			}

			function goView(b){
			  var form = document.sForm;
		    var listSize = <%=listSize%>;
		    var cnt = 0;

		    if (listSize == 0) {
		      alert("조회내역이 없습니다.");
			    return;
		    }  
				window.open('IR010812.etax', 'seipPop' ,'height=400,width=544,top=10,left=100,scrollbars=no');
				form.file.value = b;
		    form.action = "IR010812.etax";
		    form.cmd.value = "receivedView";
		    form.target = "seipPop";
		    eSubmit(form);	
        }

        function goPrint() {
          factory.printing.header = ""; // Header에 들어갈 문장  
		      factory.printing.footer = ""; // Footer에 들어갈 문장  
		      factory.printing.portrait = false; // false 면 가로인쇄, true 면 세로 인쇄  
		      factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
		      factory.printing.topMargin = 1.0; // 위 여백 사이즈  
		      factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈  
		      factory.printing.bottomMargin = 0.5; // 아래 여백 사이즈  
		      factory.printing.Print(true); // 출력하기 
        }
	  </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
  <form name="sForm" method="post" action="IR010810.etax">
	<input type="hidden" name="cmd" value="accDateList">
	<input type="hidden" name="file" value="">
    <table width="1001" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="15">&nbsp;</td>
         <td width="978" height="40"><div class="noprint"><img src="../img/title1_8.gif"></div></td> 
      </tr>
      <tr> 
        <td width="15">&nbsp;</td>
        <td width="978" height="40"> 
          <div class="noprint">
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
            <tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
							    <tr>
								    <td>
									    <span style="width:30px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
									    회계일자&nbsp; 
											<input type="text" name="start_acc" class="box3" value="<%=start_acc%>" size="8" userType="date"> 
                      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','start_acc');" style="cursor:hand"> -
        			      	<input type="text" name="end_acc" class="box3" value="<%=end_acc%>" size="8" userType="date"> 
                      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','end_acc');" style="cursor:hand">
											<span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
											회계명&nbsp;
									    <select name="acc_code" iValue="<%=request.getParameter("acc_code")%>" onchange="changeAcc(this.value)">
											  <option value="">전체</option>
										    <option value="31">일반회계</option>
										    <option value="51">교통사업</option>
									    </select>
											<span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
											세목명&nbsp; 
											<select name="semok_code" iValue="<%=request.getParameter("semok_code")%>">
											  <option value="">전체</option>
								        <% for (int i=0; semokList != null && i < semokList.size(); i++) {
									         CommonEntity semokInfo = (CommonEntity)semokList.get(i); %>												    
										       <option value="<%=semokInfo.getString("M370_SEMOKCODE")%>"><%=semokInfo.getString("M370_SEMOKNAME")%></option>
									      <% } %>
								      </select>
											<span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
											부과번호&nbsp; <input type="text" name="gwase_no" class="box3" size="4" value="" userType="number" maxlength="6">
											<br><br>
											<span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
											수납일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="text" name="start_sunap" class="box3" value="" size="8" userType="date"> 
                      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','start_sunap');" style="cursor:hand"> -
        			      	<input type="text" name="end_sunap" class="box3" value="" size="8" userType="date"> 
                      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','end_sunap');" style="cursor:hand">
											<span style="width:30px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
									    금&nbsp;&nbsp;&nbsp;&nbsp;액&nbsp; <input type="text" name="amt" class="box3" size="15" value="" userType="money">&nbsp;원
											<span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
											부서명&nbsp; 
											<select name="part_code" iValue="<%=request.getParameter("part_code")%>">
											  <option value="">전체</option>
								        <% for (int i=0; partList != null && i < partList.size(); i++) {
									         CommonEntity partInfo = (CommonEntity)partList.get(i); %>												    
										       <option value="<%=partInfo.getString("M410_SYSTEMPARTCODE")%>"><%=partInfo.getString("M350_PARTNAME")%></option>
									      <% } %>
								      </select>
											<span style="width:30px"></span>
                      <img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:hand" align="absmiddle">
										</td>
							    </tr>
						    </table>
					    </td>
				    </tr>
				    <tr> 
					    <td><img src="../img/box_bt.gif" width="964" height="10"></td>
				    </tr>
			    </table>
          </div>
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
					    <th class="fstleft" width="10%">회계일자</th>
					    <th width="10%">회계구분</th>
					    <th width="18%">부서</th>
					    <th width="10%">회계명</th>
					    <th width="18%">세목명</th>
					    <th width="10%">부과번호</th>
							<th width="10%">수납일</th>
					    <th width="14%">금액</th>		
				    </tr>
						<%
						  long tot_amt = 0L;

					    for (int i=0; accDateList != null && i < accDateList.size(); i++) {
						    CommonEntity data = (CommonEntity)accDateList.get(i);
	
						  tot_amt += data.getLong("AMT");
				    %>
				    <tr>
					    <td class="fstleft">&nbsp;<a href="javascript:goView('<%=data.getString("ETC_PATH")%>');"><!-- <a href="http://193.1.2.27:8088/report<%=data.getString("ETC_PATH")%>" target="blank"> --><b><%=data.getString("ETC_ACCDATE").substring(0,4)%>-<%=data.getString("ETC_ACCDATE").substring(4,6)%>-<%=data.getString("ETC_ACCDATE").substring(6,8)%></b></a></td>
					    <td class="center">&nbsp;<%=data.getString("ACCTYPE")%></td>
					    <td class="center">&nbsp;<%=data.getString("PARTNAME")%></td>	
					    <td class="center">&nbsp;<%=data.getString("ACCNAME")%></td>
					    <td class="center">&nbsp;<%=data.getString("SEMOKNAME")%></td> 
					    <td class="center">&nbsp;<%=data.getString("ETC_GWASENO")%></td>
							<td class="center">&nbsp;
							<%=data.getString("ETC_SUNAPDATE").substring(0,4)%>-<%=data.getString("ETC_SUNAPDATE").substring(4,6)%>-<%=data.getString("ETC_SUNAPDATE").substring(6,8)%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("AMT"))%></td>
            </tr>
				    <%				 
					   } if (accDateList == null || accDateList.size() == 0) { 
				    %>
						<tr>
						  <td class="fstleft" colspan="9">&nbsp;</td>
						</tr>            
						<%
					  	} else {
						%>
						<tr>
					    <td class="fstleft" colspan="2">총 건 수</td>
					    <td class="center" colspan="2">&nbsp;<%=listSize%></td>
					    <td class="center" colspan="2">총 합 계</td>	
					    <td class="center" colspan="2">&nbsp;<%=TextHandler.formatNumber(tot_amt)%></td>
            </tr>
						<%}%>
			    </table>
			  </td>
		  </tr>
	    <tr> 
        <td width="15">&nbsp;</td>
        <td width="975">
          <div class="noprint">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr>
						  <td>
						    <img src="../img/btn_print.gif" alt="인쇄" onClick="goPrint()" style="cursor:hand" align="absmiddle">
					    </td> 
					  </tr>
			    </table>
          </div>
		    </td>
	    </tr>
    </table>
    <div class="noprint">
    <table width="1000">
      <tr>
        <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	    </tr>
    </table>
    </div>
  </form>
	<iframe name="hidFrame" width="0" height="0"></iframe>
  </body>
</html>