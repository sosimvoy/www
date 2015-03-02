<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR050911.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2010-06-16
* 프로그램내용   : 자금배정 > 잉여금이입요구등록 > 출금계좌/입금계좌설정
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // 부서코드 (시군구에 속한)
    List<CommonEntity> outAcctList = (List<CommonEntity>)request.getAttribute("page.mn05.outAcctList");
		List<CommonEntity> inAcctList = (List<CommonEntity>)request.getAttribute("page.mn05.inAcctList");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>
    // Object 정의
    function OutInfo() {
        this.outAcctCd;   //계좌번호
        this.outAcctNm;   //계좌명
        this.text = getOutTextValue; //text 출력형태
        this.value = getOutValue; //계좌명
    }

		// Object 정의
    function InInfo() {
        this.inAcctCd;   //계좌번호
        this.inAcctNm;   //계좌명
        this.text = getInTextValue; //text 출력형태
        this.value = getInValue; //계좌명
    }
    
    //text value 출력형태(계좌번호-계좌명)
    function getOutTextValue() {
        return this.outAcctNm;
    }

		//text value 출력형태(계좌번호-계좌명)
    function getInTextValue() {
        return this.inAcctNm;
    }

    function getOutValue() {
        return this.outAcctCd.toString();
    }

		function getInValue() {
        return this.inAcctCd.toString();
    }
    
    //출금계좌 정보 Object 를 담을 배열 정의
    var outInfoList = new Array();

    <%
    for( int i=0 ; outAcctList != null && i < outAcctList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)outAcctList.get(i);
    %>
    var newObject = new OutInfo();
    newObject.outAcctCd = "<%=rowData.getString("M300_ACCOUNTNO")%>";
    newObject.outAcctNm = "<%=rowData.getString("M300_ACCNAME")%>";
    //배열에 정보 Object 저장
    outInfoList[<%=i%>] = newObject;
    <%
    }
    %>


		//입금계좌 정보 Object 를 담을 배열 정의
    var inInfoList = new Array();

    <%
    for( int i=0 ; inAcctList != null && i < inAcctList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)inAcctList.get(i);
    %>
    var newObject = new InInfo();
    newObject.inAcctCd = "<%=rowData.getString("M300_ACCOUNTNO")%>";
    newObject.inAcctNm = "<%=rowData.getString("M300_ACCNAME")%>";
    //배열에 정보 Object 저장
    inInfoList[<%=i%>] = newObject;
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
						parentSelect1 = parent.document.sForm.out_acct;
					
					  if( parentSelect1 != null ) {
              deleteOptions(parentSelect1);
              for( var i=0 ; i < outInfoList.length ; i++ ) {
                var object = outInfoList[i];
                addOption(parentSelect1, object.value(), object.text());
              }
            }
					} else if (flag == "1") {
						parentSelect2 = parent.document.sForm.in_acct;

					  if( parentSelect2 != null ) {
              deleteOptions(parentSelect2);
              for( var i=0 ; i < inInfoList.length ; i++ ) {
                var object = inInfoList[i];
                addOption(parentSelect2, object.value(), object.text());
              }
            }
					}						
        } catch(e) {
            //parentSelect1 = parent.document.uForm.out_acct;
						//parentSelect2 = parent.document.uForm.in_acct;
        }
    }        
</script>