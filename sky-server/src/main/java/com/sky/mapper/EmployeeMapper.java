package com.sky.mapper;

import com.sky.entity.Employee;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /*员工信息插入语句 */
    @Insert("insert into employee (name, username, password, phone, sex, id_number, create_time, update_time, update_user, create_user) " +
            "values (#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{updateUser}, #{createUser})")
    void insert(Employee employee);

}
