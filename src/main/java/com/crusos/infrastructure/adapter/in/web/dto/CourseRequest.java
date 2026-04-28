package com.crusos.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import lombok.Data;

@Data
public class CourseRequest {
    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    private String imageUrl;

    @NotBlank(message = "La URL del curso es obligatoria")
    @URL(protocol = "http", message = "El formato de la URL es incorrecto, debe incluir http o https")
    private String courseUrl;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoryId;
}
