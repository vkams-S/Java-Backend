package com.employee.management.manager;


import com.employee.management.entites.Employee;
import com.employee.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;
//    @Autowired
//    HrRepository hrRepository;

    @Override
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmployeeId(employeeId).orElseThrow(()->
                new UsernameNotFoundException("username not found"));
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return employee.getRoles();
            }

            @Override
            public String getPassword() {
                return employee.getPassword();
            }

            @Override
            public String getUsername() {
                return employee.getEmployeeId();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
        return userDetails;
    }
}
