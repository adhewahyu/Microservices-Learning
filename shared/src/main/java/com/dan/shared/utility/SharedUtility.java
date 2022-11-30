package com.dan.shared.utility;

import com.dan.shared.enums.RegexType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class SharedUtility {

    public String getRandomUUID(){
        return UUID.randomUUID().toString();
    }

    public Boolean isValidRegex(String input, String regexPattern, String type){
        if(StringUtils.isEmpty(input)){
            log.error("SharedUtility - validateRegex - Input is required");
            throw new UnsupportedOperationException("Input is required");
        }
        if(type.equals(RegexType.ANY.getValue()) && StringUtils.isEmpty(regexPattern)){
            log.error("SharedUtility - validateRegex - Regex Pattern is required");
            throw new UnsupportedOperationException("Regex Pattern is required");
        }

        if(type.equals(RegexType.ANY.getValue()) && StringUtils.isNotEmpty(regexPattern)){
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(input);
            return matcher.matches();
        }

        String defaultRegexPattern = null;
        if(type.equals(RegexType.NUMERIC_ONLY.getValue())){
            defaultRegexPattern = "[0-9]+";
        }
        if(type.equals(RegexType.ALPHANUMERIC.getValue())){
            defaultRegexPattern = "^[a-zA-Z0-9]$";
        }
        if(type.equals(RegexType.EMAIL.getValue())){
            defaultRegexPattern = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-z0-9_]*\\.[a-z][a-z]{2,6}$";
        }
        if(type.equals(RegexType.ALPHABET_ONLY.getValue())){
            defaultRegexPattern = "^[a-zA-Z]$";
        }
        Pattern pattern = Pattern.compile(StringUtils.isEmpty(regexPattern) ?
                Objects.requireNonNull(defaultRegexPattern) : regexPattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public Long getDateOrReturnNull(Date inputDate){
        return ObjectUtils.isEmpty(inputDate) ? null : inputDate.getTime();
    }

}
