package cn.w.im.domains;

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
     * default constructor.
     */
    protected OtherServerBasic() {

    }

    /**
     * constructor.
     *
     * @param serverBasic server basic.
     * @param sourceType  source type.
     */
    public OtherServerBasic(ServerBasic serverBasic, SourceType sourceType) {
        this.setStart(serverBasic.isStart());
        this.setHost(serverBasic.getHost());
        this.setNodeId(serverBasic.getNodeId());
        this.setPort(serverBasic.getPort());
        this.setStartDateTime(serverBasic.getStartDateTime());
        this.sourceType = sourceType;
    }
}
