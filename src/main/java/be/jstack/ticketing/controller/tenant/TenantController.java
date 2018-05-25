package be.jstack.ticketing.controller.tenant;

import be.jstack.ticketing.service.tenant.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tenants")
@Api(value = "Tenant controller", description = "Add and delete tenants")
public class TenantController {

    private final TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    @ApiOperation(value = "Add a new tenant", response = String.class)
    public String addTenant(@RequestParam String tenantId, @RequestParam String tenantName) {
        return new JSONObject()
                .put("result", tenantService.addTenant(tenantId, tenantName))
                .toString();
    }

    @DeleteMapping("/{tenantId}")
    @ApiOperation(value = "Delete a tenant", response = String.class)
    public String deleteTenant(@PathVariable(value = "tenantId") String tenantId) {
        return new JSONObject()
                .put("result", tenantService.deleteTenant(tenantId))
                .toString();
    }
}