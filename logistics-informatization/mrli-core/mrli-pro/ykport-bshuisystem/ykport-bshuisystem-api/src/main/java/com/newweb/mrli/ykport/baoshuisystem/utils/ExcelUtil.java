package com.newweb.mrli.ykport.baoshuisystem.utils;

import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
public class ExcelUtil {
	private static final String XLS = "^.+\\.(?i)(xls)$";
	private static final String XLSX = "^.+\\.(?i)(xlsx)$";

	/**基于注解导入和导出*/
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ java.lang.annotation.ElementType.FIELD })
	public @interface ExcelAttribute {

		/** Excel中的导出列名*/
		String name() default "";

		/**导出列名对应的A,B,C,D...,不指定按照默认顺序排序  */
		String column() default "";

		/**导出提示信息*/
		String prompt() default "";

		/**设置只能选择不能输入的列内容*/
		String[] combo() default {};

		/**是否导出数据*/
		boolean isExport() default true;

		/** 是否为重要字段（整列标红,着重显示）*/
		boolean isMark() default false;

		/**是否合计当前列 */
		boolean isSum() default false;
	}
	/**
	 * 将list数据源的数据导入到excel表单
	 *
	 * @param list      数据源
	 * @param sheetName 工作表的名称
	 * @param output    java输出流
	 */
	public static <T> boolean getListToExcel(@NonNull Class<T> clazz,@NonNull List<T> list,@NonNull String sheetName,@NonNull OutputStream output) {
		try {
			// excel中每个sheet中最多有65536行
			int sheetSize = 65536;
			// 得到所有定义字段
			Field[] allFields = clazz.getDeclaredFields();
			List<Field> fields = new ArrayList<>();
			// 得到所有field并存放到一个list中
			for (Field field : allFields) {
				if (field.isAnnotationPresent(ExcelAttribute.class)) {
					fields.add(field);
				}
			}
			// 产生工作薄对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 取出一共有多少个sheet
			int listSize = 0;
			if (list != null) {
				listSize = list.size();
			}
			int sheetNo = ((listSize % sheetSize) == 0) ? listSize / sheetSize : (listSize / sheetSize + 1);
			for (int index = 0; index <= sheetNo; index++) {
				createSheet(list, sheetName, sheetSize, fields, workbook,  index);
			}
			output.flush();
			workbook.write(output);
			return Boolean.TRUE;
		} catch (Exception e) {
			throw new RuntimeException("将list数据源的数据导入到excel表单时异常!", e);
		}finally {
			IOUtils.closeQuietly(output);
		}
	}
	/**
	 * 将excel表单数据源的数据导入到list
	 *
	 * @param sheetName 工作表的名称
	 * @param file     输入
	 * @param clazz     要转换的对象类型
	 */
	@NonNull
	public static <T> List<T> getExcelToList(@Nullable String sheetName,@NonNull MultipartFile file,@NonNull Class<T> clazz,int fistGet,int lastGet,int titleRow) {
		List<T> list = new ArrayList<>();
		try {
			String fileName = file.getOriginalFilename();
			if (!checkExcel(fileName)) {
				throw new Exception("请上传正确的Excel格式");
			}
			Workbook book;
			try (InputStream input = file.getInputStream()) {
				if (fileName.matches(XLS)) {
					book = new HSSFWorkbook(input);
				} else {
					book = new XSSFWorkbook(input);
				}
			}
			Sheet sheet = null;
			// 如果指定sheet名,则取指定sheet中的内容.
			if (StringUtils.isNotBlank(sheetName)) {
				sheet = book.getSheet(sheetName);
			}
			// 如果传入的sheet名不存在则默认指向第1个sheet.
			if (sheet == null) {
				sheet = book.getSheetAt(0);
			}
			// 得到数据的行数
			int rows = sheet.getLastRowNum();
			// 有数据时才处理
			if (rows > 0) {
				// 得到类的所有field
				Field[] allFields = clazz.getDeclaredFields();
				// 定义一个map用于存放列的序号和field
				Map<String, Field> fieldsMap = new HashMap<>(32);
				for (int i = 0, index = 0; i < allFields.length; i++) {
					Field field = allFields[i];
					// 将有注解的field存放到map中
					if (field.isAnnotationPresent(ExcelAttribute.class)) {
						// 设置类的私有字段属性可访问
						field.setAccessible(true);
						ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
						fieldsMap.put(attr.name(), field);
						index++;
					}
				}
				//列数
				int num = sheet.getRow(0).getLastCellNum();
				// 从第fistGet行开始取数据,取到第lastGet行，默认第一行是表头
				for (int i = fistGet; i <= lastGet; i++) {
					// 得到一行中的所有单元格对象.
					Row row = sheet.getRow(i);
					T entity = null;

					Row rowTitle = sheet.getRow(titleRow);

					for (int a = 0; a < num; a++) {
						Cell next = row.getCell(a);
						if (next == null) {
							//index++;
							continue;
						}
						//单元格格式
						next.setCellType(CellType.STRING);
						String c = next.getStringCellValue();
						if (!StringUtils.isNotBlank(c)) {
							//index++;
							continue;
						}
						if (c.contains("合计：")) {
							continue;
						}
						// 如果不存在实例则新建
						entity = (entity == null ? clazz.newInstance() : entity);
						// 从map中得到对应列的field
						Field field;
						if(rowTitle.getCell(a).getStringCellValue().indexOf("净重")!=-1){
							if(rowTitle.getCell(a).getStringCellValue().indexOf("净重总和")!=-1){
								field = fieldsMap.get("净重总和");
							}else{
								field = fieldsMap.get("净重");
							}

						} else if(rowTitle.getCell(a).getStringCellValue().indexOf("单价")!=-1){
							field = fieldsMap.get("单价");
						} else if(rowTitle.getCell(a).getStringCellValue().indexOf("总价")!=-1){
							field = fieldsMap.get("总价");
						} else if(rowTitle.getCell(a).getStringCellValue().indexOf("毛重")!=-1){
							field = fieldsMap.get("毛重");
						} else if(rowTitle.getCell(a).getStringCellValue().indexOf("进口国别")!=-1){
							field = fieldsMap.get("原产国");
						} else if(rowTitle.getCell(a).getStringCellValue().indexOf("桶数")!=-1){
							field = fieldsMap.get("件数/桶数");
						} else if(rowTitle.getCell(a).getStringCellValue().indexOf("原进区报关单号")!=-1){
							field = fieldsMap.get("进区报关单号");
						} else if(rowTitle.getCell(a).getStringCellValue().indexOf("原进区核注清单号")!=-1){
							field = fieldsMap.get("进区核注清单号");
						} else if(rowTitle.getCell(a).getStringCellValue().indexOf("业务类型")!=-1){
							if(sheetName.indexOf("仓库货物")!=-1){
								field = fieldsMap.get("业务类型C");
							} else{
								field = fieldsMap.get("业务类型W");
							}
						} else{
							field = fieldsMap.get(rowTitle.getCell(a).getStringCellValue());
						}


						//index++;
						if (field == null) {
							continue;
						}
						// 取得类型,并根据对象类型设置值.
						if (!setValue(entity, c, field)){
							continue;
						}

					}
					if (entity != null) {
						list.add(entity);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			List<T> failList = new ArrayList<>();
			InoutYardBody failMap = new InoutYardBody();
			failMap.setMarkTxt("导入异常:"+e.getMessage());
			failList.add((T) failMap);
			return failList;
//			throw new RuntimeException("Excel导入异常!", e.getCause());
		}
		return list;
	}

	/**
	 * 判断是否是excel
	 *
	 * @param fileName 文件名
	 * @return 获取结果
	 */
	private static boolean checkExcel(String fileName ) {
		return fileName != null && (fileName.matches(XLS) || fileName.matches(XLSX));
	}
	/**
	 * 设置值配置
	 * @param entity 当前实体
	 * @param c excel当前取值
	 * @param field 实体当前的域对象
	 * @return 是否成功
	 * @throws IllegalAccessException
	 * @throws ParseException
	 */
	private static <T> boolean  setValue(T entity, String c, Field field) throws IllegalAccessException, ParseException {
		Class<?> fieldType = field.getType();
		if (fieldType == null) {
			return false;
		}
		if (String.class == fieldType) {
			field.set(entity, String.valueOf(c));
		} else if (BigDecimal.class == fieldType) {
			c = c.contains("%") ? c.replace("%", "") : c;
			field.set(entity, new BigDecimal(c));
		} else if (Date.class == fieldType) {
			field.set(entity, DateUtils.parseDate(c));
		} else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
			field.set(entity, Integer.parseInt(c));
		} else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
			field.set(entity, Long.valueOf(c));
		} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
			field.set(entity, Float.valueOf(c));
		} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
			field.set(entity, Short.valueOf(c));
		} else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
			field.set(entity, Double.valueOf(c));
		} else if (Character.TYPE == fieldType) {
			if ((c != null) && (c.length() > 0)) {
				field.set(entity, c.charAt(0));
			}
		}
		return true;
	}

	/**
	 * 分别产生单个sheet
	 * @param list 原数据
	 * @param sheetName sheet基础名
	 * @param sheetSize 单个sheet最大数量
	 * @param fields 实体的域对象集合
	 * @param workbook 工作对象
	 * @param index 当前是第几个sheet
	 * @throws IllegalAccessException
	 * @throws ParseException
	 */
	private static <T> void createSheet(List<T> list, String sheetName, int sheetSize, List<Field> fields, HSSFWorkbook workbook, int index) throws IllegalAccessException, ParseException, ParseException {
		// 产生工作表对象
		HSSFSheet sheet = workbook.createSheet();
		// 设置工作表的名称.
		workbook.setSheetName(index, sheetName + index);
		HSSFRow row;
		HSSFCell cell;// 产生单元格
		row = sheet.createRow(0);
		// 产生一行
		/*         *********普通列样式*********         */
		HSSFFont font = workbook.createFont();
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		font.setFontName("Arail narrow");
		// 字体
		font.setBold(true);
		// 字体宽度
		/* *********标红列样式********* */
		HSSFFont newFont = workbook.createFont();
		HSSFCellStyle newCellStyle = workbook.createCellStyle();
		newFont.setFontName("Arail narrow");
		// 字体
		newFont.setBold(true);
		// 字体宽度
		/* *************创建列头名称*************** */
//		for (int i = 0; i < fields.size(); i++) {
//			Field field = fields.get(i);
//			ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
//			int col = i;
//			// 根据指定的顺序获得列号
//			if (StringUtils.isNotBlank(attr.column())) {
//				col = getExcelCol(attr.column());
//			}
//			// 创建列
//			cell = row.createCell(col);
//			if (attr.isMark()) {
//				newFont.setColor(HSSFFont.COLOR_RED);
//				// 字体颜色
//				newCellStyle.setFont(newFont);
//				cell.setCellStyle(newCellStyle);
//			} else {
//				font.setColor(HSSFFont.COLOR_NORMAL);
//				// 字体颜色
//				cellStyle.setFont(font);
//				cell.setCellStyle(cellStyle);
//			}
//			///sheet.setColumnWidth(i, (int) ((attr.name().getBytes().length <= 4 ? 6 : attr.name().getBytes().length) * 1.5 * 256));
//			sheet.setColumnWidth(i,  ((attr.name().length()) * 3 * 256));
//			// 设置列中写入内容为String类型
//			cell.setCellType(CellType.STRING);
//			// 写入列名
//			cell.setCellValue(attr.name());
//			// 如果设置了提示信息则鼠标放上去提示.
//			if (StringUtils.isNotBlank(attr.prompt())) {
//				setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);
//			}
//			// 如果设置了combo属性则本列只能选择不能输入
//			if (attr.combo().length > 0) {
//				setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);
//			}
//		}
		/* *************创建内容列*************** */
		font = workbook.createFont();
		cellStyle = workbook.createCellStyle();
		int startNo = index * sheetSize;
		int endNo = Math.min(startNo + sheetSize, list.size());
		// 写入各条记录,每条记录对应excel表中的一行
		for (int i = startNo; i < endNo; i++) {
			row = sheet.createRow(i + 1 - startNo);
			T vo = list.get(i);
			// 得到导出对象.
			for (int j = 0; j < fields.size(); j++) {
				// 获得field
				Field field = fields.get(j);
				// 设置实体类私有属性可访问
				field.setAccessible(true);
				ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
				int col = j;
				// 根据指定的顺序获得列号
				if (StringUtils.isNotBlank(attr.column())) {
					col = getExcelCol(attr.column());
				}
				// 根据ExcelAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
				if (attr.isExport()) {
					// 创建cell
					cell = row.createCell(col);
					if (attr.isMark()) {
						newFont.setColor(HSSFFont.COLOR_RED);
						newCellStyle.setFont(newFont);
						cell.setCellStyle(newCellStyle);
					} else {
						font.setColor(HSSFFont.COLOR_NORMAL);
						cellStyle.setFont(font);
						cell.setCellStyle(cellStyle);
					}
					// 如果数据存在就填入,不存在填入空格
					Class<?> classType =  field.getType();
					String value = null;
					if (field.get(vo) != null && classType.isAssignableFrom(Date.class)) {
						SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
						value = sdf.parse(String.valueOf(field.get(vo))).toString();
					}
					cell.setCellValue(field.get(vo) == null ? "" : value == null ? String.valueOf(field.get(vo)) : value);
				}
			}
		}
		/* *************创建合计列  - 暂不需要*************** */
		HSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
		for (int i = 0; i < fields.size(); i++) {
			Field field = fields.get(i);
			ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
			if (attr.isSum()) {
				int col = i;
				// 根据指定的顺序获得列号
				if (StringUtils.isNotBlank(attr.column())) {
					col = getExcelCol(attr.column());
				}
				BigDecimal totalNumber = BigDecimal.ZERO;

				for (int j = 1, len = (sheet.getLastRowNum() - 1); j < len; j++) {
					HSSFRow hssfRow = sheet.getRow(j);
					if (hssfRow != null) {
						HSSFCell hssfCell = hssfRow.getCell(col);
//                                if (hssfCell != null && hssfCell.getCellType() == HSSFCell.CELL_TYPE_STRING
//                                        && ValidateUtil.isFloat(hssfCell.getStringCellValue())) {
//                                    totalNumber = BigDecimalUtils.getValue(totalNumber,
//                                            BigDecimal.valueOf(Double.valueOf(hssfCell.getStringCellValue())), CalculateType.Add);
//                                }
						totalNumber = totalNumber.add(new BigDecimal(hssfCell.getStringCellValue()));
					}
				}
				HSSFCell sumCell = lastRow.createCell(col);
				sumCell.setCellValue(new HSSFRichTextString("合计：" + totalNumber));
			}
		}
	}

	/**
	 * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
	 *
	 * @param col 列名
	 */
	private static int getExcelCol(String col) {
		col = col.toUpperCase();
		// 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
		int count = -1;
		char[] cs = col.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
		}
		return count;
	}

	/**
	 * 设置单元格上提示
	 *
	 * @param sheet         要设置的sheet.
	 * @param promptTitle   标题
	 * @param promptContent 内容
	 * @param firstRow      开始行
	 * @param endRow        结束行
	 * @param firstCol      开始列
	 * @param endCol        结束列
	 * @return 设置好的sheet.
	 */
	private static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
										   int firstCol, int endCol) {
		// 构造constraint对象
		DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation dataValidationView = new HSSFDataValidation(regions, constraint);
		dataValidationView.createPromptBox(promptTitle, promptContent);
		sheet.addValidationData(dataValidationView);
		return sheet;
	}

	/**
	 * 设置某些列的值只能输入预制的数据,显示下拉框.
	 *
	 * @param sheet    要设置的sheet.
	 * @param textlist 下拉框显示的内容
	 * @param firstRow 开始行
	 * @param endRow   结束行
	 * @param firstCol 开始列
	 * @param endCol   结束列
	 * @return 设置好的sheet.
	 */
	private static HSSFSheet setHSSFValidation(HSSFSheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
		// 加载下拉列表内容
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation datavalidationlist = new HSSFDataValidation(regions, constraint);
		sheet.addValidationData(datavalidationlist);
		return sheet;
	}

	public static <T> void exportExcel(String name,HSSFWorkbook wb ,@NonNull List<T> list,@NonNull Class<T> clazz,int startIndex,int titleIndex, HttpServletRequest request, HttpServletResponse response) {

		try {
		 	Sheet sheet = wb.getSheetAt(0);
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			HSSFFont font = wb.createFont();
			font.setFontHeight((short)160);
			cellStyle.setFont(font);
			Field[] allFields = clazz.getDeclaredFields();
			// 定义一个map用于存放列的序号和field
			Map<String, Field> fieldsMap = new HashMap<>(32);
			for (int i = 0, index = 0; i < allFields.length; i++) {
				Field field = allFields[i];
				// 将有注解的field存放到map中
				if (field.isAnnotationPresent(ExcelAttribute.class)) {
					// 设置类的私有字段属性可访问
					field.setAccessible(true);
					ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
					fieldsMap.put(attr.name(), field);
					index++;
				}
			}
			T entity = null;
			entity = (entity == null ? clazz.newInstance() : entity);
			Row titleRow = sheet.getRow(titleIndex);
			List<Field> fields = new ArrayList<>();
			// 得到所有field并存放到一个list中
			for (Field field : allFields) {
				if (field.isAnnotationPresent(ExcelAttribute.class)) {
					fields.add(field);
				}
			}
			int ind = 1;

			for (int i = 0; i < list.size(); i++) {
				Row row = null;
				if(sheet.getRow(startIndex+i) == null){
					row = sheet.createRow(startIndex + i);
				}else if(sheet.getRow(startIndex+i).getCell(1)!=null&&!"".equals(sheet.getRow(startIndex+i).getCell(1).getStringCellValue().trim())){
					sheet.shiftRows(startIndex+i, sheet.getLastRowNum(), 1,true,false);
					 row = sheet.createRow(startIndex + i);
				}else{
					 row = sheet.getRow(startIndex + i);
				}

					T vo = list.get(i);

					for (int j = 0; j < titleRow.getPhysicalNumberOfCells(); j++) {
						row.createCell(j);
						Cell cell = row.getCell(j);
						cell.setCellStyle(cellStyle);
						cell.setCellType(CellType.STRING);
						cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
						cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
						cellStyle.setBorderTop(BorderStyle.THIN);//上边框
						cellStyle.setBorderRight(BorderStyle.THIN);//右边框
						cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
						cellStyle.setAlignment(HorizontalAlignment.CENTER);
						cellStyle.setWrapText(true);
						System.out.println(titleRow);

						Field field;
						if(titleRow.getCell(j).getStringCellValue().indexOf("毛重")!=-1){
							field = fieldsMap.get("毛重");
						} else if(titleRow.getCell(j).getStringCellValue().indexOf("净重")!=-1){
							field = fieldsMap.get("净重");
						} else if(titleRow.getCell(j).getStringCellValue().indexOf("单价")!=-1){
							field = fieldsMap.get("单价");
						} else if(titleRow.getCell(j).getStringCellValue().indexOf("总价")!=-1){
							field = fieldsMap.get("总价");
						}else if(titleRow.getCell(j).getStringCellValue().indexOf("原进区报关单号")!=-1){
							field = fieldsMap.get("进区报关单号");
						}else if(titleRow.getCell(j).getStringCellValue().indexOf("原进区核注清单号")!=-1){
							field = fieldsMap.get("进区核注清单号");
						}else if(titleRow.getCell(j).getStringCellValue().indexOf("进口国别")!=-1){
							field = fieldsMap.get("原产国");
						}
						else{
							field = fieldsMap.get(titleRow.getCell(j).getStringCellValue());
						}


						if("序号".equals(titleRow.getCell(j).getStringCellValue())){

							cell.setCellValue(ind);
						}
						if(field!=null&&field.get(vo)!=null){
							if(field.get(vo).getClass() == Date.class){
								if (field.isAnnotationPresent(DateTimeFormat.class)) {
									DateTimeFormat attr = field.getAnnotation(DateTimeFormat.class);
									SimpleDateFormat sdf = new SimpleDateFormat(attr.pattern());

									cell.setCellValue(String.valueOf(sdf.format(field.get(vo))));

								}else{
									cell.setCellValue(String.valueOf(field.get(vo)));
								}
							}else{
								cell.setCellValue(String.valueOf(field.get(vo)));
							}

						}

					}
					ind++;

			}

		}catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

	}

	public static <T> void exportExcel2(String name, HSSFWorkbook wb , @NonNull List<InoutYardBody> list, @NonNull Class<T> clazz, int startIndex, int titleIndex, HttpServletRequest request, HttpServletResponse response) {

		try {
			Sheet sheet = wb.getSheetAt(0);
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			HSSFFont font = wb.createFont();
			font.setFontHeight((short)160);
			cellStyle.setFont(font);
			Field[] allFields = clazz.getDeclaredFields();
			// 定义一个map用于存放列的序号和field
			Map<String, Field> fieldsMap = new HashMap<>(32);
			for (int i = 0, index = 0; i < allFields.length; i++) {
				Field field = allFields[i];
				// 将有注解的field存放到map中
				if (field.isAnnotationPresent(ExcelAttribute.class)) {
					// 设置类的私有字段属性可访问
					field.setAccessible(true);
					ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
					fieldsMap.put(attr.name(), field);
					index++;
				}
			}
			T entity = null;
			entity = (entity == null ? clazz.newInstance() : entity);
			Row titleRow = sheet.getRow(titleIndex);
			List<Field> fields = new ArrayList<>();
			// 得到所有field并存放到一个list中
			for (Field field : allFields) {
				if (field.isAnnotationPresent(ExcelAttribute.class)) {
					fields.add(field);
				}
			}
			Double roughWetSum=0.0; //毛重
			Double netWetSum=0.0;  //净重
			Double totalPriceSum=0.0;// 总价
			int pieceNumSum=0;// 件数

			for(InoutYardBody info:list){
				roughWetSum+=info.getRoughWet() == null ? 0:info.getRoughWet();

				netWetSum += StrWithNumChkUtil.isRealNumber(info.getNetWet()) ? Double.parseDouble(info.getNetWet()):0;
				totalPriceSum+=info.getTotalPrice() == null ? 0:info.getTotalPrice();
				pieceNumSum+=info.getPieceNum() == null ? 0:info.getPieceNum();
			}

			for (int i = 0; i < 1; i++) {
				Row row = null;

				if(sheet.getRow(startIndex+i).getCell(1)!=null&&!"".equals(sheet.getRow(startIndex+i).getCell(1).getStringCellValue().trim())){
					sheet.shiftRows(startIndex+i, sheet.getLastRowNum(), 1,true,false);
					row = sheet.createRow(startIndex + i);
				}else{
					row = sheet.getRow(startIndex + i);
				}


				for (int j = 0; j < 11; j++) {
					row.createCell(j);
					Cell cell = row.getCell(j);
					cell.setCellStyle(cellStyle);
					cell.setCellType(CellType.STRING);
					cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
					cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
					cellStyle.setBorderTop(BorderStyle.THIN);//上边框
					cellStyle.setBorderRight(BorderStyle.THIN);//右边框
					cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
					System.out.println(titleRow);
//					Field field = fieldsMap.get(titleRow.getCell(j).getStringCellValue());
				if(j==0){
					cell.setCellValue("合计");
				}

				if(name.indexOf("入库")!=-1){
					if(j==4){
						cell.setCellValue(pieceNumSum);
					}
					if(j==5){
						cell.setCellValue(roughWetSum);
					}
					if(j==6){
						cell.setCellValue(netWetSum);
					}
					if(j==8){
						cell.setCellValue(totalPriceSum);
					}
				}

				if(name.indexOf("出库")!=-1){
					if(j==4){
						cell.setCellValue(pieceNumSum);
					}
					if(j==5){
						cell.setCellValue(roughWetSum);
					}
					if(j==6){
						cell.setCellValue(netWetSum);
					}
					if(j==8){
						cell.setCellValue(totalPriceSum);
					}
				}


				}

			}

		}catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

	}

	public static <T> void exportExcel3(String name, HSSFWorkbook wb , @NonNull List<InoutYardBody> list, @NonNull Class<T> clazz, int startIndex, int titleIndex, HttpServletRequest request, HttpServletResponse response) {

		try {
			Sheet sheet = wb.getSheetAt(0);
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			HSSFFont font = wb.createFont();
			font.setFontHeight((short)160);
			cellStyle.setFont(font);
			Field[] allFields = clazz.getDeclaredFields();
			// 定义一个map用于存放列的序号和field
			Map<String, Field> fieldsMap = new HashMap<>(32);
			for (int i = 0, index = 0; i < allFields.length; i++) {
				Field field = allFields[i];
				// 将有注解的field存放到map中
				if (field.isAnnotationPresent(ExcelAttribute.class)) {
					// 设置类的私有字段属性可访问
					field.setAccessible(true);
					ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
					fieldsMap.put(attr.name(), field);
					index++;
				}
			}
			T entity = null;
			entity = (entity == null ? clazz.newInstance() : entity);
			Row titleRow = sheet.getRow(titleIndex);
			List<Field> fields = new ArrayList<>();
			// 得到所有field并存放到一个list中
			for (Field field : allFields) {
				if (field.isAnnotationPresent(ExcelAttribute.class)) {
					fields.add(field);
				}
			}
//			Double netWetSum=0.0;
			Double totalPriceSum=0.0;
			Double unitPrice = 0.0;

			for(InoutYardBody info:list){
				unitPrice+=info.getUnitPrice() == null ? 0:info.getUnitPrice();
				totalPriceSum+=info.getTotalPrice() == null ? 0:info.getTotalPrice();
			}

			for (int i = 0; i < 1; i++) {
				Row row = null;

				if(sheet.getRow(startIndex+i).getCell(1)!=null&&!"".equals(sheet.getRow(startIndex+i).getCell(1).getStringCellValue().trim())){
					sheet.shiftRows(startIndex+i, sheet.getLastRowNum(), 1,true,false);
					row = sheet.createRow(startIndex + i);
				}else{
					row = sheet.getRow(startIndex + i);
				}


				for (int j = 0; j < 11; j++) {
					row.createCell(j);
					Cell cell = row.getCell(j);
					cell.setCellStyle(cellStyle);
					cell.setCellType(CellType.STRING);
					cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
					cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
					cellStyle.setBorderTop(BorderStyle.THIN);//上边框
					cellStyle.setBorderRight(BorderStyle.THIN);//右边框
					cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
					System.out.println(titleRow);
//					Field field = fieldsMap.get(titleRow.getCell(j).getStringCellValue());
					if(j==0){
						cell.setCellValue("合计");
					}
					if(name.indexOf("入库")!=-1){
						if(j==7){
							cell.setCellValue(unitPrice);
						}
						if(j==8){
							cell.setCellValue(totalPriceSum);
						}
					}

					if(name.indexOf("出库")!=-1){
						if(j==7){
							cell.setCellValue(unitPrice);
						}
						if(j==8){
							cell.setCellValue(totalPriceSum);
						}
					}


				}

			}

		}catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

	}
}
