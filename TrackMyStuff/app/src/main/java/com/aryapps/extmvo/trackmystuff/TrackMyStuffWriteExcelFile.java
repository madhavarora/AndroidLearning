package com.aryapps.extmvo.trackmystuff;

import android.os.Environment;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TrackMyStuffWriteExcelFile {

	public  void writeExcel(List<TrackInfoBean> trackInfoBeanList) {
		try {
			
			File directory = null;
			File ExcelFile = null;
			if (!Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// handle case of no SDCARD present
			} else {
				directory = new File(Environment.getExternalStorageDirectory()
						+ File.separator + "TrackMyStuffData" // folder name
				); // file name
				directory.mkdirs();

				ExcelFile = new File(directory, "trackInfo.xls");
			}
			
			FileOutputStream fileOut = null;
			if (null != ExcelFile) {
				fileOut = new FileOutputStream(ExcelFile);
			}
			
			//FileOutputStream fileOut = new FileOutputStream("poi-test.xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("POI Worksheet");

			HSSFRow row0 = worksheet.createRow((short) 0);
			HSSFFont defaultFont= workbook.createFont();
		    defaultFont.setFontHeightInPoints((short)10);
		    defaultFont.setFontName("Arial");
		    defaultFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		    
			
			
			HSSFCell cellA0 = row0.createCell((short) 0);
			HSSFCellStyle cellStyleBold = cellA0.getCellStyle();
			cellStyleBold.setFont(defaultFont);
			cellA0.setCellValue("articleName");
			HSSFCell cellB0 = row0.createCell((short) 1);
			cellB0.setCellValue("Location");
			HSSFCell cellC0 = row0.createCell((short) 2);
			cellC0.setCellValue("Room");
			HSSFCell cellD0 = row0.createCell((short) 3);
			cellD0.setCellValue("Container");

			
			for(int i=1;i<=trackInfoBeanList.size();i++){
				
			
			HSSFRow row1 = worksheet.createRow((short) i);

			TrackInfoBean trackInfoBean = trackInfoBeanList.get(i-1);
			HSSFCell cellA1 = row1.createCell((short) 0);
			cellA1.setCellValue(trackInfoBean.getArticleName());
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			
			cellA1.setCellStyle(cellStyle);

			//HSSFCell cellB1 = row1.createCell((short) 1);
			//cellB1.setCellValue(trackInfoBean.getImage());
			//cellStyle = workbook.createCellStyle();
			//cellB1.setCellStyle(cellStyle);

			HSSFCell cellC1 = row1.createCell((short) 1);
			cellC1.setCellValue(trackInfoBean.getLocation());
			cellStyle = workbook.createCellStyle();
			cellC1.setCellStyle(cellStyle);

			HSSFCell cellD1 = row1.createCell((short) 2);
			cellD1.setCellValue(trackInfoBean.getRoom());
			cellStyle = workbook.createCellStyle();

			cellD1.setCellStyle(cellStyle);

            HSSFCell cellE1 = row1.createCell((short) 3);
            cellE1.setCellValue(trackInfoBean.getContainer());
            cellStyle = workbook.createCellStyle();

            cellE1.setCellStyle(cellStyle);
			}
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
