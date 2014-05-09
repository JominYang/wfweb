package com.wfcsu.wfweb.service.impl;

import java.util.ArrayList;

import com.wfcsu.wfweb.dao.IMemberDao;
import com.wfcsu.wfweb.dao.impl.MemberDao;
import com.wfcsu.wfweb.service.IMemberService;
import com.wfcsu.wfweb.vo.MemberVo;


public class MemberService implements IMemberService {
	IMemberDao dao = null;
	public MemberService() {
		dao = new MemberDao();
	}

	public MemberVo get(String member_no) {
		return dao.get(member_no);
	}

	public boolean edit(MemberVo vo) {
		return dao.edit(vo);
	}

	public int add(MemberVo vo) {
		return dao.add(vo);
	}

	public ArrayList<MemberVo> get(int page, int rows, String order,
			String sort, MemberVo vo) {
		return dao.get(page, rows, order, sort, vo);
	}

	public int count(MemberVo vo) {
		return dao.count(vo);
	}

	public void del(String nos) {
		if (nos != null && !"".equals(nos)) {
			for(String no: nos.split(",")) {
				dao.del(no);
			}
		}
	}

	public boolean updateInfo(MemberVo vo) {
		return dao.updateInfo(vo);
	}

	public boolean changePwd(String no, String newpwd) {
		return dao.changePwd(no,newpwd);
	}
	
}
