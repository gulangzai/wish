package icom.yh.wish.util;


import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.lang.reflect.Field;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
/***
 */
public class ExportExcel {
 /***************************************************************************
  * @param fileName EXCEL�ļ�����
  * @param listTitle EXCEL�ļ���һ���б��⼯��
  * @param listContent EXCEL�ļ��������ݼ���
  * @return
  */
 public static String exportExcel(String fileName,String[] Title, List<?> listContent) {
  try {    
   WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
   WritableSheet sheet = workbook.createSheet("Sheet1", 0);
   jxl.SheetSettings sheetset = sheet.getSettings();
   sheetset.setProtected(false);


   WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
   WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);
   WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
   wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // ����
   wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // ���ִ�ֱ����
   wcf_center.setAlignment(Alignment.CENTRE); // ����ˮƽ����
   wcf_center.setWrap(false); // �����Ƿ���
   WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
   wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // ����
   wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // ���ִ�ֱ����
   wcf_left.setAlignment(Alignment.LEFT); // ����ˮƽ����
   wcf_left.setWrap(false); // �����Ƿ���   
 

   for (int i = 0; i < Title.length; i++) {
    sheet.addCell(new Label(i, 0,Title[i],wcf_center));
   }   
   Field[] fields=null;
   int i=1;
   for(Object obj:listContent){
	   fields=obj.getClass().getDeclaredFields();
	   int j=0;
	   for(Field v:fields){
		   v.setAccessible(true);
		   Object va=v.get(obj);
		   if(va==null){
			   va="";
		   }
		   sheet.addCell(new Label(j, i,va.toString(),wcf_left));
		   j++;
	   }
	   i++;
   }
   workbook.write();
   workbook.close();   

  } catch (Exception e) {
   e.printStackTrace();
  }
  return "";
 }

}