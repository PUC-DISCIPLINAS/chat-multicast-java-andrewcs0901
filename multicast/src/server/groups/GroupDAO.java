package server.groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GroupDAO implements IDAO<String, String> {

	private static final Map<String, List<String>> groups = new TreeMap<>();

	@Override
	public List<String> getUsers(String key) {
		return groups.get(key);
	}

	@Override
	public List<String> addUser(String user, String group) {
		List<String> usersGroup = getUsers(group);
		usersGroup.add(user);
		return groups.put(group, usersGroup);
	}

	@Override
	public void deleteUser(String user, String group) {
		List<String> usersGroup = getUsers(group);
		usersGroup.remove(user);
		groups.put(group, usersGroup);
	}

	@Override
	public synchronized List<String> addGroup(String key) {
		List<String> newGroup = new ArrayList<String>();
		if (!groups.containsKey(key)) {
			groups.put(key, new ArrayList<String>());
			newGroup.add(key);
			return newGroup;
		}
		return newGroup;
	}

	@Override
	public synchronized void deleteGroup(String key) {
		if (groups.remove(key) != null)
			System.out.println("Exclusão realizada com sucesso");
		else
			System.out.println("Grupo não encontrado");
	}

	@Override
	public List<String> listAll() {
		return new ArrayList<String>(groups.keySet());
	}

}
