package almond_chocoball.omoji.app.auth.config.filter;

import almond_chocoball.omoji.app.common.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static almond_chocoball.omoji.app.common.utils.CustomObjectMapper.objectMapper;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (Exception e){
            setExceptionResponse(response, new ErrorResponse(e.getMessage()));
        }
    }

    private void setExceptionResponse(HttpServletResponse response, ErrorResponse apiResponse) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(apiResponse));
        writer.flush();
    }
}
