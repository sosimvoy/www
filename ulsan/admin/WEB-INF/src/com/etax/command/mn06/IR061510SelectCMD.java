/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061510SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ����Ȳ ��ȸ
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR061510;
import com.etax.entity.CommonEntity;


public class IR061510SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR061510SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    int cpage = 1;
		if( !"".equals(request.getParameter("cpage")) ) {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
		}

		int fromRow = (cpage-1) * 20;
		int toRow = cpage * 20;

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",        request.getParameter("fis_year"));
		paramInfo.setValue("src_gubun",       request.getParameter("src_gubun"));
    paramInfo.setValue("acc_code",        request.getParameter("acc_code"));
		paramInfo.setValue("fixed_date",      request.getParameter("fixed_date"));
		paramInfo.setValue("start_date",      request.getParameter("start_date"));
		paramInfo.setValue("end_date",        request.getParameter("end_date"));
		paramInfo.setValue("sort_list",       request.getParameter("sort_list"));
        paramInfo.setValue("mmda_cancel",     request.getParameter("mmda_cancel"));
    
		paramInfo.setValue("fromRow",         fromRow+"");
		paramInfo.setValue("toRow",           toRow+"");

		String flag = request.getParameter("flag");

    logger.info("paramInfo : " + paramInfo);
    
    if ("".equals(request.getParameter("fis_year")) || request.getParameter("fis_year") == null) {
			paramInfo.setValue("fis_year",    "this_year");
    }
		List<CommonEntity> accList = IR061510.getAccList(conn, paramInfo);

    request.setAttribute("page.mn06.accList", accList);
    
		if ("S".equals(flag))	{
			if ("A".equals(request.getParameter("src_gubun")) ) { //�ܾ׸�
				List<CommonEntity> hwanList = IR061510.getHwanList(conn, paramInfo);
		    request.setAttribute("page.mn06.moneyRunList", hwanList);

				/* �� ī��Ʈ ��ȸ */
		    CommonEntity rowData = IR061510.getHwanCount(conn, paramInfo);
		    request.setAttribute("page.mn06.count", new Integer(rowData.getInt("CNT")));
        request.setAttribute("page.mn06.tot", rowData);

			} else if ("B".equals(request.getParameter("src_gubun")) || "C".equals(request.getParameter("src_gubun")) ) {  //�ű�������
        List<CommonEntity> sinkyuList = IR061510.getNewAndCancelList(conn, paramInfo);
		    request.setAttribute("page.mn06.moneyRunList", sinkyuList);

				/* �� ī��Ʈ ��ȸ */
		    CommonEntity rowData = IR061510.getNewCancelCount(conn, paramInfo);
		    request.setAttribute("page.mn06.count", new Integer(rowData.getInt("CNT")));
      } else if ("D".equals(request.getParameter("src_gubun")) ) { //�ڱݿ�볻����
				List<CommonEntity> totalList = IR061510.getTotalList(conn, paramInfo);
		    request.setAttribute("page.mn06.moneyRunList", totalList);
				
			}
		}
  }
}