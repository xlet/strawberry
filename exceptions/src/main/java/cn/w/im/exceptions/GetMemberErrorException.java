package cn.w.im.exceptions;

import cn.w.im.domains.messages.client.ProductType;

/**
 * get member error exception.
 */
public class GetMemberErrorException extends ServerInnerException {

    private String memberId;
    private ProductType productType;

    public GetMemberErrorException(String memberId, ProductType productType, Throwable throwable) {
        super("member:[" + memberId + "] of product[" + productType + "] not existed!", throwable);
        this.memberId = memberId;
        this.productType = productType;
    }

    public String getMemberId() {
        return memberId;
    }

    public ProductType getProductType() {
        return productType;
    }
}
