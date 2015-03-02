/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : TransferNo.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 전문번호채번 > 공통
***********************************************************************/

package com.etax.command.common;

import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.db.trans.TransDAO;
import com.etax.entity.CommonEntity;


public class TransferNo {

    private static Logger logger = Logger.getLogger("TransferNo"); // log4j 설정

    public static CommonEntity transferNo(Connection conn, String user_id) throws SQLException{ 

        CommonEntity pcNo = new CommonEntity();

        if ("".equals(user_id)) {  //단순조회
            pcNo = TransDAO.getPcNo(conn);
        } else { //책임자승인
            pcNo = TransDAO.getPcNo(conn, user_id);
        }
        return pcNo;
    }
}