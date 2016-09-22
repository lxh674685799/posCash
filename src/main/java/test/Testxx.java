package test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.soft.core.poi.ExcelUtil;
import com.soft.laboratory.model.device.DeviceFactory;
import com.soft.laboratory.model.message.InnerMessage;
import com.soft.laboratory.service.device.DeviceFactoryService;
import com.soft.laboratory.service.message.MessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:conf/spring-*.xml")
public class Testxx{
	
	@Resource
	private DeviceFactoryService factoryService;
	
	@Resource
	private MessageService messageService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		//ExcelUtil util = ExcelUtil.getInstance() ;1122sss大叔大婶覅覅上的地方
		//List<DeviceFactory> list = (List<DeviceFactory>) this.factoryService.listAll(new DeviceFactory());
		
		System.out.println("---------------------");
		System.out.println();
		
		List<InnerMessage> messages = messageService.getNotReadMsg("1000");
		
		System.out.println(messages.size());
		
		//System.out.println(list.size());
		
		//util.export2Obj("D:/lfd.xls", DeviceFactory.class, list) ;
		//util.exprot2ObjByTemp("WEB-INF/user.xls", "D:/lfd.xls", DeviceFactory.class, list, 2, null, true);
	}
	
	
	@Test
	public void testReadExcel2Obj() {
		List<DeviceFactory> users = ExcelUtil.getInstance().readExcel2Obj("D:/lfd.xls", DeviceFactory.class, 2, 0, false) ;
		System.out.println(users.size()+"-----------------------");
		for(DeviceFactory user : users) {
			System.out.println(user);
		}
	}
	
	
//	@Test
//	public void testClazz() {
//		Type type = Integer.class ;
//		System.out.println((Class<?>)type) ;
//		System.out.println(PoiTest.class.getResource("/excel"));
//	}
//	
//	@Test
//	public void testReadTemp() {
//		ExcelTemplate excel = ExcelTemplate.getInstance().readTemplate2path("excel/default.xls") ;
//		excel.newRow().newCol("lfd").newCol("e").newCol("lfd").newCol("e") ;
//		excel.newRow().newCol("lfd").newCol("e").newCol("lfd").newCol("e") ;
//		excel.newRow().newCol("lfd").newCol("e").newCol("lfd").newCol("e") ;
//		excel.newRow().newCol("lfd").newCol("e").newCol("lfd").newCol("e") ;
//		excel.newRow().newCol("lfd").newCol("e").newCol("lfd").newCol("e") ;
//		excel.newRow().newCol("lfd").newCol("e").newCol("lfd").newCol("e") ;
//		excel.newRow().newCol("lfd").newCol("e").newCol("lfd").newCol("e") ;
//		Map<String, String> datas = new HashMap<String, String>() ;
//		datas.put("date", new Date().toString()) ;
//		datas.put("dep", "广东从事") ;
//		datas.put("title", "部门人数统计") ;
//		excel.replaceFind(datas) ;
//		excel.insertSer() ;
//		excel.write2path("D:/lfd.xls") ;
//	}
//	
//	@Test
//	public void testExport2Obj() {
//		ExcelUtil util = ExcelUtil.getInstance() ;
//		List<User> users = new ArrayList<User>() ;
//		User user = null ;
//		for(int i=0; i<20; i++) {
//			user = new User() ;
//			user.setId(i) ;
//			user.setAge(i) ;
//			user.setCreateDate(new Date()) ;
//			user.setHasVisible(true) ;
//			user.setName("lfdlfdlfkdlfklfkdlfdsk" + i) ;
//			user.setSex("男") ;
//			users.add(user) ;
//		}
//		util.export2Obj("D:/lfd.xls", User.class, users) ;
// 	}
//	
//	@Test
//	public void testExprot2ObjByTemp() {
//		List<User> users = new ArrayList<User>() ;
//		User user = null ;
//		for(int i=0; i<20; i++) {
//			user = new User() ;
//			user.setId(i) ;
//			user.setAge(i) ;
//			user.setCreateDate(new Date()) ;
//			user.setHasVisible(true) ;
//			user.setName("lfdlfdlfkdlfklfkdlfdsk" + i) ;
//			user.setSex("男") ;
//			users.add(user) ;
//		}
//		ExcelUtil.getInstance().exprot2ObjByTemp("excel/user.xls", "D:/lfd.xls", User.class, users, 2, null, true) ;
//	}
//	
	
}
