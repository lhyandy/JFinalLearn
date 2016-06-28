package com.langmy.jFinal.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAdminLog<M extends BaseAdminLog<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setUid(java.lang.Integer uid) {
		set("uid", uid);
	}

	public java.lang.Integer getUid() {
		return get("uid");
	}

	public void setTargetId(java.lang.String targetId) {
		set("target_id", targetId);
	}

	public java.lang.String getTargetId() {
		return get("target_id");
	}

	public void setSource(java.lang.String source) {
		set("source", source);
	}

	public java.lang.String getSource() {
		return get("source");
	}

	public void setInTime(java.util.Date inTime) {
		set("in_time", inTime);
	}

	public java.util.Date getInTime() {
		return get("in_time");
	}

	public void setAction(java.lang.String action) {
		set("action", action);
	}

	public java.lang.String getAction() {
		return get("action");
	}

	public void setMessage(java.lang.String message) {
		set("message", message);
	}

	public java.lang.String getMessage() {
		return get("message");
	}

}