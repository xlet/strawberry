package cn.w.im.web.services.impl;

import cn.w.im.web.services.TokenService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author jackie.
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String create(String fromId, String toId, String referrer) {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
