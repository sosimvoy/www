/* textarea ���ڼ� üũ  */
function fc_chk_byte(aro_name,ari_max)
    {

    var ls_str     = aro_name.value; // �̺�Ʈ�� �Ͼ ��Ʈ���� value ��
    var li_str_len = ls_str.length;  // ��ü����
    
    // �����ʱ�ȭ
    var li_max      = ari_max; // ������ ���ڼ� ũ��
    var i           = 0;  // for���� ���
    var li_byte     = 0;  // �ѱ��ϰ��� 2 �׹ܿ��� 1�� ����
    var li_len      = 0;  // substring�ϱ� ���ؼ� ���
    var ls_one_char = ""; // �ѱ��ھ� �˻��Ѵ�
    var ls_str2     = ""; // ���ڼ��� �ʰ��ϸ� �����Ҽ� ������������ �����ش�.
    
    for(i=0; i< li_str_len; i++)
    {
        // �ѱ�������
        ls_one_char = ls_str.charAt(i);
        
        // �ѱ��̸� 2�� ���Ѵ�.
        if (escape(ls_one_char).length > 4)
        {
            li_byte += 2;
        }
        // �׹��� ���� 1�� ���Ѵ�.
        else
        {
            li_byte++;
        }
        
        // ��ü ũ�Ⱑ li_max�� ����������
        if(li_byte <= li_max)
        {
            li_len = i + 1;
        }
    }
    // ��ü���̸� �ʰ��ϸ�
    if(li_byte > li_max)
    {
        alert( li_max + " ���ڸ� �ʰ� �Է��Ҽ� �����ϴ�.");
        aro_name.focus(); 
        return false;
    }
    return true;

}