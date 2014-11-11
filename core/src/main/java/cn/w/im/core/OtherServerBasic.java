package cn.w.im.core;

import cn.w.im.core.server.ServerBasic;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-19 下午8:00.
 * Summary: other started server basic.
 */
public class OtherServerBasic extends ServerBasic {

    private SourceType sourceType;

    /**
     * get source type.
     *
     * @return source type.
     */
    public SourceType getSourceType() {
        return sourceType;
    }

    /**
     * constructor.
     *
     * @param serverBasic server basic.
     * @param sourceType  source type.
     */
    public OtherServerBasic(ServerBasic serverBasic, SourceType sourceType) {
        super(serverBasic.getServerType(), serverBasic.getHost(), serverBasic.getPort());
        this.setStart(serverBasic.isStart());
        this.setStartDateTime(serverBasic.getStartDateTime());
        this.sourceType = sourceType;
    }
}
