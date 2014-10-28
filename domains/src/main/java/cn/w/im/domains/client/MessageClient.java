package cn.w.im.domains.client;

import cn.w.im.domains.messages.client.ProductType;
import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-17 下午3:59.
 * Summary: message client.
 */
public class MessageClient extends Client {

    private ProductType productType;

    private String memberId;

    private MessageClientType messageClientType;

    /**
     * get member id.
     *
     * @return member id.
     */
    public String getMemberId() {
        return this.memberId;
    }

    /**
     * get client type.
     *
     * @return client type.
     */
    public MessageClientType getMessageClientType() {
        return this.messageClientType;
    }

    /**
     * get product type.
     *
     * @return product type.
     */
    public ProductType getProductType() {
        return this.productType;
    }

    /**
     * constructor.
     *
     * @param context           current channel context.
     * @param messageClientType client type.
     * @param productType       product type.
     * @param memberId          member id.
     */
    public MessageClient(ChannelHandlerContext context, ProductType productType,
                         MessageClientType messageClientType, String memberId) {
        super(context);
        this.productType = productType;
        this.messageClientType = messageClientType;
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "MessageClient{" +
                "memberId='" + memberId + '\'' +
                ", messageClientType=" + messageClientType +
                ", key=" + super.toString() +
                '}';
    }
}
