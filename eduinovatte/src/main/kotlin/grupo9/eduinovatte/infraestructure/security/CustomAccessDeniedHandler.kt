import org.springframework.security.web.access.AccessDeniedHandler
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: org.springframework.security.access.AccessDeniedException?
    ) {
        response?.status = HttpServletResponse.SC_UNAUTHORIZED
        response?.contentType = "application/json"
        response?.writer?.write("{\"error\": \"Access Denied\"}")
    }

}
