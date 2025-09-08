package com.viettridao.vaccination.exception;

import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import org.springframework.http.HttpStatus;

/**
 * Global exception handler for the MVC application.
 * Using @ControllerAdvice to centralize exception handling
 * across all controllers in the project.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Utility method to build error page with error details.
     *
     * @param model   the Model to pass attributes to the view
     * @param error   the error title to display
     * @param message the error message details
     * @param view    the name of the error view template
     * @return the name of the view template
     */
    private String buildErrorPage(Model model, String error, String message, String view) {
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        return view;
    }

    /**
     * Handle 404 Not Found error when no handler is found.
     *
     * @param ex    the NoHandlerFoundException
     * @param model the Model to pass attributes to the view
     * @return the 404 error page
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(NoHandlerFoundException ex, Model model) {
        return buildErrorPage(model, "Không tìm thấy trang bạn yêu cầu.", ex.getMessage(), "error/404");
    }

    /**
     * Handle validation errors when using @Valid without BindingResult.
     *
     * @param ex    the MethodArgumentNotValidException
     * @param model the Model to pass attributes to the view
     * @return the validation error page
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(MethodArgumentNotValidException ex, Model model) {
        String errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("<br/>"));
        return buildErrorPage(model, "Dữ liệu không hợp lệ", errorMessages, "error/validation");
    }

    /**
     * Handle errors when form binding fails (e.g. wrong data type).
     *
     * @param ex    the BindException
     * @param model the Model to pass attributes to the view
     * @return the validation error page
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException ex, Model model) {
        String errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("<br/>"));
        return buildErrorPage(model, "Dữ liệu không hợp lệ", errorMessages, "error/validation");
    }
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        return buildErrorPage(model, "Dữ liệu không hợp lệ", ex.getMessage(), "error/validation");
    }

    

    /**
     * Handle constraint violations when using @RequestParam or @PathVariable.
     *
     * @param ex    the ConstraintViolationException
     * @param model the Model to pass attributes to the view
     * @return the validation error page
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolation(ConstraintViolationException ex, Model model) {
        String errorMessages = ex.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining("<br/>"));
        return buildErrorPage(model, "Vi phạm ràng buộc dữ liệu", errorMessages, "error/validation");
    }

    /**
     * Handle missing request parameters.
     *
     * @param ex    the MissingServletRequestParameterException
     * @param model the Model to pass attributes to the view
     * @return the 400 error page
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParam(MissingServletRequestParameterException ex, Model model) {
        String message = "Tham số '" + ex.getParameterName() + "' là bắt buộc.";
        return buildErrorPage(model, "Thiếu tham số", message, "error/400");
    }

    /**
     * Handle type mismatch errors (e.g. string passed for a number parameter).
     *
     * @param ex    the MethodArgumentTypeMismatchException
     * @param model the Model to pass attributes to the view
     * @return the 400 error page
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, Model model) {
        String message = "Tham số '" + ex.getName() + "' không đúng kiểu dữ liệu.";
        return buildErrorPage(model, "Sai kiểu dữ liệu", message, "error/400");
    }

    /**
     * Handle database constraint violations (e.g. unique key, foreign key).
     *
     * @param ex    the DataIntegrityViolationException
     * @param model the Model to pass attributes to the view
     * @return the validation error page
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(DataIntegrityViolationException ex, Model model) {
        return buildErrorPage(model, "Lỗi dữ liệu", "Dữ liệu vi phạm ràng buộc hệ thống.", "error/validation");
    }

    /**
     * Handle access denied errors when user does not have permission.
     *
     * @param ex    the AccessDeniedException
     * @param model the Model to pass attributes to the view
     * @return the 403 error page
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex, Model model) {
        return buildErrorPage(model, "Không có quyền truy cập", "Bạn không được phép vào trang này.", "error/403");
    }

    /**
     * Handle authentication errors (user not logged in).
     *
     * @param ex    the AuthenticationException
     * @param model the Model to pass attributes to the view
     * @return the 401 error page
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthentication(AuthenticationException ex, Model model) {
        return buildErrorPage(model, "Chưa đăng nhập", "Bạn cần đăng nhập để tiếp tục.", "error/401");
    }

    /**
     * Handle all uncaught exceptions as internal server errors.
     *
     * @param ex    the Exception
     * @param model the Model to pass attributes to the view
     * @return the 500 error page
     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public String handleGeneralException(Exception ex, Model model) {
//        return buildErrorPage(model, "Lỗi hệ thống", "Đã xảy ra lỗi không mong muốn. Vui lòng thử lại sau.", "error/500");
//    }
}
