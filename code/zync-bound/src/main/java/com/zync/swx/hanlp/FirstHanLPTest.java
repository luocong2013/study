package com.zync.swx.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;

public class FirstHanLPTest {

	public static void main(String[] args) {
		HanLP.Config.ShowTermNature = true;
		String str = "你好，欢迎使用HanLP汉语处理包！";
		List<Term> terms = HanLP.segment(str);
		System.out.println(terms);
		System.out.println(HanLP.s2t(str));
		System.out.println(HanLP.s2hk(str));
		System.out.println(HanLP.s2tw(str));
		String str2 = "你好，歡迎使用HanLP漢語處理包！";
		System.out.println(HanLP.segment(str2));

	}

}
