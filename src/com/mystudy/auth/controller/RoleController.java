package com.mystudy.auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mystudy.auth.common.AjaxResult;
import com.mystudy.auth.entity.Role;
import com.mystudy.auth.entity.RoleFunctions;
import com.mystudy.auth.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/index")
	public String idnex() {
		return "/role_list";
	}
	
	@RequestMapping(value="/getRoles",method=RequestMethod.POST)
	@ResponseBody
	public List<Role> getRoles(Integer page,Integer size) {
		return roleService.getRoles(page, size);
	}
	
	@RequestMapping(value="/addEditRole",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult addEditRole(Role role) {
		String functionsIds=role.getFunctionsIds();
		String[] idArray=functionsIds.split(",");
		List<RoleFunctions> roleFunctionsList=new ArrayList<>();
		for (int i = 0; i < idArray.length; i++) {
			RoleFunctions rf=new RoleFunctions();
			rf.setFunctionId(Long.valueOf(idArray[i]));
			rf.setStatus(1);
			roleFunctionsList.add(rf);
		}
		if (role.getId()==null) {
			roleService.addRole(role, roleFunctionsList);
		}else {
			roleService.editRole(role, roleFunctionsList);
		}
		return AjaxResult.success();
	}
	
	@RequestMapping(value="/deleteEditRole",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult deleteEditRole(Long id) {
		roleService.deleteRoleById(id);
		return AjaxResult.success();
	}
}
