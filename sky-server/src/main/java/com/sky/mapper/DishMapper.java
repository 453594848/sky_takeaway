package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from sky_take_out.dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /*
     * 新增菜品
     * */
    @AutoFill(value = OperationType.INSERT)
    @ApiOperation("新增菜品")
    void insert(Dish dish);

    /*
     * 菜品分页查询
     * */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /*
     * 根据ID查询菜品
     * */
    @Select("select * from sky_take_out.dish where id=#{id}")
    Dish getById(Long id);

    /*
     * 根据ID删除菜品
     * */
    @Delete("delete from sky_take_out.dish where id =#{id}")
    void deleteByID(Long id);

    /*
     * 修改菜品
     * */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);
}
