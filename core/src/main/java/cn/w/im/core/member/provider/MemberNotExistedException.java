package cn.w.im.core.member.provider;

import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.ProductType;

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
