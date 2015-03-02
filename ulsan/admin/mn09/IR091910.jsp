<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR091910.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-19
* 프로그램내용	   : 시스템운영 > 세목코드관리
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn09.jsp" %>

<%
	List<CommonEntity> partList     = (List<CommonEntity>)request.getAttribute("page.mn09.partList");
	List<CommonEntity> docList      = (List<CommonEntity>)request.getAttribute("page.mn09.docList");
	List<CommonEntity> accList      = (List<CommonEntity>)request.getAttribute("page.mn09.accList");
  List<CommonEntity> docInfoList  = (List<CommonEntity>)request.getAttribute("page.mn09.docInfoList");
  String SucMsg                   = (String)request.getAttribute("page.mn09.SucMsg");
  if (SucMsg == null) {
    SucMsg = "";
  }
  int listSize = 0;

  if (docInfoList != null) {
    listSize = docInfoList.size();
  }

  String accyn = request.getParameter("accyn");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
    <title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
  	<script language="javascript">
      var accyn = "<%=accyn%>";
      function init() {
				typeInitialize();
        if (accyn == "N") {
          document.getElementById("accDiv").style.display = 'none';
        } else if (accyn == "Y") {
          document.getElementById("accDiv").style.display = '';
        }
			}

      function goSearch() {
				var form = document.sForm;
        var accyn = form.reportcode.value.substring(0,1);
        form.accyn.value = accyn;
		    form.cmd.value = "docManage";
		    form.action    = "IR091910.etax";
				eSubmit(form);
			}
      
			function gosaveData()	{
        var form = document.sForm;
			  var accyn = form.reportcode.value.substring(0,1);
				if (form.ilgwalyn.value == "Y") {
          if (form.elecyn.value == "N") {
            alert("일괄결재는 전자결재의 상태가 Y이어야 합니다.");
            return;
          } else if (accyn == "N") {
            alert("일괄결재는 회계구분과 회계코드가 선택이 되어야 합니다.");
            return;
          }
        }

        if (accyn == "N") {
          form.accgubun.value = "";
          form.acccode.value = "";
        }
        form.accyn.value = accyn;
				form.action = "IR091910.etax";
				form.cmd.value = "insertDocInfo";
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
					if (!form.pChk.checked) {
						alert("삭제할 건을 선택하세요");
						return;
					}
				} else if (listSize > 1) {
					for (var i=0; i<listSize; i++) {
						if (form.pChk[i].checked) {
							cnt++;
						}
					}
					 
					if (cnt == 0) {
						alert("삭제할 건을 선택하세요");
						return;
					}
				}
			form.accyn.value = accyn;
			  form.action = "IR091910.etax";  
			  form.cmd.value ="deleteDocInfo";
			  eSubmit(form);
			}

			function changeReportgubun(type) {
        var form = document.sForm;
				document.hidFrame.location = "IR091911.etax?cmd=docManage&reportgubun="+type;
			}

      function changeAcctype(type) {
        var form = document.sForm;
				document.hidFrame.location = "IR091911.etax?cmd=docManage&accgubun="+type;
			}

      function changeReportCode(code) {
        var form = document.sForm;
        var val = code.substring(0,1);
        var accgubun = form.accgubun;
        var acccode =  form.acccode;
        if (val == "N") {
          document.getElementById("accDiv").style.display = 'none';
        } else if (val == "Y") {
          document.getElementById("accDiv").style.display = '';
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
		<form name="sForm" method="post" action="IR091910.etax">
    <input type="hidden" name="cmd" value="docManage">
    <input type="hidden" name="accyn" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_38.gif"></td>
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
										<td>
                      <span style="width:10"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									  	부서명
											<select name="partcode" iValue="<%=request.getParameter("partcode")%>" style="width:190">
                      <% for (int i=0; i<partList.size() && partList != null; i++) { 
                           CommonEntity part = (CommonEntity)partList.get(i);
                      %>
												<option value="<%=part.getString("M350_PARTCODE")%>"><%=part.getString("M350_PARTNAME")%></option>
                      <% } %>
											</select>
											<span style="width:10"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
											보고서구분
											<select name="reportgubun" iValue="<%=request.getParameter("reportgubun")%>"  onchange="changeReportgubun(this.value)">
												<option value="D">일일보고서</option>
												<option value="M">월말보고서</option>
												<option value="Q">분기보고서</option>
											</select>
                      <span style="width:10px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											보고서종류
											<select name="reportcode" iValue="<%=request.getParameter("reportcode")%>" style="width:250" onchange="changeReportCode(this.value)">
								        <% for (int i=0; docList != null && i < docList.size(); i++) {
									         CommonEntity doc = (CommonEntity)docList.get(i); %>												    
								        <option value="<%=doc.getString("M230_ACCYN")%><%=doc.getString("M230_REPORTCODE")%>"><%=doc.getString("M230_REPORTNAME")%></option>
									      <% } %>
								      </select>
                    </td>
                    <td width="80"></td>
                  </tr>
                  <tr>
                    <td>
                      <br>
                      <div id="accDiv" style="display:none;">
                      <span style="width:10px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											회계구분
											<select name="accgubun" iValue="<%=request.getParameter("accgubun")%>" onchange="changeAcctype(this.value)" style="width:100">											    
								        <option value="A">일반회계</option>
                        <option value="B">특별회계</option>
                        <option value="C">공기업특별회계</option>
                        <option value="D">세입세출외현금</option>
                        <option value="E">기금</option>
								      </select>
											<span style="width:10px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											회계명
											<select name="acccode" iValue="<%=request.getParameter("acccode")%>" style="width:170">
								        <% for (int i=0; accList != null && i < accList.size(); i++) {
									         CommonEntity acc = (CommonEntity)accList.get(i); %>												    
								        <option value="<%=acc.getString("M360_ACCCODE")%>"><%=acc.getString("M360_ACCNAME")%></option>
									      <% } %>
								      </select>
                      </div>
											<span style="width:10px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											전자결재
											<select name="elecyn" iValue="<%=request.getParameter("elecyn")%>">
													<option value="Y">Y</option>
													<option value="N">N</option>
											</select>
                      <span style="width:10px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											일괄결재
											<select name="ilgwalyn" iValue="<%=request.getParameter("ilgwalyn")%>">
													<option value="N">N</option>
													<option value="Y">Y</option>
											</select>
                    </td>
                    <td>
										  <img src="../img/btn_order.gif" border="0" align="absmiddle" onClick="gosaveData()" style="cursor:hand" alt="등록">
									  </td>
                  </tr>
                </table>
              </td>
            </tr>
						<tr>
						<td height="10"></td>
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
										<td>
                      <span style="width:10px"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										  부서명
											<select name="list_part" iValue="<%=request.getParameter("list_part")%>">
											<% for (int i=0; i<partList.size() && partList != null; i++) { 
                           CommonEntity part = (CommonEntity)partList.get(i);
                      %>
												<option value="<%=part.getString("M350_PARTCODE")%>"><%=part.getString("M350_PARTNAME")%></option>
                      <% } %>	
											</select>
										<td width="150"> 
											<img src="../img/btn_search.gif" alt="조회" align="absmiddle"border="0" onClick="goSearch()" style="cursor:hand">
                      <img src="../img/btn_delete2.gif" alt="삭제" align="absmiddle" onclick="goCancel()" style="cursor:hand">
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
              <th class="fstleft" width="4%"><input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.pChk, this.checked);"></th>
							<th>부서명</th>
							<th>보고서구분</th>
							<th>보고서종류</th>
							<th>회계구분</th>
							<th>회계명</th>
							<th>전자<br>결재</th>
							<th>일괄<br>결재</th>
            </tr>
         	 <%
						for (int i=0; docInfoList != null && i < docInfoList.size(); i++) {
							CommonEntity data = (CommonEntity)docInfoList.get(i);
						%>

            <tr>                 
              <td class="fstleft"><input type="checkbox" name="pChk" value="<%=i%>"></td>
							<td><%=data.getString("M350_PARTNAME")%></td>
						  <td><%=data.getString("DOCGUBUNNAME")%></td>
							<td><%=data.getString("M230_REPORTNAME")%></td>
							<td>&nbsp;<%=data.getString("ACCTYPENAME")%></td>
							<td>&nbsp;<%=data.getString("M360_ACCNAME")%></td>
							<td><%=data.getString("M630_ELECYN")%></td>
						  <td><%=data.getString("M630_ILGWALYN")%></td>				
							<input type="hidden" name="list_reportcode" value="<%=data.getString("M630_DOCCODE")%>">
							<input type="hidden" name="list_accgubun" value="<%=data.getString("M630_ACCTYPE")%>">
							<input type="hidden" name="list_acccode" value="<%=data.getString("M630_ACCCODE")%>">
            </tr>
					   <%				 
						} if (docInfoList == null) { 
				    	%>
						<tr>
							<td class="fstleft" colspan="10">&nbsp;</td>
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
    <iframe name="hidFrame" width="0" height="0"> </iframe>
  </form>
  <% if (!"".equals(SucMsg)) { %>
  <script>
    alert("<%=SucMsg%>");
  </script>
  <% }%>
  </body>
</html>