package com.buki.twitchbot.functions.interfacesimpl;

import com.buki.twitchbot.functions.interfaces.ITextParser;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component
public class TextParserImpl implements ITextParser {
    @Override
    public int parseToInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public BigInteger parseToBigInt(Object value) {
        BigInteger ret = null;
        try {
            if (value instanceof BigInteger) {
                ret = (BigInteger) value;
            } else if (value instanceof String) {
                ret = new BigInteger(value.toString().trim());
            } else if (value instanceof BigDecimal) {
                ret = ((BigDecimal) value).toBigInteger();
            } else if (value instanceof Number) {
                ret = BigInteger.valueOf(((Number) value).longValue());
            }
        } catch (NumberFormatException ignore) { }
        return ret;
    }
}