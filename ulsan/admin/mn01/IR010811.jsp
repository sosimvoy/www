<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR010811.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2010-07-16
* 프로그램내용   : 회계명에 따른 세목코드
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // 세목코드 (시군구에 속한)
    List<CommonEntity> semokList  = (List<CommonEntity>)request.getAttribute("page.mn01.semokList");
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
        this.semokCode;   //세목코드
        this.semokName;   //회계명
        this.text = getTextValue; //text 출력형태
        this.value = getValue; // 회계코드
    }
    
    //text value 출력형태(행정동-법정동)
    function getTextValue() {
        return this.semokName;
    }

    function getValue() {
        return this.semokCode.toString();
    }
    
    //법정동행정동 정보 Object 를 담을 배열 정의
    var semokInfoList = new Array();

    <%
    for( int i=0 ; semokList != null && i < semokList.size() ; i++ ) {
        CommonEntity semokInfo = (CommonEntity)semokList.get(i);
    %>
    var newObject = new LawHangInfo();
    newObject.semokCode = "<%=semokInfo.getString("M370_SEMOKCODE")%>";
    newObject.semokName = "<%=semokInfo.getString("M370_SEMOKNAME")%>";
    
		//배열에 정보 Object 저장
    semokInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* 부모 select 객체에 법정동행정동 정보 생성 */
        var parentSelect = null;
        try {
            parentSelect = parent.document.sForm.semok_code;
        } catch(e) {
            parentSelect = parent.document.uForm.semok_code;
        }
        if( parentSelect != null ) {
            deleteOptions(parentSelect);
            addOption(parentSelect, "", "전체");
            for( var i=0 ; i < semokInfoList.length ; i++ ) {
                var object = semokInfoList[i];
                addOption(parentSelect, object.value(), object.text());
            }
        }
    }        
</script>
