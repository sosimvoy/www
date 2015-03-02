/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070114.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-13
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > �����ü��ϰ�ǥ
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;                                            
import com.etax.entity.CommonEntity;

public class IR070114 {

	/* ���� ��ȸ */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','1','2','2','','','3') TYPE 	\n");
		sb.append("      ,DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','���漼','2','���ܼ���','','','��������') GUBUN 	\n");
		sb.append("      ,(CASE WHEN SUBSTR(B.M390_SEMOKCODE,1,1) = '1' THEN '10'				\n");	// ���漼 
		sb.append("				WHEN SUBSTR(B.M390_SEMOKCODE,1,2) = '21' THEN '21'				\n");	// ���ܼ��� (�����)
		sb.append("				WHEN SUBSTR(B.M390_SEMOKCODE,1,2) = '22' THEN '22'				\n");	// ���ܼ��� (�ӽ���)
		sb.append("				WHEN SUBSTR(B.M390_SEMOKCODE,1,2) IS NULL THEN ''				\n");	
		sb.append("				ELSE '30' END) GUBUN_CODE										\n");	// ��������
		sb.append("      ,B.M390_SEMOKCODE                                                      \n");
		sb.append("      ,C.M370_SEMOKNAME                                                      \n");
		
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE IN  ('02240','02130','02140','02220','02190','00110','00140','00170','00200','00710')  \n");
    sb.append("                     OR A.M220_SUNAPGIGWANCODE = '310001' THEN 0				                                                     \n");
		sb.append("                ELSE A.M220_AMTSEIPJEONILTOT END) AMTSEIPJEONILTOT_00000																			               \n");	// ��û ���ϱ�������(�������ϴ���)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE IN  ('02240','02130','02140','02220','02190','00110','00140','00170','00200','00710')  \n");
		sb.append("                     OR A.M220_SUNAPGIGWANCODE = '310001' THEN 0                                                            \n");
		sb.append("			       ELSE (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) END) TODAYAMT_00000										 \n");	// ��û ���ϼ���(���Ծ� - ������ - ����������)  
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE IN  ('02240','02130','02140','02220','02190','00110','00140','00170','00200','00710')  \n");
		sb.append("                     OR A.M220_SUNAPGIGWANCODE = '310001' THEN 0					                                                   \n");
		sb.append("                ELSE (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)) END) TODAYAMTTOT_00000			\n");	// ��û ���ϱ��� ���� (���ϴ��� + ���ϼ���)
		
    sb.append("      ,SUM(CASE WHEN A.M220_SUNAPGIGWANCODE = '310001' THEN A.M220_AMTSEIPJEONILTOT																			\n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_02240																									\n");	// ������ϼ� ���ϱ�������(�������ϴ���)
		sb.append("      ,SUM(CASE WHEN A.M220_SUNAPGIGWANCODE = '310001' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)								\n");
		sb.append("			       ELSE 0 END) TODAYAMT_02240																											\n");	// ������ϼ� ���ϼ���(���Ծ� - ������ - ����������)
		sb.append("      ,SUM(CASE WHEN A.M220_SUNAPGIGWANCODE = '310001' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_02240                                                                                                        \n");	// ������ϼ� ���ϱ��� ���� (���ϴ��� + ���ϼ���)
		
    sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT) AMTSEIPJEONILTOT                                                                                                 \n");	// �հ� ���ϱ�������(�������ϴ���)
		sb.append("      ,SUM(A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) TODAYAMT                                                               \n");	// �հ� ���ϼ���(���Ծ� - ������ - ����������)
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)) TODAYAMTTOT                                \n");	// �հ� ���ϱ��� ���� (���ϴ��� + ���ϼ���) 
		
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02130' AND A.M220_SUNAPGIGWANCODE <> '310001'   \n");
    sb.append("                THEN A.M220_AMTSEIPJEONILTOT																			           \n");
    sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_02130																									\n");	// �ߺμҹ漭 ���ϱ�������(�������ϴ���)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02130' AND A.M220_SUNAPGIGWANCODE <> '310001' 							\n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)					                                                     \n");
		sb.append("			       ELSE 0 END) TODAYAMT_02130																											\n");	// �ߺμҹ漭 ���ϼ���(���Ծ� - ������ - ����������)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02130' AND A.M220_SUNAPGIGWANCODE <> '310001' 	\n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))  \n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_02130                                                                                                        \n");	// �ߺμҹ漭 ���ϱ��� ���� (���ϴ��� + ���ϼ���)
		
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02140' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN A.M220_AMTSEIPJEONILTOT                                            \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_02140																									\n");	// ���μҹ漭 ���ϱ�������(�������ϴ���)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02140' AND A.M220_SUNAPGIGWANCODE <> '310001' 					\n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)           \n");
		sb.append("			       ELSE 0 END) TODAYAMT_02140																											\n");	// ���μҹ漭 ���ϼ���(���Ծ� - ������ - ����������)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02140' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_02140                                                                            \n");	// ���μҹ漭 ���ϱ��� ���� (���ϴ��� + ���ϼ���)
		
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02220' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                    THEN A.M220_AMTSEIPJEONILTOT																			      \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_02220																					\n");	// ����깰���Ž��� ���ϱ�������(�������ϴ���)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02220' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_02220																											\n");	// ����깰���Ž��� ���ϼ���(���Ծ� - ������ - ����������)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02220' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_02220                                                                                                        \n");	// ����깰���Ž��� ���ϱ��� ���� (���ϴ��� + ���ϼ���)
		
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02190' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                    THEN A.M220_AMTSEIPJEONILTOT																			      \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_02190																		  		\n");	// ��ȭ����ȸ�� ���ϱ�������(�������ϴ���)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02190' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_02190																											\n");	// ��ȭ����ȸ�� ���ϼ���(���Ծ� - ������ - ����������)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02190' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_02190                                                                                                        \n");	// ��ȭ����ȸ�� ���ϱ��� ���� (���ϴ��� + ���ϼ���)
		
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00110' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n"); 
    sb.append("                    THEN A.M220_AMTSEIPJEONILTOT																			      \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_00110																				  \n");	// �߱�û ���ϱ�������(�������ϴ���)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00110' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_00110																											\n");	// �߱�û ���ϼ���(���Ծ� - ������ - ����������)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00110' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_00110                                                                                                        \n");	// �߱�û ���ϱ��� ���� (���ϴ��� + ���ϼ���)
	    
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00140' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN A.M220_AMTSEIPJEONILTOT																			          \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_00140																					\n");	// ����û ���ϱ�������(�������ϴ���)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00140' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_00140																											\n");	// ����û ���ϼ���(���Ծ� - ������ - ����������)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00140' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_00140                                                                                                        \n");	// ����û ���ϱ��� ���� (���ϴ��� + ���ϼ���)
	
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00170' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN A.M220_AMTSEIPJEONILTOT																			          \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_00170																									\n");	// ����û ���ϱ�������(�������ϴ���)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00170' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_00170																											\n");	// ����û ���ϼ���(���Ծ� - ������ - ����������)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00170' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_00170                                                                                                        \n");	// ����û ���ϱ��� ���� (���ϴ��� + ���ϼ���)
  
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00200' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN A.M220_AMTSEIPJEONILTOT																			          \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_00200																					\n");	// �ϱ�û ���ϱ�������(�������ϴ���)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00200' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_00200																											\n");	// �ϱ�û ���ϼ���(���Ծ� - ������ - ����������)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00200' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_00200                                                                                                        \n");	// �ϱ�û ���ϱ��� ���� (���ϴ��� + ���ϼ���)
	
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00710' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN A.M220_AMTSEIPJEONILTOT																			          \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_00710																					\n");	// ���ֱ� ���ϱ�������(�������ϴ���)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00710' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_00710																											\n");	// ���ֱ� ���ϼ���(���Ծ� - ������ - ����������)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00710' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_00710                                                                                                        \n");	// ���ֱ� ���ϱ��� ���� (���ϴ��� + ���ϼ���)
	
    sb.append("  FROM M390_USESEMOKCODE_T B                     \n");
		sb.append("      ,M370_SEMOKCODE_T C                        \n");
		sb.append("      ,M220_DAY_T A                              \n");
		sb.append(" WHERE B.M390_YEAR      = C.M370_YEAR            \n");
		sb.append("   AND B.M390_ACCGUBUN  = C.M370_ACCGUBUN        \n");
		sb.append("   AND B.M390_ACCCODE   = C.M370_ACCCODE         \n");
		sb.append("   AND B.M390_SEMOKCODE = C.M370_SEMOKCODE       \n");
		sb.append("   AND B.M390_WORKGUBUN = C.M370_WORKGUBUN       \n");
		sb.append("   AND B.M390_YEAR      = A.M220_YEAR(+)         \n");
		sb.append("   AND B.M390_PARTCODE  = A.M220_PARTCODE(+)     \n");
		sb.append("   AND B.M390_ACCGUBUN  = A.M220_ACCTYPE(+)      \n");
		sb.append("   AND B.M390_ACCCODE   = A.M220_ACCCODE(+)      \n");
		sb.append("   AND B.M390_SEMOKCODE = A.M220_SEMOKCODE(+)    \n");
		sb.append("   AND B.M390_ACCGUBUN  = 'A'					\n");	// ȸ�豸�� (A:�Ϲ�ȸ��)        
		sb.append("   AND B.M390_ACCCODE   = '11'					\n");	// ȸ���ڵ� (11)                
		sb.append("   AND B.M390_SEMOKCODE < 9000000				\n");	// �����ڵ� (9000000 ����)  
        //sb.append("   AND B.M390_SEMOKCODE <> '6110300'             \n");   // ����ä ������
		sb.append("   AND B.M390_WORKGUBUN = '0'					\n");	// �������� (0:����)     
		sb.append("   AND C.M370_SEGUMGUBUN <> '2'                  \n");   //  ��������   
		

		// WHERE START
		sb.append("   AND B.M390_YEAR = ?							\n");
		sb.append("   AND A.M220_DATE(+) = ?						\n");
		
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("   AND B.M390_ACCGUBUN = ?								\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("   AND B.M390_PARTCODE = ?								\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("   AND B.M390_ACCCODE = ?								\n");
		}
		// WHERE END


		sb.append("GROUP BY ROLLUP(DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','1','2','2','','','3')           \n");
		sb.append("               ,(CASE WHEN SUBSTR(B.M390_SEMOKCODE,1,1) = '1' THEN '10'                  \n");
		sb.append("                      WHEN SUBSTR(B.M390_SEMOKCODE,1,2) = '21' THEN '21'                 \n");
		sb.append("                      WHEN SUBSTR(B.M390_SEMOKCODE,1,2) = '22' THEN '22'                 \n");
		sb.append("                      WHEN SUBSTR(B.M390_SEMOKCODE,1,2) IS NULL THEN ''                  \n");
		sb.append("                      ELSE '30' END)                                                     \n");
		sb.append("              ,(B.M390_SEMOKCODE,C.M370_SEMOKNAME))                                      \n");
		sb.append(" HAVING ((CASE WHEN SUBSTR(B.M390_SEMOKCODE,1,1) = '1' THEN '10'                         \n");
		sb.append("               WHEN SUBSTR(B.M390_SEMOKCODE,1,2) = '21' THEN '21'                        \n");
		sb.append("               WHEN SUBSTR(B.M390_SEMOKCODE,1,2) = '22' THEN '22'                        \n");
		sb.append("               WHEN SUBSTR(B.M390_SEMOKCODE,1,2) IS NULL THEN ''                         \n");
		sb.append("               ELSE '30' END) IS NOT NULL                                                \n");
		sb.append("       OR DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','1','2','2','','','3') IS NULL         \n");
		sb.append("       OR DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','1','2','2','','','3') = '2')          \n");

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

		return template.getList(conn, parameters);
	}

    /* ���� ��ȸ */
	public static CommonEntity getReportRemark(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT M470_REMARK1                                  \n");
		sb.append("      ,M470_REMARK2                                  \n");
		sb.append("      ,M470_REMARK3                                  \n");
    sb.append("      ,DECODE(M470_AMT1, 0, '', M470_AMT1) M470_AMT1 \n");
    sb.append("      ,DECODE(M470_AMT2, 0, '', M470_AMT2) M470_AMT2 \n");
    sb.append("      ,DECODE(M470_AMT3, 0, '', M470_AMT3) M470_AMT3 \n");
    sb.append("  FROM M470_DAYREMARK_T  \n");
		sb.append(" WHERE M470_YEAR = ?		\n");
		sb.append("   AND M470_DATE = ?		\n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		return template.getData(conn, parameters);
	}
}