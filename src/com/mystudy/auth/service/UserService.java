package com.mystudy.auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mystudy.auth.dao.UserDao;
import com.mystudy.auth.dao.UserRoleDao;
import com.mystudy.auth.entity.User;
import com.mystudy.auth.entity.UserRole;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;
	
	/**
	 * 保存用户信息
	 * @param user 用户信息
	 */
	public void addUser(User user) {		
		userDao.saveUser(user);
	}
	/**
	 * 更新用户信息
	 * @param user 用户信息
	 */
	public void updateUser(User user) {
		userDao.update(user);
	}
	/**
	 * 根据用户ID删除用户
	 * @param id 用户ID
	 */
	public void deleteUserById(Long id) {
		userDao.deleteUserById(id);
	}
	/**
	 * 根据用户名、密码查询用户，用于登陆
	 * @param name 用户名
	 * @param password 密码
	 * @return 用户信息
	 */
	public User getUser(String name,String password) {
		return userDao.getUser(name, password);
	}
	/**
	 * 分页查询用户信息
	 * @param page 当前页码
	 * @param size 每页记录数
	 * @return 用户信息集合
	 */
	public Collection<User> getUsers(Integer page,Integer size) {
	return userDao.findPage(page,size);
}
	/**
	 * 根据ID集合查询对应的用户信息
	 * @param ids 用户id集合
	 * @return 用户信息集合
	 */
	public Collection<User> getUsers(Collection<Long> ids) {
		return userDao.findByIds(ids);
	}	
	/**
	 * 分页查询用户角色对应关系
	 * @param page 当前页码
	 * @param size 每页记录数
	 * @return 用户角色对应关系集合
	 */
	public List<UserRole> getUserRoles(Integer page,Integer size) {
		return userRoleDao.findUserRoles(page, size);
	}
	/**
	 * 根据用户ID查询用户角色对应关系
	 * @param userId 用户id
	 * @return 用户角色对应关系集合
	 */
	public List<UserRole> getUserRolesById(Long userId) {
		return userRoleDao.findUserRoleByUserId(userId);
	}
	/**
	 * 保持用户角色对应关系
	 * @param userId 用户ID
	 * @param roleIds 用户角色对应关系集合（一组角色id）
	 */
	public void addUserRoles(Long userId,Long[] roleIds) {
		List<UserRole> userRoles=new ArrayList<>();
		
		Arrays.asList(roleIds).forEach((roleId)->  {
			UserRole userRole=new UserRole();
			userRole.setRoleId(roleId);
			userRole.setUserId(userId);
			userRoles.add(userRole);		
		});
		userRoleDao.saveUserRoles(userRoles);
	}

}
