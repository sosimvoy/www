<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR030111.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2010-07-16
* 프로그램내용   : 부서코드에 따른 회계명
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // 부서코드 (시군구에 속한)
    List<CommonEntity> accNmList  = (List<CommonEntity>)request.getAttribute("page.mn01.accNmList");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" src="../js/utility.js"></script>
<script>
    //법정동행정동 정보 Object 정의
    function LawHangInfo() {
        this.acctCode;   //회계코드
        this.acctName;   //회계명
        this.text = getTextValue; //text 출력형태
        this.value = getValue; // 회계코드
    }
    
    //text value 출력형태(행정동-법정동)
    function getTextValue() {
        return this.acctName;
    }

    function getValue() {
        return this.acctCode.toString();
    }
    
    //법정동행정동 정보 Object 를 담을 배열 정의
    var acctCdInfoList = new Array();

    <%
    for( int i=0 ; accNmList != null && i < accNmList.size() ; i++ ) {
        CommonEntity acctCdInfo = (CommonEntity)accNmList.get(i);
    %>
    var newObject = new LawHangInfo();
    newObject.acctCode = "<%=acctCdInfo.getString("M360_ACCCODE")%>";
    newObject.acctName = "<%=acctCdInfo.getString("M360_ACCNAME")%>";
    
		//배열에 정보 Object 저장
    acctCdInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* 부모 select 객체에 법정동행정동 정보 생성 */
        var parentSelect = null;
        try {
            parentSelect = parent.document.sForm.acc_code;
        } catch(e) {
            parentSelect = parent.document.uForm.acc_code;
        }
        if( parentSelect != null ) {
            deleteOptions(parentSelect);
            for( var i=0 ; i < acctCdInfoList.length ; i++ ) {
                var object = acctCdInfoList[i];
                addOption(parentSelect, object.value(), object.text());
            }
        }
    }        
</script>
