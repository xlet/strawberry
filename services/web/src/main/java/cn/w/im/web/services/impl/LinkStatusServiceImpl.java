package cn.w.im.web.services.impl;

import cn.w.im.web.exceptions.IllegalMemberException;
import cn.w.im.web.services.LinkStatusService;
import cn.w.im.web.services.MemberService;
import cn.w.im.web.services.TokenService;
import cn.w.im.web.vo.LinkmanViewObject;
import cn.w.im.web.vo.response.GetTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author jackie.
 */
@Service
public class LinkStatusServiceImpl implements LinkStatusService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenService tokenService;

    @Override
    public GetTokenResponse create(String fromId, String toId, String ip, String referrer, String requestURI, String agent) {
        String realFromId = fromId;
        if (!StringUtils.isEmpty(realFromId) && !memberService.existed(fromId, referrer)) {
            throw new IllegalMemberException(fromId);
        }
        if (!StringUtils.isEmpty(toId) && !memberService.existed(toId, referrer)) {
            throw new IllegalMemberException(toId);
        }

        String token = tokenService.create(realFromId, toId, referrer);
        GetTokenResponse getTokenResponse = new GetTokenResponse();
        getTokenResponse.setSuccess(true);
        getTokenResponse.setToken(token);
        getTokenResponse.setHasCreateTempId(false);
        if (StringUtils.isEmpty(realFromId)) {
            LinkmanViewObject tempMember = memberService.createTemp(referrer);
            getTokenResponse.setHasCreateTempId(true);
            getTokenResponse.setTempId(tempMember.getId());
            getTokenResponse.setTempName(tempMember.getName());
            realFromId = tempMember.getId();
        }
        memberService.online(realFromId, referrer);
        return getTokenResponse;
    }
}
