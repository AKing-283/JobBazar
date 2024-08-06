package com.aking.Job.portal.services;


import com.aking.Job.portal.entity.UserType;
import com.aking.Job.portal.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UserType> getAll(){
        return usersTypeRepository.findAll();
    }
}
