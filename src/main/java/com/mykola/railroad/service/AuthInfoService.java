package com.mykola.railroad.service;

import com.mykola.railroad.dto.TypeACL;
import com.mykola.railroad.mapper.TypeACLMapper;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mykola.railroad.security.AuthenticatedUserInfo;

import java.util.List;

import static com.mykola.railroad.db.public_.Tables.EMPLOYEE;
import static com.mykola.railroad.db.public_.Tables.EMPLOYEE_ACL;

@Service
public class AuthInfoService {
    @Autowired
    private DSLContext dsl;
    @Autowired
    private TypeACLMapper aclMapper;

    public AuthenticatedUserInfo getAuthenticatedEmployeeInfo(String email) {
        Record2<Integer, String> r2 = dsl
                .select(EMPLOYEE.ID, EMPLOYEE.PASSWORD)
                .from(EMPLOYEE)
                .where(EMPLOYEE.EMAIL.eq(email))
                .fetchOne();
        Integer id = r2.get(EMPLOYEE.ID);
        String password = r2.get(EMPLOYEE.PASSWORD);

        List<TypeACL> acls = dsl
                .select(EMPLOYEE_ACL.ACL)
                .from(EMPLOYEE_ACL)
                .where(EMPLOYEE_ACL.EMPLOYEE.eq(id))
                .fetch()
                .map(r -> aclMapper.toDto(r.get(EMPLOYEE_ACL.ACL)));

        return new AuthenticatedUserInfo(id, email, password, acls);
    }
}
