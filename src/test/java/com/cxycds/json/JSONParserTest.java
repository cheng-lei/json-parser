package com.cxycds.json;

import com.cxycds.json.JSONParser;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * Created by leicheng on 2020/10/14.
 */
public class JSONParserTest {
    @Test
    public void testParseJson() {
        HashMap hashMap = JSONParser.parseObject("{'name':'value','age':14,'man':false,'address':null,'children':{'boyName':'zhang','age':12,'man':true,'address2':null},'list':[['a','b','c'],['1','2','3']}");
        System.out.println(hashMap);
        List list = JSONParser.parseList("['aaaa','bbbb','cccc']");
        System.out.println(list);
    }
}
