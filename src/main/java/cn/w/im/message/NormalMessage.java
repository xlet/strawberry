package cn.w.im.message;

/**
 * Creator: JackieHan
 * DateTime: 13-12-24 下午2:21
 */
public class NormalMessage extends Message{

    private String from,to,content;

    public NormalMessage(){
        super(MessageType.Normal);
    }

    public NormalMessage(String from,String to,String content){
        this();
        this.from=from;
        this.to=to;
        this.content=content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
