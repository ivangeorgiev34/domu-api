package com.ivangeorgiev.domuapi.web.controllers;

import com.ivangeorgiev.domuapi.core.services.BuildingService;
import com.ivangeorgiev.domuapi.data.dtos.building.BuildingDto;
import com.ivangeorgiev.domuapi.data.request.building.AssignCompanyRequest;
import com.ivangeorgiev.domuapi.data.request.building.create.CreateBuildingRequest;
import com.ivangeorgiev.domuapi.data.request.building.edit.EditBuildingRequest;
import com.ivangeorgiev.domuapi.web.utils.ApiResponse;
import com.ivangeorgiev.domuapi.web.utils.ApiResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/buildings")
public class BuildingController{

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BuildingDto>> createBuilding(@Valid @RequestBody CreateBuildingRequest request) {

        BuildingDto building = this.buildingService.create(request);

        return ApiResponseFactory.create("Building created successfully", HttpStatus.CREATED, building);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteBuilding(@RequestParam(name = "id") UUID id) {

        this.buildingService.delete(id);

        return ApiResponseFactory.create("Delete building successfully", HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<BuildingDto>> editBuilding(@RequestParam(name = "id") UUID id, @Valid @RequestBody EditBuildingRequest request) {

        BuildingDto building = this.buildingService.edit(id, request);

        return ApiResponseFactory.create("Building edited successfully", HttpStatus.OK, building);
    }

    @PostMapping(value = "/{buildingId}/assign-company")
    public ResponseEntity<ApiResponse<BuildingDto>> assignCompany(@PathVariable("buildingId") UUID buildingId, @Valid @RequestBody AssignCompanyRequest request){

        BuildingDto building = this.buildingService.assignCompany(buildingId, request);

        return ApiResponseFactory.create("Building assigned to company successfully", HttpStatus.OK, building);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BuildingDto>>> getAll(){

        List<BuildingDto> buildings = this.buildingService.getAll();

        return ApiResponseFactory.create("Buildings retrieved", HttpStatus.OK, buildings);
    }
}
