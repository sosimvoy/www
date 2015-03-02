/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : TransferNo.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : ������ȣä�� > ����
***********************************************************************/

package com.etax.command.common;

import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.db.trans.TransDAO;
import com.etax.entity.CommonEntity;


public class TransferNo {

    private static Logger logger = Logger.getLogger("TransferNo"); // log4j ����

    public static CommonEntity transferNo(Connection conn, String user_id) throws SQLException{ 

        CommonEntity pcNo = new CommonEntity();

        if ("".equals(user_id)) {  //�ܼ���ȸ
            pcNo = TransDAO.getPcNo(conn);
        } else { //å���ڽ���
            pcNo = TransDAO.getPcNo(conn, user_id);
        }
        return pcNo;
    }
}