/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR030710.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ����е��
******************************************************/
package com.etax.db.mn03;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR030710 {


  /* ����е��  */
  public static int batchcashInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		
    sb.append(" INSERT INTO M040_TAXCASH_T                                           \n");                                                                                      
    sb.append("       ( M040_SEQ                                                     \n");                                                                                      
    sb.append("        ,M040_YEAR                                                    \n");                                                                                      
    sb.append("        ,M040_DATE                                                    \n");                                                                                      
    sb.append("        ,M040_PARTCODE                                                \n");                                                                                      
    sb.append("        ,M040_ACCCODE                                                 \n");                                                                                      
    sb.append("        ,M040_DWTYPE                                                  \n");                                                                                      
    sb.append("        ,M040_INTYPE                                                  \n");                                                                                      
    sb.append("        ,M040_CASHTYPE                                                \n");                                                                                      
    sb.append("        ,M040_DEPOSITTYPE                                             \n");                                                                                      
    sb.append("        ,M040_ORDERNAME                                               \n");                                                                                      
    sb.append("        ,M040_ORDERNO                                                 \n");                                                                                      
    sb.append("        ,M040_NOTE                                                    \n");                                                                                      
    sb.append("        ,M040_CNT                                                     \n");                                                                                      
    sb.append("        ,M040_AMT                                                     \n");                                                                                      
    sb.append("        ,M040_LOGNO                                                   \n");                                                                                      
    sb.append("        ,M040_WORKTYPE                                                \n");                                                                                      
    sb.append("        ,M040_TRANSGUBUN  )                                           \n");                                                                                      
    sb.append(" select M040_SEQ.NEXTVAL                                              \n");                                                                                      
    sb.append("         ,a.FIS_YEAR                                                  \n");                                                                                      
    sb.append("         ,TRNX_YMD                                                    \n");                                                                                      
    sb.append("         ,b.part_code                                                 \n");                                                                                      
    sb.append("         ,B.ACC_CODE                                                  \n");                                                                                      
    sb.append("         ,'G2'                                                        \n"); //�Ա����ޱ���(G1:�Ա�, G2:����)                                                                                     
    sb.append("         ,'I1'                                                        \n"); //�Է±���(I1:�Ա�����, I2:�ݳ�, I3:����)                                                                                     
    sb.append("         ,DECODE(A.TYPE_CD, '01', 'C1', '02', 'C2', '03', 'C3', 'C4') \n");  //���ݱ���(C1:������, C2:������, C3:������, C4:�ΰ���ġ��)                          
    sb.append("         ,'D3'                                                        \n"); //��������(D1:���⿹��, D2:���ܿ���, D3:���ݿ���)                                                                                     
    sb.append("         ,out_acct_detl_item                                          \n"); //ä�ּ���                                                                                      
    sb.append("         ,pay_cmd_regi_no                                             \n"); //���޹�ȣ                                                                                      
    sb.append("         ,LINE_NM                                                     \n"); //���                                                                                       
    sb.append("         ,IN_TOT_CNT                                                  \n");  //�Ǽ�                                                                                     
    sb.append("         ,trnx_amt                                                    \n");  //�ݾ�                                                                                    
    sb.append("         ,?                                                           \n");  //�α׹�ȣ                                                                                    
    sb.append("         ,'A03'                                                       \n");                                                                                      
    sb.append("         ,'111'                                                       \n");                                                                                      
    sb.append("  FROM  trans_tef_efam026 a                                           \n");                                                                                      
    sb.append("        , tef_account b                                               \n");                                                                                      
    sb.append("  where a.fis_year = b.fis_year                                       \n");                                                                                      
    sb.append("    and a.OUT_ACCT_ACCT_NO = b.account_no                             \n");                                                                                      
    sb.append("    and a.fis_year = ?                                                \n");                                                                                      
    sb.append("     and a.trnx_ymd = ?                                               \n");                                                                                      
    sb.append("     and bank_process_code IN ('0000', '0001')                        \n");                                                                                      
    sb.append("     AND DATA_FG = '60'                                               \n");                                                                                      
    sb.append("     and not exists (                                                 \n");                                                                                      
    sb.append("               select 1                                               \n");                                                                                      
    sb.append("                 from M040_TAXCASH_T std                              \n");                                                                                      
    sb.append("                where std.M040_YEAR        =  a.FIS_YEAR              \n");                                                                                      
    sb.append("                   and  std.M040_DATE        =  a.TRNX_YMD            \n");                                                                                      
    sb.append("                   and  std.M040_CASHTYPE   = b.accgubun              \n");                                                                                      
    sb.append("                   and  std.M040_ACCCODE  = b.acc_code                \n");                                                                                      
    sb.append("                   and  std.M040_PARTCODE = b.part_code               \n");                                                                                      
    sb.append("                   and  std.M040_ORDERNO   = a.pay_cmd_regi_no        \n");                                                                                      
    sb.append("           )                                                          \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("fis_date"));
		
		return template.insert(conn, parameters);
	}


}