package com.cxycds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leicheng on 2020/6/3.
 */
public class JSONParser {
    private static final char START_OBJECT = '{'; //开始大括号
    private static final char END_OBJECT = '}';//结束大括号
    private static final char START_ARRAY = '[';// 开始中括号
    private static final char END_ARRAY = ']';//开始小括号
    private static final char COMMA = ',';//逗号
    private static final char COLON = ':';//冒号
    private static final char SINGLE_QUOTE = '\'';//单引号
    private static final char DOUBLE_QUOTE = '\"';//双引号
    private static final char WHITESPACE = ' ';
    private static final String NULL = "null";
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    private int ignoreWhitespace(String text, int index) {
        while (text.charAt(index) == WHITESPACE) {
            index++;
        }
        return index;
    }

    private int matchSpecialChar(String text, int index, char c) {
        if (text.charAt(index) == c) {
            index++;
            return index;
        }
        return index;
    }

    private int extractKey(String text, int index, StringBuilder key) {
        while (text.charAt(index) != SINGLE_QUOTE) {
            key.append(text.charAt(index));
            index++;
        }
        return index;
    }


    private int parseObject2(String text, int index, HashMap hashMap) {
        index = ignoreWhitespace(text, index);
        index = matchSpecialChar(text, index, START_OBJECT);
        boolean isFirst = true;
        do {
            if (!isFirst) {
                index++;
            }
            isFirst = false;
            index = ignoreWhitespace(text, index);
            index = matchSpecialChar(text, index, SINGLE_QUOTE);
            StringBuilder key = new StringBuilder();
            index = extractKey(text, index, key);
            index = matchSpecialChar(text, index, SINGLE_QUOTE);
            index = ignoreWhitespace(text, index);
            index = matchSpecialChar(text, index, COLON);
            Token token = new Token();
            index = ignoreWhitespace(text, index);
            index = extractVal(text, index, token);
            hashMap.put(key.toString(), token.o);
            index = ignoreWhitespace(text, index);
        } while (text.charAt(index) == COMMA);
        index = matchSpecialChar(text, index, END_OBJECT);

        return index;
    }

    public static HashMap parseObject(String text) {
        JSONParser jsonParser = new JSONParser();
        HashMap hashMap = new HashMap();
        int index = jsonParser.parseObject2(text, 0, hashMap);
        if (index != text.length()) {
            throw new IllegalArgumentException(" not Match !");
        }
        return hashMap;
    }

    public static List parseList(String text) {
        JSONParser jsonParser = new JSONParser();
        List list = new ArrayList(5);
        int index = jsonParser.parseArray2(text, 0, list);
        if (index != text.length()) {
            throw new IllegalArgumentException(" not match array !");
        }
        return list;
    }

    private int extractVal(String text, int index, Token token) {
        if (text.charAt(index) == DOUBLE_QUOTE) {
            return extractStringToken(text, index, DOUBLE_QUOTE, token);
        } else if (text.charAt(index) == SINGLE_QUOTE) {
            return extractStringToken(text, index, SINGLE_QUOTE, token);
        } else if (Character.isDigit(text.charAt(index))) {
            return extractNumberToken(text, index, token);
        } else if (text.charAt(index) == START_OBJECT) {
            return extractObjectToken(text, index, token);
        } else if (text.charAt(index) == START_ARRAY) {
            return extractArrayToken(text, index, token);
        } else {
            int afterIndex = index;
            if ((afterIndex = extractKeywordToken(text, index, NULL)) > index) {
                token.set(Token.NULL, null);
                return afterIndex;
            } else if ((afterIndex = extractKeywordToken(text, index, TRUE)) > index) {
                token.set(Token.TRUE, true);
                return afterIndex;
            } else if ((afterIndex = extractKeywordToken(text, index, FALSE)) > index) {
                token.set(Token.FALSE, false);
                return afterIndex;
            }
        }
        return index;
    }

    private int extractKeywordToken(String text, int index, String keyword) {
        if (keyword.equalsIgnoreCase(text.substring(index, index + keyword.length()))) {
            index = index + keyword.length();
            return index;
        }
        return index;
    }

    private int extractArrayToken(String text, int index, Token token) {
        List list = new ArrayList();
        index = parseArray2(text, index, list);
        token.set(Token.ARRAY, list);
        return index;
    }

    private int parseArray2(String text, int index, List list) {
        index = ignoreWhitespace(text, index);
        index = matchSpecialChar(text, index, START_ARRAY);
        boolean isFirst = true;
        do {
            if (!isFirst) {
                index++;
            }
            isFirst = false;
            index = ignoreWhitespace(text, index);
            Token token = new Token();
            index = extractVal(text, index, token);
            index = ignoreWhitespace(text, index);
            list.add(token.o);
        } while (text.charAt(index) == COMMA);
        index = matchSpecialChar(text, index, END_ARRAY);
        return index;
    }

    private int extractObjectToken(String text, int index, Token token) {
        HashMap hashMap = new HashMap();
        index = parseObject2(text, index, hashMap);
        token.set(Token.OBJECT, hashMap);
        return index;
    }

    private int extractNumberToken(String text, int index, Token token) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(text.charAt(index));
        index++;
        while (Character.isDigit(text.charAt(index))) {
            stringBuilder.append(text.charAt(index));
            index++;
        }
        token.set(Token.NUMBER, Long.parseLong(stringBuilder.toString()));
        return index;
    }

    private int extractStringToken(String text, int index, char c, Token token) {
        index++;
        StringBuilder stringBuilder = new StringBuilder();
        while (text.charAt(index) != c) {
            stringBuilder.append(text.charAt(index));
            index++;
        }
        index++;
        token.set(Token.STRING, stringBuilder.toString());
        return index;
    }


}
