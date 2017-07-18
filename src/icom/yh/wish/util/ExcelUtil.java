package icom.yh.wish.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @author : ����
 * @Date : 2015-4-27 ����9:13:21
 * @Comments : ���뵼��Excel������
 * @Version : 1.0.0
 */
public class ExcelUtil {

	/**
	 * ����excel2003
	 * 
	 * @param list
	 *            ���ݼ���
	 * @param fieldMap
	 *            ���Ӣ�����Ժ�Excel�е����������Ķ�Ӧ��ϵ
	 * @param sheetName
	 *            �����������
	 * @param out
	 *            ������
	 */
	public static <T> void listToExcel(List<T> list, LinkedHashMap<String, String> fieldMap, String sheetName,
			OutputStream out) throws Exception {
		if (list.size() == 0 || list == null) {
			throw new Exception("����Դ��û���κ�����");
		}
		int sheetSize = list.size();
		if (sheetSize > 65535 || sheetSize < 1) {
			sheetSize = 65535;
		}
		// ���������������͵�OutputStreamָ���ĵط�
		WritableWorkbook wwb;
		try {
			wwb = Workbook.createWorkbook(out);
			// 1.����һ���ж��ٸ�������
			int sheetNum = list.size() % sheetSize == 0 ? list.size() / sheetSize : (list.size() / sheetSize + 1);
			// 2.������Ӧ�Ĺ��������������������
			for (int i = 0; i < sheetNum; i++) {
				// ���ֻ��һ������������
				if (1 == sheetNum) {
					WritableSheet sheet = wwb.createSheet(sheetName, i);
					fillSheet(sheet, list, fieldMap, 0, list.size() - 1);
					// �ж������������
				} else {
					WritableSheet sheet = wwb.createSheet(sheetName + (i + 1), i);
					// ��ȡ��ʼ�����ͽ�������
					int firstIndex = i * sheetSize;
					int lastIndex = (i + 1) * sheetSize - 1 > list.size() - 1 ? list.size() - 1
							: (i + 1) * sheetSize - 1;
					// ��乤����
					fillSheet(sheet, list, fieldMap, firstIndex, lastIndex);
				}
			}
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <---------------------listתexcel---------------------> �����������
	 * 
	 * public static <T> void listToExcel(List<T> list,LinkedHashMap<String,
	 * String> fieldMap, String sheetName,HttpServletResponse response) {
	 * 
	 * // ����Ĭ���ļ���Ϊ��ǰʱ�䣺������ʱ���� String fileName = new
	 * SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString(); //
	 * ����responseͷ��Ϣ response.reset();
	 * response.setContentType("application/vnd.ms-excel"); // �ĳ����excel�ļ�
	 * response.setHeader("Content-disposition", "attachment; filename="+
	 * fileName + ".xls"); // ���������������͵������ try { OutputStream out =
	 * response.getOutputStream(); listToExcel(list, fieldMap, sheetName, out);
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */
	// ���ù������Զ��п�����мӴ�
	private static void setColumnAutoSize(WritableSheet ws, int extraWith) {
		// ��ȡ���е����Ԫ��Ŀ��
		for (int i = 0; i < ws.getColumns(); i++) {
			int colWith = 0;
			for (int j = 0; j < ws.getRows(); j++) {
				String content = ws.getCell(i, j).getContents().toString();
				int cellWith = content.length();
				if (colWith < cellWith) {
					colWith = cellWith;
				}
			}
			// ���õ�Ԫ��Ŀ��Ϊ�����+������
			ws.setColumnView(i, colWith + extraWith);
		}
	}

	/**
	 * @MethodName : excelToList
	 * @Description : ��Excelת��ΪList
	 * @param in
	 *            : ������
	 * @param sheetIndex
	 *            ��Ҫ����Ĺ��������
	 * @param entityClass
	 *            ��ʵ��class
	 * @param fieldMap
	 *            ��Excel�е�������ͷ�����Ӣ�����ԵĶ�Ӧ��ϵMap
	 */
	public static <T> List<T> excelToList(InputStream in, String sheetName, Class<T> entityClass,
			LinkedHashMap<String, String> fieldMap) {
		// ����Ҫ���ص�list
		List<T> resultList = new ArrayList<T>();
		try {
			// ����Excel����Դ����WorkBook
			Workbook wb = Workbook.getWorkbook(in);
			// ��ȡ������
			Sheet sheet = wb.getSheet(sheetName);
			// ��ȡ���������Ч����
			int realRows = 0;
			for (int i = 0; i < sheet.getRows(); i++) {

				int nullCols = 0;
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell currentCell = sheet.getCell(j, i);
					if (currentCell == null || "".equals(currentCell.getContents().toString())) {
						nullCols++;
					}
				}
				if (nullCols == sheet.getColumns()) {
					break;
				} else {
					realRows++;
				}
			}
			// ���Excel��û����������ʾ����
			if (realRows <= 1) {
				throw new Exception("Excel�ļ���û���κ�����");
			}
			Cell[] firstRow = sheet.getRow(0);
			String[] excelFieldNames = new String[firstRow.length];
			// ��ȡExcel�е�����
			for (int i = 0; i < firstRow.length; i++) {
				excelFieldNames[i] = firstRow[i].getContents().toString().trim();
			}
			// �ж���Ҫ���ֶ���Excel���Ƿ񶼴���
			boolean isExist = true;
			List<String> excelFieldList = Arrays.asList(excelFieldNames);
			for (String cnName : fieldMap.keySet()) {
				if (!excelFieldList.contains(cnName)) {
					isExist = false;
					break;
				}
			}
			// ��������������ڣ����׳��쳣����ʾ����
			if (!isExist) {
				throw new Exception("Excel��ȱ�ٱ�Ҫ���ֶΣ����ֶ���������");
			}
			// ���������кŷ���Map��,����ͨ�������Ϳ����õ��к�
			LinkedHashMap<String, Integer> colMap = new LinkedHashMap<String, Integer>();
			for (int i = 0; i < excelFieldNames.length; i++) {
				colMap.put(excelFieldNames[i], firstRow[i].getColumn());
			}
			// ��sheetת��Ϊlist
			for (int i = 1; i < realRows; i++) {
				// �½�Ҫת���Ķ���
				T entity = entityClass.newInstance();
				// �������е��ֶθ�ֵ
				for (Entry<String, String> entry : fieldMap.entrySet()) {
					// ��ȡ�����ֶ���
					String cnNormalName = entry.getKey();
					// ��ȡӢ���ֶ���
					String enNormalName = entry.getValue();
					// ���������ֶ�����ȡ�к�
					int col = colMap.get(cnNormalName);
					// ��ȡ��ǰ��Ԫ���е�����
					String content = sheet.getCell(col, i).getContents().toString().trim();
					ReflectUtil.setProperty(entity, enNormalName, content);
				}
				resultList.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	// ���������������
	private static <T> void fillSheet(WritableSheet sheet, List<T> list, LinkedHashMap<String, String> fieldMap,
			int firstIndex, int lastIndex) throws Exception {

		// ������Ӣ���ֶ����������ֶ���������
		String[] enFields = new String[fieldMap.size()];
		String[] cnFields = new String[fieldMap.size()];

		// �������
		int count = 0;
		for (Entry<String, String> entry : fieldMap.entrySet()) {
			enFields[count] = entry.getKey();
			cnFields[count] = entry.getValue();
			count++;
		}
		// ����ͷ
		for (int i = 0; i < cnFields.length; i++) {
			Label label = new Label(i, 0, cnFields[i]);
			sheet.addCell(label);
		}
		// �������
		int rowNo = 1;
		for (int index = firstIndex; index <= lastIndex; index++) {
			// ��ȡ��������
			T item = list.get(index);
			for (int i = 0; i < enFields.length; i++) {
				Object objValue = null;
				objValue = ReflectUtil.getNestedProperty(item, enFields[i]);
				String fieldValue = objValue == null ? "" : objValue.toString();
				Label label = new Label(i, rowNo, fieldValue);
				sheet.addCell(label);
			}
			rowNo++;
		}
		// �����Զ��п�
		setColumnAutoSize(sheet, 5);
	}

	public static List<String> excelToList(InputStream in) {
		List<String> list = new ArrayList<>();
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(in);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ��ȡ������
		Sheet sheet = wb.getSheet("Sheet1");
		for (int i = 1; i < sheet.getRows(); i++) {
			String ID = sheet.getCell(0, i).getContents();
			list.add(ID);
		}
		return list;
	}
}