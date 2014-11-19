package cn.w.im.core.member.provider;

import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.ProductType;

/**
 * get member error exception.
 */
public class GetMemberErrorException extends ServerInnerException {

    private String memberId;
    private ProductType productType;

    public GetMemberErrorException(String memberId, ProductType productType, Throwable throwable) {
        super("get member:[" + memberId + "] of product[" + productType + "] error!", throwable);
        this.memberId = memberId;
        this.productType = productType;
    }

    public GetMemberErrorException(String memberId, ProductType productType) {
        super("not support! please try other method.");
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
