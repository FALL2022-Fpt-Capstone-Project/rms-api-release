package vn.com.fpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.entity.MoneySource;
import vn.com.fpt.entity.RoomBill;
import vn.com.fpt.entity.ServiceBill;
import vn.com.fpt.entity.TableChangeLog;
import vn.com.fpt.repositories.TableChangeLogRepository;
import vn.com.fpt.security.domain.AccountAuthenticationDetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static vn.com.fpt.common.constants.ConfigVariable.*;

@Component
@RequiredArgsConstructor
@Log4j2
public class TableLogComponent {

    private final TableChangeLogRepository billChangeLogRepo;
    private final ObjectMapper objectMapper;

    @Transactional
    public void addEvent(List<AddLog> addLog) throws JsonProcessingException {
        List<TableChangeLog> var1 = new ArrayList<>(Collections.emptyList());
        for (AddLog al : addLog) {
            var1.add(TableChangeLog.add(
                    al.getProperty(),
                    "",
                    al.getNewValue(),
                    al.getTableName(),
                    al.getUsername(),
                    al.getId(),
                    INSERT
            ));
        }
        log.info(objectMapper.writeValueAsString(billChangeLogRepo.saveAll(var1)));
    }

    @Transactional
    public void updateEvent(List<UpdateLog> updateLogs) throws JsonProcessingException {
        List<TableChangeLog> var1 = new ArrayList<>(Collections.emptyList());
        for (UpdateLog ul : updateLogs) {
            var1.add(TableChangeLog.add(
                    ul.getProperty(),
                    ul.getOldValue(),
                    ul.getNewValue(),
                    ul.getTableName(),
                    ul.getUsername(),
                    ul.getId(),
                    INSERT
            ));
        }
        log.info(objectMapper.writeValueAsString(billChangeLogRepo.saveAll(var1)));
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

    @SneakyThrows
    @Transactional
    public void saveRoomBillHistory(RoomBill roomBill){
        addEvent(
                List.of(
                        new AddLog(RoomBill.TABLE_NAME,
                                roomBill.getId(),
                                "contract_id",
                                String.valueOf(roomBill.getId()),
                                Operator.operatorName()),
                        new AddLog(
                                RoomBill.TABLE_NAME,
                                roomBill.getId(),
                                "group_contract_id",
                                String.valueOf(roomBill.getGroupContractId()),
                                Operator.operatorName()),
                        new AddLog(
                                RoomBill.TABLE_NAME,
                                roomBill.getId(),
                                "group_id",
                                String.valueOf(roomBill.getGroupId()),
                                Operator.operatorName()),
                        new AddLog(
                                RoomBill.TABLE_NAME,
                                roomBill.getId(),
                                "room_id",
                                String.valueOf(roomBill.getId()),
                                Operator.operatorName()),
                        new AddLog(
                                RoomBill.TABLE_NAME,
                                roomBill.getId(),
                                "room_total_money",
                                String.valueOf(roomBill.getRoomTotalMoney()),
                                Operator.operatorName()),
                        new AddLog(
                                RoomBill.TABLE_NAME,
                                roomBill.getId(),
                                "payment_cycle",
                                String.valueOf(roomBill.getPaymentCycle()),
                                Operator.operatorName()),
                        new AddLog(
                                RoomBill.TABLE_NAME,
                                roomBill.getId(),
                                "note",
                                roomBill.getNote(),
                                Operator.operatorName())
                )
        );
    }

    @SneakyThrows
    @Transactional
    public void saveChangeRoomBillHistory(RoomBill old, RoomBill neww) {
        List<UpdateLog> updateLogs = new ArrayList<>(Collections.emptyList());
        if (!old.getRoomTotalMoney().equals(neww.getRoomTotalMoney())) {
            updateLogs.add(
                    new UpdateLog(
                            RoomBill.TABLE_NAME,
                            old.getId(),
                            "room_total_money",
                            Operator.operatorName(),
                            String.valueOf(old.getRoomTotalMoney()),
                            String.valueOf(neww.getRoomTotalMoney())

                    )
            );
        }
        if (!old.getPaymentCycle().equals(neww.getPaymentCycle())) {
            updateLogs.add(
                    new UpdateLog(
                            RoomBill.TABLE_NAME,
                            old.getId(),
                            "payment_cycle",
                            Operator.operatorName(),
                            String.valueOf(old.getPaymentCycle()),
                            String.valueOf(neww.getPaymentCycle())

                    )
            );
        }
        if (!old.getNote().equals(neww.getNote())) {
            updateLogs.add(
                    new UpdateLog(
                            RoomBill.TABLE_NAME,
                            old.getId(),
                            "note",
                            Operator.operatorName(),
                            old.getNote(),
                            neww.getNote()

                    )
            );
        }

    }

    @SneakyThrows
    @Transactional
    public void saveMoneySourceHistory(MoneySource request){
        addEvent(
                List.of(new AddLog(
                                MoneySource.TABLE_NAME,
                                request.getId(),
                                "money_source_description",
                                String.valueOf(request.getDescription()),
                                Operator.operatorName()),
                        new AddLog(
                                MoneySource.TABLE_NAME,
                                request.getId(),
                                "money_source_total_money",
                                String.valueOf(request.getTotalMoney()),
                                Operator.operatorName()),
                        new AddLog(
                                MoneySource.TABLE_NAME,
                                request.getId(),
                                "money_source_type",
                                String.valueOf(request.getMoneyType()),
                                Operator.operatorName()),
                        new AddLog(
                                MoneySource.TABLE_NAME,
                                request.getId(),
                                "money_source_time",
                                String.valueOf(request.getMoneySourceTime()),
                                Operator.operatorName())
                )
        );
    }

    @SneakyThrows
    @Transactional
    public void saveServiceBillSourceHistory(List<ServiceBill> list){
        List<AddLog> addLogs = new ArrayList<>(Collections.emptyList());
        list.forEach(e -> {
            addLogs.add(
                    new AddLog(
                            ServiceBill.TABLE_NAME,
                            e.getId(),
                            "",
                            String.valueOf(""),
                            Operator.operatorName()
                    )
            );
            addLogs.add(
                    new AddLog(
                            ServiceBill.TABLE_NAME,
                            e.getId(),
                            "",
                            String.valueOf(""),
                            Operator.operatorName()
                    )
            );
            addLogs.add(
                    new AddLog(
                            ServiceBill.TABLE_NAME,
                            e.getId(),
                            "",
                            String.valueOf(""),
                            Operator.operatorName()
                    )
            );
            addLogs.add(
                    new AddLog(
                            ServiceBill.TABLE_NAME,
                            e.getId(),
                            "",
                            String.valueOf(""),
                            Operator.operatorName()
                    )
            );
            addLogs.add(
                    new AddLog(
                            ServiceBill.TABLE_NAME,
                            e.getId(),
                            "",
                            String.valueOf(""),
                            Operator.operatorName()
                    )
            );
            addLogs.add(
                    new AddLog(
                            ServiceBill.TABLE_NAME,
                            e.getId(),
                            "",
                            String.valueOf(""),
                            Operator.operatorName()
                    )
            );
            addLogs.add(
                    new AddLog(
                            ServiceBill.TABLE_NAME,
                            e.getId(),
                            "",
                            String.valueOf(""),
                            Operator.operatorName()
                    )
            );
            addLogs.add(
                    new AddLog(
                            ServiceBill.TABLE_NAME,
                            e.getId(),
                            "",
                            String.valueOf(""),
                            Operator.operatorName()
                    )
            );
            addLogs.add(
                    new AddLog(
                            ServiceBill.TABLE_NAME,
                            e.getId(),
                            "",
                            String.valueOf(""),
                            Operator.operatorName()
                    )
            );
        });
    }


}
