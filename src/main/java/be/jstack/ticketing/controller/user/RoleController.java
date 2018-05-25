package be.jstack.ticketing.controller.user;

import be.jstack.ticketing.entities.user.Role;
import be.jstack.ticketing.service.user.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Api(value = "Role controller", description = "View info about all the existing user roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @ApiOperation(value = "View all the user roles", response = Role.class, responseContainer = "List")
    public List<Role> findAllRoles() {
        return Lists.newArrayList(roleService.findAllRoles());
    }
}