package cc.oit.bsmes.common.util;

import cc.oit.bsmes.bas.model.Resources;
import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.ResourcesService;
import cc.oit.bsmes.bas.service.RoleService;
import cc.oit.bsmes.common.constants.PropKeyConstants;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.exception.ErrorBasConfigException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SessionUtils {

	private static ThreadLocal<User> user = new ThreadLocal<User>();

	public static User getUser() {
		return user.get();
	}

	public static User getUser(HttpSession session) {
		return (User) session.getAttribute(WebConstants.USER_SESSION_KEY);
	}

	public static boolean setUser(HttpSession session) {
		if (session != null && session.getAttribute(WebConstants.USER_SESSION_KEY) != null) {
			user.set((User) session.getAttribute(WebConstants.USER_SESSION_KEY));
			return true;
		} else {
			return false;
		}
	}

	public static boolean login(HttpSession session) {
		boolean result = true;
		if (session != null && session.getAttribute(WebConstants.USER_SESSION_KEY) != null) {
			result = result && setUser(session);
			result = result && setUserRoles(session);
			result = result && setResourcesMap(session, false);
			return result;
		} else {
			return false;
		}
	}

	public static void logout(HttpSession session) {
		session.removeAttribute(WebConstants.USER_SESSION_KEY);
		session.removeAttribute(WebConstants.USER_ROLES_SESSION_KEY);
		session.removeAttribute(WebConstants.USER_ROLES_SESSION_KEY);
	}

	// 增加用户拥有的角色
	private static boolean setUserRoles(HttpSession session) {
		if (session != null && session.getAttribute(WebConstants.USER_SESSION_KEY) != null) {
			RoleService roleService = (RoleService) ContextUtils.getBean(RoleService.class);
			User user = getUser();
			List<Role> roleList = roleService.getByUserId(user.getId());
			user.setRoleList(roleList);
			session.setAttribute(WebConstants.USER_ROLES_SESSION_KEY, roleList);
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Role> getUserRoles(HttpSession session) {
		if (session.getAttribute(WebConstants.USER_ROLES_SESSION_KEY) != null) {
			return (List<Role>) session.getAttribute(WebConstants.USER_ROLES_SESSION_KEY);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<Resources> getUserResources(HttpSession session) {
		if (session.getAttribute(WebConstants.USER_RESOURCES_SESSION_KEY) != null) {
			return (List<Resources>) session.getAttribute(WebConstants.USER_RESOURCES_SESSION_KEY);
		}
		return null;
	}

	// 增加用户所能访问的菜单
	private static boolean setResourcesMap(HttpSession session, boolean checkIfEmpty) {
		if (checkIfEmpty && session.getAttribute(WebConstants.USER_RESOURCES_FOR_PAGE) != null) {
			return true;
		}
		if (getUserResources(session) == null) {
			if (getUserRoles(session) != null) {
				List<Role> roleList = (List<Role>) getUserRoles(session);
				if (!roleList.isEmpty()) {
					List<String> roleIds = new ArrayList<String>();
					for (Role role : (List<Role>) getUserRoles(session)) {
						roleIds.add(role.getId());
					}
					ResourcesService resourcesService = (ResourcesService) ContextUtils.getBean(ResourcesService.class);
					List<Resources> resources = resourcesService.getByRoleIds(roleIds);

					JSONArray results = new JSONArray();
					ConcurrentMap<String, List<Resources>> map = new ConcurrentHashMap<String, List<Resources>>();
					for (Resources resource : resources) {
						if (resource.getType().equals("MENU")) {
							if (map.get(resource.getParentId()) != null) {
								map.get(resource.getParentId()).add(resource);
							} else {
								List<Resources> list = new ArrayList<Resources>();
								list.add(resource);
								map.put(resource.getParentId(), list);
							}
						}
					}
					String parentId = WebConstants.ROOT_ID;
					List<Resources> list = map.get(parentId);
					if (list == null) {
						return false;
					}
					Collections.sort(list, new Comparator<Resources>() {
						@Override
						public int compare(Resources o1, Resources o2) {
							if (o1.getSeq() == null && o2.getSeq() == null) {
								return 0;
							} else if (o1.getSeq() == null) {
								return -1;
							} else if (o2.getSeq() == null) {
								return 1;
							} else {
								return o1.getSeq() - o2.getSeq();
							}
						}
					});

					for (Resources resource : list) {
						JSONObject item = new JSONObject();
						item.put("text", resource.getName());
						parentId = resource.getId();
						loadChilds(map, parentId, item, 0);
						results.add(item);
					}
					session.setAttribute(WebConstants.USER_RESOURCES_SESSION_KEY, resources);
					session.setAttribute(WebConstants.USER_RESOURCES_FOR_PAGE, results);
				}

				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}

	}

	private static void loadChilds(ConcurrentMap<String, List<Resources>> map, String parentId, JSONObject parent, int level) {
		String maxDepth = WebContextUtils.getPropValue(PropKeyConstants.MENU_MAX_DEPTH);
		int menuMaxDepth;
		try {
			menuMaxDepth = Integer.parseInt(maxDepth);
		} catch (NumberFormatException e) {
			throw new ErrorBasConfigException();
		}
		if (level >= menuMaxDepth) {
			return;
		}
		List<Resources> list = map.get(parentId);
		if (list == null || list.isEmpty()) {
			return;
		}
		Collections.sort(list, new Comparator<Resources>() {
			@Override
			public int compare(Resources o1, Resources o2) {
				if (o1.getSeq() == null && o2.getSeq() == null) {
					return 0;
				} else if (o1.getSeq() == null) {
					return -1;
				} else if (o2.getSeq() == null) {
					return 1;
				} else {
					return o1.getSeq() - o2.getSeq();
				}
			}
		});

		JSONObject menu = new JSONObject();
		parent.put("menu", menu);
		JSONArray items = new JSONArray();
		menu.put("items", items);
		for (Resources resource : list) {
			JSONObject item = new JSONObject();
			item.put("text", resource.getName());
			item.put("href",
					"javascript:openTab(&#39;" + resource.getName() + "&#39;, &#39;" + resource.getUri() + "?add=" + resource.getRoleCreate()
							+ "&amp;edit=" + resource.getRoleEdit() + "&amp;remove=" + resource.getRoleDelete() + "&#39;)");
			parentId = resource.getId();
			loadChilds(map, parentId, item, level + 1);
			items.add(item);
		}
	}

	public static void setUser(User parm) {
		user.set(parm);
	}

}