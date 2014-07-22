package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.basic.Member;
import cn.w.im.domains.messages.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-22 上午10:57
 * Summary:
 */
public class GetProfileResponseMessage extends ResponseMessage {

    private List<Member> members = new ArrayList<Member>();

    public GetProfileResponseMessage() {
        super(MessageType.GetProfileResponse);
    }

    public GetProfileResponseMessage(Member... members) {
        this();
        for(Member member:members){
            add(member);
        }
    }


    public GetProfileResponseMessage(List<Member> members) {
        this();
        this.members = members;
    }

    public GetProfileResponseMessage add(Member member){
        this.members.add(member);
        return this;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
