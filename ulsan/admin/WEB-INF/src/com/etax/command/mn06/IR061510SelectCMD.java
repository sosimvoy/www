/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR061510SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금운용현황 조회
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

  private static Logger logger = Logger.getLogger(IR061510SelectCMD.class); // log4j 설정

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
			if ("A".equals(request.getParameter("src_gubun")) ) { //잔액명세
				List<CommonEntity> hwanList = IR061510.getHwanList(conn, paramInfo);
		    request.setAttribute("page.mn06.moneyRunList", hwanList);

				/* 총 카운트 조회 */
		    CommonEntity rowData = IR061510.getHwanCount(conn, paramInfo);
		    request.setAttribute("page.mn06.count", new Integer(rowData.getInt("CNT")));
        request.setAttribute("page.mn06.tot", rowData);

			} else if ("B".equals(request.getParameter("src_gubun")) || "C".equals(request.getParameter("src_gubun")) ) {  //신규해지명세
        List<CommonEntity> sinkyuList = IR061510.getNewAndCancelList(conn, paramInfo);
		    request.setAttribute("page.mn06.moneyRunList", sinkyuList);

				/* 총 카운트 조회 */
		    CommonEntity rowData = IR061510.getNewCancelCount(conn, paramInfo);
		    request.setAttribute("page.mn06.count", new Integer(rowData.getInt("CNT")));
      } else if ("D".equals(request.getParameter("src_gubun")) ) { //자금운용내역장
				List<CommonEntity> totalList = IR061510.getTotalList(conn, paramInfo);
		    request.setAttribute("page.mn06.moneyRunList", totalList);
				
			}
		}
  }
}