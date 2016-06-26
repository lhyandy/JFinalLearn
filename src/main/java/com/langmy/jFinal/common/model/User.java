package com.langmy.jFinal.common.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.langmy.jFinal.common.utils.PasswordHelper;
import com.langmy.jFinal.common.utils.StrUtil;
import com.langmy.jFinal.common.model.base.BaseUser;
import java.util.List;

/**
 * Generated by JFinal.
 * 数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
@SuppressWarnings("serial")
public class User extends BaseUser<User> {
    public static final User dao = new User();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    public User localLogin(String email, String password) {
        return super.findFirst("select * from sec_user where email = ? and password = ?", email, password);
    }

    public User findByEmail(String email) {
        return super.findFirst("select * from sec_user where email = ?", email);
    }

    public User findByNickname(String nickname) {
        return super.findFirst("select * from sec_user where nickname = ?", nickname);
    }
    public User findByToken(String token) {
        return super.findFirst("select u.* from sec_user u where u.token = ?", token);
    }
    // 查询连续签到次数
//    public Integer findDayByAuthorId(String authorId) {
//        String startTime = DateUtil.formatDateTime(new Date(), DateUtil.FORMAT_DATE);
//        String endTime = DateUtil.formatDateTime(new Date(), DateUtil.FORMAT_DATE) + " 23:59:59";
//        return Db.queryInt("select max(day) from jfbbs_mission where author_id = ? and (in_time between ? and ?)", authorId, startTime, endTime);
//    }

    public int countUsers() {
        return super.find("select id from sec_user").size();
    }

    // ---------- 后台查询方法 -------
    public Page<User> page(int pageNumber, int pageSize, String nickname, String email) {
        StringBuffer condition = new StringBuffer();
        if (!StrUtil.isBlank(nickname)) {
            condition.append(" and username like \"%" + nickname + "%\" ");
        }
        if (!StrUtil.isBlank(email)) {
            condition.append(" and email like \"%" + email + "%\" ");
        }
        return super.paginate(pageNumber, pageSize, "select * ",
                "from sec_user where 1 = 1 " + condition + " order by id desc");
    }

    public List<User> list() {
        return super.find("select * from sec_user");
    }

//    public List<User> findToday() {
//        String start = DateUtil.formatDate(new Date()) + " 00:00:00";
//        String end = DateUtil.formatDate(new Date()) + " 23:59:59";
//        return super.find("select nickname, email, qq_nickname, sina_nickname, qq_avatar, sina_avatar, in_time " +
//                "from jfbbs_user where in_time between ? and ? order by in_time desc", start, end);
//    }


    public Page<User> page(Integer pageNumber, Integer pageSize) {
        return super.paginate(pageNumber, pageSize, "select * ", "from sec_user order by id desc");
    }

    public User findByUsername(String username) {
        return super.findFirst("select * from sec_user where username = ?", username);
    }

    public void correlationRole(Integer userId, Integer[] roles) {
        //先删除已经存在的关联
        Db.update("delete from sec_user_role where user_id = ?", userId);
        //建立新的关联关系
        for (Integer rid : roles) {
            UserRole userRole = new UserRole();
            userRole.set("user_id", userId)
                    .set("role_id", rid)
                    .save();
        }
    }
    public User createUser(User user){
        //对用户密码进行加密
        PasswordHelper.encryptPassword(user);
        if(user.save())
            return user;
        throw new RuntimeException("创建用户异常");
    }
    public  boolean changePassword(Long id,String newPassword){
        User user=User.dao.findById(id);
        if(null==user){
            throw new RuntimeException("修改的用户ID不存在");
        }
        PasswordHelper.encryptPassword(user);
        return user.save( );
    }


}