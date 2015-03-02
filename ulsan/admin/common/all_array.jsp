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
    List<CommonEntity> deptList = (List<CommonEntity>)request.getAttribute("page.mn01.specDeptList");
		List<CommonEntity> accList = (List<CommonEntity>)request.getAttribute("page.mn01.specNameList");
		List<CommonEntity> semokList = (List<CommonEntity>)request.getAttribute("page.mn01.specSemokList");
		String array_val = (String)request.getParameter("array_val");
		String array_row = (String)request.getParameter("array_row");
		String rowCount  = (String)request.getParameter("rowCount");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>
    
    function deptInfo() {
        this.deptCd;   //부서코드
        this.deptNm;   //부서명
        this.text  = getDeptTextValue; //text 출력형태
        this.value = getDeptValue; //부서명
    }

		function accInfo() {
        this.accCd;   //회계코드
        this.accNm;   //회계명
        this.text  = getAccTextValue; //text 출력형태
        this.value = getAccValue; //회계명
    }

		function semokInfo() {
        this.semokCd;   //세목코드
        this.semokNm;   //세목명
        this.text  = getSemokTextValue; //text 출력형태
        this.value = getSemokValue; //세목명
    }

		//text value 출력형태(부서코드-부서명)
    function getDeptTextValue() {
        return this.deptNm;
    }

    function getDeptValue() {
        return this.deptCd.toString();
    }

		//text value 출력형태(회계코드-회계명)
    function getAccTextValue() {
        return this.accNm;
    }

    function getAccValue() {
        return this.accCd.toString();
    }

		//text value 출력형태(세목코드-세목명)
    function getSemokTextValue() {
        return this.semokNm;
    }

    function getSemokValue() {
        return this.semokCd.toString();
    }
    
    //부서 정보 Object 를 담을 배열 정의
    var deptInfoList = new Array();

    <%
    for( int i=0 ; deptList != null && i < deptList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)deptList.get(i);
    %>
    var newObject = new deptInfo();
    newObject.deptCd = "<%=rowData.getString("M350_PARTCODE")%>";
    newObject.deptNm = "<%=rowData.getString("M350_PARTNAME")%>";
    //배열에 정보 Object 저장
    deptInfoList[<%=i%>] = newObject;
    <%
    }
    %>


		//회계 정보 Object 를 담을 배열 정의
    var accInfoList = new Array();

    <%
    for( int i=0 ; accList != null && i < accList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accList.get(i);
    %>
    var newObject = new accInfo();
    newObject.accCd = "<%=rowData.getString("M360_ACCCODE")%>";
    newObject.accNm = "<%=rowData.getString("M360_ACCNAME")%>";
    //배열에 정보 Object 저장
    accInfoList[<%=i%>] = newObject;
    <%
    }
    %>


		//세목 정보 Object 를 담을 배열 정의
    var semokInfoList = new Array();

    <%
    for( int i=0 ; semokList != null && i < semokList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)semokList.get(i);
    %>
    var newObject = new semokInfo();
    newObject.semokCd = "<%=rowData.getString("M370_SEMOKCODE")%>";
    newObject.semokNm = "<%=rowData.getString("M370_SEMOKNAME")%>";
    //배열에 정보 Object 저장
    semokInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* 부모 select 객체에 세목 정보 생성 */
        var parentSelect1 = null;
				var parentSelect2 = null;
				var parentSelect3 = null;
				var bonus = "<%=request.getParameter("bonus")%>";
        try {
					<% if ("1".equals(rowCount) && "0".equals(array_val)) { %>
						if (bonus == "0")	{
						  parentSelect1 = parent.document.sForm.part_code;
							parentSelect2 = parent.document.sForm.acc_code;
						}
						if (bonus == "1")	{
					    parentSelect2 = parent.document.sForm.acc_code;
						}
						parentSelect3 = parent.document.sForm.semok_code;
					<% } else if ("0".equals(array_val) && !"1".equals(rowCount)) { %>
						if (bonus == "0")	{
						  parentSelect1 = parent.document.sForm.part_code[0];
							parentSelect2 = parent.document.sForm.acc_code[0];
						}
						if (bonus == "1")	{
						  parentSelect2 = parent.document.sForm.acc_code[0];
						}
						parentSelect3 = parent.document.sForm.semok_code[0];
					<% } else { %>
						if (bonus == "0")	{
						  parentSelect1 = parent.document.sForm.part_code[<%=array_row%>];
							parentSelect2 = parent.document.sForm.acc_code[<%=array_row%>];
						}
						if (bonus == "1")	{
						  parentSelect2 = parent.document.sForm.acc_code[<%=array_row%>];
						}
						parentSelect3 = parent.document.sForm.semok_code[<%=array_row%>];
					<% } %>
        } catch(e) {

        }
        if( parentSelect1 != null ) {
          deleteOptions(parentSelect1);
          for( var i=0 ; i < deptInfoList.length ; i++ ) {
            var object = deptInfoList[i];
            addOption(parentSelect1, object.value(), object.text());
          }
        }

				if( parentSelect2 != null ) {
          deleteOptions(parentSelect2);
          for( var i=0 ; i < accInfoList.length ; i++ ) {
            var object = accInfoList[i];
            addOption(parentSelect2, object.value(), object.text());
          }
        }

				if( parentSelect3 != null ) {
          deleteOptions(parentSelect3);
          for( var i=0 ; i < semokInfoList.length ; i++ ) {
            var object = semokInfoList[i];
            addOption(parentSelect3, object.value(), object.text());
          }
        }
    }        
</script>