package com.mystudy.auth.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mystudy.auth.common.Whether;
import com.mystudy.auth.dto.Accordion;
import com.mystudy.auth.entity.Functions;
import com.mystudy.auth.entity.Role;
import com.mystudy.auth.entity.RoleFunctions;
import com.mystudy.auth.entity.User;
import com.mystudy.auth.entity.UserRole;
import com.mystudy.auth.service.RoleService;
import com.mystudy.auth.service.UserService;

@Component
public class LoginUserHelper {
	@Autowired private UserService userService;
	
	@Autowired private RoleService roleService;
	
	@Autowired private NativeCache nativeCache;
	
	public void executeLogin(String username,String pwd){
		User user = userService.getUser(username, pwd);
		List<UserRole>userRoles = userService.getUserRolesById(user.getId());
		if(null == user || 0==userRoles.size()){
			return;
		}
		List<Long> roleIds = new ArrayList<Long>();
		for(UserRole userRole : userRoles){
			roleIds.add(userRole.getRoleId());
		}
		List<Role> roles = roleService.getRoles(roleIds);
		nativeCache.setRoles(user.getId(), roles);
		
		LoginUserCache.put(user);
		List<Accordion> accordions = getAccordion(false,user.getId());
		LoginUserCache.setAccordions(user.getName(), accordions);
		
	}
	
	private List<Accordion> getAccordion(boolean isAdmin , Long userId)
	{
		Set<String>permissionUrls = new HashSet<String>();
		Set<Long>rootFunctionIdSet = new HashSet<Long>();
		if(!isAdmin){
			for(Role role:nativeCache.getRoles(userId)){
				List<RoleFunctions>roleFunctions = roleService.getRoleFunctions(role.getId());
				for(RoleFunctions rf : roleFunctions){
					Functions func = nativeCache.getFunction(rf.getFunctionId());
					if(func.getAccordion() == Whether.YES.getValue()){
						rootFunctionIdSet.add(func.getId());
					}else{
						permissionUrls.add(func.getUrl());
					}
				}
			}
		}
		
		Map<Long,Accordion> accordionMap = new HashMap<Long,Accordion>();
		List<Accordion> permissionAccordionSet = new LinkedList<Accordion>();
		
		List<Functions> functions = nativeCache.getFunctions();
		
		List<Accordion> rootAccordionSet = new LinkedList<Accordion>();
		
		for(Functions function:functions){
			Accordion accordion = new Accordion(function.getId(),function.getParentId(),function.getName(),function.getUrl(),function.getSerialNum());
			accordionMap.put(function.getId(), accordion);
			if(!isAdmin){
				if(permissionUrls.contains(function.getUrl())){
					permissionAccordionSet.add(accordion);
				}
				if(rootFunctionIdSet.contains(function.getUrl())){
					rootAccordionSet.add(accordion);
				}
			}else{
				permissionAccordionSet.add(accordion);
				if(Whether.YES.getValue() == function.getAccordion()){
					rootAccordionSet.add(accordion);
				}
			}
		}
		
		Collections.sort(rootAccordionSet);
		Collections.sort(permissionAccordionSet);
		
		for(Accordion accordion : rootAccordionSet){
			completeAccordion(permissionAccordionSet,accordion);
		}
		return rootAccordionSet;
	}
	
	private void completeAccordion(List<Accordion> permissionAccordionSet,
			Accordion rootAccordion) {
		for(Accordion accordion : permissionAccordionSet){
			if(accordion.getParentId() == rootAccordion.getId()){
				rootAccordion.getChildren().add(accordion);
			}
		}
		
	}
	

}
