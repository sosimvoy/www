<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070153.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-17
* 프로그램내용		: 일계/보고서 > 보고서조회 > 세입세출일계표(회계) > PDF 파일 저장
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

<%@ page import = "com.lowagie.text.Cell"               %>
<%@ page import = "com.lowagie.text.Table"              %>

<%@ page import = "com.lowagie.text.pdf.PdfPTable"      %>
<%@ page import = "com.lowagie.text.pdf.BaseFont"       %>
<%@ page import = "com.lowagie.text.pdf.PdfWriter"      %>
<%@ page import = "com.lowagie.text.pdf.PdfPCell"       %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax"  %>
<%

List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");

int listSize = reportList.size();
//out.println("::::iTextStart::::"+listSize);

//String fileName = "세입세출일계표_" +new java.sql.Date(System.currentTimeMillis()) + ".pdf";
String fileName = "세입세출일계표(회계).pdf";
String gubun    = "";
String acc_name = "";
String acc_year = request.getParameter("acc_year");
String acc_date = StringUtil.replace(request.getParameter("acc_date"),"-","");

//Document document = new Document();
Document document = new Document(PageSize.A4.rotate()); // A4 사이즈 설정

try {

	String filePath = request.getRealPath("/");
    // PDF 파일 생성경로 설정
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath+"upload/report/" + fileName));

    // 한글깨짐방지
    BaseFont objBaseFont = BaseFont.createFont("HYGoThic-Medium", "UniKS-UCS2-H", false);
    Font objFont = new Font(objBaseFont, 5);
    
    
    BaseFont objBaseFont2 = BaseFont.createFont("HYGoThic-Medium", "UniKS-UCS2-H", false);
    Font objFont2 = new Font(objBaseFont2, 6);

    document.open();

    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);


        /* TABLE START */
        Table t = new Table(13);    // 셀 갯수 
        t.setBorderWidth(0);
        t.setPadding(5);

        // 컬럼 사이즈
        float[] widths = { 1f, 1f, 1f , 1f, 1f , 1f, 1f, 1f, 1f , 1f, 1f , 1f, 1f};    
        t.setWidths(widths);
    
        //Cell c1 = new Cell(); 
        Cell c1;


        // Title
        // Title 0 (space)
        c1 = new Cell();
        c1.setBorderWidth(0);
        c1.setColspan(13);
        t.addCell(c1);
        t.addCell(c1);
        t.addCell(c1);

        // Title 1
        c1 = new Cell(new Paragraph(acc_year+"년 세입세출 일계표(회계별)", objFont2));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidth(0);
        c1.setColspan(13);
        t.addCell(c1);
    
        // Title 2
        c1 = new Cell(new Paragraph("(단위:원)", objFont2));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c1.setBorderWidth(0);
        c1.setColspan(13);
        t.addCell(c1);

        
        // Title 3 (space)
        c1 = new Cell();
        c1.setBorderWidth(0);
        c1.setColspan(13);
        t.addCell(c1);
        t.addCell(c1);
           
        // Header 
        // Header 1 
        c1 = new Cell(new Paragraph("회계명", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        c1.setRowspan(2); 
        t.addCell(c1);

        c1 = new Cell(new Paragraph("세입", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        c1.setColspan(5);
        t.addCell(c1);  

        c1 = new Cell(new Paragraph("자금배정액", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        c1.setRowspan(2); 
        t.addCell(c1);  

        c1 = new Cell(new Paragraph("세출", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        c1.setColspan(5);
        t.addCell(c1);  

        c1 = new Cell(new Paragraph("잔액", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        c1.setRowspan(2); 
        t.addCell(c1);  


        // Header 2
        c1 = new Cell(new Paragraph("전일누계", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        t.addCell(c1);  
    
        c1 = new Cell(new Paragraph("금일수입", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        t.addCell(c1);  
    
        c1 = new Cell(new Paragraph("과오납반납", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        t.addCell(c1);  
    
        c1 = new Cell(new Paragraph("과목정정액등", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        t.addCell(c1);  
    
        c1 = new Cell(new Paragraph("총계", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        t.addCell(c1);   
    
        c1 = new Cell(new Paragraph("전일누계", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        t.addCell(c1); 
    
        c1 = new Cell(new Paragraph("당일지급", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        t.addCell(c1);
    
        c1 = new Cell(new Paragraph("반납액", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        t.addCell(c1);
    
        c1 = new Cell(new Paragraph("과목정정액등", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        t.addCell(c1);
    
        c1 = new Cell(new Paragraph("총계", objFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        t.addCell(c1);

    //t.setHeaderRows(4); // 타이틀 고정
    
    
        c1 = new Cell(new Paragraph(rowData.getString("ACCNAME"), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		t.addCell(c1);

        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIP")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSEIPTOT")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTBAEJUNG")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);

        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHULJEONILTOT")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHUL")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHULBANNAP")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHULJEONGJEONG")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTSECHULTOT")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
        
        c1 = new Cell(new Paragraph(StringUtil.formatNumber(rowData.getString("AMTJAN")), objFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		t.addCell(c1);
         
        // Footer 0 (space)
        c1 = new Cell();
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidth(0);
        c1.setColspan(13);
        t.addCell(c1);
        t.addCell(c1);
        t.addCell(c1);
        t.addCell(c1);
        
        // Footer 1
        c1 = new Cell(new Paragraph("위와같이보고함", objFont2));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidth(0);
        c1.setColspan(13);
        t.addCell(c1);

        // Footer 2
        c1 = new Cell(new Paragraph(acc_date.substring(0,4) + "년 " + acc_date.substring(4,6) + "월 " + acc_date.substring(6,8) + "일", objFont2));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidth(0);
        c1.setColspan(13);
        t.addCell(c1);

        // Footer 3
        c1 = new Cell(new Paragraph("울산광역시 세입징수관 귀하", objFont2));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setBorderWidth(0);
        c1.setColspan(6);
        t.addCell(c1);

        c1 = new Cell(new Paragraph("경남은행 울산시청점", objFont2));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c1.setBorderWidth(0);
        c1.setColspan(7);
        t.addCell(c1);
        
        // Footer 4 (space)
        c1 = new Cell();
        c1.setBorderWidth(0);
        c1.setColspan(13);
        t.addCell(c1);
        t.addCell(c1);

        document.add(t);

    }
    /* TABLE END */
    


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