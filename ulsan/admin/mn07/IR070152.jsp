<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070152.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-17
* ���α׷�����		: �ϰ�/���� > ������ȸ > ���Լ����ϰ�ǥ > PDF ���� ����
****************************************************************/
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"                         %>
<%@ page import = "com.etax.entity.*"                   %>
<%@ page import = "com.etax.util.*"                     %>

<%@ page import = "java.awt.Color"	                    %>
<%@ page import = "java.io.FileOutputStream"            %>
<%@ page import = "com.lowagie.text.Chapter"            %>
<%@ page import = "com.lowagie.text.Document"           %>
<%@ page import = "com.lowagie.text.DocumentException"	%>
<%@ page import = "com.lowagie.text.Font"	            %>
<%@ page import = "com.lowagie.text.FontFactory"	    %>
<%@ page import = "com.lowagie.text.PageSize"           %>
<%@ page import = "com.lowagie.text.Paragraph"          %>
<%@ page import = "com.lowagie.text.Section"            %>
<%@ page import = "com.lowagie.text.Element"            %>
<%@ page import = "com.lowagie.text.Phrase"             %>

<%@ page import = "com.lowagie.text.Cell"               %>
<%@ page import = "com.lowagie.text.Table"              %>
<%@ page import = "com.lowagie.text.Image"              %>

<%@ page import = "com.lowagie.text.pdf.PdfPTable"      %>
<%@ page import = "com.lowagie.text.pdf.BaseFont"       %>
<%@ page import = "com.lowagie.text.pdf.PdfWriter"      %>
<%@ page import = "com.lowagie.text.pdf.PdfPCell"       %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax"  %>
<%

List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");
List<CommonEntity> reportList2 = (List<CommonEntity>)request.getAttribute("page.mn07.reportList2");
CommonEntity prtState = (CommonEntity)request.getAttribute("page.mn07.prtState");

int listSize = reportList.size();
int listSize2 = reportList2.size();
//out.println("::::iTextStart::::"+listSize);

//String fileName = "���Լ����ϰ�ǥ_" +new java.sql.Date(System.currentTimeMillis()) + ".pdf";
String fileName = "���Լ����ϰ�ǥ.pdf";
String gubun    = "";
String acc_name = "";
String acc_year = request.getParameter("acc_year");
String acc_date = request.getParameter("acc_date");

//Document document = new Document();
Document document = new Document(PageSize.A4.rotate(), 0, 0, 30, 30); // A4 ������ ����

