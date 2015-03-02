<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR070101.jsp
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-29
* 프로그램내용   : 회계구분에 따른 부서코드와 회계명 
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // 부서코드 (시군구에 속한)
    List<CommonEntity> partList         = (List<CommonEntity>)request.getAttribute("page.mn07.partList");	 
    List<CommonEntity> accList          = (List<CommonEntity>)request.getAttribute("page.mn07.accList");
    List<CommonEntity> reportCodeList   = (List<CommonEntity>)request.getAttribute("page.mn07.reportCodeList");

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
        this.partCd;   //부서코드
        this.partNm;   //부서명
        this.text = getDeptTextValue; //text 출력형태
        this.value = getDeptValue; //부서명
    }

    // Object 정의
    function ReportInfo() {
        this.reportCd;   //보고서코드
        this.reportNm;   //보고서명
        this.text = getReportTextValue; //text 출력형태
        this.value = getReportValue; //보고서명
    }

    
    //text value 출력형태(회계코드-회계명)
    function getAccTextValue() {
        return this.accNm;
    }

	//text value 출력형태(부서코드-부서명)
    function getDeptTextValue() {
        return this.partNm;
    }

    //text value 출력형태(보고서코드-보고서명)
    function getReportTextValue() {
        return this.reportNm;
    }


    function getAccValue() {
        return this.accCd.toString();
    }

	function getDeptValue() {
        return this.partCd.toString();
    }

    function getReportValue() {
        return this.reportCd.toString();
    }
    
    //회계 정보 Object 를 담을 배열 정의
    var accInfoList = new Array();

    <%
    for( int i=0 ; accList != null && i < accList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)accList.get(i);
    %>
        var newObject = new AccInfo();
        newObject.accCd = "<%=rowData.getString("ACCCODE")%>";
        newObject.accNm = "<%=rowData.getString("ACCNAME")%>";
        //배열에 정보 Object 저장
        accInfoList[<%=i%>] = newObject;
    <%}%> 

	//부서 정보 Object 를 담을 배열 정의
    var partInfoList = new Array();

    <%
    for( int i=0 ; partList != null && i < partList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)partList.get(i);
    %>
    var newObject = new DeptInfo();
    newObject.partCd = "<%=rowData.getString("PARTCODE")%>";
    newObject.partNm = "<%=rowData.getString("PARTNAME")%>";
    //배열에 정보 Object 저장
    partInfoList[<%=i%>] = newObject;
    <%}%> 


    
	//보고서 정보 Object 를 담을 배열 정의
    var reportInfoList = new Array();

    <%
    for( int i=0 ; reportCodeList != null && i < reportCodeList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)reportCodeList.get(i);
    %>
    var newObject = new ReportInfo();
    newObject.reportCd = "<%=rowData.getString("REPORTCODE")%>"+"|"+"<%=rowData.getString("REPORTURL")%>";
    newObject.reportNm = "<%=rowData.getString("REPORTNAME")%>";
    //배열에 정보 Object 저장
    reportInfoList[<%=i%>] = newObject;
    <%}%> 

    function window.onload() {
        /* 부모 select 객체에 세목 정보 생성 */
        var parentSelect1 = null;
        var parentSelect2 = null;
        var parentSelect3 = null;
        var flag = "<%=request.getParameter("flag")%>";
        try {
            if (flag == "0") {

			    parentSelect2 = parent.document.sForm.part_code;

			    if( parentSelect2 != null ) {
                    deleteOptions(parentSelect2);
                    for( var i=0 ; i < partInfoList.length ; i++ ) {
                        var object = partInfoList[i];
                        addOption(parentSelect2, object.value(), object.text());
                    }
                }

				parentSelect1 = parent.document.sForm.acc_code;
				
				if( parentSelect1 != null ) {
                    deleteOptions(parentSelect1);
                    for( var i=0 ; i < accInfoList.length ; i++ ) {
                        var object = accInfoList[i];
                        addOption(parentSelect1, object.value(), object.text());
                    }
                }
			}		
				
			if (flag == "1") {

			    parentSelect1 = parent.document.sForm.acc_code;
				
				if( parentSelect1 != null ) {
                    deleteOptions(parentSelect1);
                    for( var i=0 ; i < accInfoList.length ; i++ ) {
                        var object = accInfoList[i];
                        addOption(parentSelect1, object.value(), object.text());
                    }
                }
			}
            
            // 보고서만 선택 시
            if (flag == "2") {

			    parentSelect3 = parent.document.sForm.report_code;
				
				if( parentSelect3 != null ) {
                    deleteOptions(parentSelect3);
                    for( var i=0 ; i < reportInfoList.length ; i++ ) {
                        var object = reportInfoList[i];
                        addOption(parentSelect3, object.value(), object.text());
                    }
                }
			}
						
        } catch(e) {
        		//
        }
    }        
</script>