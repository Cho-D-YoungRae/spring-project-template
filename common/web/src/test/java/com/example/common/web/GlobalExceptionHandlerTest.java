package com.example.common.web;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(GlobalExceptionHandlerTest.ExceptionTestController.class)
@WebMvcTest(GlobalExceptionHandlerTest.ExceptionTestController.class)
class GlobalExceptionHandlerTest extends AbstractWebTest {

    @Test
    void requestBodyValidation() throws Exception {
        // given
        final ValidationDto dto = new ValidationDto(null, "content");

        // expected
        mockMvc.perform(post("/method-argument-not-valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Slf4j
    @RestController
    static class ExceptionTestController {

        @PostMapping("/method-argument-not-valid")
        public void methodArgumentNotValidException(@RequestBody @Validated final ValidationDto dto) {
            log.info("dto={}", dto);
        }

    }

    record ValidationDto(
            @NotNull String title,
            String content
    ) { }
}

