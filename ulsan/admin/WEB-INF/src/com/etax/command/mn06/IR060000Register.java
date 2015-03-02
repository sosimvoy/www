/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR060000Register.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ�Ź������ȸ-�ϰ���
***********************************************************************/
package com.etax.command.mn06;

import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR060000;
import com.etax.command.common.TransLogInsert;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;
public class IR060000Register {
    private static Logger logger = Logger.getLogger("IR060000Register"); // log4j ����

    public static String permission(Connection conn, CommonEntity pmsData) throws SQLException{     
    String SucMsg = "";    
    String acc_date = pmsData.getString("acc_date");  //ȸ������        logger.info("acc_date 1 : " + acc_date);    
    long addAmt = 0L;  // ���̺� ������    
    conn.setAutoCommit(false);
        while (Integer.parseInt(acc_date) <= Integer.parseInt(TextHandler.getBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(), 1))) ) {
            logger.info("acc_date 2 : " + acc_date);
            CommonEntity singleCnt = IR060000.getSingleCount(conn, pmsData, acc_date);  //�ܾ��� ������ȸ
            if ("0".equals(singleCnt.getString("CNT")) ) {
                if (acc_date.equals(pmsData.getString("acc_date")) ) {
                    //���� �� ȸ�����ڿ� �Էµ� ȸ�����ڰ� ���� ��
                    if ("1".equals(pmsData.getString("work_flag")) || "3".equals(pmsData.getString("work_flag")) ) {

                        CommonEntity impCnt = IR060000.getImpCount(conn, pmsData);  // �ܾ��� ��ȸ ȸ��⵵, ȸ������ ����
                        if ("0".equals(impCnt.getString("CNT")) ) {
                            if (IR060000.insertHiddenData(conn, pmsData, acc_date) < 1 ) {
                                //�����ܾ��� 0����, ������������ �Է±ݾ����� ���
                                conn.rollback();
                                conn.setAutoCommit(true);
                                SucMsg = "��� �� ������ �߻��Ͽ����ϴ�.";
                            }
                        } else {
                            //�ܾ��� ������ ��
                            SucMsg = "��� �� ������ �߻��Ͽ����ϴ�.";
                            acc_date = "99999999";
                            conn.rollback();
                            conn.setAutoCommit(true);
                        }
                    } else {
                        //work_flag�� 2, 4�� ��
                        SucMsg = "��� �� ������ �߻��Ͽ����ϴ�.";
                        acc_date = "99999999";
                        conn.rollback();
                        conn.setAutoCommit(true);
                    }
                } else {
                    SucMsg = "";
                    acc_date = "99999999";
                }                logger.info("acc_date 3 : " + acc_date);
            } else {                logger.info("acc_date 4 : " + acc_date);
                //�ܾ��� ������ȸ�� 0���� �ƴ� ��
                if (acc_date.equals(pmsData.getString("acc_date")) ) {
                    //�۾�ȸ�����ڿ� �Է� ���ڰ� ���� ��                    logger.info("deposit_type : " + pmsData.getString("deposit_type"));                    logger.info("due_day : " + pmsData.getString("due_day"));
                    CommonEntity fieldInfo = IR060000.getFieldInfo(conn, pmsData);
                    String field_name = fieldInfo.getString("FIELD_NAME");                    logger.info("field_name : " + field_name);
                    if ("1".equals(pmsData.getString("work_flag")) || "2".equals(pmsData.getString("work_flag")) ) {
                    // �۾� ������ 1, 2 �� ��(�ű�, �Ա�)
                    //������Ʈ �߰�
                        if (IR060000.updatePlusAmt(conn, pmsData, acc_date, field_name) < 1) {
                            //�ش�ݾ����� ���� �Ǵ� ���� �ݾ� ����
                            conn.rollback();
                            conn.setAutoCommit(true);
                        }
                    } else {
                        //�۾� ������ 3, 4 �� ��(����, �Ա����)
                        //������Ʈ �߰�
                        if (IR060000.updateMinusAmt(conn, pmsData, acc_date, field_name) < 1) {
                            //�ش�ݾ����� ���� �Ǵ� ���� �ݾ� ����
                            conn.rollback();
                            conn.setAutoCommit(true);
                        }
                    }
                } else {
                    //�۾�ȸ�����ڿ� �Է� ���ڰ� �ٸ� ��
                    CommonEntity yesterField = IR060000.getYesterFieldInfo(conn, pmsData);
                    String yester_field = yesterField.getString("FIELD_NAME");
                    //������Ʈ �߰�
                    if (IR060000.updateYesterAmt(conn, pmsData, acc_date, yester_field, addAmt) < 1) {
                        //�ش�ݾ����� ���� �Ǵ� ���� �ݾ� ����
                        conn.rollback();
                        conn.setAutoCommit(true);
                    }
                }
        
                CommonEntity yesterInfo = IR060000.getYesterInfo(conn, pmsData, acc_date);
                addAmt = yesterInfo.getLong("YESTER_AMT");                logger.info("acc_date 5 : " + acc_date);
                acc_date = TextHandler.getBusinessDate(conn, TextHandler.addDays(acc_date, 1));  // �۾�ȸ�����ڸ� ���� �����Ϸ� ����
                logger.info("acc_date 6 : " + acc_date);
            }
        }
        return SucMsg;
    }
}