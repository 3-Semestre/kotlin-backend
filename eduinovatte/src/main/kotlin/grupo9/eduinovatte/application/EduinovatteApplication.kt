package grupo9.eduinovatte

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class EduinovatteApplication

fun main(args: Array<String>) {
	runApplication<EduinovatteApplication>(*args)
}