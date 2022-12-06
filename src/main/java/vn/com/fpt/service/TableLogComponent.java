package vn.com.fpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.com.fpt.entity.TableChangeLog;
import vn.com.fpt.repositories.TableChangeLogRepository;
import vn.com.fpt.security.domain.AccountAuthenticationDetail;

import static vn.com.fpt.common.constants.ConfigVariable.*;

@Component
@RequiredArgsConstructor
@Log4j2
public class TableLogComponent {

    private final TableChangeLogRepository billChangeLogRepo;
    private final ObjectMapper objectMapper;

    @Transactional
    public void addEvent(String tableName,
                         Long tableId,
                         String property,
                         String newValue,
                         String userName) throws JsonProcessingException {
        var logs = TableChangeLog.add(
                property,
                null,
                newValue,
                tableName,
                userName,
                tableId,
                INSERT);
        log.info(objectMapper.writeValueAsString(billChangeLogRepo.save(logs)));
    }

    @Transactional
    public void updateEvent(Long tableId,
                            String tableName,
                            String property,
                            String oldValue,
                            String newValue,
                            Authentication authentication) throws JsonProcessingException {
        var operator = (AccountAuthenticationDetail) authentication.getPrincipal();
        var logs = TableChangeLog.add(
                property,
                oldValue,
                newValue,
                tableName,
                operator.getUsername(),
                tableId,
                UPDATE
        );
        log.info(objectMapper.writeValueAsString(billChangeLogRepo.save(logs)));
    }

    @Transactional
    public void deleteEvent(Long tableId,
                            String tableName,
                            String property,
                            String oldValue,
                            String newValue,
                            Authentication authentication) throws JsonProcessingException {
        var operator = (AccountAuthenticationDetail) authentication.getPrincipal();
        var logs = TableChangeLog.add(
                property,
                oldValue,
                newValue,
                tableName,
                operator.getUsername(),
                tableId,
                DELETE
        );
        log.info(objectMapper.writeValueAsString(billChangeLogRepo.save(logs)));
    }
}
