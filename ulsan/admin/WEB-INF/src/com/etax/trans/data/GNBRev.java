/*****************************************************
* ����(�������� �ؽ��ʿ� ���) 
* @author	   			 (��)�̸�����
* @version         1.0
* @since           1.0
******************************************************/

package com.etax.trans.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.etax.entity.CommonEntity;
import com.etax.util.StringUtil;

public class GNBRev {
	static Logger logger = Logger.getLogger("GNBRev");
	private static int sp = 100;
	static CommonEntity data = new CommonEntity();
	static List<CommonEntity> arrayList = new ArrayList<CommonEntity>();
  
	/****************************************************************************
    * ������ byte�� �߶� CommonEntity�� ��´�
    * @param msg           	��������
    * @return                CommonEntity�� ���� ����
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public static CommonEntity getData(String msg, String flag) throws Exception {
        CommonEntity rowData =  new CommonEntity();

        byte[] bytemsg = msg.getBytes();
        rowData.setValue("common01", new String(bytemsg,  0,  4));
        rowData.setValue("common02", new String(bytemsg,  4, 12));
        rowData.setValue("common03", new String(bytemsg, 16,  2));
        rowData.setValue("common04", new String(bytemsg, 18,  4));
        rowData.setValue("common05", new String(bytemsg, 22,  3));
        rowData.setValue("common06", new String(bytemsg, 25,  1));
        rowData.setValue("common07", new String(bytemsg, 26,  6));
        rowData.setValue("common08", new String(bytemsg, 32,  8));
        rowData.setValue("common09", new String(bytemsg, 40,  6));
        rowData.setValue("common10", new String(bytemsg, 46,  4));
        rowData.setValue("common11", new String(bytemsg, 50, 24));
        rowData.setValue("common12", new String(bytemsg, 74,  3));
        rowData.setValue("common13", new String(bytemsg, 77,  3));
        rowData.setValue("common14", new String(bytemsg, 80,  1));
        rowData.setValue("common15", new String(bytemsg, 81, 19));
        
        if ("ACCT".equals(flag)) {
			//��������ȸ
			rowData.setValue("detail01", new String(bytemsg, 100,  15));  //���¹�ȣ 
            rowData.setValue("detail02", new String(bytemsg, 115,  13));  //�ֹλ���ڹ�ȣ
            rowData.setValue("detail03", new String(bytemsg, 128,  12));  //�����ָ�
            rowData.setValue("detail04", new String(bytemsg, 140,   3));  //�����ڵ�
            rowData.setValue("detail05", new String(bytemsg, 143,  20));  //�����ָ��γ���
            rowData.setValue("detail06", new String(bytemsg, 163,  12));  //�ݾ�
            rowData.setValue("detail07", new String(bytemsg, 175,   2));  //�з�����
            rowData.setValue("detail08", new String(bytemsg, 177, 123));  //����
        } else if ("COMM".equals(flag)) {
			//��Ÿ�����
		    rowData.setValue("detail01", new String(bytemsg, 100, 1));  //���� 
		    rowData.setValue("detail02", new String(bytemsg, 101, 199));  //����
        } else if ("JAN".equals(flag)) {
			//�ܾ���ȸ
		    rowData.setValue("detail01", new String(bytemsg, 100, 8));  //��������
		    rowData.setValue("detail02", new String(bytemsg, 108, 2));  //�迭�Ǽ�
        } else if ("DEAL".equals(flag)) {
			//�ŷ���
		    rowData.setValue("detail01", new String(bytemsg, 100, 15));  //���¹�ȣ
		    rowData.setValue("detail02", new String(bytemsg, 115, 8));  //������
			rowData.setValue("detail03", new String(bytemsg, 123, 8));  //������
		    rowData.setValue("detail04", new String(bytemsg, 131, 5));  //�ŷ��Ϸù�ȣ
			rowData.setValue("detail05", new String(bytemsg, 136, 8));  //ó��������
			rowData.setValue("detail06", new String(bytemsg, 144, 2));  //�迭�Ǽ�
        } 		
		return rowData;
		
	}
	
	/****************************************************************************
    * ������ byte�� �߶� CommonEntity�� ��´�
    * @param msg           	��������
	* @param num           	�迭 �Ǽ�
    * @return                List�� ���� ����
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public static List<CommonEntity> getArray(String msg, int num, String work_type) throws Exception{
	    byte[] bytemsg = msg.getBytes();
		List<CommonEntity> rowList = new ArrayList<CommonEntity>();
		if ("JAN".equals(work_type)) {
			sp = 110; //��������(8)+�迭�Ǽ�(2)
			for (int i=0; i<num; i++) {
			  CommonEntity rowData = new CommonEntity();
			  rowData.setValue("detail03", new String(bytemsg, sp, 15));  //���¹�ȣ 
              rowData.setValue("detail04", new String(bytemsg, nextPoint(sp, 15), 13));  //�����ܾ�
              rowData.setValue("detail05", new String(bytemsg, nextPoint(sp, 13), 4));  //ó������ڵ�
              rowData.setValue("detail06", new String(bytemsg, nextPoint(sp, 4), 24));  //ó������޽���
			  /*
			  rowData.setValue("detail03", StringUtil.substringByte(msg,sp,nextPoint(sp, 15)));  //���¹�ȣ 
			  rowData.setValue("detail04", StringUtil.substringByte(msg,sp,nextPoint(sp, 13)));  //�����ܾ�
			  rowData.setValue("detail05", StringUtil.substringByte(msg,sp,nextPoint(sp,  4)));  //ó������ڵ�
			  rowData.setValue("detail06", StringUtil.substringByte(msg,sp,nextPoint(sp, 24)));  //ó������޽���
			  */			
			  rowList.add(rowData);
		  }
		} else if ("DEAL".equals(work_type)) {
			sp = 146;
			for (int i=0; i<num; i++) {
			    CommonEntity rowData = new CommonEntity();
			    rowData.setValue("detail07", StringUtil.substringByte(msg,sp,nextPoint(sp,  8)));  //�ŷ����� 
			    rowData.setValue("detail08", StringUtil.substringByte(msg,sp,nextPoint(sp,  2)));  //�ŷ�����
			    rowData.setValue("detail09", StringUtil.substringByte(msg,sp,nextPoint(sp, 13)));  //�ŷ��ݾ�
			    rowData.setValue("detail10", StringUtil.substringByte(msg,sp,nextPoint(sp, 13)));  //�ŷ����ܾ�
				rowData.setValue("detail11", StringUtil.substringByte(msg,sp,nextPoint(sp, 20)));  //����
			
				rowList.add(rowData);
			}
		}
		

		return rowList;
	}
	
	/****************************************************************************
    * byte�� �ڸ� ������ ���� �ε���
    * @param startPoint      �����ε���
	* @param term           	�߸� byte ũ��
    * @return                �����ε���
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	private static int nextPoint(int startPoint, int term) throws Exception {
	    sp = startPoint + term;
	    return sp;
	}
	
	
	/****************************************************************************
    * �ܾ���ȸ �� �ŷ��� ����Ʈ ���ϱ�
    * @param msg      				��������
    * @return                �ϰ�� ��Ʈ�� ��� ����Ʈ
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public static List<CommonEntity>  getList(String msg, String flag) throws Exception {
		data = getData(msg, flag);
		int num = 0;
		if ("JAN".equals(flag))	{
			num = data.getInt("detail02");
		} else if ("DEAL".equals(flag))	{
			num = data.getInt("detail06");
		}
		arrayList = getArray(msg, num, flag);
		return arrayList;
	}
}
