package server.groups;

import java.util.List;

public interface IDAO<T, K> {
	public List<T> getUsers(K key);

	public List<T> addGroup(K key);

	public void deleteGroup(T key);

	public List<T> addUser(T user, K key);

	public void deleteUser(T user, K key);

	public List<K> listAll();
}
