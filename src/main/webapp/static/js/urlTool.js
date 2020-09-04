function request_par(paras) {
    var url = location.href;
    // 获取所有参数、形式"key=value" 是数组形式
    var paramStr = url.substring(url.indexOf('?') + 1, url.length).split("&");

    //转换成键值对的形式
    var paramObj = {};
    for (var i = 0; i<paramStr.length; i++) {
        j = paramStr[i];
        var tmp = j.split("=");     // 再次分割字串为数组，长度2
        if (tmp.length==2){
            // 键值对添加元素
            paramObj[tmp[0]]=tmp[1];
        }
    }
    console.log(paramObj);

    if (paramObj!=null&&paramObj[paras]!=undefined&&paramObj[paras]!="undefined"&&paramObj[paras]!=""){
        return paramObj[paras];
    }
    return "";
}