<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR040111.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-06-16
* ���α׷�����   : ���ܼ��� > �Աݳ�����ȸ > ȸ�迬���� ���¸���Ʈ
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // �μ��ڵ� (�ñ����� ����)
    List<CommonEntity> acctList = (List<CommonEntity>)request.getAttribute("page.mn04.acctList");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>

		// Object ����
    function AcctInfo() {
        this.acctCd;   //���¹�ȣ
        this.acctNm;   //���¸�
        this.text = getAcctText; //text �������
        this.value = getAcctValue; //���¸�
    }

		//text value �������(���¹�ȣ-���¸�)
    function getAcctText() {
        return this.acctNm;
    }

		function getAcctValue() {
        return this.acctCd.toString();
    }

		//�Աݰ��� ���� Object �� ���� �迭 ����
    var acctInfoList = new Array();

    <%
    for( int i=0 ; acctList != null && i < acctList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)acctList.get(i);
    %>
    var newObject = new AcctInfo();
    newObject.acctCd = "<%=rowData.getString("M300_ACCOUNTNO")%>";
    newObject.acctNm = "<%=rowData.getString("M300_ACCNAME")%>";
    //�迭�� ���� Object ����
    acctInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
      /* �θ� select ��ü�� ���� ���� ���� */
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