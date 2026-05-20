package az.edu.ada.wm2.courseservice.controller;

import az.edu.ada.wm2.courseservice.model.dto.CourseRequestDto;
import az.edu.ada.wm2.courseservice.model.dto.CourseResponseDto;
import az.edu.ada.wm2.courseservice.model.dto.CourseStudentsResponseDto;
import az.edu.ada.wm2.courseservice.model.dto.EnrollmentResponseDto;
import az.edu.ada.wm2.courseservice.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Tag(name = "Kurslar", description = "Kursların və kursa yazılmaların idarə edilməsi üçün endpointlər")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @Operation(summary = "Yeni kurs yaratmaq", description = "Sistemdə yeni kurs yaradır.")
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseRequestDto requestDto) {
        CourseResponseDto createdCourse = courseService.createCourse(requestDto);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Bütün kursları əldə etmək", description = "Sistemdə olan bütün kursları qaytarır.")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }
    
    @GetMapping("/by-student-name")
    @Operation(
            summary = "Tələbə adına görə kursları əldə etmək",
            description = "Adı və ya soyadı verilən mətnə uyğun gələn tələbələrin yazıldığı kursları qaytarır."
    )
    public ResponseEntity<List<CourseResponseDto>> getCoursesByStudentName(@RequestParam String name) {
        return ResponseEntity.ok(courseService.getCoursesByStudentName(name));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Kursu ID-yə görə əldə etmək", description = "Verilən ID-yə uyğun bir kursu qaytarır.")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Kursu yeniləmək", description = "Verilən ID-yə uyğun kurs məlumatlarını yeniləyir.")
    public ResponseEntity<CourseResponseDto> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequestDto requestDto) {
        return ResponseEntity.ok(courseService.updateCourse(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Kursu silmək", description = "Verilən ID-yə uyğun kursu sistemdən silir.")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/students/{studentId}")
    @Operation(
            summary = "Tələbəni kursa yazmaq",
            description = "Tələbəni kursa yazır. Əvvəlcə tələbənin mövcudluğu, təkrar yazılma və prerequisite kurs tələbi yoxlanılır."
    )
    public ResponseEntity<EnrollmentResponseDto> enrollStudent(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        EnrollmentResponseDto responseDto = courseService.enrollStudent(courseId, studentId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{courseId}/students")
    @Operation(
            summary = "Kursdakı tələbələri əldə etmək",
            description = "Verilən kursa yazılmış tələbələrin ətraflı məlumatlarını student-service vasitəsilə qaytarır."
    )
    public ResponseEntity<CourseStudentsResponseDto> getCourseStudents(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseStudents(courseId));
    }
}
