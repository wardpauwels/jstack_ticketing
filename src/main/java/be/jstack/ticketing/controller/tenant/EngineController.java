package be.jstack.ticketing.controller.tenant;

import be.jstack.ticketing.service.tenant.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/engines")
@Api(value = "Engine controller", description = "Get info about and manage the camunda engines")
public class EngineController {

    private final TenantService tenantService;

    @Autowired
    public EngineController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping(value = "/start")
    @ApiOperation(value = "Start a new engine", response = String.class)
    public String startEngine(@RequestParam String engineId) {
        return new JSONObject()
                .put("result", tenantService.startProcessEngine(engineId))
                .toString();
    }

    @PostMapping(value = "/startall")
    @ApiOperation(value = "Start all the engines", response = String.class)
    public String startEngines() {
        return new JSONObject()
                .put("result", tenantService.startProcessEngines())
                .toString();
    }

    @PostMapping(value = "/stop")
    @ApiOperation(value = "Stop an engine", response = String.class)
    public String stopEngine(@RequestParam String engineId) {
        return new JSONObject()
                .put("result", tenantService.stopProcessEngine(engineId))
                .toString();
    }

    @PostMapping(value = "/stopall")
    @ApiOperation(value = "Stop all the engines", response = String.class)
    public String stopEngines() {
        return new JSONObject()
                .put("result", tenantService.stopProcessEngines())
                .toString();
    }

    @PostMapping(value = "/deployprocess")
    @ApiOperation(value = "Deploy a process to an engine", response = String.class)
    public String deployProcessToEngine(@RequestParam String processKey, @RequestParam String engineId) {
        return new JSONObject()
                .put("result", tenantService.deployProcessToEngine(processKey, engineId))
                .toString();
    }
}
