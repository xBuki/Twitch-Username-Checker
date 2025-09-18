package com.buki.twitchbot.functions.interfaces;

import java.math.BigInteger;

public interface ITextParser {
    int parseToInt(String text);

    BigInteger parseToBigInt(Object value);
}
