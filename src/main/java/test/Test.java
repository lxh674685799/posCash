package test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/download")
public class Test {
//	
//	@Autowired
//	private NoticeService noticeService;
//	
//	@RequestMapping("/excel")
//	public void down(HttpServletResponse response,HttpServletRequest request) throws IOException {
//	
//		List<Student> students = null;  
//	    SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");  
//	  
//	        try {  
//	            Student student1 = new Student(1, "小李", 20, "男", df  
//	                    .parse("1992-12-30"));  
//	            Student student2 = new Student(2, "小张", 24, "男", df  
//	                    .parse("1945-02-30"));  
//	            Student student3 = new Student(3, "小孙", 25, "男", df  
//	                    .parse("1988-11-20"));  
//	            Student student4 = new Student(4, "小王", 21, "男", df  
//	                    .parse("1992-05-30"));  
//	            Student student5 = new Student(5, "小刘", 30, "男", df  
//	                    .parse("2012-08-30"));  
//	            Student student6 = new Student(6, "小单", 28, "男", df  
//	                    .parse("1992-12-30"));  
//	            Student student7 = new Student(7, "小红", 22, "男", df  
//	                    .parse("1560-10-30"));  
//	            Student student8 = new Student(8, "小明", 29, "男", df  
//	                    .parse("2001-07-30"));  
//	            students = new ArrayList<Student>();  
//	            students.add(student1);  
//	            students.add(student2);  
//	            students.add(student3);  
//	            students.add(student4);  
//	            students.add(student5);  
//	            students.add(student6);  
//	            students.add(student7);  
//	            students.add(student8);  
//	  
//	        } catch (Exception e) {  
//	        }  
//	  
//	        String title = "学生表";  
//	        String fileName = "学生表.xls";
//	        
//	        response.reset();
//			response.setHeader("Content-Disposition",
//					"attachment; filename="+new String(fileName.getBytes("gb2312"), "iso8859-1"));
//			response.setContentType("application/ms-excel; charset=utf-8");
//	        
//	        StudentExportExcel excel = new StudentExportExcel(students, response,  
//	                fileName, title);  
//	        
//	        excel.exportExcel();  
//	    }  
//	
//	
//	@RequestMapping("/excel2")
//	public void down2(HttpServletResponse response,HttpServletRequest request) throws IOException {
//	ExcelUtil util = ExcelUtil.getInstance() ;
////	List<User> users = new ArrayList<User>() ;
////	User user = null ;
////	for(int i=0; i<20; i++) {
////		user = new User() ;
////		user.setId(i) ;
////		user.setAge(i) ;
////		user.setCreateDate(new Date()) ;
////		user.setHasVisible(true) ;
////		user.setName("lfdlfdlfkdlfklfkdlfdsk" + i) ;
////		user.setSex("男") ;
////		users.add(user) ;
////	}
//	String page ="";
//	Map<String, Object> map = new HashMap<String, Object>();
//	List<Notice> noticeList = noticeService.find(new Notice(),1,map);
//	
//	System.out.println(noticeList.size()+"--------------------------------------");
//	
//	 String fileName = "学生表.xls";
//	   response.reset();
//				response.setHeader("Content-Disposition",
//						"attachment; filename="+new String(fileName.getBytes("gb2312"), "iso8859-1"));
//				response.setContentType("application/ms-excel; charset=utf-8");
//				
//	util.export2Obj(response.getOutputStream(), Notice.class, noticeList,true) ;
//	
//	}
}
