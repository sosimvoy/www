<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR050512.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2010-06-16
* 프로그램내용   : 자금배정 > 계좌등록 > 부서, 회계리스트
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // 부서코드 (시군구에 속한)
    List<CommonEntity> accList = (List<CommonEntity>)request.getAttribute("page.mn05.accList");
		List<CommonEntity> deptList = (List<CommonEntity>)request.getAttribute("page.mn05.deptList");
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
    
    //text value 출력형태(회계코드-회계명)
    function getAccTextValue() {
        return this.accNm;
    }

		//text value 출력형태(부서코드-부서명)
    function getDeptTextValue() {
        return this.deptNm;
    }

    function getAccValue() {
        return this.accCd.toString();
    }

		function getDeptValue() {
        return this.deptCd.toString();
    }
    
    //회계 정보 Object 를 담을 배열 정의
    var accInfoList = new Array();

    <%
    for( int i=0 ; accList != null && i < accList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accList.get(i);
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
    for( int i=0 ; deptList != null && i < deptList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)deptList.get(i);
    %>
    var newObject = new DeptInfo();
    newObject.deptCd = "<%=rowData.getString("M350_PARTCODE")%>";
    newObject.deptNm = "<%=rowData.getString("M350_PARTNAME")%>";
    //배열에 정보 Object 저장
    deptInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
      /* 부모 select 객체에 세목 정보 생성 */
      var parentSelect1 = null;
			var parentSelect2 = null;
			var flag = "<%=request.getParameter("flag")%>";
      try {
				parentSelect1 = parent.document.sForm.acc_cd;
				
				if( parentSelect1 != null ) {
          deleteOptions(parentSelect1);
          for( var i=0 ; i < accInfoList.length ; i++ ) {
            var object = accInfoList[i];
            addOption(parentSelect1, object.value(), object.text());
          }
        }
				if (flag == "1") {
					parentSelect2 = parent.document.sForm.dept_cd;

				  if( parentSelect2 != null ) {
            deleteOptions(parentSelect2);
            for( var i=0 ; i < deptInfoList.length ; i++ ) {
              var object = deptInfoList[i];
              addOption(parentSelect2, object.value(), object.text());
            }
          }
				}						
      } catch(e) {
				//
      }
    }        
</script>