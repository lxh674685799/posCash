package com.soft.laboratory.controller.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.controller.GenericController;
import com.soft.core.poi.ExcelUtil;
import com.soft.core.syscontext.Const;
import com.soft.core.util.Base64Demo;
import com.soft.core.util.PinyinUtil;
import com.soft.core.util.RequestUtil;
import com.soft.core.web.ResultMessage;
import com.soft.laboratory.model.InitInfo;
import com.soft.laboratory.model.device.Device;
import com.soft.laboratory.model.device.DeviceBuy;
import com.soft.laboratory.model.device.DeviceEntity;
import com.soft.laboratory.model.device.DeviceFactory;
import com.soft.laboratory.model.device.DeviceType;
import com.soft.laboratory.model.user.SysOrg;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.device.DeviceBuyService;
import com.soft.laboratory.service.device.DeviceEntityService;
import com.soft.laboratory.service.device.DeviceFactoryService;
import com.soft.laboratory.service.device.DeviceService;
import com.soft.laboratory.service.device.DeviceTypeService;
import com.soft.laboratory.service.user.SysOrgService;
import com.soft.laboratory.service.user.SysUserService;

/**
 * 系统初始化
 * 
 */
@Controller
@RequestMapping({"/system/init"})
public class SystemInitController extends GenericController{
	
	@Resource
	private SysUserService userService;
	
	@Resource
	private SysOrgService orgService;
	
	@Resource
	private DeviceService deviceService;
	
	@Resource
	private DeviceBuyService buyService;
	
	@Resource
	private DeviceTypeService typeService;
	
	@Resource
	private DeviceFactoryService factoryService;
	
	@Resource
	private DeviceEntityService entityService;
	
	private static final Logger log = LogManager.getLogger(SystemInitController.class);
	
	
	@RequestMapping({"deviceImp"})
	public void deviceImp(HttpServletRequest request, HttpServletResponse response,MultipartHttpServletRequest attachmentFile) throws IOException{
		ResultMessage message = null;
			
		InputStream	infFile = attachmentFile.getFile("impFile").getInputStream();
		
		try {
			List<InitInfo> infos = ExcelUtil.getInstance().readExcel2Obj(infFile, InitInfo.class, 1, 0) ;
		int success =0;
		int falseNumber =0;
		for(InitInfo d : infos) {
			DeviceFactory factory = new DeviceFactory();
			String factoryId =null;
			factory.setName(d.getManufactureFactory());
			List<DeviceFactory> factorys = factoryService.isNameExistAdd(factory.getName(), "name");
			if(factorys.size()<=0){
				factoryId =factoryService.add(factory);
				log.debug("添加厂家------>>>"+factory.getName());
			}else{
				factoryId = factorys.get(0).getId();
			}
			
			DeviceType type =new DeviceType();
			String typeId = null;
			type.setClassNumber(d.getClassNumber());
			List<DeviceType> types = typeService.isNameExistAdd(type.getClassNumber(), "classNumber");
			if(types.size()<=0){
				type.setName(d.getDeviceName());
				type.setParentId("-1");
				type.setisParent(false);
				
				typeId =typeService.add(type);
				
				type.setPath("1."+typeId+".");	
				typeService.update(type);
				
				log.debug("添加设备类型------>>>"+type.getName());
			}else{
				typeId = types.get(0).getId();
			}
			
			Device device = new Device();
			String deviceId = null;
			device.setName(d.getType());
			List<Device> devices = deviceService.isNameExistAdd(device.getName(), "name");
			if(devices.size()<=0){
				device.setFactoryId(factoryId);
				device.setTypeId(typeId);
				device.setCountryCode(d.getNationality());
				device.setCode(d.getBarCode());
				device.setSpec(d.getStandard());
				device.setNumber(1L);
				
				deviceId = deviceService.add(device);
				log.debug("添加设备------>>>"+device.getName());
			}else{
				Device de =devices.get(0);
				de.setNumber(de.getNumber()+1);
				deviceService.update(de);
				deviceId = devices.get(0).getId();
			}
			
			SysOrg org = new SysOrg();
			String orgId = null;
			org.setCode(d.getUnitNumber());
			List<SysOrg> orgs = orgService.isNameExistAdd(org.getCode(), "code");
			if(orgs.size()<=0){
				org.setName(d.getUnitName());
				org.setParentId("100");
				org.setisParent(false);
				String id = orgService.add(org);
				
				org.setPath("1.100."+id+".");	
				orgService.update(org);
				orgId = id;
				log.debug("添加组织机构------>>>"+org.getName());	
			}else{
				orgId = orgs.get(0).getId();
			}

			SysUser user = new SysUser();
			String userId =null;
			if((d.getUser()!=null)&&(!d.getUser().equals(""))){
				user.setName(d.getUser());
				List<SysUser> users = userService.isNameExistAdd(user.getName(), "name");
			if(users.size()<=0){
				user.setSex(true);
				user.setAccount(PinyinUtil.getPinyinToLowerCase(user.getName()));
				user.setPassWord(Base64Demo.getBASE64(PinyinUtil.getPinyinToLowerCase(user.getName())));
				user.setPostId(orgId);
				user.setPostName(d.getUnitName());
				userId = userService.add(user);
				log.debug("添加用户------>>>"+user.getName());
			}else{
				userId = users.get(0).getId();
			}
			
			DeviceBuy buy = new DeviceBuy();
			String buyId =null;
			buy.setDeviceId(deviceId);
			buy.setBuyTime(d.getBuyDate());
			List<DeviceBuy> buys = buyService.isBuyExist(deviceId, buy.getBuyTime());
			if(buys.size()<=0){
				buy.setPrice(d.getPrice());
				buy.setAmount(d.getAmount());
				buy.setBillNumber(d.getBillNumber());
				buy.setDeviceResource(d.getDeviceResource());
				buy.setFinancialResources(d.getFinancialResources());
				buy.setNumber(1L);
				buy.setStatus(Const.STATUS_BUYED);
				buy.setBuyUserId(userId);
				buyId =  buyService.add(buy);
				log.debug("添加购买信息------>>>"+buy.getBuyTime());
			}else{
				DeviceBuy dBuy = buys.get(0);
				dBuy.setNumber(dBuy.getNumber()+1);
				buyService.update(dBuy);
				buyId = buys.get(0).getId();
			}
			
			DeviceEntity entity = new DeviceEntity();
			entity.setNumber(d.getCodes());
			entity.setBuyId(buyId);
			entity.setOwerId(userId);
			entity.setPostId(orgId);
			if(d.getUser().equals("范毅华")){
				entity.setStatus(Const.DEVICE_STATUS_NORMAL);
			}else{
				entity.setStatus(Const.DEVICE_STATUS_GET);
			}
			entity.setDeviceId(deviceId);
			entityService.add(entity);
			log.debug("添加设备------>>>"+d.getCodes());
			}
			
		}
			message = new ResultMessage(Const.MESSAGE_WARN, "成功添加："+success+"条记录，添加失败："+falseNumber+"条！");
		} catch (Exception e) {
			message = new ResultMessage(Const.MESSAGE_ERROR,"导入失败,请严格按照模版格式填写！");
			e.printStackTrace();
		}
		addMessage(message, request);
		response.sendRedirect(RequestUtil.getPrePage(request));	
	}
	
	/**
	 * 导入厂家信息页面跳转
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "impUI" })
	public ModelAndView impUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = getAutoView(request);
		return mv;
	}

}