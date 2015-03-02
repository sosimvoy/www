<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : semok_array.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2010-06-16
* 프로그램내용   : 회계구분에 따른 세목리스트 변경
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // 부서코드 (시군구에 속한)
    List<CommonEntity> semokList = (List<CommonEntity>)request.getAttribute("page.mn01.impSemokList");
		String array_val = (String)request.getParameter("array_val");
		String array_row = (String)request.getParameter("array_row");
		String rowCount  = (String)request.getParameter("rowCount");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>
    //법정동행정동 정보 Object 정의
    function SemokInfo() {
        this.semokCd;   //세목코드
        this.semokNm;   //세목명
        this.text  = getTextValue; //text 출력형태
        this.value = getValue; //세목명
    }
    
    //text value 출력형태(세목코드-세목명)
    function getTextValue() {
        return this.semokNm;
    }

    function getValue() {
        return this.semokCd.toString();
    }
    
    //계좌 정보 Object 를 담을 배열 정의
    var semokInfoList = new Array();

    <%
    for( int i=0 ; semokList != null && i < semokList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)semokList.get(i);
    %>
    var newObject = new SemokInfo();
    newObject.semokCd = "<%=rowData.getString("M370_SEMOKCODE")%>";
    newObject.semokNm = "<%=rowData.getString("M370_SEMOKNAME")%>";
    //배열에 정보 Object 저장
    semokInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* 부모 select 객체에 세목 정보 생성 */
        var parentSelect = null;
        try {
					<% if ("1".equals(rowCount) && "0".equals(array_val)) { %>
						parentSelect = parent.document.sForm.semok_code;
					<% } else if ("0".equals(array_val) && !"1".equals(rowCount)) { %>
            parentSelect = parent.document.sForm.semok_code[0];
					<% } else { %>
						parentSelect = parent.document.sForm.semok_code[<%=array_row%>];
					<% } %>
        } catch(e) {
            //parentSelect = parent.document.uForm.semok_code;
        }
        if( parentSelect != null ) {
          deleteOptions(parentSelect);
          for( var i=0 ; i < semokInfoList.length ; i++ ) {
            var object = semokInfoList[i];
            addOption(parentSelect, object.value(), object.text());
          }
        }
    }        
</script>