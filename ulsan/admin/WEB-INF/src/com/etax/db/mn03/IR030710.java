/*****************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR030710.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세출 > 수기분등록
******************************************************/
package com.etax.db.mn03;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR030710 {


  /* 수기분등록  */
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
    sb.append("         ,'G2'                                                        \n"); //입금지급구분(G1:입금, G2:지급)                                                                                     
    sb.append("         ,'I1'                                                        \n"); //입력구분(I1:입금지급, I2:반납, I3:정정)                                                                                     
    sb.append("         ,DECODE(A.TYPE_CD, '01', 'C1', '02', 'C2', '03', 'C3', 'C4') \n");  //현금구분(C1:보증금, C2:보관금, C3:잡종금, C4:부가가치세)                          
    sb.append("         ,'D3'                                                        \n"); //예금종류(D1:정기예금, D2:별단예금, D3:공금예금)                                                                                     
    sb.append("         ,out_acct_detl_item                                          \n"); //채주성명                                                                                      
    sb.append("         ,pay_cmd_regi_no                                             \n"); //지급번호                                                                                      
    sb.append("         ,LINE_NM                                                     \n"); //비고                                                                                       
    sb.append("         ,IN_TOT_CNT                                                  \n");  //건수                                                                                     
    sb.append("         ,trnx_amt                                                    \n");  //금액                                                                                    
    sb.append("         ,?                                                           \n");  //로그번호                                                                                    
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