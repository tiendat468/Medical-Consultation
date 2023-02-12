package com.kltn.medical_consultation.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.SecureRandom;
import java.util.regex.Pattern;


public class ICheckBCryptPasswordEncoder
{
    private Pattern BCRYPT_PATTERN;
    private Pattern BCRYPT_PATTERN_2;
    private Pattern BCRYPT_PATTERN_3;
    private final Log logger;
    private final int strength;
    private final SecureRandom random;

    public ICheckBCryptPasswordEncoder() {
        this(-1);
    }

    public ICheckBCryptPasswordEncoder(int strength) {
        this(strength, (SecureRandom)null);
    }

    public ICheckBCryptPasswordEncoder(int strength, SecureRandom random) {
        this.BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        this.BCRYPT_PATTERN_2 = Pattern.compile("\\A\\$2b?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        this.BCRYPT_PATTERN_3 = Pattern.compile("\\A\\$2y?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        this.logger = LogFactory.getLog(this.getClass());
        if (strength == -1 || strength >= 4 && strength <= 31) {
            this.strength = strength;
            this.random = random;
        } else {
            throw new IllegalArgumentException("Bad strength");
        }
    }

    public String encode(CharSequence rawPassword) {
        String salt;
        if (this.strength > 0) {
            if (this.random != null) {
                salt = IcheckBcrypt.gensalt(this.strength, this.random);
            } else {
                salt = IcheckBcrypt.gensalt(this.strength);
            }
        } else {
            salt = IcheckBcrypt.gensalt();
        }

        return IcheckBcrypt.hashpw(rawPassword.toString(), salt);
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            if (!this.BCRYPT_PATTERN.matcher(encodedPassword).matches()
                    && !this.BCRYPT_PATTERN_2.matcher(encodedPassword).matches()
                    && !this.BCRYPT_PATTERN_3.matcher(encodedPassword).matches()
            ) {
                this.logger.warn("Encoded password does not look like BCrypt");
                return false;
            } else {
                return IcheckBcrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        } else {
            this.logger.warn("Empty encoded password");
            return false;
        }
    }

}
