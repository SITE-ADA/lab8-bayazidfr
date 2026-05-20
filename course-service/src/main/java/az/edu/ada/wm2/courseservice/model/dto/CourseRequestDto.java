package az.edu.ada.wm2.courseservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {

    @Schema(description = "Kursun adı", example = "Data Structures")
    @NotBlank(message = "Title is required")
    private String title;

    @Schema(description = "Kursun kodu", example = "CS201")
    @NotBlank(message = "Code is required")
    private String code;

    @Schema(description = "Kredit sayı", example = "4")
    @Positive(message = "Credits must be positive")
    private Integer credits;

    @Schema(description = "Prerequisite kursun ID-si. Əgər prerequisite yoxdursa null göndərilir.", example = "1")
    private Long prerequisiteCourseId;
}
