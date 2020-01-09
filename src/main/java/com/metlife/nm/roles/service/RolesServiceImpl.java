package com.metlife.nm.roles.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.roles.dao.RolesDao;
import com.metlife.nm.roles.vo.PantallasVO;

@Service(value="RolesService")
public class RolesServiceImpl implements RolesService{

    @Resource(name = BeanNames.RolesDao)
    protected RolesDao dao;

    public List<PantallasVO> getPantallas(String idRol) {
        return dao.getPantallas(idRol);
    }

    public void updatePant(PantallasVO pant) {
        dao.updatePant(pant);
    }

   
    
   
}
