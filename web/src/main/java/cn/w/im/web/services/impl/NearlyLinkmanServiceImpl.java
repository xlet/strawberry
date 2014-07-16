package cn.w.im.web.services.impl;

import cn.w.im.domains.basic.NearlyLinkman;
import cn.w.im.persistent.NearlyLinkmanDao;
import cn.w.im.web.services.MemberService;
import cn.w.im.web.services.NearlyLinkmanService;
import cn.w.im.web.vo.LinkmanViewObject;
import cn.w.im.web.vo.MemberViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * implement {@link cn.w.im.web.services.NearlyLinkmanService}.
 */
@Service
public class NearlyLinkmanServiceImpl implements NearlyLinkmanService {

    @Autowired
    private NearlyLinkmanDao nearlyLinkmanDao;

    @Autowired
    private MemberService memberService;

    @Override
    public List<LinkmanViewObject> get(String from, String referrer, int pageSize, int pageIndex) {
        List<LinkmanViewObject> linkmanViewObjects = new ArrayList<LinkmanViewObject>();
        List<NearlyLinkman> nearlyLinkmen = nearlyLinkmanDao.get(from, pageSize, pageIndex);
        for (NearlyLinkman nearlyLinkman : nearlyLinkmen) {
            String linkmanId = getLinkmanId(nearlyLinkman, from);
            LinkmanViewObject linkmanViewObject = new LinkmanViewObject();
            linkmanViewObject.setId(linkmanId);
            MemberViewObject memberViewObject = memberService.get(linkmanId, referrer);
            linkmanViewObject.setName(memberViewObject.getId());
            linkmanViewObject.setStatus(memberViewObject.getStatus());
            linkmanViewObjects.add(linkmanViewObject);
        }
        return linkmanViewObjects;
    }

    private String getLinkmanId(NearlyLinkman nearlyLinkman, String from) {
        if (nearlyLinkman.getMemberId1().equals(from)) {
            return nearlyLinkman.getMemberId2();
        }
        return nearlyLinkman.getMemberId1();
    }
}
