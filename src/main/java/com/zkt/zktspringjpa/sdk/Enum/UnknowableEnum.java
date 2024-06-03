package com.zkt.zktspringjpa.sdk.Enum;

public enum UnknowableEnum {
    unknown;

    public static UnknowableEnum _missing_(String value) {
        return UnknowableEnum.unknown;
    }
}