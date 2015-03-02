/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR091310TransCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ý��ۿ > ��Ÿ���������
***********************************************************************/
package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091310;
import com.etax.entity.CommonEntity;
import com.etax.trans.TransHandler;
import com.etax.command.common.TransferNo;
import com.etax.trans.data.*;


public class IR091310TransCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR091310TransCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
    
        String errMsg = "";

        String result = "";
        CommonEntity resultData =  new CommonEntity();

        if("init".equals(request.getParameter("gubun")) ) {
            CommonEntity lastInfo = IR091310.getLastInfo(conn);
            request.setAttribute("page.mn09.lastINFO", lastInfo);
        } else {
            CommonEntity transOpen = IR091310.getTransOpen(conn);

            if ("0000".equals(transOpen.getString("M570_ERR_CODE")) ) {
                errMsg = "������ �̹� �������ð� ����Ǿ����ϴ�.";
                request.setAttribute("page.mn09.lastINFO", transOpen);
            } else {
                CommonEntity pcNo = TransferNo.transferNo(conn, "");  // ����� ����
        
                HashMap<String, String> findData = new HashMap<String, String>();
        
                findData.put("common01", "0300");
                findData.put("common04", "1000");
                findData.put("common05", "100");
        
                findData.put("common07", pcNo.getString("TRANS_MANAGE_NO"));
                findData.put("common13", pcNo.getString("M260_TERMINALNO"));
                findData.put("detail01", "1");

                logger.info("�����˻����� : " +  findData);

                try	{
                    result = TransHandler.execute(conn, findData, "COMM");
                    resultData = GNBRev.getData(result, "COMM");
                } catch (Exception e) {
                    e.printStackTrace();
                    errMsg = "��� �� ������ �߻��Ͽ����ϴ�.";
                    resultData.setValue("common10", "9999");
                    resultData.setValue("common11", "�����ְ� �߻��Ͽ����ϴ�.�ٽýõ��ϼ���.");
                }

                if ("0000".equals(resultData.getString("common10")) ) {
                    resultData.setValue("common11", "�������ð� ���� ���ŵǾ����ϴ�.");
                }

                IR091310.updateRevData(conn, resultData);  //�������� ���� ���

                CommonEntity transResult = IR091310.getTransOpen(conn);
                request.setAttribute("page.mn09.lastINFO", transResult);
            }
        }

        request.setAttribute("page.mn09.ErrMsg", errMsg);
    }
}