package cn.w.im.core.token;

import java.util.UUID;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午2:56.
 * Summary: implement TokenProvider.
 * <p/>
 * use UUID.
 */
public class DefaultTokenProvider implements TokenProvider {
    @Override
    public String create() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
