<%
/***************************************************************
* 프로젝트명     : 울산광역시 자금배정관리시스템
* 프로그램명     : IR061511.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2010-06-16
* 프로그램내용   : 회계연도에 따른 회계 코드/명
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // 부서코드 (시군구에 속한)
    List accList = (List)request.getAttribute("page.mn06.accList");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" src="../js/utility.js"></script>
<script>
    //회계코드 정보 Object 정의
    function AccInfo() {
        this.accCode;   //회계코드
        this.accName;    //회계이름
        this.name = getName; //name 출력형태
        this.code = getCode; //회계명
    }
    
    //name code 출력형태(회계명)
    function getName() {
        return this.accName;
    }

    function getCode() {
        return this.accCode.toString();
    }
    
    //회계 정보 Object 를 담을 배열 정의
    var accArrayList = new Array();

    <%
    for( int i=0 ; accList != null && i < accList.size() ; i++ ) {
        CommonEntity acctInfo = (CommonEntity)accList.get(i);
    %>
    var newObject = new AccInfo();
    newObject.accCode = "<%=acctInfo.getString("M360_VALUE")%>";
    newObject.accName = "<%=acctInfo.getString("M360_ACCNAME")%>";
    //배열에 정보 Object 저장
    accArrayList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* 부모 select 객체에 회계 정보 생성 */
        var parentSelect = null;
        try {
            parentSelect = parent.document.sForm.acc_code;
        } catch(e) {
            parentSelect = parent.document.uForm.acc_code;
        }
        if( parentSelect != null ) {
            deleteOptions(parentSelect);
						addOption(parentSelect, "", "전체선택");
            for( var i=0 ; i < accArrayList.length ; i++ ) {
                var object = accArrayList[i];
                addOption(parentSelect, object.code(), object.name());
            }
        }
    }        
</script>
