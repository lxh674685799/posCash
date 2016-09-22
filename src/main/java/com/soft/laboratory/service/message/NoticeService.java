package com.soft.laboratory.service.message;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.core.db.GenericDao;
import com.soft.core.service.GenericService;
import com.soft.core.util.FileUtil;
import com.soft.core.util.RegExpUtil;
import com.soft.laboratory.dao.message.NoticeDao;
import com.soft.laboratory.model.message.Notice;

@Transactional
@Service
public class NoticeService  extends GenericService<Notice> {
	
	@Resource
	private NoticeDao dao;

	@Override
	protected GenericDao<Notice> getEntityDao() {
		return dao;
	}
	
	public void delByIds(String[] ids) {
		for (String p : ids){
			Notice info = dao.findById(p);
			dao.deleteEntity(info);
			// 删除图片文件
			FileUtil.deleteFile(RegExpUtil.getImgSrcFromHtml(info
								.getContent()));
		}
	}

}