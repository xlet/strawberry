package cn.w.im.core.message;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-10 下午2:40.
 * Summary: 回复消息.
 */
public abstract class ResponseMessage extends Message {

    private boolean success;

    private int errorCode;

    private String errorMessage;

    /**
     * 构造函数.
     *
     * @param messageType 消息类型.
     */
    public ResponseMessage(MessageType messageType) {
        super(messageType);
        this.success = true;
    }

    /**
     * error response.
     *
     * @param messageType  message type.
     * @param errorCode    error code.
     * @param errorMessage error message.
     */
    public ResponseMessage(MessageType messageType, int errorCode, String errorMessage) {
        super(messageType);
        this.success = false;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * 获取是否成功.
     *
     * @return true:成功.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置是否成功.
     *
     * @param success true:成功.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * get error code.
     *
     * @return error code.
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * set error code.
     *
     * @param errorCode error code.
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * get error message.
     *
     * @return error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * set error message.
     *
     * @param errorMessage error message.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
