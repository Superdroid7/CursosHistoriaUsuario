package com.crusos.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequest {
    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotBlank(message = "La imagen URL es obligatoria")
    private String imageUrl;

    @NotBlank(message = "La URL del curso es obligatoria")
    private String courseUrl;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoryId;
}
