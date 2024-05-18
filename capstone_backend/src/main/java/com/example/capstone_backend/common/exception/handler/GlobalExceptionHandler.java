package com.example.capstone_backend.common.exception.handler;

import com.example.capstone_backend.common.Response;
import com.example.capstone_backend.domain.account.exception.AlreadyExistEmailException;
import com.example.capstone_backend.domain.account.exception.CheckEmailOrPasswordException;
import com.example.capstone_backend.domain.account.exception.ForbiddenException;
import com.example.capstone_backend.domain.account.exception.UserPermissionDeniedException;
import com.example.capstone_backend.domain.community.exception.ContentsNotFoundException;
import com.example.capstone_backend.domain.fileserver.exception.*;
import com.example.capstone_backend.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    // BAD REQUEST 400
    @ExceptionHandler({AlreadyExistEmailException.class})
    public ResponseEntity<?> handleAlreadyExistEmailException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler({CheckEmailOrPasswordException.class})
    public ResponseEntity<?> handleCheckEmailOrPasswordException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({InvalidFileTypeException.class})
    public ResponseEntity<?> handleInvalidFileTypeException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({InvalidImageTypeException.class})
    public ResponseEntity<?> handleInvalidImageTypeException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({InvalidVideoTypeException.class})
    public ResponseEntity<?> handleInvalidVideoTypeException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({NotFoundImageException.class})
    public ResponseEntity<?> handleNotFoundImageException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({NotFoundVideoException.class})
    public ResponseEntity<?> handleNotFoundVideoException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // 403
    @ExceptionHandler({UserPermissionDeniedException.class})
    public ResponseEntity<?> handleShelterPermissionDeniedException(final Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Response.error(e.getMessage(), HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<?> handleForbiddenExceptionn(final Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Response.error(e.getMessage(), HttpStatus.FORBIDDEN));
    }

    // 404
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<?> handlePetNotFoundException(final Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(e.getMessage(), HttpStatus.NOT_FOUND));
    }
    @ExceptionHandler({ContentsNotFoundException.class})
    public ResponseEntity<?> handleShelterNotFoundException(final Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<?> handleMaxUploadSizeExceededException(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(Response.error(e.getMessage(), HttpStatus.PAYLOAD_TOO_LARGE));
    }

    // INTERNAL SERVER ERROR 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류", content = @Content)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> internalServerError(final Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
//TODO: AmazonS3 연결 후 활성화
//    @ExceptionHandler({AmazonS3SaveError.class})
//    public ResponseEntity<?> handleAmazonS3SaveException(final Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
//    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class,

            ConstraintViolationException.class
    })
    public ResponseEntity<?> badRequest(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            MissingRequestValueException.class
    })
    public ResponseEntity<?> badRequest(final ServletException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> badRequest(final BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> notFound(final ServletException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(e.getMessage(), HttpStatus.NOT_FOUND));
    }

}
