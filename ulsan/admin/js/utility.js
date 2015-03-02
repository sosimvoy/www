/* select ��ü�� option ���� */
function addOption(parent, value, text) {
    try {
        var oOption = document.createElement("OPTION");
        oOption.value = value;
        oOption.text = text;
        parent.options.add(oOption);
    } catch (e) {
        alert("select ��ü���� Ȯ���ϼ���\n\n����: " + e.description);
    }
}

/* select ��ü�� ��� option ���� */
function deleteOptions(parent) {
    try {
        parent.options.length = 0;
    } catch (e) {
        alert("select ��ü���� Ȯ���ϼ���\n\n����: " + e.description);
    }
}

/* select ��ü�� option ����(value �� �˻�) */
function deleteOptionByValue(parent, value) {
    try {
        for( var i=0 ; i<parent.options.length ; i++ ) {
            var oOption = parent.options[i];
            if( oOption.value == value ) {
                parent.options.remove(i);
            }
        }
    } catch (e) {
        alert("select ��ü���� Ȯ���ϼ���\n\n����: " + e.description);
    }
}

/* select ��ü�� option ����(text �� �˻�) */
function deleteOptionByText(parent, text) {
    try {
        for( var i=0 ; i<parent.options.length ; i++ ) {
            var oOption = parent.options[i];
            if( oOption.text == text ) {
                parent.options.remove(i);
            }
        }
    } catch (e) {
        alert("select ��ü���� Ȯ���ϼ���\n\n����: " + e.description);
    }
}

/* select ��ü�� option ����(index �� �˻�) */
function deleteOptionByIndex(parent, index) {
    try {
        parent.options.remove(index);
    } catch (e) {
        alert("select ��ü���� Ȯ���ϼ���\n\n����: " + e.description);
    }
}
