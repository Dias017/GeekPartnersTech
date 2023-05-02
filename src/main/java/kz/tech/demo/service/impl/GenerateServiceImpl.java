package kz.tech.demo.service.impl;

import kz.tech.demo.model.entity.CodeEntity;
import kz.tech.demo.repository.CodeRepository;
import kz.tech.demo.service.GenerateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenerateServiceImpl implements GenerateService {

    private final CodeRepository codeRepository;

    @Override
    public String generateCode() {
        Optional<CodeEntity> codeEntity = codeRepository.findTopByOrderByCodeDesc();
        if (codeEntity.isEmpty()) {
            throw new NullPointerException("No data in database.");
        }
        String nextCode = generateNextCode(codeEntity.get().getCode());

        codeRepository.save(new CodeEntity(nextCode));
        return nextCode;
    }

    public String generateNextCode(String code) {
        StringBuilder codeBuilder = new StringBuilder(code);
        StringBuilder codeLast = new StringBuilder();
        String pattern = "^[z9]+$";

        int index = codeBuilder.length() - 2;
        while (index >= 0) {
            if (codeBuilder.substring(index).matches(pattern)) {
                index -= 2;
            } else {
                if (codeBuilder.charAt(index + 1) != '9') {
                    codeLast.append(codeBuilder.substring(0, index + 1));
                    codeLast.append((Integer.valueOf(String.valueOf(codeBuilder.charAt(index + 1))) + 1));
                    if (index + 1 != codeBuilder.length()) {
                        codeLast.append("a0".repeat((codeBuilder.length() - index - 2) / 2));
                    }
                    return codeLast.toString();
                }
                if (codeBuilder.charAt(index) != 'z') {
                    codeLast.append(codeBuilder.substring(0, index));
                    codeLast.append((char) (codeBuilder.charAt(index) + 1));
                    codeLast.append(0);
                    if (index + 1 != codeBuilder.length()) {
                        codeLast.append("a0".repeat((codeBuilder.length() - index - 2) / 2));
                    }
                    return codeLast.toString();
                }

            }
        }
        return "a0".repeat((codeBuilder.length() / 2) + 1);
    }
}