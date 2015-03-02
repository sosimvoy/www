/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR070210InsertCMD.java
* 프로그램작성자    : (주)미르이즈
* 프로그램작성일    :  2010-08-27
* 프로그램내용      : 일계/보고서 > 일일업무마감 > 마감
***********************************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR070210;
import com.etax.entity.CommonEntity;

public class IR070210InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR070210InsertCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
    
	    boolean errChk = false;
		String magamdata = "";
		String accyear   = "";
		String accdate   = "";
		String retMsg    = "";
		int agoCnt = 0;

		magamdata = request.getParameter("magamdata");
		accyear   = magamdata.substring(0,4);
		accdate   = magamdata.substring(4,12);

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("accyear", accyear);
        paramInfo.setValue("accdate", accdate);
        paramInfo.setValue("acc_year",    request.getParameter("acc_year"));
		
		logger.info("param : " + paramInfo);

		int typeCnt = 0;
		String inWhere = "";
		boolean seipSechulChk = false;

		List<CommonEntity> magamTypeList = IR070210.getMagamType(conn, paramInfo); //전일누계 회계타입추출
        for (int x=0; magamTypeList != null && x<magamTypeList.size(); x++) {
			CommonEntity magamtypedata = (CommonEntity)magamTypeList.get(x);

			inWhere += "'" + magamtypedata.getString("M320_ACCTYPE") + "'";
			typeCnt++;

			if (typeCnt > 0 && typeCnt<magamTypeList.size()){
			    inWhere += ",";
			}

			if ("E".equals(magamtypedata.getString("M320_ACCTYPE"))){ //세외세출외현금 전일누계 루틴 처리 여부
			    seipSechulChk = true;
			}
		}

        paramInfo.setValue("acctype", inWhere);
        logger.info("acctype : " + paramInfo.getString("acctype"));

		//-------세입세출외현금 뺀 나머지 테이블 마감처리-----------------
		//전일누계액 자료 오늘일자로 INSERT
		int setCnt = 0;
		if (typeCnt > 0){
		    setCnt = IR070210.setAgoDataSetting(conn, paramInfo);
		}

		if (setCnt == 0){
			logger.info("========전일누계 0건 ================");
			if (IR070210.setAgoNoDataAllSetting(conn, paramInfo) < 1){
				errChk = true;
				retMsg = "일일마감 작업중 오류가 발생하였습니다.(errCode1)";
				logger.info("retMsg : " + retMsg);
		    }
		} else {
			logger.info("========금일누계 루프 ================");
			List<CommonEntity> todaymagamList = IR070210.getTodayMagamList(conn, paramInfo);
		
			for (int i=0; todaymagamList != null && i<todaymagamList.size(); i++) {
			    CommonEntity todaymagamdata = (CommonEntity)todaymagamList.get(i);

				agoCnt = IR070210.getAgoSumDataCnt(conn, todaymagamdata);
				if (agoCnt == 0){
				    if (IR070210.setTodayMagamInsert(conn, todaymagamdata) < 1){
				        errChk = true;
						retMsg = "일일마감 작업중 오류가 발생하였습니다.(errCode2)";
						logger.info("retMsg : " + retMsg);
				    }
				} else {
				    if (IR070210.setTodayMagamUpdate(conn, todaymagamdata) < 1){
						errChk = true;
						retMsg = "일일마감 작업중 오류가 발생하였습니다.(errCode3)";
						logger.info("retMsg : " + retMsg);
				    }
				}
			}
		}

		logger.info("========세입세출외현금 시작 ================");
		
		//------------세출세출외현금---------------------------------------------
		//전일누계액 자료 오늘일자로 INSERT
		setCnt = 0;
		agoCnt = 0;

		if (seipSechulChk){
		    setCnt = IR070210.setSeipSechulAgoDataSetting(conn, paramInfo);
		}

		if (setCnt == 0){
			logger.info("========전일누계 0건 ================");
		    if (IR070210.setSeipSechulAgoNoDataAllSetting(conn, paramInfo) < 1){
		        errChk = true;
				retMsg = "일일마감 작업중 오류가 발생하였습니다.(errCode4)";
				logger.info("retMsg : " + retMsg);
		    }
		} else {
		    logger.info("========금일누계 루프 ================");
			List<CommonEntity> sechultodaymagamList = IR070210.getSeipSechulTodayMagamList(conn, paramInfo);
		
			for (int i=0; sechultodaymagamList != null && i<sechultodaymagamList.size(); i++) {
			    CommonEntity sechultodaymagamdata = (CommonEntity)sechultodaymagamList.get(i);
                //자료가 있는지 체크
				agoCnt = 0;
				agoCnt = IR070210.getSeipSechulAgoSumDataCnt(conn, sechultodaymagamdata);
				if (agoCnt == 0){
				    if (IR070210.setSeipSechulTodayMagamInsert(conn, sechultodaymagamdata) < 1){
		                errChk = true;
				        retMsg = "일일마감 작업중 오류가 발생하였습니다.(errCode5)";
						logger.info("retMsg : " + retMsg);
				    }
				} else {
				    if (IR070210.setSeipSechulTodayMagamUpdate(conn, sechultodaymagamdata) < 1){
		                errChk = true;
				        retMsg = "일일마감 작업중 오류가 발생하였습니다.(errCode6)";
						logger.info("retMsg : " + retMsg);
				    }
				}
			}
		}
		
		if (IR070210.setdailyMagamUpdate(conn, paramInfo) < 1){
			retMsg = "일일마감 작업중 오류가 발생하였습니다.(errCode7)";
			logger.info("retMsg : " + retMsg);
		}

		retMsg = "일일마감 작업이 완료되었습니다.";

		 /* 일일업무마감 현황 조회 */
		List<CommonEntity> magamList = IR070210.getMagamList(conn, paramInfo);
		request.setAttribute("page.mn07.magamList", magamList);
		request.setAttribute("page.mn07.retMsg", retMsg);

        CommonEntity dateData = IR070210.getDateData(conn, paramInfo);
        request.setAttribute("page.mn07.dateData", dateData);
    }
}
