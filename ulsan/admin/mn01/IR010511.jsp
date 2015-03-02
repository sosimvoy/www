<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR010511.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-12
* 프로그램내용	   : 세입 > 수기분 수정
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  CommonEntity expWriteView = (CommonEntity)request.getAttribute("page.mn01.expWriteView");
  CommonEntity endChk = (CommonEntity)request.getAttribute("page.mn01.endChk");

	List<CommonEntity> deptList  = (List<CommonEntity>)request.getAttribute("page.mn01.deptList");
	List<CommonEntity> accCdList  = (List<CommonEntity>)request.getAttribute("page.mn01.accCdList");
  List<CommonEntity> semokList  = (List<CommonEntity>)request.getAttribute("page.mn01.semokList");
  List<CommonEntity> gigwanList  = (List<CommonEntity>)request.getAttribute("page.mn01.gigwanList");
  
	int listSize = 0;
	if (expWriteView != null && expWriteView.size() != 0) {
		listSize = expWriteView.size();
	}

	String SucMsg = (String)request.getAttribute("page.mn01.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
    <title>울산광역시 세입 및 자금배정관리시스템</title>
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link href="../css/class.css" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../js/calendar.js"></script>	
    <script language="javascript" src="../js/base.js"></script>
    <script language="javascript">
		  function init() {
		    typeInitialize();
      }
	
	    function saveData()	{
  	    var form = document.sForm;
		    form.action = "IR010511.etax";
		    form.cmd.value = "expWriteUpdate";
		    form.target = "_self";
  	    eSubmit(form);
      }
			
			function chDept(acc_type) {
				var form = document.sForm;
				var acc_code;

        if(form.year_type.value == null || form.year_type.value == "" || form.year_type.value == "undefinded"){
            var parentSelect = form.year_type[idx];         // 년도구분
        }else{
            var parentSelect = form.year_type;         // 년도구분
        }

				if (acc_type == "A")	{
					acc_code = "11";
          addOption(parentSelect, "Y1", "현년도");

				}	else if (acc_type == "B")	{
					acc_code = "04";
          deleteOptions(parentSelect);      
          addOption(parentSelect, "Y1", "현년도");
          addOption(parentSelect, "Y2", "과년도");

				} else if (acc_type == "E") {
          acc_code = "01"
          addOption(parentSelect, "Y1", "현년도");
				}
				document.hidFrame.location = "IR010512.etax?cmd=expWriteList&acc_type="+acc_type+"&acc_code="+acc_code+"&flag=0";
			}

			function chAcc(part_code) {
				var form = document.sForm;
				document.hidFrame.location = "IR010512.etax?cmd=expWriteList&acc_type="+form.acc_type.value+"&part_code="+part_code+"&flag=1";
			}

		  function chSemok(acc_code) {
				var form = document.sForm;
				document.hidFrame.location = "IR010512.etax?cmd=expWriteList&acc_type="+form.acc_type.value+"&part_code="+form.part_code.value+"&acc_code="+acc_code;
			}

    </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR010511.etax">
  <input type="hidden" name="cmd" value="expWriteUpdate">
  <% if (expWriteView != null && expWriteView.size() > 0) {%>
	<input type="hidden" name="m010_seq" value="<%=expWriteView.getString("M010_SEQ")%>">
  <input type="hidden" name="fis_year" value="<%=expWriteView.getString("M010_YEAR")%>">
  <input type="hidden" name="fis_date" value="<%=expWriteView.getString("M010_DATE")%>">
	<% } %>
    <table width="500" border="0" cellspacing="0" cellpadding="5">
      <tr> 
        <td> 
          <table width="81%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
					    <td width="18">&nbsp;</td>
					    <td width="482" height="50"><img src="../img/title1_10.gif"></td>
				    </tr>
          </table>
        </td>
      </tr>
	    <% if (expWriteView != null && expWriteView.size() > 0) {%>
			<tr>
       <td>
			   <table width="100%" border="0" cellspacing="0" cellpadding="0">
           <tr>
             <td>			
               <span style="width:50px"></span>
						   <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;
               <b>회계연도&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
               <%=expWriteView.getString("M010_YEAR")%>
						   
               <br><br>
						   <span style="width:50px"></span>
						   <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;						
               <b>회계일자&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
               <%=expWriteView.getString("M010_DATE").substring(0,4)%>-<%=expWriteView.getString("M010_DATE").substring(4,6)%>-<%=expWriteView.getString("M010_DATE").substring(6,8)%>
						   
               <br><br>
						   <span style="width:50px"></span>
						   <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;
						   <b>입력구분&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
               <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                  <%=expWriteView.getString("M010_INTYPE_NM")%>
               <% } else { %>
						   <select name="intype" iValue="<%=expWriteView.getString("M010_INTYPE")%>">
						     <option value="I1">세입</option>
                 <option value="I2">과오납</option>
                 <option value="I3">정정</option>
						   </select>
							 <% 
                  }  
               %>
               
               <br><br>
						   <span style="width:50px"></span>
						   <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;
						   <b>년도구분&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
               <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                  <%=expWriteView.getString("M010_YEARTYPE_NM")%>
               <% } else { %>
						   <select name="year_type" iValue="<%=expWriteView.getString("M010_YEARTYPE")%>">
						     <option value="Y1">현년도</option>
                 <option value="Y2">과년도</option>
						   </select>
							 <% 
                  }  
               %>

               <br><br>
               <span style="width:50px"></span>
							 <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;
							 <b>수납기관&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
               <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                  <%=expWriteView.getString("M430_SUNAPGIGWANNAME")%>
               <% } else { %>
							 <select name="gigwan" iValue="<%=expWriteView.getString("M010_SUNAPGIGWANCODE")%>">
							   <% for (int i=0; gigwanList != null && i < gigwanList.size(); i++) {
									  CommonEntity gigwanInfo = (CommonEntity)gigwanList.get(i); %>												    
								 <option value="<%=gigwanInfo.getString("M430_SUNAPGIGWANCODE")%>"><%=gigwanInfo.getString("M430_SUNAPGIGWANNAME")%></option>
								 <% } %>
							 </select>
						   <% 
                  }  
               %>

               <br><br>
						   <span style="width:50px"></span>
						   <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;
               <b>회계구분&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
               <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>  
                  <%=expWriteView.getString("M010_ACCTYPE_NM")%>
               <% } else { %>
						   <select name="acc_type" iValue="<%=expWriteView.getString("M010_ACCTYPE")%>" onchange="chDept(this.value)">
                 <option value="A">일반회계</option>
                 <option value="B">특별회계</option>
                <option value="C">기금</option>
						   </select>
						   <% 
                  }  
               %>

               <br><br>
						   <span style="width:50px"></span>
						   <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;
						   <b>부&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;서&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
               <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                  <%=expWriteView.getString("M350_PARTNAME")%>
               <% } else { %>
						   <select name="part_code" iValue="<%=expWriteView.getString("M010_PARTCODE")%>" onchange="chAcc(this.value)">
						     <% for (int i=0; deptList != null && i < deptList.size(); i++) {
						        CommonEntity deptInfo = (CommonEntity)deptList.get(i); %>												    
						     <option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
						     <% } %>
						   </select>
						   <% 
                  }  
               %>

               <br><br>
						   <span style="width:50px"></span>
						   <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;
						   <b>회&nbsp;&nbsp;계&nbsp;&nbsp;명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
               <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                  <%=expWriteView.getString("M360_ACCNAME")%>
               <% } else { %>
						   <select name="acc_code" iValue="<%=expWriteView.getString("M010_ACCCODE")%>" onchange="chSemok(this.value)">
						     <% for (int i=0; accCdList != null && i < accCdList.size(); i++) {
						        CommonEntity accCdInfo = (CommonEntity)accCdList.get(i); %>												    
						     <option value="<%=accCdInfo.getString("M360_ACCCODE")%>"><%=accCdInfo.getString("M360_ACCNAME")%></option>
						     <% } %>
						   </select>                                   
						   <% 
                  }  
               %>

               <br><br>
						   <span style="width:50px"></span>
					     <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;
						   <b>세&nbsp;&nbsp;목&nbsp;&nbsp;명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
               <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                  <%=expWriteView.getString("M370_SEMOKNAME")%>
               <% } else { %>
						   <select name="semok_code" iValue="<%=expWriteView.getString("M010_SEMOKCODE")%>">
						     <% for (int i=0; semokList != null && i < semokList.size(); i++) {
						        CommonEntity semokInfo = (CommonEntity)semokList.get(i); %>												    
						     <option value="<%=semokInfo.getString("M370_SEMOKCODE")%>"><%=semokInfo.getString("M370_SEMOKNAME")%></option>
						     <% } %>
						   </select>
               <% 
                  }  
               %>
						   
               <br><br>
						   <span style="width:50px"></span>
					 	   <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;
						   <b>금&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;액&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
               <% if ("Y".equals(endChk.getString("M210_WORKENDSTATE"))) { %>
                  <%=TextHandler.formatNumber(expWriteView.getString("M010_AMT"))%>&nbsp;원
               <% } else { %>
               <input type="text" name="amt" class="box3" size="15" value="<%=expWriteView.getString("M010_AMT")%>" required desc="금액" userType="money">&nbsp;원
						   <% 
                  }  
               %>

               <br><br>
						   <span style="width:50px"></span>
						   <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">&nbsp;&nbsp;
						   <b>건&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;수&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</b>
               <input type="text" name="cnt" class="box3" size="3"  value="<%=expWriteView.getString("M010_CNT")%>" userType="number" maxlength="5" required desc="건수">
          	</td>
					</tr>
          <tr>
				    <td width="380" height="70"> 
					    <div align="center"><img src="../img/btn_modify.gif" alt="수정" onClick="saveData()" style="cursor:hand"></div>
						</td>
          </tr>
        </table>
		  </td>
	  </tr>
	 <% } %>
    </table>
  </form>
	<iframe name="hidFrame" width="0" height="0"></iframe>
	<% if (!"".equals(SucMsg)) { %>
	  <script>
	    alert("<%=SucMsg%>");    
      opener.goSearch();
			//self.close();	
		  </script>
	<% } %>
  </body>
</html>