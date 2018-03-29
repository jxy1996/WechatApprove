package ysg.weixinutils.request;
/** 
 * 图片消息 
 *  
 * @date 2013-09-10 
 */  
public class ImageMessage extends BaseMessage {  
    // 图片链接  
    private String PicUrl;  
  
    public String getPicUrl() {  
        return PicUrl;  
    }  
  
    public void setPicUrl(String picUrl) {  
        PicUrl = picUrl;  
    } 
}