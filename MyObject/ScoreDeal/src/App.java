import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class App {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("C:\\Users\\15940\\OneDrive\\Desktop\\[92413]20230902琢名小渔高二年级开学测试考试成绩分析报表\\联考报表_成绩分析\\试卷质量分析.xlsx");
        XSSFWorkbook xwf = new XSSFWorkbook(fis);
        int sheets = xwf.getNumberOfSheets();
        System.out.println(sheets);
    }
}
