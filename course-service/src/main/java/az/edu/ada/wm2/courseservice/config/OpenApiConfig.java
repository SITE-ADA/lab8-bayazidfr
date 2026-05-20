package az.edu.ada.wm2.courseservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Kurs Servisi API",
                version = "v1",
                description = "Kursların yaradılması, oxunması, yenilənməsi, silinməsi, tələbələrin kursa yazılması və kurs-tələbə əlaqələrinin idarə edilməsi üçün REST API.",
                contact = @Contact(name = "WM2 Backend Kursu"),
                license = @License(name = "Tədris məqsədli istifadə")
        ),
        servers = {
                @Server(url = "http://localhost:8081", description = "Lokal server")
        }
)
public class OpenApiConfig {
}
