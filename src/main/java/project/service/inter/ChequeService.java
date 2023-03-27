package project.service.inter;

import project.dto.request.ChequeRequest;
import project.dto.response.ChequeResponse;
import project.dto.response.SimpleResponse;
import project.dto.response.SumAllChequeOfDay;

import java.time.LocalDate;
import java.util.List;

public interface ChequeService {
    SimpleResponse saveCheque(Long userId,ChequeRequest chequeRequest);

    SimpleResponse deleteCheque(Long chequeId);

    List<ChequeResponse> getAll(Long userId);

    SimpleResponse update(Long chequeId, ChequeRequest chequeRequest);

    SumAllChequeOfDay sumAllChequeOfDay(Long userId, LocalDate date);
}
