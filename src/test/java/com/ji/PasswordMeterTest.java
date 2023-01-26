package com.ji;

import org.assertj.core.api.Assertions;
import com.service.PasswordMeter;
import com.service.PasswordStrength;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordMeterTest {

    private PasswordMeter passwordMeter = new PasswordMeter();

    @DisplayName("null 입력이면 익셉션 발생")
    @Test
    void nullInput(){
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> passwordMeter.meter(null));
    }

    @DisplayName("null 입력이면 익셉션 발생")
    @Test
    void emptyInput(){
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> passwordMeter.meter(""));
    }

    @DisplayName("모든 조건을 충족하면 강함")
    @Test
    void meetAllRules(){
        assertPasswordStrength("abcABC123", PasswordStrength.STRONG);
        assertPasswordStrength("123abcABC", PasswordStrength.STRONG);
    }
    private void assertPasswordStrength(String password, PasswordStrength expected){
        PasswordStrength result = passwordMeter.meter(password);
        assertThat(result).isEqualTo(expected);
    }
    
    @DisplayName("길이가 8미만, 다른 조건은 충족")
    @Test
    void digitAndUppercase(){
        assertPasswordStrength("abcC123", PasswordStrength.NORMAL);
        assertPasswordStrength("123abcC", PasswordStrength.NORMAL);
        assertPasswordStrength("Cabc12", PasswordStrength.NORMAL);
    }

    @DisplayName("대문자 없음, 다른 조건 충족")
    @Test
    void digitAndLength(){
        assertPasswordStrength("abcd1234", PasswordStrength.NORMAL);
        assertPasswordStrength("1234abcdwefw", PasswordStrength.NORMAL);
    }

    @DisplayName("숫자 없음, 다른 조건 충족")
    @Test
    void uppercaseAndLength(){
        assertPasswordStrength("ABCDabcde", PasswordStrength.NORMAL);
        assertPasswordStrength("abcdeABCDEF", PasswordStrength.NORMAL);
    }

    @DisplayName("길이만 충족")
    @Test
    void length(){
        assertPasswordStrength("abcdehiohw", PasswordStrength.WEAK);
    }

    @DisplayName("길이만 충족")
    @Test
    void uppercase(){
        assertPasswordStrength("abcABC", PasswordStrength.WEAK);
    }

    @DisplayName("숫자만 충족")
    @Test
    void digit(){
        assertPasswordStrength("abc123", PasswordStrength.WEAK);
        assertPasswordStrength("123abc", PasswordStrength.WEAK);
    }

    @DisplayName("아무것도 충족하지 않음")
    @Test
    void nothing(){
        assertPasswordStrength("awef", PasswordStrength.WEAK);
    }

}
