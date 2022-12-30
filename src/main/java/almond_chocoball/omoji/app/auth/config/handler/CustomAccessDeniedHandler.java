package almond_chocoball.omoji.app.auth.config.handler;

import almond_chocoball.omoji.app.common.dto.ErrorResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static almond_chocoball.omoji.app.common.utils.CustomObjectMapper.objectMapper;


@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler { //권한 없는 리소스에 접근 시

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {

        ErrorResponse apiResponse= new ErrorResponse(e.getMessage());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(apiResponse));
        writer.flush();
    }
}
