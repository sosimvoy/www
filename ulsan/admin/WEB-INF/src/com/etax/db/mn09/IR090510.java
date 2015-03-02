/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090510.java
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-18
* ���α׷�����	   : �ý��ۿ > �ŷ��αװ���
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090510 {

	/* �ŷ��α� �� */
	public static List getLogList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
                            
		sb.append(" SELECT M280_TRANSDATE,			     	  	                                      \n"); //�ŷ��Ͻ�
		sb.append("			   M280_LOGNO,				                                                  \n"); //�α׹�ȣ
		sb.append("			   M280_USERNAME,				                                                \n"); //����ڸ�
    sb.append("  DECODE(M280_WORKTYPE,   'A01','����',                                      \n");
		sb.append("                          'A02','����',                                      \n");
		sb.append("                          'A03','���Լ��������',                            \n");
		sb.append("                          'A04','���ܼ���',                                  \n");
		sb.append("                          'A05','�ڱݹ���',                                  \n");
		sb.append("                          'A06','�ڱݿ��',                                  \n");
		sb.append("                          'A07','�ϰ�/����',                               \n");
		sb.append("                          'A08','���ڰ���',                                  \n");
		sb.append("                          'A09','�ý��ۿ') M280_WORKTYPE,                 \n");
		sb.append("  DECODE(M280_WORKTYPE,   'A01',DECODE(M280_TRANSGUBUN, '111','ȸ������ ���',                  \n");                      
    sb.append("                                                        '121','����� ���(���漼,������)',     \n");
    sb.append("                                                        '131','����� ���(���漼,���ܼ���)',   \n");
    sb.append("                                                        '141','����� ���(���,Ư��ȸ��)',     \n");    
    sb.append("                                                        '150','����� ��ȸ',                    \n"); 
    sb.append("                                                        '159','����� ����',                    \n"); 
    sb.append("                                                        '160','EXCEL�ڷ� ��ȸ',                 \n"); 
    sb.append("                                                        '161','EXCEL�ڷ� ���',                 \n"); 
    sb.append("                                                        '169','EXCEL�ڷ� ����',                 \n");
    sb.append("                                                        '177','OCR��������',                    \n"); 
    sb.append("                                                        '180','������������ ��ȸ',              \n"); 
    sb.append("                                                        '190','���Ա������䱸�� ��ȸ',          \n"); 
    sb.append("                                                        '197','���Ա������䱸�� ����')          \n");               
    sb.append("                          ,'A02',DECODE(M280_TRANSGUBUN,'111','����� ���',             			 \n");
    sb.append("                                                        '120','��ϳ��� ��ȸ')                  \n");  
    sb.append("                          ,'A03',DECODE(M280_TRANSGUBUN,'111','����� ���',            				 \n");
    sb.append("                                                        '120','��ϳ��� ��ȸ',                  \n"); 
    sb.append("                                                        '129','��ϳ��� ����',          				 \n"); 
    sb.append("                                                        '122','��ϳ��� ����',          				 \n"); 
    sb.append("                                                        '130','�������� ������Ȳ ��ȸ', 				 \n"); 
    sb.append("                                                        '131','�������� ������Ȳ ���', 				 \n"); 
    sb.append("                                                        '132','�������� ������Ȳ ����', 				 \n"); 
    sb.append("                                                        '141','���༼ ���',            				 \n"); 
    sb.append("                                                        '150','���༼ ��ȸ',            				 \n"); 
    sb.append("                                                        '159','���༼ ����',            				 \n"); 
    sb.append("                                                        '152','���༼ ����')										 \n"); 
    sb.append("                          ,'A04',DECODE(M280_TRANSGUBUN,'110','�����Աݳ��� ��ȸ',              \n");    
    sb.append("                                                        '120','���꼭 ��ȸ',                    \n");    
    sb.append("                                                        '131','¡������ ���',                  \n");    
    sb.append("                                                        '140','��ϳ��� ��ȸ',                  \n");    
    sb.append("                                                        '149','��ϳ��� ����',                  \n");    
    sb.append("                                                        '142','��ϳ��� ����',                  \n");    
    sb.append("                                                        '150','�������ݼ۱ݹ�ȭ��������ȸ',      \n");   
    sb.append("                                                        '157','�������ݼ۱ݹ�ȭ������ �������') \n");   
    sb.append("                          ,'A05',DECODE(M280_TRANSGUBUN,'110','�ڱݹ�����ó���� �� ��ȸ', 			  \n"); 
    sb.append("                                                        '120','�ڱݹ��� ���γ�����ȸ',    			  \n"); 
    sb.append("                                                        '123','�ڱݹ��� ó��',            			  \n"); 
    sb.append("                                                        '130','�ڱ������ ���γ�����ȸ',  			  \n"); 
    sb.append("                                                        '133','�ڱ������ ó��',          			  \n"); 
    sb.append("                                                        '140','�ڱݹ��������� ��ȸ',      			  \n"); 
    sb.append("                                                        '150','�ڱݹ�������� ��ȸ',      			  \n"); 
		sb.append("                                                        '151','����е��',                      \n"); 
    sb.append("                                                        '159','�ڱݹ�������� ��ȸ',      			  \n"); 
    sb.append("                                                        '171','�׿������Կ䱸 ���',      			  \n"); 
    sb.append("                                                        '170','�׿������Կ䱸 ��ȸ',      			  \n"); 
    sb.append("                                                        '179','�׿������Կ䱸 ����',      			  \n"); 
    sb.append("                                                        '180','�׿������Խ��� ��ȸ',      			  \n"); 
    sb.append("                                                        '181','�׿������� ó��',          			  \n"); 
    sb.append("                                                        '190','�׿������������� ��ȸ',    			  \n"); 
    sb.append("                                                        '211','�׿������Լ���� ���',           \n"); 
    sb.append("                                                        '210','�׿������Լ���� ��ȸ',    			  \n"); 
    sb.append("                                                        '219','�׿������Լ���� ���',    			  \n"); 
    sb.append("                                                        '221','���µ��',                 			  \n"); 
    sb.append("                                                        '230','���º� �ŷ����� ��ȸ')     			  \n"); 
    sb.append("                          ,'A06',DECODE(M280_TRANSGUBUN,'111','�ڱݿ�Ź �䱸���',           	  \n"); 
    sb.append("                                                        '120','�ڱݿ�Ź �䱸��ȸ/���',      	  \n"); 
    sb.append("                                                        '130','�ڱݿ�Ź ������ȸ',           	  \n"); 
    sb.append("                                                        '131','�ڱݿ�Ź �ϰ���',           	  \n"); 
    sb.append("                                                        '140','�ڱݿ�Ź �ϰ��� ��ȸ',      	  \n"); 
    sb.append("                                                        '149','�ڱݿ�Ź �ϰ��� ���',      	  \n"); 
    sb.append("                                                        '150','�ڱݿ�Ź ��������ȸ',         	  \n"); 
    sb.append("                                                        '160','�ڱݿ�Ź(���,Ư��)��ȸ',     	  \n");
		sb.append("                                                        '169','�ڱݿ�Ź(���,Ư��)�ϰ���',     \n"); 
    sb.append("                                                        '179','�ڱݿ�Ź(���,Ư��)���',     	  \n"); 
    sb.append("                                                        '181','�ڱ�����䱸���',            	  \n"); 
    sb.append("                                                        '190','�ڱ�����䱸��ȸ',            	  \n"); 
    sb.append("                                                        '199','�ڱ�����䱸���',                \n");  
    sb.append("                                                        '200','�ڱ����������ȸ',            	  \n"); 
    sb.append("                                                        '201','�ڱ������ϰ���',            	  \n"); 
    sb.append("                                                        '210','�ڱ������ϰ�����ȸ',        	  \n"); 
    sb.append("                                                        '219','�ڱ������ϰ������',        	  \n"); 
    sb.append("                                                        '220','�ڱ�������������ȸ',          	  \n"); 
    sb.append("                                                        '230','�ڱ�����(���,Ư��)��ȸ',     	  \n"); 
		sb.append("                                                        '239','�ڱ�����(���,Ư��)�ϰ���',     \n"); 
    sb.append("                                                        '249','�ڱ�����(���,Ư��)���',     	  \n"); 
    sb.append("                                                        '250','�ڱݿ����Ȳ��ȸ',            	  \n"); 
    sb.append("                                                        '261','�ܾ����ڷ��Է�',              	  \n"); 
    sb.append("                                                        '270','�ܾ�����ȸ',                  	  \n"); 
    sb.append("                                                        '280','����ǥ��ȸ',                  	  \n"); 
    sb.append("                                                        '291','�ڱݿ��ȸ�迬���̿�')					  \n"); 
    sb.append("                          ,'A07',DECODE(M280_TRANSGUBUN,'110','������ȸ',      							  \n"); 
    sb.append("                                                        '121','���Ͼ�������',    							  \n"); 
    sb.append("                                                        '131','ȸ�迬���̿�')                    \n"); 
    sb.append("                          ,'A09',DECODE(M280_TRANSGUBUN,'110','�����û������ȸ',       				  \n"); 
    sb.append("                                                        '111','����ڵ��',             				  \n"); 
    sb.append("                                                        '115','����ڽ���',             				  \n"); 
    sb.append("                                                        '120','����ڳ�����ȸ',         				  \n"); 
    sb.append("                                                        '132','������������',           				  \n"); 
    sb.append("                                                        '141','��������������û',     				  \n"); 
    sb.append("                                                        '150','�ŷ��α���ȸ',           				  \n"); 
    sb.append("                                                        '160','�μ��ڵ���ȸ',           				  \n"); 
    sb.append("                                                        '161','�μ��ڵ���',           				  \n"); 
    sb.append("                                                        '169','�μ��ڵ����',           				  \n"); 
    sb.append("                                                        '170','�����ڵ���ȸ',           				  \n"); 
    sb.append("                                                        '171','�����ڵ���',           				  \n"); 
    sb.append("                                                        '179','�����ڵ����',           				  \n"); 
    sb.append("                                                        '180','������ȸ',               				  \n"); 
    sb.append("                                                        '181','���ϵ��',               				  \n"); 
    sb.append("                                                        '189','���ϼ���',                        \n");
    sb.append("                                                        '191','�����ϵ��',                      \n");
    sb.append("                                                        '201','ȸ�����ڵ���')                  \n");
    sb.append("        ) M280_TRANSGUBUN,                                                                       \n");
		sb.append("			         M280_CONNECTIP				                                                              \n"); //����IP
		sb.append("			    FROM M280_TRANSACTIONLOG_T                                                              \n");
		sb.append("        WHERE SUBSTR(M280_TRANSDATE,0,8) BETWEEN ? AND ?	                                        \n");
		
		if (!"".equals(paramInfo.getString("user_name"))){
		  sb.append("		     AND M280_USERNAME LIKE ?                                \n");
		}           
    sb.append("        AND SUBSTR(M280_WORKTYPE, 1, 1) = 'A'                     \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("su_sdate"));
		parameters.setString(i++,  paramInfo.getString("su_edate"));

		if (!"".equals(paramInfo.getString("user_name")) ) {
      parameters.setString(i++, "%"+paramInfo.getString("user_name")+"%");
    }
  
		return template.getList(conn, parameters);
	}
}