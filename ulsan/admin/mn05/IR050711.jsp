<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR050711.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2010-06-16
* 프로그램내용   : 자금배정 > 자금배정수기분등록 > 셀렉트박스값 변경
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // 부서코드 (시군구에 속한)
    List<CommonEntity> semokList = (List<CommonEntity>)request.getAttribute("page.mn05.deptList");
		List<CommonEntity> acctList = (List<CommonEntity>)request.getAttribute("page.mn05.acctList");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>
    // Object 정의
    function SemokInfo() {
        this.semokCd;   //세목코드
        this.semokNm;   //세목명
        this.text = getTextValue; //text 출력형태
        this.value = getValue; //세목명
    }

		// Object 정의
    function AcctInfo() {
        this.acctCd;   //회계코드
        this.acctNm;   //회계명
        this.text = getAcctTextValue; //text 출력형태
        this.value = getAcctValue; //회계명
    }
    
    //text value 출력형태(세목코드-세목명)
    function getTextValue() {
        return this.semokNm;
    }

		//text value 출력형태(회계코드-회계명)
    function getAcctTextValue() {
        return this.acctNm;
    }

    function getValue() {
        return this.semokCd.toString();
    }

		function getAcctValue() {
        return this.acctCd.toString();
    }
    
    //세목 정보 Object 를 담을 배열 정의
    var semokInfoList = new Array();

    <%
    for( int i=0 ; semokList != null && i < semokList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)semokList.get(i);
    %>
    var newObject = new SemokInfo();
    newObject.semokCd = "<%=rowData.getString("M350_PARTCODE")%>";
    newObject.semokNm = "<%=rowData.getString("M350_PARTNAME")%>";
    //배열에 정보 Object 저장
    semokInfoList[<%=i%>] = newObject;
    <%
    }
    %>


		//회계 정보 Object 를 담을 배열 정의
    var acctInfoList = new Array();

    <%
    for( int i=0 ; acctList != null && i < acctList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)acctList.get(i);
    %>
    var newObject = new AcctInfo();
    newObject.acctCd = "<%=rowData.getString("M360_ACCCODE")%>";
    newObject.acctNm = "<%=rowData.getString("M360_ACCNAME")%>";
    //배열에 정보 Object 저장
    acctInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* 부모 select 객체에 세목 정보 생성 */
        var parentSelect1 = null;
				var parentSelect2 = null;
        var parentSelect3 = null;
        var parentSelect4 = null;
				var flag = "<%=request.getParameter("bonus")%>";
        try {
					  if (flag != "1") {
							parentSelect1 = parent.document.sForm.dept_kind;
						
						  if( parentSelect1 != null ) {
                deleteOptions(parentSelect1);
                for( var i=0 ; i < semokInfoList.length ; i++ ) {
                  var object = semokInfoList[i];
                  addOption(parentSelect1, object.value(), object.text());
                }
              }


              parentSelect3 = parent.document.sForm.t_dept_kind;
						
						  if( parentSelect3 != null ) {
                deleteOptions(parentSelect3);
                <%if("A".equals(request.getParameter("acc_type"))) { %>
                  addOption(parentSelect3, "00000", "본청");
                <% } else { %>
                for( var i=0 ; i < semokInfoList.length ; i++ ) {
                  var object = semokInfoList[i];
                  addOption(parentSelect3, object.value(), object.text());
                }
                <% } %>
              }


              parentSelect4 = parent.document.sForm.t_acc_kind;

						  if( parentSelect4 != null ) {
                deleteOptions(parentSelect4);
                <%if("A".equals(request.getParameter("acc_type"))) { %>
                  addOption(parentSelect4, "11", "일반회계");
                <% } else { %>
                for( var i=0 ; i < acctInfoList.length ; i++ ) {
                  var object = acctInfoList[i];
                  addOption(parentSelect4, object.value(), object.text());
                }
                <% } %>
              }
					  }
            

						parentSelect2 = parent.document.sForm.acc_kind;

						if( parentSelect2 != null ) {
              deleteOptions(parentSelect2);
              for( var i=0 ; i < acctInfoList.length ; i++ ) {
                var object = acctInfoList[i];
                addOption(parentSelect2, object.value(), object.text());
              }
            }
        } catch(e) {
            //parentSelect1 = parent.document.uForm.dept_kind;
						//parentSelect2 = parent.document.uForm.acc_kind;
        }
    }        
</script>