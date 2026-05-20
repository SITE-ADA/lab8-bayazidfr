package az.edu.ada.wm2.studentservice.controller;

import az.edu.ada.wm2.studentservice.model.dto.StudentRequestDto;
import az.edu.ada.wm2.studentservice.model.dto.StudentResponseDto;
import az.edu.ada.wm2.studentservice.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Tələbələr", description = "Tələbələrin idarə edilməsi üçün endpointlər")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @Operation(summary = "Yeni tələbə yaratmaq", description = "Sistemdə yeni tələbə məlumatı yaradır.")
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentRequestDto requestDto) {
        StudentResponseDto createdStudent = studentService.createStudent(requestDto);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Bütün tələbələri əldə etmək", description = "Sistemdə olan bütün tələbələri qaytarır.")
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/search")
    @Operation(
            summary = "Tələbələri ada görə axtarmaq",
            description = "Verilən mətnə görə adı və ya soyadı uyğun gələn tələbələri qaytarır."
    )
    public ResponseEntity<List<StudentResponseDto>> searchStudentsByName(@RequestParam String name) {
        return ResponseEntity.ok(studentService.searchStudentByName(name));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Tələbəni ID-yə görə əldə etmək", description = "Verilən ID-yə uyğun bir tələbəni qaytarır.")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Tələbəni yeniləmək", description = "Verilən ID-yə uyğun tələbə məlumatlarını yeniləyir.")
    public ResponseEntity<StudentResponseDto> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDto requestDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Tələbəni silmək", description = "Verilən ID-yə uyğun tələbəni sistemdən silir.")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
