/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070113.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-18
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > ���Լ����ϰ�ǥ(ȸ�躰)
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070113 {

	/* ���� ��ȸ */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT LIST.*                                                                                                                \n");
		sb.append("  FROM (                                                                                                                     \n");
		sb.append("		     SELECT ORG.*, ROWNUM RN                                                                                    		\n");
		sb.append("		       FROM (                                                                                                           \n");
	    sb.append("                  SELECT (A.M220_ACCTYPE || SUBSTR(A.M220_ACCCODE,1,1)) AS SUMGUBUN                                                                                      \n");
		sb.append("                        ,A.M220_ACCCODE AS ACCCODE                                                                                                                       \n");
		sb.append("                        ,DECODE(C.M360_ACCNAME,'�Ϲ�ȸ��','��û(����)', C.M360_ACCNAME) AS ACCNAME                                                                       \n");
		
        sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'0',A.M220_AMTSEIPJEONILTOT,0))  AMTSEIPJEONILTOT                                                                   \n");   // ���� ���ϴ���        
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'0',A.M220_AMTSEIP,0)) AMTSEIP                                                                                      \n");   // ���� ���ϼ���      
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'0',A.M220_AMTSEIPGWAONAP,0)) AMTSEIPGWAONAP                                                                        \n");   // ���� �������ݳ�    
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'0',A.M220_AMTSEIPJEONGJEONG,0)) AMTSEIPJEONGJEONG                                                                  \n");   // ���� ���������׵�  
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'0',A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG,0)) AMTSEIPTOT	    \n");   // ���� �Ѱ�       
		
        sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'2',(A.M220_AMTBAJEONGSUJEONILTOT + M220_AMTBAJEONGSURYUNG),0))  AMTBAEJUNG                                         \n");   // �ڱݹ�����
		
        sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'1',A.M220_AMTSECHULJEONILTOT,0)) AMTSECHULJEONILTOT                                                                \n");   // ���� ���ϴ���
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'1',A.M220_AMTSECHUL,0)) AMTSECHUL                                                                                  \n");   // ���� ��������       
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'1',A.M220_AMTSECHULBANNAP,0)) AMTSECHULBANNAP                                                                      \n");   // ���� �ݳ���         
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'1',A.M220_AMTSECHULJEONGJEONG,0)) AMTSECHULJEONGJEONG                                                              \n");   // ���� ���������׵�   
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'1',(A.M220_AMTSECHULJEONILTOT + A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG),0)) AMTSECHULTOT	\n");   // ���� �Ѱ� 
		
        sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'0',A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG,0)				    \n");
		sb.append("                           + DECODE(B.M390_WORKGUBUN,'2',(A.M220_AMTBAJEONGSUJEONILTOT + A.M220_AMTBAJEONGSURYUNG),0)												    \n");
		sb.append("                           - DECODE(B.M390_WORKGUBUN,'1',(A.M220_AMTSECHULJEONILTOT + A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG),0)		    \n");
		sb.append("                           - DECODE(B.M390_WORKGUBUN,'2',A.M220_AMTBAJEONGJEONILTOT,0)																                    \n");
		sb.append("                           - DECODE(B.M390_WORKGUBUN,'2',A.M220_AMTBAJEONG,0)) AMTJAN									                                                \n");   // �ܾ�
		
        sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'3',A.M220_AMTJEONGGI,0)) AMTJEONGGI                                                                                \n");   // �ڱݿ�� ���⿹�ݵ� 
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'3',M220_AMTGONGGEUM,0)) AMTGONGGEUM                                                                                \n");   // �ڱݿ�� �����ܾ�   
		
        sb.append("                    FROM M220_DAY_T A                                \n");
		sb.append("                        ,M390_USESEMOKCODE_T B                       \n");
		sb.append("                        ,M360_ACCCODE_T C                            \n");
		sb.append("                        ,M370_SEMOKCODE_T D                          \n");
		sb.append("                   WHERE A.M220_YEAR = B.M390_YEAR                   \n");
		sb.append("                     AND A.M220_PARTCODE = B.M390_PARTCODE           \n");
		sb.append("                     AND A.M220_ACCTYPE = B.M390_ACCGUBUN            \n");
		sb.append("                     AND A.M220_ACCCODE = B.M390_ACCCODE             \n");
		sb.append("                     AND A.M220_SEMOKCODE = B.M390_SEMOKCODE         \n");
		sb.append("                     AND B.M390_YEAR = C.M360_YEAR					\n");
		sb.append("                     AND B.M390_ACCGUBUN = C.M360_ACCGUBUN			\n");
		sb.append("                     AND B.M390_ACCCODE = C.M360_ACCCODE	            \n");
		sb.append("                     AND B.M390_YEAR = D.M370_YEAR					\n");
		sb.append("                     AND B.M390_ACCGUBUN = D.M370_ACCGUBUN			\n");
		sb.append("                     AND B.M390_ACCCODE = D.M370_ACCCODE				\n");
		sb.append("                     AND B.M390_WORKGUBUN = D.M370_WORKGUBUN			\n");
		sb.append("                     AND B.M390_SEMOKCODE = D.M370_SEMOKCODE         \n");
		sb.append("                     AND D.M370_SEGUMGUBUN <> '2'                    \n");

		sb.append("                     AND A.M220_YEAR = ?								\n");
		sb.append("                     AND A.M220_DATE = ?								\n");
		
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("                 AND A.M220_ACCTYPE = ?							\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("                 AND A.M220_PARTCODE = ?							\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("                 AND A.M220_ACCCODE = ?							\n");
		}
		sb.append("                  GROUP BY (A.M220_ACCTYPE || SUBSTR(A.M220_ACCCODE,1,1))    \n");
		sb.append("                          ,(A.M220_ACCCODE                                   \n");
		sb.append("                          ,C.M360_ACCNAME)                                   \n");
        sb.append("		            ) ORG                                                       \n");
		sb.append("		      WHERE ROWNUM <= ?                                                 \n");
		sb.append("       ) LIST                                                                \n");
		sb.append(" WHERE LIST.RN > ?                                                           \n");
		

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		if(!paramInfo.getString("acc_type").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
		if(!paramInfo.getString("part_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
		if(!paramInfo.getString("acc_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
		parameters.setInt(idx++, paramInfo.getInt("toRow"));
		parameters.setInt(idx++, paramInfo.getInt("fromRow"));

		return template.getList(conn, parameters);
	}

    
	/* ���� �� �Ǽ� ��ȸ */
	public static CommonEntity getTotalCount(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT SUM(COUNT(DISTINCT A.M220_ACCCODE)) CNT       \n");
        sb.append("  FROM M220_DAY_T A                                  \n");
		sb.append("      ,M390_USESEMOKCODE_T B                         \n");
		sb.append("      ,M360_ACCCODE_T C                              \n");
		sb.append("      ,M370_SEMOKCODE_T D                            \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                     \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE             \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN              \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE               \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE           \n");
		sb.append("   AND B.M390_YEAR = C.M360_YEAR					    \n");
		sb.append("   AND B.M390_ACCGUBUN = C.M360_ACCGUBUN			    \n");
		sb.append("   AND B.M390_ACCCODE = C.M360_ACCCODE	            \n");
		sb.append("   AND B.M390_YEAR = D.M370_YEAR                     \n");
		sb.append("   AND B.M390_ACCGUBUN = D.M370_ACCGUBUN             \n");
		sb.append("   AND B.M390_ACCCODE = D.M370_ACCCODE				\n");
		sb.append("   AND B.M390_WORKGUBUN = D.M370_WORKGUBUN			\n");
		sb.append("   AND B.M390_SEMOKCODE = D.M370_SEMOKCODE           \n");
		sb.append("   AND D.M370_SEGUMGUBUN <> '2'                      \n");

		sb.append("   AND A.M220_YEAR = ?								\n");
		sb.append("   AND A.M220_DATE = ?								\n");
		
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("                 AND A.M220_ACCTYPE = ?				\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("                 AND A.M220_PARTCODE = ?				\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("                 AND A.M220_ACCCODE = ?				\n");
		}
		sb.append(" GROUP BY (A.M220_ACCTYPE || SUBSTR(A.M220_ACCCODE,1,1)) \n");
		sb.append("         ,(A.M220_ACCCODE                                \n");
		sb.append("         ,C.M360_ACCNAME)                                \n");
		

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		if(!paramInfo.getString("acc_type").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
		if(!paramInfo.getString("part_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
		if(!paramInfo.getString("acc_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}

		return template.getData(conn, parameters);
	}
}