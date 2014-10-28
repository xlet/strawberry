package cn.w.im.exceptions;

import cn.w.im.domains.messages.client.ProductType;

/**
 * member not existed exception.
 */
public class MemberNotExistedException extends ServerInnerException {

    private String memberId;
    private ProductType productType;

    public MemberNotExistedException(String memberId, ProductType productType) {
        super("member:[" + memberId + "] of product[" + productType + "] not existed!");
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
