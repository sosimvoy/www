/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR070210InsertCMD.java
* ���α׷��ۼ���    : (��)�̸�����
* ���α׷��ۼ���    :  2010-08-27
* ���α׷�����      : �ϰ�/���� > ���Ͼ������� > ����
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

    private static Logger logger = Logger.getLogger(IR070210InsertCMD.class); // log4j ����

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

		List<CommonEntity> magamTypeList = IR070210.getMagamType(conn, paramInfo); //���ϴ��� ȸ��Ÿ������
        for (int x=0; magamTypeList != null && x<magamTypeList.size(); x++) {
			CommonEntity magamtypedata = (CommonEntity)magamTypeList.get(x);

			inWhere += "'" + magamtypedata.getString("M320_ACCTYPE") + "'";
			typeCnt++;

			if (typeCnt > 0 && typeCnt<magamTypeList.size()){
			    inWhere += ",";
			}

			if ("E".equals(magamtypedata.getString("M320_ACCTYPE"))){ //���ܼ�������� ���ϴ��� ��ƾ ó�� ����
			    seipSechulChk = true;
			}
		}

        paramInfo.setValue("acctype", inWhere);
        logger.info("acctype : " + paramInfo.getString("acctype"));

		//-------���Լ�������� �� ������ ���̺� ����ó��-----------------
		//���ϴ���� �ڷ� �������ڷ� INSERT
		int setCnt = 0;
		if (typeCnt > 0){
		    setCnt = IR070210.setAgoDataSetting(conn, paramInfo);
		}

		if (setCnt == 0){
			logger.info("========���ϴ��� 0�� ================");
			if (IR070210.setAgoNoDataAllSetting(conn, paramInfo) < 1){
				errChk = true;
				retMsg = "���ϸ��� �۾��� ������ �߻��Ͽ����ϴ�.(errCode1)";
				logger.info("retMsg : " + retMsg);
		    }
		} else {
			logger.info("========���ϴ��� ���� ================");
			List<CommonEntity> todaymagamList = IR070210.getTodayMagamList(conn, paramInfo);
		
			for (int i=0; todaymagamList != null && i<todaymagamList.size(); i++) {
			    CommonEntity todaymagamdata = (CommonEntity)todaymagamList.get(i);

				agoCnt = IR070210.getAgoSumDataCnt(conn, todaymagamdata);
				if (agoCnt == 0){
				    if (IR070210.setTodayMagamInsert(conn, todaymagamdata) < 1){
				        errChk = true;
						retMsg = "���ϸ��� �۾��� ������ �߻��Ͽ����ϴ�.(errCode2)";
						logger.info("retMsg : " + retMsg);
				    }
				} else {
				    if (IR070210.setTodayMagamUpdate(conn, todaymagamdata) < 1){
						errChk = true;
						retMsg = "���ϸ��� �۾��� ������ �߻��Ͽ����ϴ�.(errCode3)";
						logger.info("retMsg : " + retMsg);
				    }
				}
			}
		}

		logger.info("========���Լ�������� ���� ================");
		
		//------------���⼼�������---------------------------------------------
		//���ϴ���� �ڷ� �������ڷ� INSERT
		setCnt = 0;
		agoCnt = 0;

		if (seipSechulChk){
		    setCnt = IR070210.setSeipSechulAgoDataSetting(conn, paramInfo);
		}

		if (setCnt == 0){
			logger.info("========���ϴ��� 0�� ================");
		    if (IR070210.setSeipSechulAgoNoDataAllSetting(conn, paramInfo) < 1){
		        errChk = true;
				retMsg = "���ϸ��� �۾��� ������ �߻��Ͽ����ϴ�.(errCode4)";
				logger.info("retMsg : " + retMsg);
		    }
		} else {
		    logger.info("========���ϴ��� ���� ================");
			List<CommonEntity> sechultodaymagamList = IR070210.getSeipSechulTodayMagamList(conn, paramInfo);
		
			for (int i=0; sechultodaymagamList != null && i<sechultodaymagamList.size(); i++) {
			    CommonEntity sechultodaymagamdata = (CommonEntity)sechultodaymagamList.get(i);
                //�ڷᰡ �ִ��� üũ
				agoCnt = 0;
				agoCnt = IR070210.getSeipSechulAgoSumDataCnt(conn, sechultodaymagamdata);
				if (agoCnt == 0){
				    if (IR070210.setSeipSechulTodayMagamInsert(conn, sechultodaymagamdata) < 1){
		                errChk = true;
				        retMsg = "���ϸ��� �۾��� ������ �߻��Ͽ����ϴ�.(errCode5)";
						logger.info("retMsg : " + retMsg);
				    }
				} else {
				    if (IR070210.setSeipSechulTodayMagamUpdate(conn, sechultodaymagamdata) < 1){
		                errChk = true;
				        retMsg = "���ϸ��� �۾��� ������ �߻��Ͽ����ϴ�.(errCode6)";
						logger.info("retMsg : " + retMsg);
				    }
				}
			}
		}
		
		if (IR070210.setdailyMagamUpdate(conn, paramInfo) < 1){
			retMsg = "���ϸ��� �۾��� ������ �߻��Ͽ����ϴ�.(errCode7)";
			logger.info("retMsg : " + retMsg);
		}

		retMsg = "���ϸ��� �۾��� �Ϸ�Ǿ����ϴ�.";

		 /* ���Ͼ������� ��Ȳ ��ȸ */
		List<CommonEntity> magamList = IR070210.getMagamList(conn, paramInfo);
		request.setAttribute("page.mn07.magamList", magamList);
		request.setAttribute("page.mn07.retMsg", retMsg);

        CommonEntity dateData = IR070210.getDateData(conn, paramInfo);
        request.setAttribute("page.mn07.dateData", dateData);
    }
}
