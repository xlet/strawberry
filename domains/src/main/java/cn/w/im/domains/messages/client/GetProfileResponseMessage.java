package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.messages.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-22 上午10:57
 * Summary:
 */
public class GetProfileResponseMessage extends ResponseMessage {

    private List<BasicMember> members = new ArrayList<BasicMember>();

    public GetProfileResponseMessage() {
        super(MessageType.GetProfileResponse);
    }

    public GetProfileResponseMessage(BasicMember... members) {
        this();
        for(BasicMember member:members){
            add(member);
        }
    }


    public GetProfileResponseMessage(List<BasicMember> members) {
        this();
        this.members = members;
    }

    public GetProfileResponseMessage add(BasicMember member){
        this.members.add(member);
        return this;
    }

    public List<BasicMember> getBasicMembers() {
        return members;
    }

    public void setBasicMembers(List<BasicMember> members) {
        this.members = members;
    }
}
