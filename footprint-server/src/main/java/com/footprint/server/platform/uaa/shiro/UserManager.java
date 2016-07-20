package com.footprint.server.platform.uaa.shiro;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footprint.server.common.config.ConfigHelper;
import com.footprint.server.common.config.MenuNode;
import com.footprint.server.platform.uaa.dao.RoleDao;
import com.footprint.server.platform.uaa.dao.UserDao;
import com.footprint.server.platform.uaa.entity.Role;
import com.footprint.server.platform.uaa.entity.User;

/**
 * @author:zhangjz
 * @createTime:2013-7-8
 */
@Service("userManager")
public class UserManager {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	public User queryByUsername(String username) {
		return userDao.queryByUsername(username);
	}
	
	public String getCurrentUsername() {
		Subject subject = SecurityUtils.getSubject();
		if (subject == null) {
			return null;
		}
		return (String) subject.getPrincipal();
	}

	public Long getCurrentUserId() {
		User user = getCurrentUser();
		if (user == null) {
			return null;
		}
		return user.getId();
	}

	public User getCurrentUser() {
		return userDao.queryByUsername(getCurrentUsername());
	}

	public Set<String> getCurrentPermSet() {
		Long userId = getCurrentUserId();
		if (userId == null) {
			return Collections.emptySet();
		}
		List<Role> roles = roleDao.queryListByUserId(userId);
		Set<String> ret = new HashSet<String>();
		for (Role role : roles) {
			if (role.getSuperAdmin()) {
				ret = ConfigHelper.getAllPerms();
				break;
			} else {
				List<String> perms = roleDao.queryPermListByRoleId(role.getId());
				ret.addAll(perms);
			}
		}
		return ret;
	}

	public MenuNode getCurrentMenuTree() {
		return createMenuTree(ConfigHelper.getMenuTree(), getCurrentPermSet());
	}

	private MenuNode createMenuTree(MenuNode menu, Collection<String> authzPerms) {
		MenuNode newMenu = new MenuNode();
		newMenu.setId(menu.getId());
		newMenu.setName(menu.getName());
		newMenu.setUrl(menu.getUrl());
		newMenu.setPerm(menu.isPerm());

		for (MenuNode child : menu.getChildren()) {
			if (child.isPerm()) {
				if (authzPerms.contains(child.getUrl())) {
					MenuNode newChild = new MenuNode();
					newChild.setId(child.getId());
					newChild.setName(child.getName());
					newChild.setUrl(child.getUrl());
					newChild.setPerm(child.isPerm());

					newMenu.getChildren().add(newChild);
				}
			} else {
				MenuNode cMenu = createMenuTree(child, authzPerms);

				if (cMenu.getChildren().size() > 0) {
					newMenu.getChildren().add(cMenu);
				}
			}
		}

		return newMenu;
	}
	
	public static void main(String[] args) {
		User user = null;
		System.out.println(user instanceof User);
	}
}
