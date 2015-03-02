/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR061910InsertCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금운용회계연도이월
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR061910;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR061910InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR061910InsertCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("this_year_list",     request.getParameter("this_year_list"));
        paramInfo.setValue("last_year_list",     request.getParameter("last_year_list"));
		paramInfo.setValue("acc_type",           request.getParameter("acc_type"));
    
		List<CommonEntity> carryInfo = IR061910.getCarryYN(conn, paramInfo);

		int jeongi = 0;
		int mmda = 0;
        int yetak = 0;

		if (carryInfo.size() == 1 && "Y".equals(carryInfo.get(0).getString("M250_ACCTRANSFERYN")) ) {

            CommonEntity dateInfo = IR061910.getAccDate(conn, paramInfo);
            paramInfo.setValue("acc_date",    TextHandler.getndayafterBusinessDate(conn, dateInfo.getString("ACC_DATE"), 1));

            logger.info("paramInfo : " + paramInfo);

			//정기예금, 환매체
            jeongi = IR061910.insertJeongiHwan(conn, paramInfo);

			//MMDA
			mmda = IR061910.insertMMDA(conn, paramInfo);       

             //예탁테이블(M120)
            yetak = IR061910.insertYetak(conn, paramInfo);

			request.setAttribute("page.mn06.SucMsg", "정기예금,환매체: " + jeongi + "건, MMDA: " + mmda + "건이 이월처리되었습니다.");
            logger.info("예탁 : " + yetak + "건, 정기 : " + jeongi + "건, MMDA : " + mmda + "건 등록");
		} else {
            logger.info("회계연도 이월 정보 : " + carryInfo);
			request.setAttribute("page.mn06.SucMsg", "해당하는 이월회계연도의 이월작업이 완료되지 않았습니다.");
		}		
    }
}
