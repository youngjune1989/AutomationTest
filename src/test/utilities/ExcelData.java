package test.utilities;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import jxl.*;

public class ExcelData implements Iterator<Object[]> {
	private Workbook book = null;
	private Sheet sheet = null;
	private int rowNum = 0; // è¡Œæ•°
	private int curRowNo = 0; // å½“å‰�è¡Œæ•°
	private int columnNum = 0; // åˆ—æ•°
	private String[] columnnName; // åˆ—å��

	/**
	 * åœ¨TestNGä¸­ç”±@DataProvider(dataProvider = "name")ä¿®é¥°çš„æ–¹æ³•è¯»å�–Excelæ—¶ï¼Œ
	 * è°ƒç”¨æ­¤ç±»çš„æž„é€ æ–¹æ³•(æ­¤æ–¹æ³•ä¼šå¾—åˆ°åˆ—å��å¹¶å°†å½“å‰�è¡Œç§»åˆ°ä¸‹ä¸€è¡Œ)æ‰§è¡Œå®Œå�Žï¼Œ è½¬åˆ°TestNGè‡ªå·±çš„æ–¹æ³•ä¸­åŽ»ï¼Œç„¶å�Žç”±å®ƒä»¬è°ƒç”¨æ­¤ç±»å®žçŽ°çš„
	 * hasNext()ã€�next()æ–¹æ³• å¾—åˆ°ä¸€è¡Œæ•°æ�®ï¼Œç„¶å�Žè¿”å›žç»™ç”±@Test(dataProvider =
	 * "name")ä¿®é¥°çš„æ–¹æ³•ï¼Œå¦‚æ­¤å��å¤�åˆ°æ•°æ�®è¯»å®Œä¸ºæ­¢.
	 * 
	 * @param filepath
	 *            Excelæ–‡ä»¶å��
	 * @param casename
	 *            ç”¨ä¾‹å��
	 */
	public ExcelData(String filepath, String casename) {
		try {
			File directory = new File(".");
			String ss = ".";
			book = Workbook.getWorkbook(new File(directory.getCanonicalPath()
					+ "\\conf\\"
					+ ss.replaceAll("\\.", Matcher.quoteReplacement("\\"))
					+ filepath + ".xls"));
			this.sheet = book.getSheet(casename);
			this.rowNum = sheet.getRows(); // å¾—åˆ°è¡Œæ•°

			Cell[] c = sheet.getRow(0);
			this.columnNum = c.length; // å¾—åˆ°åˆ—æ•°
			columnnName = new String[c.length];
			for (int i = 0; i < c.length; i++) {
				columnnName[i] = c[i].getContents().toString();
			}
			this.curRowNo++; // å½“å‰�è¡Œæ•°

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getCellData(int row, int col) {
		
		Cell rc = sheet.getCell(row, col);
		String varName = rc.getContents();
		return varName;
		
	}

	@Override
	public boolean hasNext() {
		/**
		 * æ–¹æ³•åŠŸèƒ½ï¼šæ˜¯å�¦æœ‰ä¸‹ä¸€æ�¡æ•°æ�®. å¦‚æžœè¡Œæ•°ä¸º0å�³ç©ºsheet æˆ–è€… å½“å‰�è¡Œæ•°å¤§äºŽæ€»è¡Œæ•°
		 * å°±å…³é—­å¯¹excelçš„æ“�ä½œè¿”å›žfalseï¼Œå�¦åˆ™è¿”å›žtrue.
		 */
		if (this.rowNum == 0 || this.curRowNo >= this.rowNum) {
			try {
				book.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} else
			return true;
	}

	@Override
	public Object[] next() {
		/**
		 * æ–¹æ³•åŠŸèƒ½ï¼šå¾—åˆ°å¹¶è¿”å›žä¸‹ä¸€è¡Œæ•°æ�®.
		 * ä½¿ç”¨forå°†ä¸€è¡Œçš„æ•°æ�®æ”¾å…¥TreeMapä¸­(TreeMapé»˜è®¤æŒ‰ç…§keyå€¼å�‡åº�æŽ’åˆ—ï¼ŒHashMapæ²¡æœ‰æŽ’åº�)
		 * ç„¶å�Žå°†Mapè£…å…¥Object[]å¹¶è¿”å›žï¼Œä¸”å°†curRowNoå½“å‰�è¡Œä¸‹ç§».
		 */
		Cell[] c = sheet.getRow(this.curRowNo);
		Map<String, String> s = new TreeMap<String, String>();
		for (int i = 0; i < this.columnNum; i++) {
			String temp = "";
			try {
				temp = c[i].getContents().toString();
			} catch (ArrayIndexOutOfBoundsException ex) {
				temp = "";
			}
			s.put(this.columnnName[i], temp);
		}

		Object r[] = new Object[1];
		r[0] = s;
		this.curRowNo++;
		return r;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove unsupported.");
	}
	
}
