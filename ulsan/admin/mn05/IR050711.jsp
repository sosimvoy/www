<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR050711.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-06-16
* ���α׷�����   : �ڱݹ��� > �ڱݹ�������е�� > ����Ʈ�ڽ��� ����
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // �μ��ڵ� (�ñ����� ����)
    List<CommonEntity> semokList = (List<CommonEntity>)request.getAttribute("page.mn05.deptList");
		List<CommonEntity> acctList = (List<CommonEntity>)request.getAttribute("page.mn05.acctList");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>
    // Object ����
    function SemokInfo() {
        this.semokCd;   //�����ڵ�
        this.semokNm;   //�����
        this.text = getTextValue; //text �������
        this.value = getValue; //�����
    }

		// Object ����
    function AcctInfo() {
        this.acctCd;   //ȸ���ڵ�
        this.acctNm;   //ȸ���
        this.text = getAcctTextValue; //text �������
        this.value = getAcctValue; //ȸ���
    }
    
    //text value �������(�����ڵ�-�����)
    function getTextValue() {
        return this.semokNm;
    }

		//text value �������(ȸ���ڵ�-ȸ���)
    function getAcctTextValue() {
        return this.acctNm;
    }

    function getValue() {
        return this.semokCd.toString();
    }

		function getAcctValue() {
        return this.acctCd.toString();
    }
    
    //���� ���� Object �� ���� �迭 ����
    var semokInfoList = new Array();

    <%
    for( int i=0 ; semokList != null && i < semokList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)semokList.get(i);
    %>
    var newObject = new SemokInfo();
    newObject.semokCd = "<%=rowData.getString("M350_PARTCODE")%>";
    newObject.semokNm = "<%=rowData.getString("M350_PARTNAME")%>";
    //�迭�� ���� Object ����
    semokInfoList[<%=i%>] = newObject;
    <%
    }
    %>


		//ȸ�� ���� Object �� ���� �迭 ����
    var acctInfoList = new Array();

    <%
    for( int i=0 ; acctList != null && i < acctList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)acctList.get(i);
    %>
    var newObject = new AcctInfo();
    newObject.acctCd = "<%=rowData.getString("M360_ACCCODE")%>";
    newObject.acctNm = "<%=rowData.getString("M360_ACCNAME")%>";
    //�迭�� ���� Object ����
    acctInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* �θ� select ��ü�� ���� ���� ���� */
        var parentSelect1 = null;
				var parentSelect2 = null;
        var parentSelect3 = null;
        var parentSelect4 = null;
				var flag = "<%=request.getParameter("bonus")%>";
        try {
					  if (flag != "1") {
							parentSelect1 = parent.document.sForm.dept_kind;
						
						  if( parentSelect1 != null ) {
                deleteOptions(parentSelect1);
                for( var i=0 ; i < semokInfoList.length ; i++ ) {
                  var object = semokInfoList[i];
                  addOption(parentSelect1, object.value(), object.text());
                }
              }


              parentSelect3 = parent.document.sForm.t_dept_kind;
						
						  if( parentSelect3 != null ) {
                deleteOptions(parentSelect3);
                <%if("A".equals(request.getParameter("acc_type"))) { %>
                  addOption(parentSelect3, "00000", "��û");
                <% } else { %>
                for( var i=0 ; i < semokInfoList.length ; i++ ) {
                  var object = semokInfoList[i];
                  addOption(parentSelect3, object.value(), object.text());
                }
                <% } %>
              }


              parentSelect4 = parent.document.sForm.t_acc_kind;

						  if( parentSelect4 != null ) {
                deleteOptions(parentSelect4);
                <%if("A".equals(request.getParameter("acc_type"))) { %>
                  addOption(parentSelect4, "11", "�Ϲ�ȸ��");
                <% } else { %>
                for( var i=0 ; i < acctInfoList.length ; i++ ) {
                  var object = acctInfoList[i];
                  addOption(parentSelect4, object.value(), object.text());
                }
                <% } %>
              }
					  }
            

						parentSelect2 = parent.document.sForm.acc_kind;

						if( parentSelect2 != null ) {
              deleteOptions(parentSelect2);
              for( var i=0 ; i < acctInfoList.length ; i++ ) {
                var object = acctInfoList[i];
                addOption(parentSelect2, object.value(), object.text());
              }
            }
        } catch(e) {
            //parentSelect1 = parent.document.uForm.dept_kind;
						//parentSelect2 = parent.document.uForm.acc_kind;
        }
    }        
</script>