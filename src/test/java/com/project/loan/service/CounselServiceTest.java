package com.project.loan.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import com.project.loan.domain.Counsel;
import com.project.loan.exception.BaseException;
import com.project.loan.exception.ResultType;
import com.project.loan.repository.CounselRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static com.project.loan.dto.CounselDto.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CounselServiceTest {

    @InjectMocks CounselServiceImpl counselService;

    @Mock private CounselRepository counselRepository;
    @Spy private ModelMapper modelMapper;
    //mocking처리 보다는 각각다른 오브젝트 매핑 유틸상의 역할성을 하기 위해사

    @Test
    void Should_ReturnResponseOfNewCounselEntity_When_RequestCounsel() {
        Counsel entity = Counsel.builder()
                .name("Member Song")
                .cellPhone("010-1111-2222")
                .email("abc@naver.com")
                .memo("저는 대출을 받고 싶어요. 연락 바랍니다.")
                .zipCode("12345")
                .address("서울특별시 강북구 미아동")
                .addressDetail("101동 103호")
                .build();

        Request request = Request.builder()
                .name("Member Song")
                .cellPhone("010-1111-2222")
                .email("abc@naver.com")
                .memo("저는 대출을 받고 싶어요. 연락 바랍니다.")
                .zipCode("12345")
                .address("서울특별시 강북구 미아동")
                .addressDetail("101동 103호")
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);

        Response actual = counselService.create(request);

        assertThat(actual.getName()).isSameAs(entity.getName());
    }

    @Test
    void Should_ReturnResponseOfExistCounselEntity_When_RequestExistCounselId() {
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();

        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = counselService.get(findId);

        assertThat(actual.getCounselId()).isSameAs(findId);
    }

    @Test
    void Should_ThrowException_When_RequestNotExistCounselId() {
        Long findId = 2L;

        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

        org.junit.jupiter.api.Assertions.assertThrows(BaseException.class, () -> counselService.get(findId));
    }

    @Test
    void Should_ReturnUpdatedResponseOfExistCounselEntity_When_RequestUpdateExistCounselInfo() {
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .name("Member Song")
                .build();

        Request request = Request.builder()
                .name("Member Kim")
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = counselService.update(findId, request);

        assertThat(actual.getCounselId()).isSameAs(findId);
        assertThat(actual.getName()).isSameAs(request.getName());
    }

    @Test
    void Should_DeletedCounselEntity_When_RequestExistCounselInfo() {
        Long targetId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
        when(counselRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

        counselService.delete(targetId);

        assertThat(entity.getIsDeleted()).isSameAs(true);
    }
}