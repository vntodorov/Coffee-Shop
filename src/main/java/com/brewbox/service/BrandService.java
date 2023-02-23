package com.brewbox.service;

import com.brewbox.model.DTOs.BrandDTO;
import com.brewbox.model.entity.BrandEntity;
import com.brewbox.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    private final ModelMapper mapper;

    @Autowired
    public BrandService(BrandRepository brandRepository, ModelMapper mapper) {
        this.brandRepository = brandRepository;
        this.mapper = mapper;
    }

    public List<BrandDTO> getAllBrands() {
        return brandRepository.
                findAll().
                stream().
                map(this::mapToBrandDTO).
                toList();
    }

    public void addBrand(BrandDTO brandDTO) {
        brandRepository.save(mapToBrand(brandDTO));
    }

    private BrandDTO mapToBrandDTO(BrandEntity brand) {
        return mapper.map(brand, BrandDTO.class);
    }

    private BrandEntity mapToBrand(BrandDTO brandDTO){
        return mapper.map(brandDTO, BrandEntity.class);
    }
}
