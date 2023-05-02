package kz.tech.demo.controllers;


import kz.tech.demo.service.GenerateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
@RequiredArgsConstructor
public class CodeController {

    private final GenerateService generateService;

    @GetMapping("/generate")
    public String generateCode() {
        return generateService.generateCode();
    }
}

