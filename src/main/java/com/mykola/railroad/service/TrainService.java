package com.mykola.railroad.service;

import com.mykola.railroad.mapper.TrainMapper;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainService {
    @Autowired
    private DSLContext dsl;

    @Autowired
    private TrainMapper trainMapper;
}
