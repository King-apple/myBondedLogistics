package com.newweb.mrli.ykport.baoshuisystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.api.feign.UserFeignClient;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.service.*;
import com.newweb.mrli.ykport.baoshuisystem.vo.BSUserRoleNameAndCodeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bsuser" )
@Api(value = "bsuser", tags = "保税系统下的用户信息")
public class BSUserController {

	private final BSUserService bsUserService;
	private final OrganizerUserService organizerUserService;
	private final UserFeignClient userFeignClient;
	private final BSUserRoleService bsUserRoleService;
	private final DeptInfoService deptInfoService;
	private final SysDeptService sysDeptService;
	private final ParameterListService parameterListService;
	private final ApprovalProcessUserService approvalProcessUserService;


	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param bsUser 用户信息表
	 * @return
	 */
	@ApiOperation(value = "分页查询已绑定用户信息", notes = "分页查询已绑定用户信息")
	@GetMapping("/list" )
	public R getOrganizerInfoPage(@RequestParam Integer oFid,Page page, BSUser bsUser) {
		Collection<Integer> collection  = new ArrayList<>();
//		Integer deptId = SecurityUtils.getUser().getDeptId();
//		QueryWrapper<BSUser> queryUser = new QueryWrapper<>();
//		queryUser.eq("dept_id",deptId);
//		//获取部门下的多个用户id
//		List<BSUser> listUser = bsUserService.list(queryUser);
//		Collection<Integer> collList = new ArrayList<>();
//		for(BSUser bsUser1 : listUser){
//			collList.add(bsUser1.getUserId());
//		}
		QueryWrapper<OrganizerUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("O_FID", oFid);
		List<OrganizerUser> list = organizerUserService.list(queryWrapper);
		Collection<Integer> coll = new ArrayList();
		for(OrganizerUser organizerUser : list){
			coll.add(organizerUser.getUFid());
		}
		//取两个集合的交集
		collection.addAll(coll);
//		collection.retainAll(coll);
		if(collection != null && collection.size()>0){
			return R.ok(bsUserService.page(page, Wrappers.query(bsUser).in("user_id", collection)));
		}
		return R.ok(bsUserService.page(page, Wrappers.query(bsUser).eq("user_id", 0)));
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param bsUser 用户信息表
	 * @return
	 */
	@ApiOperation(value = "分页查询未绑定用户信息", notes = "分页查询未绑定用户信息")
	@GetMapping("/page" )
	public R getUserInfoPage(@RequestParam Integer oFid,Page page, BSUser bsUser) {
//		Integer deptId = SecurityUtils.getUser().getDeptId();
//		QueryWrapper<BSUser> queryUser = new QueryWrapper<>();
//		queryUser.eq("dept_id",deptId);


		List<Integer> depts = new ArrayList<Integer>();
		//查询参数管理表保税物流公司名称设定
		ParameterList parameterList = parameterListService.selectValueByCode("logistics_company_set");
		parameterList.getParamValue();
		//通过获取的保税物流公司参数名，查询对应的部门对应id
		SysDept sysDept = new SysDept();
		sysDept.setName(parameterList.getParamValue());
		List<SysDept> listDept = sysDeptService.list(Wrappers.query(sysDept));
		depts.add(listDept.get(0).getDeptId());
		//获取保税物流公司下的所有部门id
		List<SysDept> listSysDept = sysDeptService.getAllDeptId(listDept.get(0));
		for(SysDept info:listSysDept){
			depts.add(info.getDeptId());
		}


		QueryWrapper<BSUser> queryUser = new QueryWrapper<>();
		queryUser.in("dept_id",depts);

		//获取部门下的多个用户id
		List<BSUser> listUser = bsUserService.list(queryUser);
		Collection<Integer> coll = new ArrayList<>();
		for(BSUser bsUser1 : listUser){
			coll.add(bsUser1.getUserId());
		}
		QueryWrapper<OrganizerUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("O_FID", oFid);
		List<OrganizerUser> list = organizerUserService.list(queryWrapper);
		for(OrganizerUser organizerUser : list){
			if(coll.contains(organizerUser.getUFid())){
				coll.remove(organizerUser.getUFid());
			}
		}
		if(coll != null && coll.size()>0){
			return R.ok(bsUserService.page(page, Wrappers.query(bsUser).in("user_id",coll)));
		}
		return R.ok(bsUserService.page(page, Wrappers.query(bsUser).eq("user_id", 0)));
	}

	/**
	 * 查询当前用户下的角色名称
	 */
	@ApiOperation(value = "分页查询未绑定用户信息", notes = "分页查询未绑定用户信息")
	@GetMapping("/dict" )
	public R getUserInfoList() {
		Integer deptId = SecurityUtils.getUser().getDeptId();
		QueryWrapper<BSUser> queryUser = new QueryWrapper<>();
		queryUser.eq("dept_id",deptId);
		//获取部门下的多个用户id
		List<BSUser> list = bsUserService.list(queryUser);
		Collection<Integer> coll = new ArrayList<>();
		for(BSUser bsUser : list){
			coll.add(bsUser.getUserId());
		}
		QueryWrapper<BSUserRole> queryRole = new QueryWrapper<>();
		List<BSUserRole> roleList = new ArrayList<>();
		if(coll != null && coll.size()>0){
			roleList = bsUserRoleService.list(queryRole.in("user_id", coll));
		}
		List<Integer> roleIdList = new ArrayList<>();
		for(BSUserRole bsUserRole : roleList){
			if(!roleIdList.contains(bsUserRole.getRoleId())){
				roleIdList.add(bsUserRole.getRoleId());
			}
		}

		List<BSUserRoleNameAndCodeVo> lists = bsUserRoleService.getSelectRoleNameAndroleCodeList(roleIdList);

		return R.ok(lists);
	}

	/**
	 * 查询用户详细信息
	 */
	@ApiOperation(value = "查询用户详细信息", notes = "查询用户详细信息")
	@GetMapping("/getUserInfo" )
	public R getUserInfo(BSUser bsUser){
		return R.ok(bsUserService.list(Wrappers.query(bsUser)));
	}

	/**
	 * 分页查询（业务单据审核）
	 * @param page 分页对象
	 * @param bsUser 用户信息表
	 * @return
	 */
	@ApiOperation(value = "分页查询未绑定用户信息（业务单据审核）", notes = "分页查询未绑定用户信息")
	@GetMapping("/pageForProcessUser" )
	public R pageForProcessUSer(@RequestParam Integer oFid,Page page, BSUser bsUser) {
		List<Integer> depts = new ArrayList<Integer>();

		//查询参数管理表保税物流公司名称设定
//		ParameterList parameterList = parameterListService.selectValueByCode("logistics_company_set");
//		parameterList.getParamValue();
		//通过获取的保税物流公司参数名，查询对应的部门对应id
		SysDept sysDept = new SysDept();
		sysDept.setDeptId(SecurityUtils.getUser().getDeptId());
		List<SysDept> listDept = sysDeptService.list(Wrappers.query(sysDept));


		depts.add(listDept.get(0).getDeptId());
		//获取保税物流公司下的所有部门id
		List<SysDept> listSysDept = sysDeptService.getAllDeptId(listDept.get(0));

		for(SysDept info:listSysDept){
			depts.add(info.getDeptId());
		}

		Integer deptId = SecurityUtils.getUser().getDeptId();
		QueryWrapper<BSUser> queryUser = new QueryWrapper<>();
//		queryUser.eq("dept_id",listDept.get(0).getDeptId());
		queryUser.in("dept_id",depts);
		//获取部门下的多个用户id
		List<BSUser> listUser = bsUserService.list(queryUser);
		Collection<Integer> coll = new ArrayList<>();
		for(BSUser bsUser1 : listUser){
			coll.add(bsUser1.getUserId());
		}
		QueryWrapper<ApprovalProcessUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("PROCESS_FID", oFid);
//		List<OrganizerUser> list = organizerUserService.list(queryWrapper);
//		for(OrganizerUser organizerUser : list){
//			if(coll.contains(organizerUser.getUFid())){
//				coll.remove(organizerUser.getUFid());
//			}
//		}
		List<ApprovalProcessUser> list = approvalProcessUserService.list(queryWrapper);
		for(ApprovalProcessUser approvalProcessUser : list){
			if(coll.contains(approvalProcessUser.getUserId())){
				coll.remove(approvalProcessUser.getUserId());
			}
		}
		QueryWrapper<BSUser> user_id = Wrappers.query(new BSUser());
		if(bsUser.getUsername() !=null&&!"".equals(bsUser.getUsername())){
			user_id = user_id.like("UPPER(username)",bsUser.getUsername().toUpperCase());
		}
		if(bsUser.getManName() !=null&&!"".equals(bsUser.getManName())){
			user_id = user_id.like("UPPER(man_name)",bsUser.getManName().toUpperCase());
		}
		if(coll != null && coll.size()>0){

			return R.ok(bsUserService.page(page, user_id.in("user_id", coll)));
		}
		return R.ok(bsUserService.page(page, user_id.eq("user_id", 0)));
	}

}
