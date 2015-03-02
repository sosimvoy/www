<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR040111.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2010-06-16
* 프로그램내용   : 세외수입 > 입금내역조회 > 회계연도별 계좌리스트
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // 부서코드 (시군구에 속한)
    List<CommonEntity> acctList = (List<CommonEntity>)request.getAttribute("page.mn04.acctList");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>

		// Object 정의
    function AcctInfo() {
        this.acctCd;   //계좌번호
        this.acctNm;   //계좌명
        this.text = getAcctText; //text 출력형태
        this.value = getAcctValue; //계좌명
    }

		//text value 출력형태(계좌번호-계좌명)
    function getAcctText() {
        return this.acctNm;
    }

		function getAcctValue() {
        return this.acctCd.toString();
    }

		//입금계좌 정보 Object 를 담을 배열 정의
    var acctInfoList = new Array();

    <%
    for( int i=0 ; acctList != null && i < acctList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)acctList.get(i);
    %>
    var newObject = new AcctInfo();
    newObject.acctCd = "<%=rowData.getString("M300_ACCOUNTNO")%>";
    newObject.acctNm = "<%=rowData.getString("M300_ACCNAME")%>";
    //배열에 정보 Object 저장
    acctInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
      /* 부모 select 객체에 세목 정보 생성 */
      var parentSelect = null;

      try {
				parentSelect = parent.document.sForm.acct_no;

				if( parentSelect != null ) {
          deleteOptions(parentSelect);
          for( var i=0 ; i < acctInfoList.length ; i++ ) {
            var object = acctInfoList[i];
            addOption(parentSelect, object.value(), object.text());
          }
        }
									
      } catch(e) {
          //parentSelect1 = parent.document.uForm.out_acct;
					//parentSelect2 = parent.document.uForm.in_acct;
      }
    }        
</script>