package com.metlife.nm.roles.dao;

import java.util.List;

import com.metlife.nm.roles.vo.PantallasVO;

public interface RolesDao {

	List<PantallasVO> getPantallas(String idRol);

	void updatePant(PantallasVO pant);

}
