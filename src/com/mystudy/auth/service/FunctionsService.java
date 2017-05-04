package com.mystudy.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mystudy.auth.dao.FunctionsDao;
import com.mystudy.auth.entity.Functions;

@Service
public class FunctionsService {

	@Autowired private FunctionsDao functionsDao;
	/**
	 * 增加功能
	 * @param functions
	 */
	public void addFunction(Functions functions){
		functionsDao.saveFunctions(functions);
	}
	/**
	 * 根据功能id跟新其url信息
	 * @param id 功能id
	 * @param url URL
	 */
	public void updateUrl(Long id,String url){
		functionsDao.updateUrl(id, url);
	}
	/**
	 * 根据id删除功能
	 * @param id 功能id
	 */
	public void deleteFunctionsById(Long id) {
		functionsDao.deleteById(id);
	}
	/**
	 * 分页查询指定父节点的子节点
	 * @param page 当前页码
	 * @param size 每页记录数
	 * @param parentId 父节点id
	 * @return 功能集合
	 */
	public List<Functions> getFunctions(int page,int size,Long parentId) {
		return functionsDao.findFunctions(page, size, parentId);
	}
	/**
	 * 查询全部功能信息
	 * @return 全部功能信息
	 */
	public List<Functions> findAllFunctions() {
		return functionsDao.findALlFunctions();
	}
}
