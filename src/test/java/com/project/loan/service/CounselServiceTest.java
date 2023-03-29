package com.project.loan.service;

import static org.mockito.Mockito.when;
import com.project.loan.domain.Counsel;
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

        Assertions.assertThat(actual.getName()).isSameAs(entity.getName());

    }
}