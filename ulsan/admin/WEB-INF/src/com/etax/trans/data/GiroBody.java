package com.etax.trans.data;

import java.util.LinkedHashMap;

public class GiroBody extends _AbstractData {
    
    public GiroBody() {
        LinkedHashMap<String, String[]> fieldset = new LinkedHashMap<String, String[]>();

        fieldset.put("data01", new String[] { "��������"                                , "6"   , "C" });
        fieldset.put("data02", new String[] { "�����ͱ���"                              , "2"   , "C" });
        fieldset.put("data03", new String[] { "�Ϸù�ȣ"                                , "7"   , "C" });
        fieldset.put("data04", new String[] { "�������з�"                            , "2"   , "C" });
        fieldset.put("data05", new String[] { "���ι�ȣ"                                , "7"   , "C" });
        fieldset.put("data06", new String[] { "�ݰ������ڵ�"                            , "3"   , "C" });
        fieldset.put("data07", new String[] { "�����ǹ��� �ֹ�(����,�����)��Ϲ�ȣ"    , "13"  , "C" });
        fieldset.put("data08", new String[] { "������ȣ"                                , "32"  , "C" });
        fieldset.put("data09", new String[] { "���ڳ��ι�ȣ"                            , "19"  , "C" });
        fieldset.put("data10", new String[] { "�����ݾ�"                                , "12"  , "C" });
        fieldset.put("data11", new String[] { "������������ڵ�"                        , "7"   , "C" });// �ſ�ī�� ������ ���, ī����ڵ�(3)+"0000"�� SET�Ѵ�.
        fieldset.put("data12", new String[] { "������ �ֹ�(����,�����)��Ϲ�ȣ"        , "13"  , "C" });
        fieldset.put("data13", new String[] { "�����̿�ý���"                          , "1"   , "C" });
        fieldset.put("data14", new String[] { "�������±���"                            , "1"   , "C" });// Q:��ó���, C:�Ѱ�����, R:���ೳ��, D:��ּ���
        fieldset.put("data15", new String[] { "�ſ�ī����ι�ȣ"                        , "12"  , "C" });
        fieldset.put("data16", new String[] { "�ſ�ī���Һΰ�����"                      , "2"   , "C" });// ����Ʈ ����: (60+ �Һΰ���)�� ���� �� ����Ʈ ��� �Ͻú�: 60���(61�� ������� ����)
        fieldset.put("data17", new String[] { "���⳻�ݾ�"                              , "12"  , "C" });
        fieldset.put("data18", new String[] { "�����ıݾ�"                              , "12"  , "C" });
        fieldset.put("data19", new String[] { "������(���⳻)"                          , "6"   , "C" });
        fieldset.put("data20", new String[] { "�󼼼���1�ݾ�"                           , "12"  , "C" });
        fieldset.put("data21", new String[] { "�󼼼���2�ݾ�"                           , "12"  , "C" });
        fieldset.put("data22", new String[] { "�󼼼���3�ݾ�"                           , "12"  , "C" });
        fieldset.put("data23", new String[] { "�󼼼���4�ݾ�"                           , "12"  , "C" });
        fieldset.put("data24", new String[] { "�󼼼���5�ݾ�"                           , "12"  , "C" });
        fieldset.put("data25", new String[] { "��ǥ���Կ���"                            , "1"   , "C" });
        fieldset.put("data26", new String[] { "�뷮���ΰ�����ȣ"                        , "19"  , "C" });
        fieldset.put("data27", new String[] { "������������ڵ�"                        , "7"   , "C" });
        fieldset.put("data28", new String[] { "â���ܸ���ȣ"                            , "20"  , "C" });// ��ֽ� ����(D)�� ��츸 ���
        fieldset.put("data29", new String[] { "FILLER"                                  , "24"  , "C" });

        initMsg("���漼-����������", 0, 300, fieldset);
    }

}
