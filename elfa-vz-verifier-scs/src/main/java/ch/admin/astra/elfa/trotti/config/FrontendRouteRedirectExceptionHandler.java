package ch.admin.astra.elfa.trotti.config;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Set;

@RequiredArgsConstructor
@ControllerAdvice
public class FrontendRouteRedirectExceptionHandler extends ResponseEntityExceptionHandler {

    private final Set<String> nonFrontendRootPathParts = Set.of("api", "actuator", "health");

    /**
     * This handler makes sure that direct navigation to a route of a single page application is forwarded to index.html.
     * When then navigating inside the SPA in the browser, this handler is not invoked as the SPA code in the browser
     * takes care of route navigation.
     */
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(@NotNull NoResourceFoundException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        // If the request looks like a frontend route, return index.html
        if (mightBeFrontendRoute(request)) {
            return new ResponseEntity<>(new ClassPathResource("/static/index.html"), headers, 200);
        }
        // Otherwise, generate a 404 NOT FOUND response
        return super.handleNoResourceFoundException(ex, headers, status, request);
    }

    public boolean mightBeFrontendRoute(WebRequest webRequest) {
        if (webRequest instanceof ServletWebRequest servletRequest && servletRequest.getRequest().getServletPath() != null) {
            String path = servletRequest.getRequest().getServletPath();
            // Does the request look like a file with a dot extension?
            boolean hasFileExtension = path.contains(".");
            if (hasFileExtension) {
                return false;
            }

            // Does the request contain a known non-frontend path part such as api, actuator, ...
            String[] pathSegments = path.replaceFirst("^/", "").split("/", 2);
            String rootPath = pathSegments[0].toLowerCase();

            return nonFrontendRootPathParts.stream()
                    .noneMatch(rootPath::contains);
        }
        return false;
    }

}


