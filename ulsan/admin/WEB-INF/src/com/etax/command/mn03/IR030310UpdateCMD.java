/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR030310UpdateCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-08-05
* ���α׷�����	 : ���Լ�������� > ��������������Ȳ ���/��ȸ/����
******************************************************/

package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn03.IR030310;
import com.etax.entity.CommonEntity;
import com.etax.util.*;

public class IR030310UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR030310UpdateCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
     
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("year",             request.getParameter("date").substring(0,4));
		paramInfo.setValue("date",             request.getParameter("date"));
    
        String SucMsg = "";

        CommonEntity canYn = IR030310.Updatable(conn, paramInfo);  
	
		if ("0".equals(canYn.getString("CNT")))	{
		    SucMsg = "������ �ڷᰡ �����ϴ�.";
		} else {

            String[] gueonjong_list = request.getParameterValues("gueonjong");
			String[] create_list = request.getParameterValues("create");
	 	    String[] ldisuse_list = request.getParameterValues("ldisuse");
			String[] gumgosale_list = request.getParameterValues("gumgosale");
	 	    String[] citysale_list = request.getParameterValues("citysale");
            String[] citydivide_list = request.getParameterValues("citydivide");

            List<CommonEntity> gwonList = new ArrayList<CommonEntity>(); //�Է°��� �ٸ� ���� ���� ����Ʈ
            CommonEntity gwonData = new CommonEntity();  //�Է°� ���� ���� ����
            for (int x=0; x<gueonjong_list.length; x++) {
                paramInfo.setValue("gueonjong",        gueonjong_list[x]);
			    paramInfo.setValue("create",           TextHandler.unformatNumber(create_list[x]));
				paramInfo.setValue("ldisuse",          TextHandler.unformatNumber(ldisuse_list[x]));
				paramInfo.setValue("gumgosale",        TextHandler.unformatNumber(gumgosale_list[x]));
				paramInfo.setValue("citysale",         TextHandler.unformatNumber(citysale_list[x]));
				paramInfo.setValue("citydivide",       TextHandler.unformatNumber(citydivide_list[x]));	

                gwonData = IR030310.getGwonValue(conn, paramInfo);
                if (!"EVEN".equals(gwonData.getString("GWON_VAL")) ) {
                    gwonData.setValue("new_gwon",           gwonData.getString("GWON_VAL"));
                    gwonData.setValue("create",             TextHandler.unformatNumber(create_list[x]));
				    gwonData.setValue("ldisuse",            TextHandler.unformatNumber(ldisuse_list[x]));
				    gwonData.setValue("gumgosale",          TextHandler.unformatNumber(gumgosale_list[x]));
				    gwonData.setValue("citysale",           TextHandler.unformatNumber(citysale_list[x]));
				    gwonData.setValue("citydivide",         TextHandler.unformatNumber(citydivide_list[x]));	
                    gwonData.setValue("sigumgo_jan",        gwonData.getString("SIGUMGO_JAN"));
			        gwonData.setValue("city_jan",           gwonData.getString("CITY_JAN"));
                    gwonList.add(gwonData);
                } 
            }

            List<CommonEntity> dayList = IR030310.getDayList(conn, paramInfo);  //���� ����Ʈ

            if (dayList.size() > 100) {
                SucMsg = "�ִ� 100�� ���� �ڷ� ������ �����մϴ�.";
            } else {
      
                int cnt = 0;
                for (int y=0; y < dayList.size(); y++) {
                    CommonEntity dayInfo = dayList.get(y);
                    paramInfo.setValue("roop_date",    dayInfo.getString("M050_DATE"));
                    paramInfo.setValue("roop_year",    dayInfo.getString("M050_YEAR"));
                    if (!paramInfo.getString("year").equals(paramInfo.getString("roop_year")) ) {  //���� ù ���������� üũ
                        cnt++;
                    }
                    for (int i=0; i<gwonList.size(); i++)	{
                        CommonEntity gwonInfo = (CommonEntity)gwonList.get(i);
			            paramInfo.setValue("gueonjong",        gwonInfo.getString("new_gwon"));
			            paramInfo.setValue("create",           gwonInfo.getString("create"));
			            paramInfo.setValue("ldisuse",          gwonInfo.getString("ldisuse"));
			            paramInfo.setValue("gumgosale",        gwonInfo.getString("gumgosale"));
			            paramInfo.setValue("citysale",         gwonInfo.getString("citysale"));
			            paramInfo.setValue("citydivide",       gwonInfo.getString("citydivide"));
                        paramInfo.setValue("sigumgo_jan",      gwonInfo.getString("sigumgo_jan"));
			            paramInfo.setValue("city_jan",         gwonInfo.getString("city_jan"));
		
                        logger.info("paramInfo : " + paramInfo);

                        CommonEntity maxDate = IR030310.getMaxDate(conn, paramInfo);  //�۾��������� �� ������
                        paramInfo.setValue("max_year",    maxDate.getString("MAX_YEAR"));
                        paramInfo.setValue("max_date",    maxDate.getString("MAX_DATE"));

                        CommonEntity iwalData = IR030310.getIwalData(conn, paramInfo);  //���� ������ �̿� �� ����ġ  
                        paramInfo.setValue("sigumgo_amt",  iwalData.getString("SIGUMGO_AMT")); 
                        paramInfo.setValue("city_amt",     iwalData.getString("CITY_AMT"));

                        if (y ==0) {
                            if (IR030310.updateFirst(conn, paramInfo) < 1) {  //ù ������ ���� �ʵ常 ����
                                throw new ProcessException("E003");
                            }
                        } else {

                            if (cnt == 1) {  //���ʿ������� ��
                                paramInfo.setValue("lastyear_amt",  iwalData.getString("LASTYEAR_AMT")); 
                                paramInfo.setValue("create_nu",     "0"); 
                                paramInfo.setValue("ldisuse_nu",    "0"); 
                                paramInfo.setValue("gumgosale_nu",  "0"); 
                                paramInfo.setValue("citysale_nu",   "0"); 

                            } else {  //���ʿ����� �ƴ� ��
                                paramInfo.setValue("lastyear_amt",  iwalData.getString("M050_LASTYEAR")); 
                                paramInfo.setValue("create_nu",     iwalData.getString("M050_TOTALCREATE")); 
                                paramInfo.setValue("ldisuse_nu",    iwalData.getString("M050_TOTALDISUSE")); 
                                paramInfo.setValue("gumgosale_nu",  iwalData.getString("M050_TOTALGUMGOSALE")); 
                                paramInfo.setValue("citysale_nu",   iwalData.getString("M050_TOTALCITYSALE"));

                            }

                            if (IR030310.updateNugye(conn, paramInfo) < 1) {  
                                throw new ProcessException("E003");
                            }
                        }
                    }
			    }
            }
		}
	  
        if (!"".equals(SucMsg)) {
            request.setAttribute("page.mn03.SucMsg", SucMsg);
        } else {
            request.setAttribute("page.mn03.SucMsg", "�����Ǿ����ϴ�.");
        }

        /* �������� ������Ȳ  */
		List<CommonEntity> stampList = IR030310.getStampList(conn, paramInfo);
		request.setAttribute("page.mn03.stampList", stampList);
	}
}