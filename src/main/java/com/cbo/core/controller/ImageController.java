package com.cbo.core.controller;

import com.cbo.core.constants.URIS;
import com.cbo.core.response.ImageRes;
import com.cbo.core.service.ImageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageController {

    private ImageService imageService;

    @GetMapping(value = URIS.AUTHORITY_IMAGES_BY_ID)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ImageRes getImage(@PathVariable("id") Long id) throws IOException {

        return imageService.getImages(id);

    }
}
