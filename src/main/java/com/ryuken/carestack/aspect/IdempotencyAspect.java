package com.ryuken.carestack.aspect;

import tools.jackson.databind.ObjectMapper;
import com.ryuken.carestack.entity.IdempotencyRecord;
import com.ryuken.carestack.exception.IdempotencyConflictException;
import com.ryuken.carestack.repository.IdempotencyRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.MessageDigest;
import java.time.Instant;
import java.time.YearMonth;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class IdempotencyAspect {

    private final IdempotencyRepository idempotencyRepository;
    private final ObjectMapper objectMapper;

    @Around("@annotation(com.ryuken.carestack.annotation.Idempotent)")
    public Object aroundIdempotentMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = currentRequest();
        String idempotencyKey = request.getHeader("Idempotency-Key");

        if (idempotencyKey == null || idempotencyKey.isBlank()) {

            log.warn("No Idempotency-Key header present on call to {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        String requestHash = hash(objectMapper.writeValueAsString(joinPoint.getArgs()));
        Optional<IdempotencyRecord> existing = idempotencyRepository.findById(idempotencyKey);

        if (existing.isPresent()) {
            IdempotencyRecord record = existing.get();
            if (!record.getRequestHash().equals(requestHash)) {
                throw new IdempotencyConflictException(
                        "Idempotency-Key '" + idempotencyKey + "' was already used with a different request payload");
            }
            log.info("Replaying stored response for Idempotency-Key {}", idempotencyKey);
            Object cachedBody = objectMapper.readValue(record.getResponseBody(), Object.class);
            return ResponseEntity.status(record.getStatusCode()).body(cachedBody);
        }

        Object result = joinPoint.proceed();

        if (result instanceof ResponseEntity<?> responseEntity) {
            IdempotencyRecord record = new IdempotencyRecord(
                    idempotencyKey,
                    requestHash,
                    objectMapper.writeValueAsString(responseEntity.getBody()),
                    responseEntity.getStatusCode().value(),
                    Instant.now()
            );
            idempotencyRepository.save(record);
            log.info("Stored new idempotency record for key {}", idempotencyKey);
        }

        return result;
    }

    private HttpServletRequest currentRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("Idempotency aspect used outside of an HTTP request context");
        }
        return attributes.getRequest();
    }

    private String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for  (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to hash request payload", e);
        }
    }
}
