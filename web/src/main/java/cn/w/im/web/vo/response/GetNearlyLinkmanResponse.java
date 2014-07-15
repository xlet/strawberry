package cn.w.im.web.vo.response;

import cn.w.im.web.vo.LinkmanViewObject;

import java.util.ArrayList;
import java.util.List;

/**
 * get nearly linkman response.
 */
public class GetNearlyLinkmanResponse extends BaseResponse {

    private List<LinkmanViewObject> linkmans;

    /**
     * constructor.
     */
    public GetNearlyLinkmanResponse(){
        linkmans = new ArrayList<LinkmanViewObject>();
    }

    /**
     * get linkmans.
     * @return
     */
    public List<LinkmanViewObject> getLinkmans(){
        return this.linkmans;
    }

    /**
     * set linkmans.
     * @param linkmans
     */
    public void setLinkmans(List<LinkmanViewObject> linkmans){
        this.linkmans = linkmans;
    }
}
