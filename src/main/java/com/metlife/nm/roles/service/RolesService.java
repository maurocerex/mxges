package com.metlife.nm.roles.service;

import java.util.List;

import com.metlife.nm.roles.vo.PantallasVO;

public interface RolesService {

	List<PantallasVO> getPantallas(String idRol);

	void updatePant(PantallasVO pant);

}
