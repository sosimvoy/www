<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR011010.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입 > 세입금정정요구서 조회/결과통지
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn01.jsp" %>
<%
	List<CommonEntity>expLedgerList = (List<CommonEntity>)request.getAttribute("page.mn01.expLedgerList");
	
	int listSize = 0;
	if (expLedgerList != null && expLedgerList.size() != 0) {
		listSize = expLedgerList.size();
	}
	  String last_year = "";
		String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		int last_int = Integer.parseInt(this_year) - 1;
		last_year = String.valueOf(last_int);

	String odate = request.getParameter("odate");
	if ("".equals(odate)) {
    odate = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

  String SucMsg =  (String)request.getAttribute("page.mn01.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
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
	
	    function goSearch() {								  //조회
	      var form = document.sForm;
		    form.action = "IR011010.etax";
		    form.cmd.value = "expLedgerList";
		    form.target = "_self";
		    eSubmit(form);
	    }

      function goExcute(){         //결과통지
        var form = document.sForm;
		    var listSize = <%=listSize%>;
		    var cnt = 0;

		    if (listSize == 0) {
		      alert("조회내역이 없습니다.");
			    return;
		    }

		    if (listSize == 1) {
	        if (!form.taxinChk.checked) {
			      alert("결과통지할 건을 선택하세요");
				    return;
			    }
          form.seq.value =  form.seq_list.value;
          cnt = 1;
		    } else if (listSize > 1) {
			    for (var i=0; i<listSize; i++) {
				    if (form.taxinChk[i].checked) {
              form.seq.value = form.seq_list[i].value;
              cnt++;
			      }
			    }
			 
			    if (cnt == 0) {
				    alert("결과통지할 건을 선택하세요");
				    return;
			    }

			    if (cnt > 1) {
				    alert("결과통지하고자하는 건을 하나만 선택하세요.");
				    return;
			    }
		    }

        if (confirm("선택 건을 결과통보하시겠습니까?")) { 
				form.action = "IR011010.etax";
				form.cmd.value = "expLedgerUpdate";
				form.target = "_self";
				eSubmit(form);
			 }
     }

    /*  function goExcute(){										//결과통지
				var form = document.sForm;
				var listSize = <%=listSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.taxinChk.checked)	{
					  alert("결과통지할 건을 선택하세요.");
					  return;
            form.seq.value =  form.seq_list.value;
				  } else {
						form.taxin_yn.value = "Y";
						if (form.taxin_state.value == "S3")	{
							alert("선택 건 중에 결과통지건이 존재합니다.");
							return;
						}
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.taxinChk.length; i++)	{
					  if (form.taxinChk[i].checked)	{
              form.seq.value = form.seq_list[i].value;
						  cnt++;
							form.taxin_yn[i].value = "Y";
							if (form.taxin_state[i].value == "S3")	{
								alert("선택 건 중에 결과통지건이 존재합니다.");
							  return;
						  }
					  }
				  }

				  if (cnt == 0)	{
					  alert("결과통지할 건을 선택하세요.");
					  return;
				  }
				}
       if (confirm("선택 건을 결과통보하시겠습니까?")) { 
				form.action = "IR011010.etax";
				form.cmd.value = "expLedgerUpdate";
				form.target = "_self";
				eSubmit(form);
			 }
     }
     */
	    function goView(b){
        var form = document.sForm;
		    var listSize = <%=listSize%>;
		    var cnt = 0;
    
	     if (listSize == 0) {
	       alert("조회내역이 없습니다.");
		     return;
	     }  
        window.open('IR011011.etax', 'seipPop' ,'height=620,width=430,top=10,left=100,scrollbars=no');
           form.seq.value = b;
           form.action = "IR011011.etax";
           form.cmd.value = "informView";
           form.target = "seipPop";
           eSubmit(form);	
       }
    </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR011010.etax">
  <input type="hidden" name="cmd" value="expLedgerList">
	<input type="hidden" name="seq" value="">
  <input type="hidden" name="work_log" value="A01">
  <input type="hidden" name="trans_gubun" value="197">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title1_9.gif"></td>
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
                <table width="900" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td>
                      <span style="width:50px"></span>
                        <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
                        회계연도&nbsp;
                        <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
                          <option value="<%=this_year%>"><%=this_year%></option>
                          <option value="<%=last_year%>"><%=last_year%></option>
                        </select>
                        <span style="width:50px"></span>
                        <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
                        정정일자&nbsp; <input type="text" name="odate" class="box3" size="8" value="<%=odate%>" userType="date">
                        <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','odate');" style="cursor:hand">
                    </td>
                    <td width="123"> 
                      <div align="right"><img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:hand">
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
			    <table width="975" border="0" cellspacing="0" cellpadding="0" class="list">
				    <tr> 
					    <th class="fstleft" width="30">선택</th>
					    <th width="100">년월</th>
					    <th width="150">과목</th>
					    <th width="120">영수일자</th>
					    <th width="155">납입금액</th>
					    <th width="150">납입자명</th>
					    <th width="150">사유</th>
					    <th width="120">정정일자</th>
				    </tr>
				    <%
					    for (int i=0; expLedgerList != null && i < expLedgerList.size(); i++) {
						  CommonEntity data = (CommonEntity)expLedgerList.get(i);
				    %>
				    <tr>
			  	    <% if ("S2".equals(data.getString("M020_STATE"))) { %>
              <td class="fstleft"><input type="checkbox" name="taxinChk" value="<%=i%>"></td>
							<% } else { %>
							<td class="fstleft"><input type="hidden" name="taxinChk" value="<%=i%>">&nbsp;</td>
							<% } %>
					    <td class="center">&nbsp;
						  <%=data.getString("M020_CYEARMONTH").substring(0,4)%>-<%=data.getString("M020_CYEARMONTH").substring(4,6)%>
						  </td>
              <td class="center">&nbsp;<%=data.getString("M020_CSUBJECT")%></td>
              <td class="center">&nbsp;
						  <%=data.getString("M020_CRECEIPTDATE").substring(0,4)%>-<%=data.getString("M020_CRECEIPTDATE").substring(4,6)%>-<%=data.getString("M020_CRECEIPTDATE").substring(6,8)%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("M020_CPAYMENTAMT"))%>원</td>
              <td class="center">&nbsp;<a href="javascript:goView('<%=data.getString("M020_SEQ")%>')"><b><%=data.getString("M020_OPAYMENTNAME")%></b></a></td>
						  <td class="center">&nbsp;<%=data.getString("M020_OREASON")%></td>
						  <td class="center">&nbsp;
						  <%=data.getString("M020_ODATE").substring(0,4)%>-<%=data.getString("M020_ODATE").substring(4,6)%>-<%=data.getString("M020_ODATE").substring(6,8)%></td>
					    <input type="hidden" name="taxin_state" value="<%=data.getString("M020_STATE")%>">
					    <input type="hidden" name="year_list" value="<%=data.getString("M020_YEAR")%>">
						  <input type="hidden" name="seq_list" value="<%=data.getString("M020_SEQ")%>">
							<input type="hidden" name="taxin_yn" value="">
				    </tr>
				    <%				 
				      } if (expLedgerList == null || expLedgerList.size() == 0) { 
				    %>
				    <tr> 
              <td class="fstleft" colspan="8">&nbsp;</td>
            </tr>
				    <% } %>
			    </table>
			  </td>
		  </tr>
		  <tr>
		    <td width="18">&nbsp;</td>
        <td width="975"> 
			    <table width="900" border="0" cellspacing="1" cellpadding="1" align="center">
				    <tr>
					    <td height="22"> 
						    <div align="right"><img src="../img/btn_inform.gif" alt="결과통지" onClick="goExcute()" style="cursor:hand" align="absmiddle">
						    </div>
					    </td>
				    </tr>
			    </table>
		    </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
  <% if (!"".equals(SucMsg)) { %>
	  <script>
	  alert("<%=SucMsg%>");
		</script>
	<% } %>
  </body>
</html>