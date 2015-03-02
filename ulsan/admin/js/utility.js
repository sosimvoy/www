/* select 객체의 option 생성 */
function addOption(parent, value, text) {
    try {
        var oOption = document.createElement("OPTION");
        oOption.value = value;
        oOption.text = text;
        parent.options.add(oOption);
    } catch (e) {
        alert("select 객체인지 확인하세요\n\n내용: " + e.description);
    }
}

/* select 객체의 모든 option 삭제 */
function deleteOptions(parent) {
    try {
        parent.options.length = 0;
    } catch (e) {
        alert("select 객체인지 확인하세요\n\n내용: " + e.description);
    }
}

/* select 객체의 option 삭제(value 로 검색) */
function deleteOptionByValue(parent, value) {
    try {
        for( var i=0 ; i<parent.options.length ; i++ ) {
            var oOption = parent.options[i];
            if( oOption.value == value ) {
                parent.options.remove(i);
            }
        }
    } catch (e) {
        alert("select 객체인지 확인하세요\n\n내용: " + e.description);
    }
}

/* select 객체의 option 삭제(text 로 검색) */
function deleteOptionByText(parent, text) {
    try {
        for( var i=0 ; i<parent.options.length ; i++ ) {
            var oOption = parent.options[i];
            if( oOption.text == text ) {
                parent.options.remove(i);
            }
        }
    } catch (e) {
        alert("select 객체인지 확인하세요\n\n내용: " + e.description);
    }
}

/* select 객체의 option 삭제(index 로 검색) */
function deleteOptionByIndex(parent, index) {
    try {
        parent.options.remove(index);
    } catch (e) {
        alert("select 객체인지 확인하세요\n\n내용: " + e.description);
    }
}
