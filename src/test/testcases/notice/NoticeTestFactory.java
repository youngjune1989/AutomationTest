package test.testcases.notice;

import org.testng.annotations.Factory;

public class NoticeTestFactory {
	@Factory
	public Object[] createInstances(){
		Object[] result = new Object[3];
		String[] para = {
				"/src/resources/notice/notice_para_01.xml",
				"/src/resources/notice/notice_para_02.xml",
				"/src/resources/notice/notice_para_03.xml"
				};
		String url = "http://192.168.30.170:8887"; 	
		for (int i = 0; i < para.length; i++) {
			result[i] = new NoticeTest01(para[i],url);
		}
		return result;
	}

}
