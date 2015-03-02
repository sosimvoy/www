<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR050911.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-06-16
* ���α׷�����   : �ڱݹ��� > �׿������Կ䱸��� > ��ݰ���/�Աݰ��¼���
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // �μ��ڵ� (�ñ����� ����)
    List<CommonEntity> outAcctList = (List<CommonEntity>)request.getAttribute("page.mn05.outAcctList");
		List<CommonEntity> inAcctList = (List<CommonEntity>)request.getAttribute("page.mn05.inAcctList");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>
    // Object ����
    function OutInfo() {
        this.outAcctCd;   //���¹�ȣ
        this.outAcctNm;   //���¸�
        this.text = getOutTextValue; //text �������
        this.value = getOutValue; //���¸�
    }

		// Object ����
    function InInfo() {
        this.inAcctCd;   //���¹�ȣ
        this.inAcctNm;   //���¸�
        this.text = getInTextValue; //text �������
        this.value = getInValue; //���¸�
    }
    
    //text value �������(���¹�ȣ-���¸�)
    function getOutTextValue() {
        return this.outAcctNm;
    }

		//text value �������(���¹�ȣ-���¸�)
    function getInTextValue() {
        return this.inAcctNm;
    }

    function getOutValue() {
        return this.outAcctCd.toString();
    }

		function getInValue() {
        return this.inAcctCd.toString();
    }
    
    //��ݰ��� ���� Object �� ���� �迭 ����
    var outInfoList = new Array();

    <%
    for( int i=0 ; outAcctList != null && i < outAcctList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)outAcctList.get(i);
    %>
    var newObject = new OutInfo();
    newObject.outAcctCd = "<%=rowData.getString("M300_ACCOUNTNO")%>";
    newObject.outAcctNm = "<%=rowData.getString("M300_ACCNAME")%>";
    //�迭�� ���� Object ����
    outInfoList[<%=i%>] = newObject;
    <%
    }
    %>


		//�Աݰ��� ���� Object �� ���� �迭 ����
    var inInfoList = new Array();

    <%
    for( int i=0 ; inAcctList != null && i < inAcctList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)inAcctList.get(i);
    %>
    var newObject = new InInfo();
    newObject.inAcctCd = "<%=rowData.getString("M300_ACCOUNTNO")%>";
    newObject.inAcctNm = "<%=rowData.getString("M300_ACCNAME")%>";
    //�迭�� ���� Object ����
    inInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* �θ� select ��ü�� ���� ���� ���� */
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