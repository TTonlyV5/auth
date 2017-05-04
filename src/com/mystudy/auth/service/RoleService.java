package com.mystudy.auth.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mystudy.auth.dao.RoleDao;
import com.mystudy.auth.dao.RoleFunctionsDao;
import com.mystudy.auth.entity.Role;
import com.mystudy.auth.entity.RoleFunctions;

@Service
public class RoleService {

	@Autowired 
	private RoleDao roleDao;
	@Autowired
	private RoleFunctionsDao roleFunctionsDao;
	/**
	 * 保存角色信息，同时保存角色对应功能
	 * @param role 角色
	 * @param roleFunctions 角色对应的功能（即角色功能的关联关系）
	 */
	public void addRole(Role role ,Collection<RoleFunctions> roleFunctions) {
		roleDao.saveRole(role);
		//和下面注释代码作用相同（另一种写法）
		roleFunctions.forEach((rf)->rf.setRoleId(role.getId()));
		
//		for(RoleFunctions rf:roleFunctions){
//			rf.setRoleId(role.getId());
//		}
		
		roleFunctionsDao.saveRoleFunctions(roleFunctions);
	}
	/**
	 * 修改角色信息，同时编辑角色对应功能
	 * @param role 角色
	 * @param roleFunctions 角色对应的功能（即角色功能的关联关系）
	 */
	public void editRole(Role role,Collection<RoleFunctions> roleFunctions)	 {
		roleDao.updateRole(role);
		roleFunctionsDao.deleteByRoleId(role.getId());
		roleFunctions.forEach((rf)->rf.setRoleId(role.getId()));
		roleFunctionsDao.saveRoleFunctions(roleFunctions);
	}
	/**
	 * 根据ID删除角色
	 * @param roleId 角色id
	 */
	public void deleteRoleById(Long roleId) {
		roleDao.deleteRoleById(roleId);
		roleFunctionsDao.deleteByRoleId(roleId);
	}
	/**
	 * 分页查询角色信息
	 * @param page 当前页码
	 * @param size 每页记录数
	 * @return 角色集合
	 */
	public List<Role> getRoles(int page,int size) {
		List<Role> roles=roleDao.findRolesByPages(page, size);
		roles.forEach((role)->{
			List<RoleFunctions> roleFunctions=roleFunctionsDao.findRoleFunctionsByRoleId(role.getId());
			StringBuilder functionIds=new StringBuilder();
			roleFunctions.forEach((rf)->{
				functionIds.append(rf.getFunctionId()).append(",");
			});
			
			if (functionIds.length()>1) {
				role.setFunctionsIds(functionIds.deleteCharAt(functionIds.length()-1).toString());
			}
		});
		return roles;
	}
	/**
	 * 根据id集合查询角色信息	
	 * @param ids 角色id集合
	 * @return 角色集合
	 */
	public List<Role> getRoles(Collection<Long> ids) {
		return roleDao.findRoleByIds(ids);
	}
	/**
	 * 根据用户id查询角色和功能对应关系
	 * @param roleId 角色id
	 * @return 角色功能对应关系集合
	 */
	public List<RoleFunctions> getRoleFunctions(Long roleId) {
		return roleFunctionsDao.findRoleFunctionsByRoleId(roleId);
	}
}
