    function printArea(Obj, type) {		// �μ⿵�� DIV��, ���μ��μ��� 
        var W = Obj.offsetWidth;        //screen.availWidth; 
        var H = Obj.offsetHeight;        //screen.availHeight; 
        var features = "menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes,width=" + W + ",height=" + H + ",left=0,top=0"; 
        var PrintPage = window.open("about:blank",Obj.id,features);		// blank page open
        
		// blank �˾�â�� �� html ����
		PrintPage.document.open(); 
		PrintPage.document.write("<html>");
		PrintPage.document.write("\n<head>");
		PrintPage.document.write("\n<link href='../css/class.css' rel='stylesheet' type='text/css' />"); 
        PrintPage.document.write("\n<style>");	// ���� ��½ø� ����
		PrintPage.document.write("\n .report {font-family: 'arial','����', 'Helvetica', 'sans-serif'; border-top:0px solid #000000;border-right:0px solid #000000; border-left:0px solid #000000; border-bottom:0px solid #000000;}");
		PrintPage.document.write("\n .report td.re01 {font-size:6pt; border-top:1px solid #000000;border-right:1px solid #000000; border-left:1px solid #000000; border-bottom:1px solid #000000;} ");                              
		PrintPage.document.write("\n .report td.re02 {font-size:5pt; border-top:1px solid #000000;border-right:1px solid #000000; border-left:1px solid #000000; border-bottom:1px solid #000000;}");
		PrintPage.document.write("\n .report td.re03 {font-size:6pt; border-top:0px solid #ffffff;border-right:0px solid #ffffff; border-left:0px solid #ffffff; border-bottom:0px solid #ffffff;}");
		PrintPage.document.write("\n</style>");
		PrintPage.document.write("\n<object id=factory style='display:none' classid='clsid:1663ed61-23eb-11d2-b92f-008048fdd814'  codebase='../js/smsx.cab#Version=6,5,439,72'></object>"); 
		PrintPage.document.write("\n</head>");
		PrintPage.document.write("\n<body>");
		//PrintPage.document.write("\n<center>" + Obj.innerHTML + "\n</center>");
		PrintPage.document.write("\n" + Obj.innerHTML + "\n");
		PrintPage.document.write("\n</body>");
		PrintPage.document.write("\n<script>");
		PrintPage.document.write("\n function pagePrint(){");
		PrintPage.document.write("\n factory.printing.header		= '';");				// �Ӹ���
		PrintPage.document.write("\n factory.printing.footer		= '';");				// ������
		PrintPage.document.write("\n factory.printing.portrait		= " + type + ";");		//true : ����  false: ���� ���(���� ȭ�鿡�� ������)
		PrintPage.document.write("\n factory.printing.leftMargin	= 15.0;");				// ������ ���� ����
		PrintPage.document.write("\n factory.printing.topMargin		= 10.0;");
		PrintPage.document.write("\n factory.printing.rightMargin	= 15.0;");
		PrintPage.document.write("\n factory.printing.bottomMargin	= 0.5");
		//PrintPage.document.write("\n factory.printing.Preview();");	// �̸�����
		PrintPage.document.write("\n factory.printing.Print(true, window);");	// �μ⼳��
		PrintPage.document.write("\n self.close();");
		PrintPage.document.write("}");
		PrintPage.document.write("\n</script>");
		PrintPage.document.write("\n</html>"); 
        PrintPage.document.close(); 
        PrintPage.document.title = document.domain; 

		// blank �˾� �� blank ���� �ִ� pagePrint ȣ�� - ��� �� �ݱ�
        PrintPage.pagePrint(); 
    } 

