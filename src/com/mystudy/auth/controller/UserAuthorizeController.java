package com.mystudy.auth.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mystudy.auth.common.AjaxResult;
import com.mystudy.auth.common.BaseEntity;
import com.mystudy.auth.dto.Authorize;
import com.mystudy.auth.entity.Role;
import com.mystudy.auth.entity.User;
import com.mystudy.auth.entity.UserRole;
import com.mystudy.auth.service.RoleService;
import com.mystudy.auth.service.UserService;


@RequestMapping("/authorize")
@Controller
public class UserAuthorizeController {
	
	@Autowired private UserService userService;
	
	@Autowired private RoleService roleService;
	/**
	 * 授权首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(){
		return "/authorize/authorize_list";
	}
	/**
	 * 用户角色首页
	 * @return
	 */
	@RequestMapping("/userRole")
	@ResponseBody
	public String authorizeIndex(){
		return "/user_role_list";
	}
	/**
	 *  分页查询授权
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/getAuthorizes",method=RequestMethod.POST)
	@ResponseBody
	public List<Authorize> getAuthorizes(Integer page,Integer rows)
	{
		List<UserRole> userRoles = userService.getUserRoles(page, rows);
		Collection<Long> userIds = new HashSet<Long>();
		Collection<Long> roleIds = new HashSet<Long>();
		for(UserRole ur : userRoles){
			userIds.add(ur.getUserId());
			roleIds.add(ur.getRoleId());
		}
		
		Collection<User> users =  userService.getUsers(userIds);
		List<Role> roles = roleService.getRoles(roleIds);
		
		Map<Long,User> userMap = BaseEntity.idEntityMap(users);
		Map<Long,Role> roleMap = BaseEntity.idEntityMap(roles);
		
		List<Authorize> authorizes = new LinkedList<Authorize>();
		for(UserRole ur : userRoles){
			Authorize authorize = new Authorize();
			authorize.setRoleId(ur.getRoleId());
			authorize.setUserId(ur.getUserId());
			authorize.setUserName(userMap.get(ur.getUserId()).getName());
			authorize.setUserRoleId(ur.getId());
			authorize.setRoleName(roleMap.get(ur.getRoleId()).getName());
			authorizes.add(authorize);
		}
		
		return authorizes;
		
	}
	
	/**
	 * 根据用户id查询用户和角色的对应关系
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/getUserRoleBuUserId",method=RequestMethod.POST)
	@ResponseBody
	public List<UserRole> getUserRoleBuUserId(Long userId){
		return userService.getUserRolesById(userId);
	}
	
	/**
	 * 设置权限
	 * @param user
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value="/setAuthorize",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult setAuthorize(User user,String roleIds)
	{
		String[] temp = roleIds.split(",");
		Long[] roleIdArray  = new Long[temp.length];
		for(int i=0;i<roleIdArray.length;i++){
			roleIdArray[i] = Long.valueOf(temp[i]);
		}
		userService.addUserRoles(user.getId(), roleIdArray);
		return AjaxResult.success();
	}
	

}
