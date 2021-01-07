package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.Product;
import ru.geekbrains.repository.ProductRepository;
import ru.geekbrains.service.PictureService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/picture")
public class PictureController {

    private static final Logger logger = LoggerFactory.getLogger(PictureController.class);
    private final PictureService pictureService;
    private ProductRepository productRepository;
    @Autowired
    public PictureController(PictureService pictureService, ProductRepository productRepository) {
        this.pictureService = pictureService;
        this.productRepository = productRepository;
    }

    @GetMapping("/{pictureId}")
    public void downloadProductPicture(@PathVariable("pictureId") Long pictureId, HttpServletResponse resp) throws IOException {
        logger.info("Downloading picture with id: {}", pictureId);
        Optional<String> opt = pictureService.getPictureContentTypeById(pictureId);
        if (opt.isPresent()) {
            resp.setContentType(opt.get());
            resp.getOutputStream().write(pictureService.getPictureDataById(pictureId).get());
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("deleteimg/{pictureId}")
    public void deleteImage(@PathVariable(value = "pictureId") Long pictureId
                        ,HttpServletRequest request
                        ,HttpServletResponse response) throws IOException {
        pictureService.deletePictureByIdLike(pictureId);
        response.sendRedirect((request.getHeader("referer")));
    }
}
