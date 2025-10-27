/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.contants.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum UserType {
    
    ADMIN(0, "Tài khoản admin"),
    SYSTEM(1, "Tài khoản cục tần số"),
    VIETTEL(2, "Tài khoản nhà mạng Viettel"),
    VINA(3, "Tài khoản nhà mạng Vinaphone"),
    MOBIFONE(4, "Tài khoản nhà mạng Mobifone"),
    PERMITTED_UNIT(5, "Tài khoản đơn vị được cấp phép");
    
    private final int value;
    
    private final String name;
    
    UserType(int value, String name) {
        this.value = value;
        this.name = name;
    }
    
    public static UserType fromValue(int value) {
        return Arrays.stream(UserType.values())
            .filter(method -> method.value == value)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unsupported user type: " + value));
    }
    
    public static int getValue(int value) {
        return fromValue(value).getValue();
    }
    
    public static String getName(int value) {
        return fromValue(value).getName();
    }
    
    private static final Map<Integer, UserType> VALUE_MAP =
            Arrays.stream(UserType.values())
                .filter(userType -> userType.value != 0)
                .collect(Collectors.toMap(UserType::getValue, Function.identity()));
    
    public static boolean exists(int value) {
        return VALUE_MAP.containsKey(value);
    }
}
