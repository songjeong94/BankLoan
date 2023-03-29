package com.project.loan.service;

import static com.project.loan.dto.CounselDto.*;

public interface CounselService {

    Response create(Request request);

    Response get(Long counselId);

    Response update(Long counselId, Request request);

    void delete(Long counselId);

}
