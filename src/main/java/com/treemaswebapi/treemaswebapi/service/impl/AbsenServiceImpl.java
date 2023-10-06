package com.treemaswebapi.treemaswebapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.service.AbsenService;

@Service
public class AbsenServiceImpl implements AbsenService {
    
    @Autowired
    AbsenRepository absenRepository;

    @Override
    public void absenUser(AbsenEntity absenData){
        System.out.println(absenData);
        absenRepository.save(absenData);
    }
}
