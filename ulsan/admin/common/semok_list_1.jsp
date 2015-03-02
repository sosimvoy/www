<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR020111.jsp
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-06-16
* 프로그램내용   : 회계구분에 따른 부서코드와 회계명 
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // 부서코드 (시군구에 속한)

	 List<CommonEntity> accSemokList  = (List<CommonEntity>)request.getAttribute("page.mn01.accSemokList");
	 List<CommonEntity> accNmList  = (List<CommonEntity>)request.getAttribute("page.mn01.accNmList");
	 List<CommonEntity> accDeptList  = (List<CommonEntity>)request.getAttribute("page.mn01.accDeptList");	
	 
	 String array_row = (String)request.getParameter("array_row");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>
    // Object 정의
    function AccInfo() {
        this.accCd;   //회계코드
        this.accNm;   //회계명
        this.text = getAccTextValue; //text 출력형태
        this.value = getAccValue; //회계명
    }

		// Object 정의
    function DeptInfo() {
        this.deptCd;   //부서코드
        this.deptNm;   //부서명
        this.text = getDeptTextValue; //text 출력형태
        this.value = getDeptValue; //부서명
    }

		// Object 정의
		function SemokInfo() {
        this.semokCd;   //세목코드
        this.semokNm;   //세목명
        this.text = getSemokTextValue; //text 출력형태
        this.value = getSemokValue; //부서명
    }
    
    //text value 출력형태(회계코드-회계명)
    function getAccTextValue() {
        return this.accNm;
    }

		//text value 출력형태(부서코드-부서명)
    function getDeptTextValue() {
        return this.deptNm;
    }

		//text value 출력형태(세목코드-세목명)
    function getSemokTextValue() {
        return this.semokNm;
    }

    function getAccValue() {
        return this.accCd.toString();
    }

		function getDeptValue() {
        return this.deptCd.toString();
    }

		function getSemokValue() {
        return this.semokCd.toString();
    }
    
    //회계 정보 Object 를 담을 배열 정의
    var accInfoList = new Array();

    <%
    for( int i=0 ; accNmList != null && i < accNmList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accNmList.get(i);
    %>
    var newObject = new AccInfo();
    newObject.accCd = "<%=rowData.getString("M360_ACCCODE")%>";
    newObject.accNm = "<%=rowData.getString("M360_ACCNAME")%>";
    //배열에 정보 Object 저장
    accInfoList[<%=i%>] = newObject;
    <%
    }
    %>


		//부서 정보 Object 를 담을 배열 정의
    var deptInfoList = new Array();

    <%
    for( int i=0 ; accDeptList != null && i < accDeptList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accDeptList.get(i);
    %>
    var newObject = new DeptInfo();
    newObject.deptCd = "<%=rowData.getString("M350_PARTCODE")%>";
    newObject.deptNm = "<%=rowData.getString("M350_PARTNAME")%>";
    //배열에 정보 Object 저장
    deptInfoList[<%=i%>] = newObject;
    <%
    }
    %>


		//세목 정보 Object 를 담을 배열 정의
    var semokInfoList = new Array();

    <%
    for( int i=0 ; accSemokList != null && i < accSemokList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accSemokList.get(i);
    %>
    var newObject = new DeptInfo();
    newObject.deptCd = "<%=rowData.getString("M370_SEMOKCODE")%>";
    newObject.deptNm = "<%=rowData.getString("M370_SEMOKNAME")%>";
    //배열에 정보 Object 저장
    semokInfoList[<%=i%>] = newObject;
    <%
    }
    %>


    function window.onload() {
      /* 부모 select 객체에 세목 정보 생성 */
      var parentSelect1 = null;
			var parentSelect2 = null;
			var flag = "<%=request.getParameter("flag")%>";
      try {

					if (flag == "0") {

					parentSelect1 = parent.document.sForm.acc_code;
				
				  if( parentSelect1 != null ) {
            deleteOptions(parentSelect1);
            for( var i=0 ; i < accInfoList.length ; i++ ) {
              var object = accInfoList[i];
              addOption(parentSelect1, object.value(), object.text());
            }
          }
				}		
				

        parentSelectq = parent.document.sForm.semok_code;

					  <% if ("1".equals(array_row)) { %>
            parentSelect = parent.document.sForm.semok_code;
						if( parentSelect != null ) {
              deleteOptions(parentSelect);
              for( var i=0 ; i < semokInfoList.length ; i++ ) {
                var object = semokInfoList[i];
                addOption(parentSelect, object.value(), object.text());
              }
            }
						<% } else if (!"1".equals(array_row)) { 
							   for (int i=0; i<Integer.parseInt(array_row); i++) { 
						%>
            parentSelect = parent.document.sForm.semok_code[<%=i%>];
						if( parentSelect != null ) {
              deleteOptions(parentSelect);
              for( var i=0 ; i < semokInfoList.length ; i++ ) {
                var object = semokInfoList[i];
                addOption(parentSelect, object.value(), object.text());
              }
            }		 
						  <% } 
						   } %>
        } catch(e) {
            parentSelect = parent.document.uForm.semok_code;
        }
    }        
</script>