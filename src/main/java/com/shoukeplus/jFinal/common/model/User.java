package com.shoukeplus.jFinal.common.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.model.base.BaseUser;
import com.shoukeplus.jFinal.common.utils.DateUtil;
import com.shoukeplus.jFinal.common.utils.StrUtil;

import java.util.Date;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class User extends BaseUser<User> {
	public static final User dao = new User();
	public User findByOpenID(String openId, String type) {
		String sql = "";
		if (type.equalsIgnoreCase(AppConstants.QQ)) {
			sql = "select u.* from sk_user u where u.qq_open_id = ?";
		} else if (type.equalsIgnoreCase(AppConstants.SINA)) {
			sql = "select u.* from sk_user u where u.sina_open_id = ?";
		}
		return super.findFirst(sql, openId);
	}

	public User findByToken(String token) {
		return super.findFirst("select u.* from sk_user u where u.token = ?", token);
	}

	public List<User> findBySize(int size) {
		return super.find("select u.*, (select count(t.id) from sk_topic t where t.isdelete = 0 and t.author_id = u.id) as topic_count, " +
				"(select count(r.id) from sk_reply r where r.author_id = u.id) as reply_count " +
				"from sk_user u order by u.score desc limit 0, ?", size);
	}

	public User localLogin(String email, String password) {
		return super.findFirst("select * from sk_user where email = ? and password = ?", email, password);
	}

	public User findByEmail(String email) {
		return super.findFirst("select * from sk_user where email = ?", email);
	}

	public User findByNickname(String nickname) {
		return super.findFirst("select * from sk_user where nickname = ?", nickname);
	}

	// 查询连续签到次数
	public Integer findDayByAuthorId(String authorId) {
		String startTime = DateUtil.formatDateTime(new Date(), DateUtil.FORMAT_DATE);
		String endTime = DateUtil.formatDateTime(new Date(), DateUtil.FORMAT_DATE) + " 23:59:59";
		return Db.queryInt("select max(day) from sk_mission where author_id = ? and (in_time between ? and ?)", authorId, startTime, endTime);
	}

	public int countUsers() {
		return super.find("select id from sk_user").size();
	}

	// ---------- 后台查询方法 -------
	public Page<User> page(int pageNumber, int pageSize, String nickname, String email) {
		StringBuffer condition = new StringBuffer();
		if (!StrUtil.isBlank(nickname)) {
			condition.append(" and nickname like \"%" + nickname + "%\" ");
		}
		if (!StrUtil.isBlank(email)) {
			condition.append(" and email like \"%" + email + "%\" ");
		}
		return super.paginate(pageNumber, pageSize, "select * ",
				"from sk_user where 1 = 1 " + condition + " order by in_time desc");
	}

	public List<User> list() {
		return super.find("select * from sk_user");
	}

	public List<User> findToday() {
		String start = DateUtil.formatDate(new Date()) + " 00:00:00";
		String end = DateUtil.formatDate(new Date()) + " 23:59:59";
		return super.find("select nickname, email, qq_nickname, sina_nickname, qq_avatar, sina_avatar, in_time " +
				"from sk_user where in_time between ? and ? order by in_time desc", start, end);
	}
}
