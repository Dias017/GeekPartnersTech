package kz.tech.demo.service;

import kz.tech.demo.repository.CodeRepository;
import kz.tech.demo.service.impl.GenerateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RequiredArgsConstructor
public class GenerateServiceTest {

    @Mock
    private CodeRepository codeRepository;
    private GenerateService generateService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        generateService = new GenerateServiceImpl(codeRepository);
    }

    @Test
    public void testGenerateNextCode_CodeIsZ9Z9_ReturnsA0A0A0() {
        String lastCode = "z9z9";

        String nextCode = generateService.generateNextCode(lastCode);

        Assertions.assertEquals("a0a0a0", nextCode);
    }

    @Test
    public void testGenerateNextCode_CodeIsZ9Z9Z9_ReturnsA0A0A0A0() {
        String lastCode = "z9z9z9";

        String nextCode = generateService.generateNextCode(lastCode);

        Assertions.assertEquals("a0a0a0a0", nextCode);
    }

    @Test
    public void testGenerateNextCode_LastCodeIsA0A0_ReturnsA0A1() {
        String lastCode = "a0a0";

        String nextCode = generateService.generateNextCode(lastCode);

        Assertions.assertEquals("a0a1", nextCode);
    }

    @Test
    public void testGenerateNextCode_LastCodeIsZ9_ReturnsA0A0() {
        String lastCode = "z9";

        String nextCode = generateService.generateNextCode(lastCode);

        Assertions.assertEquals("a0a0", nextCode);
    }
    @Test
    public void testGenerateNextCode_LastCodeIsa0Z9_ReturnsA1A0() {
        // Arrange
        String lastCode = "a0z9";

        // Act
        String nextCode = generateService.generateNextCode(lastCode);

        // Assert
        Assertions.assertEquals("a1a0", nextCode);
    }
    @Test
    public void testGenerateNextCode_LastCodeIsa0A9Z9_ReturnsB0A0() {
        // Arrange
        String lastCode = "a9z9";

        // Act
        String nextCode = generateService.generateNextCode(lastCode);

        // Assert
        Assertions.assertEquals("b0a0", nextCode);
    }
}