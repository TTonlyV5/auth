package com.mystudy.auth.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mystudy.auth.common.BaseDao;
import com.mystudy.auth.entity.User;

/**
 * UserDao实体类
 */
@Repository
public class UserDao extends BaseDao {
    /**
     * UserRowMapper User对象的映射类
     */
    private class UserRowMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet resultSet, int index) throws SQLException {
            User user=new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
    }

    /**
     *  根据用户名密码查询用户
     * @param name  用户名
     * @param password 密码
     * @return 返回的实体，查询到的唯一用户实体
     */
    public User getUser(String name,String password){
        String sql="select * from auth_user where name =? and password =? ;";

        try {
            return jdbcTemplate.queryForObject(sql,new Object[]{name, password}, new UserRowMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存User对象
     * @param user user的实体类
     */
    public void saveUser(User user){
        String sql="insert into auth_user(name,password) values(?,?)";
        jdbcTemplate.update(sql,user.getName(),user.getPassword());// Object... args 可变长参数类型，处理时当作数组来处理
    }

    /**
     * 根据id删除记录
     * @param id 要删除的ID
     */
    public void deleteUserById(Long id){
        String sql="delete from auth_user where id= ?";
        jdbcTemplate.update(sql,id);
    }

    /**
     *  更新User对象
     * @param user  更新的user对象
     */
    public void update(User user){
        String sql="update auth_user set name=?,password=? where id= ?";
        jdbcTemplate.update(sql,user.getName(),user.getPassword(),user.getId());
    }

    /**
     * 查找User对象
     * @param id 要查找的User对象的id
     * @return  返回User对象
     */
    public User findById(Long id){
        String sql="select * from auth_user where id =?";
        try {
            return jdbcTemplate.queryForObject(sql,new Object[]{id},new UserRowMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据集合ids查找User对象
     * @param ids   集合ids元素
     * @return  返回User集合对象
     */
    public Collection<User> findByIds(Collection<Long> ids){
        StringBuilder sql=new StringBuilder("select * from auth_user where id in (");
        ids.forEach((id) -> sql.append(id).append(","));
        sql.deleteCharAt(sql.length()-1);//删除最后一个分号
        sql.append(")");
        return jdbcTemplate.query(sql.toString(),new UserRowMapper());
    }
	/**
	 * 分页查询
	 * @param page
	 * @param size
	 * @return
	 */
	public Collection<User> findPage(int page,int size)
	{
		if(page < 1){
			page = 1;
		}
		if(size <0){
			size = 20;
		}
		String sql = "select * from auth_user limit ?,?";
		int skip = (page - 1)*size;
		return jdbcTemplate.query(sql, new Object[]{skip,size},new UserRowMapper());
	}
}