try {

	String filePath = request.getRealPath("/");
    // PDF ���� ������� ����
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath+"upload/report/" + fileName));

    // �ѱ۱�������
    BaseFont objBaseFont = BaseFont.createFont("HYGoThic-Medium", "UniKS-UCS2-H", false);
    Font objFont = new Font(objBaseFont, 5);
    Font objFont1 = new Font(objBaseFont, 14);
		
    BaseFont objBaseFont2 = BaseFont.createFont("HYGoThic-Medium", "UniKS-UCS2-H", false);
    Font objFont2 = new Font(objBaseFont2, 5);

    /* �ٰ��� ���� �뵵 */
    Font font6 = new Font(objBaseFont2, 100, Font.NORMAL);
    Phrase blankLineFont6 = new Phrase(" ", font6);

    document.open();
   //writer.addJavaScript("this.print(false);", false);    // auto print 
    
    /* TABLE START */
    Table t = new Table(17);    // �� ���� 
	  t.setBorderWidth(0);
    
   // if(prtState.getString("PRT_STATE").equals("Y")){ //�׿������Ծ׿� �ڷᰡ �����ϸ� 
      // �÷� ������
    float[] widths = { 0.7f, 1.4f, 1.3f , 1.0f, 1.0f , 1.0f, 1.3f, 1.3f, 1.3f , 1.1f, 1.0f , 0.9f, 1.3f, 1.2f , 1.2f, 1.1f, 0.9f }; 
    t.setWidths(widths);
    
    //Cell c1 = new Cell(); 
    Cell c1;

    //Image img = Image.getInstance("/admin/img/btn_print.gif");

    //img.scaleToFit(300,300);


    // Title
    // Title 1
    c1 = new Cell(new Paragraph(acc_year+"�� ���Լ��� �ϰ�ǥ", objFont1));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBorderWidth(0);
	c1.setColspan(17);
    t.addCell(c1);
    
    // Title 2
    c1 = new Cell(new Paragraph("������ : "+acc_date, objFont));
	c1.setHorizontalAlignment(Element.ALIGN_LEFT);
    c1.setBorderWidth(0);
	c1.setColspan(3);
    t.addCell(c1);

    c1 = new Cell(new Paragraph("(����:��)", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
    c1.setBorderWidth(0);
	c1.setColspan(14);
    t.addCell(c1);
       
    // Header 
    // Title3 
    c1 = new Cell(new Paragraph("ȸ���", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
	c1.setColspan(2);
    c1.setRowspan(2); 
    t.addCell(c1);

    c1 = new Cell(new Paragraph("����", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
	c1.setColspan(5);
    t.addCell(c1);  

    c1 = new Cell(new Paragraph("�ڱݹ�����", objFont));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    c1.setRowspan(2); 
    t.addCell(c1);  

    c1 = new Cell(new Paragraph("����", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
	c1.setColspan(5);
    t.addCell(c1);  

    c1 = new Cell(new Paragraph("�ܾ�", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    c1.setRowspan(2); 
    t.addCell(c1);  

    c1 = new Cell(new Paragraph("�ڱݿ��", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
	c1.setColspan(2);
    t.addCell(c1);  

    c1 = new Cell(new Paragraph("�׿������Ծ�", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
	c1.setRowspan(2);
    t.addCell(c1);  
/*
    c1 = new Cell(new Paragraph(" ", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
	c1.setRowspan(2);
    t.addCell(c1);  
*/
    // Title 4
    c1 = new Cell(new Paragraph("���ϴ���", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  
    
    c1 = new Cell(new Paragraph("���ϼ���", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  
    
    c1 = new Cell(new Paragraph("�������ݳ�", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  
    
    c1 = new Cell(new Paragraph("���������׵�", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);  
    
    c1 = new Cell(new Paragraph("�Ѱ�", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);   
    
    c1 = new Cell(new Paragraph("���ϴ���", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1); 
    
    c1 = new Cell(new Paragraph("��������", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);
    
    c1 = new Cell(new Paragraph("�ݳ���", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);
    
    c1 = new Cell(new Paragraph("���������׵�", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);
    
    c1 = new Cell(new Paragraph("�Ѱ�", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);
    
    c1 = new Cell(new Paragraph("���⿹�ݵ�", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);
    
    c1 = new Cell(new Paragraph("�����ܾ�", objFont));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t.addCell(c1);

    //t.setHeaderRows(4); // Ÿ��Ʋ ����
    
    // Detail 
   for (int i=0; i < reportList.size()-1; i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);

        if(!rowData.getString("ACCTYPENM").equals("")){
            gubun = rowData.getString("ACCTYPENM");
        }

        if(rowData.getString("SUMGUBUN").equals("A1") && rowData.getString("ACCCODE").equals("")){
            acc_name = "��û(�հ�)";
        }else if(rowData.getString("SUMGUBUN").equals("B3") && rowData.getString("ACCCODE").equals("")){
            acc_name = "������(�հ�)";
        }else if(!rowData.getString("ACCTYPE").equals("") && rowData.getString("SUMGUBUN").equals("")){
            acc_name = gubun + " �հ�";
        }else{
            acc_name = rowData.getString("ACCNAME");
        }

        c1 = new Cell(new Paragraph(gubun, objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(TextHandler.replace(acc_name, "�����繫��", ""), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		t.addCell(c1);

        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIP")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIPTOT")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTBAEJUNG")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);

        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHULJEONILTOT")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHUL")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHULBANNAP")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHULJEONGJEONG")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHULTOT")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        
        

        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTJAN")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTJEONGGI")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getLong("AMTJAN") - rowData.getLong("AMTJEONGGI")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSURPLUS")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        /*
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("LAST_AMT")), objFont2));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
*/
    }
    /* TABLE END */
    

    document.add(t);
    document.add(blankLineFont6);
    document.add(blankLineFont6);

// list2 
    /* TABLE START */
    Table t2 = new Table(17);    // �� ���� 
	t2.setBorderWidth(0);
    
    // �÷� ������
    float[] widths2 = { 0.5f, 1f, 1f , 1f, 1f , 1f, 1f, 1f, 1f , 1f, 1f , 1f, 1f, 1f , 1f, 1f, 1f };    
    t2.setWidths(widths);
    
    //Cell c1 = new Cell(); 
    Cell c2;

    //Image img = Image.getInstance("/admin/img/btn_print.gif");

    //img.scaleToFit(300,300);


    // Title
    // Title 1
    c2 = new Cell(new Paragraph(acc_year+"�� ���Լ��� �ϰ�ǥ", objFont1));
	c2.setHorizontalAlignment(Element.ALIGN_CENTER);
    c2.setBorderWidth(0);
	c2.setColspan(17);
    t2.addCell(c2);
    
    // Title 2
    c2 = new Cell(new Paragraph("������ : "+acc_date, objFont));
	c2.setHorizontalAlignment(Element.ALIGN_LEFT);
    c2.setBorderWidth(0);
	c2.setColspan(3);
    t2.addCell(c2);

    c2 = new Cell(new Paragraph("(����:��)", objFont));
	c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
    c2.setBorderWidth(0);
	c2.setColspan(14);
    t2.addCell(c2);
       
    // Header 
    // Title3 
    c2 = new Cell(new Paragraph("ȸ���", objFont));
	c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
	c2.setColspan(2);
    c2.setRowspan(2); 
    t2.addCell(c2);

    c2 = new Cell(new Paragraph("����", objFont));
	c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
	c2.setColspan(5);
    t2.addCell(c2);  

    c2 = new Cell(new Paragraph("�ڱݹ�����", objFont));
	c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    c2.setRowspan(2); 
    t2.addCell(c2);  

    c2 = new Cell(new Paragraph("����", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
	c2.setColspan(5);
    t2.addCell(c2);  

    c2 = new Cell(new Paragraph("�ܾ�", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    c2.setRowspan(2); 
    t2.addCell(c2);  

    c2 = new Cell(new Paragraph("�ڱݿ��", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
	c2.setColspan(2);
    t2.addCell(c2);  

    c2 = new Cell(new Paragraph("�׿������Ծ�", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
	c2.setRowspan(2);
    t2.addCell(c2);  
/*
    c2 = new Cell(new Paragraph(" ", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
	c2.setRowspan(2);
    t2.addCell(c2);  
*/
    // Title 4
    c2 = new Cell(new Paragraph("���ϴ���", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t2.addCell(c2);  
    
    c2 = new Cell(new Paragraph("���ϼ���", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t2.addCell(c2);  
    
    c2 = new Cell(new Paragraph("�������ݳ�", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t2.addCell(c2);  
    
    c2 = new Cell(new Paragraph("���������׵�", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t2.addCell(c2);  
    
    c2 = new Cell(new Paragraph("�Ѱ�", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t2.addCell(c2);   
    
    c2 = new Cell(new Paragraph("���ϴ���", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t2.addCell(c2); 
    
    c2 = new Cell(new Paragraph("��������", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t2.addCell(c2);
    
    c2 = new Cell(new Paragraph("�ݳ���", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t2.addCell(c2);
    
    c2 = new Cell(new Paragraph("���������׵�", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t2.addCell(c2);
    
    c2 = new Cell(new Paragraph("�Ѱ�", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t2.addCell(c2);
    
    c2 = new Cell(new Paragraph("���⿹�ݵ�", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t2.addCell(c2);
    
    c2 = new Cell(new Paragraph("�����ܾ�", objFont));
    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
	c2.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
    t2.addCell(c2);

    //t2.setHeaderRows(4); // Ÿ��Ʋ ����
    
    // Detail 
   for (int i=0; i < reportList2.size()-3; i++) {
        CommonEntity rowData = (CommonEntity)reportList2.get(i);

        if(!rowData.getString("ACCTYPENM").equals("")){
            gubun = rowData.getString("ACCTYPENM");
        }

        if(rowData.getString("SUMGUBUN").equals("A1") && rowData.getString("ACCCODE").equals("")){
            acc_name = "��û(�հ�)";
        }else if(rowData.getString("SUMGUBUN").equals("B3") && rowData.getString("ACCCODE").equals("")){
            acc_name = "(������)";
				}else if(rowData.getString("SUMGUBUN").equals("E9") && rowData.getString("ACCCODE").equals("")){
            acc_name = "(��ȸ�������)";
        }else if(!rowData.getString("ACCTYPE").equals("") && rowData.getString("SUMGUBUN").equals("")){
            acc_name = gubun + " �հ�";
        }else{
            acc_name = rowData.getString("ACCNAME");
        }		

        c2 = new Cell(new Paragraph(gubun, objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_CENTER);
		t2.addCell(c2);
        c2 = new Cell(new Paragraph(TextHandler.replace(TextHandler.replace(acc_name, "�������", ""), "������", "����"), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_LEFT);
		t2.addCell(c2);

        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIP")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIPTOT")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTBAEJUNG")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);

        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHULJEONILTOT")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHUL")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHULBANNAP")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHULJEONGJEONG")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHULTOT")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        
        

        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTJAN")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTJEONGGI")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getLong("AMTJAN") - rowData.getLong("AMTJEONGGI")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
        
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSURPLUS")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
    /*
        c2 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("LAST_AMT")), objFont2));
		c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t2.addCell(c2);
*/
    }
    /* TABLE END */
    

    document.add(t2);

		
    //System.out.println("-----------" + document);
} catch (DocumentException de) {
    System.err.println(de.getMessage());
} catch (IOException ioe) {
    System.err.println(ioe.getMessage());
}
document.close();

%>

<html>
<head>
<script language="javascript">
function fileDown(name) {
    var form = document.downForm;
    form.fileName.value = name;
    form.submit();
}
</script>
</head>

<body onload="fileDown('<%=fileName%>')">

<form name="downForm" action="../common/file_download.etax">
      <input type="hidden" name="fileName" value="<%=fileName%>">
      <input type="hidden" name="dir" value="upload/report">
</form>

</body>
</html>