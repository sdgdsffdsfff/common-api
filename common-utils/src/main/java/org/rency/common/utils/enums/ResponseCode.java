package org.rency.common.utils.enums;

/**
 * 应答码
 * @author rencaiyu
 *
 */
public enum ResponseCode {

	SUCCESS("0000", "处理成功"),
    NO_QUERY_RESULT("0001", "无查询结果"),
    ARGUMENT_ERROR("0002", "参数错误"),
    SIGN_CHECK_FAILURE("0003", "参数签名错误"),
    TRANS_AUTH_FAILURE("0004", "接口权限错误"),
    OPERATION_FAILURE("0006", "处理失败"),
    EXCEED_COUNT_LIMIT("0007", "超过允许的最大个数"),
    DUPLICATE_RECORD("0008", "重复记录"),
    NO_EXIST_RECORD("0009", "不存在的记录"),
    ;

    /** 代码 */
    private final String code;
    /** 信息 */
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过代码获取枚举项
     * @param code
     * @return
     */
    public static ResponseCode getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (ResponseCode responseCode : ResponseCode.values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }
        return null;
    }

    public boolean equalsByCode(String code){
        return this.code.equals(code);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
	
    public boolean equals(String code) {
        return getCode().equals(code);
    }
}