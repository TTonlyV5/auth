package com.mystudy.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mystudy.auth.common.AjaxResult;
import com.mystudy.auth.common.tree.Node;
import com.mystudy.auth.common.tree.Tree;
import com.mystudy.auth.context.NativeCache;
import com.mystudy.auth.entity.Functions;
import com.mystudy.auth.service.FunctionsService;

@Controller
@RequestMapping("/functions")
public class FunctionsController {
	
	@Autowired
	private FunctionsService functionsService;
	
	@Autowired NativeCache nativeCache;
	/**
	 * 菜单首页
	 * @return
	 */
	@RequestMapping(value="/index")
	public String menuList(){
		return "function_list";
	}
	/**
	 * 增加功能（菜单）
	 * @param function
	 */
	@RequestMapping("/addFunction")
	@ResponseBody
	public AjaxResult addFunction(Functions functions){
		if(null == functions.getId()){
			functions.setSerialNum(nativeCache.getFunctions().size());
			functionsService.addFunction(functions);
			nativeCache.addFunction(functions);
		}else{
			functionsService.updateUrl(functions.getId(), functions.getUrl());
			nativeCache.replaceFunction(functions);
		}
		return AjaxResult.success();
		
	}
	
	/**
	 * 查询全部功能
	 */
	@RequestMapping("/findAllFunctions")
	@ResponseBody
	public void findAllFunctions(){
		functionsService.findAllFunctions();
	}
	
	/**
	 * 根据id删除功能
	 * @param id
	 */
	@RequestMapping("/deleteFunctionsById")
	@ResponseBody
	public AjaxResult deleteFunctionsById(Long id){
		functionsService.deleteFunctionsById(id);
		nativeCache.removeFunction(id);
		return AjaxResult.success();
	}
	/**
	 * 得到子菜单功能集合
	 * @param page 当前页码
	 * @param size 每页记录数
	 * @param parentId 父节点ID
	 * @return 子功能信息集合
	 */
	@RequestMapping("/getSubFunctions")
	@ResponseBody
	public List<Functions> getSubFunctions(int page,int size,Long parentId){
		if(null == parentId){
			parentId = 0L;
		}
		return functionsService.getFunctions(page, size, parentId);
	}
	/**
	 * 构建用于新建、修改使用的菜单（功能）树
	 * @return 包含父子关系的树型节点集合
	 */
	@RequestMapping("/buildMenuTreeForEdit")
	@ResponseBody
	public List<Node> buildMenuTreeForEdit(){
		List<Functions>functionList = nativeCache.getFunctions();
		Tree tree = new Tree(functionList);
		return tree.build();
		
	}
	
	

}
