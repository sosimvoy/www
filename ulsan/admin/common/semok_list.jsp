<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : semok_list.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-06-16
* ���α׷�����   : ȸ�豸�п� ���� ���񸮽�Ʈ ����
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%@ page import="com.etax.entity.CommonEntity" %>
<%
    // �μ��ڵ� (�ñ����� ����)
    List<CommonEntity> semokList = (List<CommonEntity>)request.getAttribute("page.mn01.accSemokList");

		String array_row = (String)request.getParameter("array_row");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>
    //������������ ���� Object ����
    function SemokInfo() {
        this.semokCd;   //�����ڵ�
        this.semokNm;   //�����
        this.text = getTextValue; //text �������
        this.value = getValue; //�����
    }
    
    //text value �������(�����ڵ�-�����)
    function getTextValue() {
        return this.semokNm;
    }

    function getValue() {
        return this.semokCd.toString();
    }
    
    //���� ���� Object �� ���� �迭 ����
    var semokInfoList = new Array();

    <%
    for( int i=0 ; semokList != null && i < semokList.size() ; i++ ) {
        CommonEntity rowData = (CommonEntity)semokList.get(i);
    %>
    var newObject = new SemokInfo();
    newObject.semokCd = "<%=rowData.getString("M370_SEMOKCODE")%>";
    newObject.semokNm = "<%=rowData.getString("M370_SEMOKNAME")%>";
    //�迭�� ���� Object ����
    semokInfoList[<%=i%>] = newObject;
    <%
    }
    %>

    function window.onload() {
        /* �θ� select ��ü�� ���� ���� ���� */
        var parentSelect = null;
        try {
					  <% if ("1".equals(array_row)) { %>
            parentSelect = parent.document.sForm.semok_code;
						if( parentSelect != null ) {
              deleteOptions(parentSelect);
              for( var i=0 ; i < semokInfoList.length ; i++ ) {
                var object = semokInfoList[i];
                addOption(parentSelect, object.value(), object.text());
              }
            }
						<% } else if (!"1".equals(array_row)) { 
							   for (int i=0; i<Integer.parseInt(array_row); i++) { 
						%>
            parentSelect = parent.document.sForm.semok_code[<%=i%>];
						if( parentSelect != null ) {
              deleteOptions(parentSelect);
              for( var i=0 ; i < semokInfoList.length ; i++ ) {
                var object = semokInfoList[i];
                addOption(parentSelect, object.value(), object.text());
              }
            }		 
						  <% } 
						   } %>
        } catch(e) {
            parentSelect = parent.document.uForm.semok_code;
        }
    }        
</script>