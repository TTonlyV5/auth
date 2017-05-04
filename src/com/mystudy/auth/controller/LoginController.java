package com.mystudy.auth.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mystudy.auth.common.Whether;
import com.mystudy.auth.context.LoginUserCache;
import com.mystudy.auth.context.NativeCache;
import com.mystudy.auth.context.UserContext;
import com.mystudy.auth.dto.Accordion;
import com.mystudy.auth.entity.Functions;
import com.mystudy.auth.entity.Role;
import com.mystudy.auth.entity.RoleFunctions;
import com.mystudy.auth.entity.User;
import com.mystudy.auth.entity.UserRole;
import com.mystudy.auth.service.RoleService;
import com.mystudy.auth.service.UserService;

@Controller
public class LoginController {

	@Autowired 
	private UserService userService;
	@Autowired 
	private NativeCache nativeCache;
	@Autowired 
	private RoleService roleService;
	
	@RequestMapping(value="",method=RequestMethod.GET)//value的值改成/index会进不去，不知道为什么
	public ModelAndView index() {
		if (UserContext.getCurrent()!=null&&UserContext.getCurrent().getUser()!=null) {
			return new ModelAndView("/login");
		}
		return new ModelAndView("/login");
	}
	
//	public String Index(){
//	if (UserContext.getCurrent()!=null&&UserContext.getCurrent().getUser()!=null) {
//		return "index";
//	}
//	return "index";
//}

	/**
	 *  
	 * @param model 控制层返回给视图层的对象
	 * @param name
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/main",method=RequestMethod.POST)//value对应login.jsp中的action的值
	public String login(Model model,String name,String password) {
		User user =userService.getUser(name, password);
		if (user==null) {
			return "/login";
		}
		try {
			
		
		LoginUserCache.put(user);
		
		if (Objects.equals("admin", user.getName())) {
			model.addAttribute("accordions",getAccordion(true,null));
		}else {
			List<UserRole> userRoles=userService.getUserRolesById(user.getId());
			if (null==userRoles||userRoles.size()==0) {
				return "/login";
			}
			List<Long> roleIds=new ArrayList<>();
			for (UserRole userRole : userRoles) {
				roleIds.add(userRole.getRoleId());
			}
			List<Role> roles=roleService.getRoles(roleIds);
			nativeCache.setRoles(user.getId(), roles);		
			LoginUserCache.put(user);
			List<Accordion> accordions=getAccordion(false, user.getId());
			model.addAttribute("accordions",accordions);//返回accordions信息
			LoginUserCache.setAccordions(user.getName(), accordions);
		}	
		model.addAttribute("user", userService.getUser(name, password));//返回登陆用户的信息
		return "/main";
		} 
		catch (Exception e) {
			e.printStackTrace();
			LoginUserCache.remove(user.getName());
			return "/login";		
		}
	}

	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(){
		if (UserContext.getCurrent()!=null&&UserContext.getCurrent().getUser()!=null) {
			LoginUserCache.remove(UserContext.getCurrent().getUser().getName());
		}
		return "redirect:/login";		
	}
	
	private List<Accordion> getAccordion(boolean isAdmin , Long userId)
	{
		Set<String>permissionUrls = new HashSet<String>();
		Set<Long>rootFunctionIdSet = new HashSet<Long>();
		if(!isAdmin){
			for(Role role:nativeCache.getRoles(userId)){
				List<RoleFunctions>roleFunction = roleService.getRoleFunctions(role.getId());
				for(RoleFunctions rf : roleFunction){
					Functions func = nativeCache.getFunction(rf.getFunctionId());
					if(Objects.equals(func.getAccordion(),Whether.YES.getValue())){
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
