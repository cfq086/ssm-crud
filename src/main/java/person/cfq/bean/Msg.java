package person.cfq.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件名：Msg.java
 *
 * @author : cfq086
 * @date : 2020/9/1 23:40
 */
public class Msg {

    // 状态码  217-成功 100-失败
    private int code;

    // 提示信息
    private String msg;

    // 返回给浏览器的数据
    private Map<String,Object> extend = new HashMap<String, Object>();

    public static Msg success(String msgStr){
        Msg resulet = new Msg();
        resulet.setCode(217);
        resulet.setMsg("成功:"+msgStr);
        return resulet;
    }
    public static Msg success(){
        Msg resulet = new Msg();
        resulet.setCode(217);
        resulet.setMsg("成功");
        return resulet;
    }

    public static Msg fail(){
        Msg resulet = new Msg();
        resulet.setCode(100);
        resulet.setMsg("失败");
        return resulet;

    }
    public static Msg fail(String msgStr){
        Msg resulet = new Msg();
        resulet.setCode(100);
        resulet.setMsg("失败:"+msgStr);
        return resulet;

    }
    public Msg add(String key,Object value){
        this.getExtend().put(key, value);
        return this;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
